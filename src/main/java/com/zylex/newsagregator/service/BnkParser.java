package com.zylex.newsagregator.service;

import com.zylex.newsagregator.exception.BnkParserException;
import com.zylex.newsagregator.model.News;
import com.zylex.newsagregator.repository.NewsEntryRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BnkParser {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy");

    private final NewsEntryRepository newsEntryRepository;

    public BnkParser(NewsEntryRepository newsEntryRepository) {
        this.newsEntryRepository = newsEntryRepository;
    }

    public void parse() {
        try {
            Document document = connectToSite("https://www.bnkomi.ru/");

            Elements newsItemElements = document.select("div.item");

            for (Element newsItemElement : newsItemElements) {
                News news = extractTodayNews(newsItemElement);
                if (news.getTitle() != null) {
                    newsEntryRepository.save(news);
                }
            }

//            Element newsElement = document.selectFirst("div.b-news-single");
//            Element title = newsElement.selectFirst("h2");
//
//            Elements newsContent = newsElement.select("div.daGallery > p");
//            List<String> paragraphs = new ArrayList<>();
//            for (Element paragraph : newsContent) {
//                if (!paragraph.text().isEmpty()) {
//                    paragraphs.add(paragraph.text());
//                }
//            }
//            paragraphs.forEach(System.out::println);
        } catch (IOException e) {
            throw new BnkParserException(e.getMessage(), e);
        }
    }

    private News extractTodayNews(Element newsItemElement) {
        News news = new News();
        Element dateCategoryElement = newsItemElement.selectFirst("div.date");

        if (dateCategoryElement == null) {
            return news;
        }

        String[] dateCategory = dateCategoryElement.text().split(" / ");
        LocalDateTime dateTime = LocalDateTime.parse(dateCategory[0], formatter);
        news.setDateTime(dateTime);

        String category = dateCategory[1];
        news.getCategories().add(category);

        Element titleElement = newsItemElement.selectFirst("h2.title");
        String title = titleElement.text();
        news.setTitle(title);

        Element linkElement = titleElement.selectFirst("a");
        String link = "https://www.bnkomi.ru" + linkElement.attr("href");
        news.setLink(link);

        Element photoElement = newsItemElement.selectFirst("div.daGallery > div.pic-container > img.pic");
        if (photoElement != null) {
            String photoLink = "https://www.bnkomi.ru" + photoElement.attr("src");
            news.getPhotoLinks().add(photoLink);
        } else {
            // Разобраться с видео!
//                Element videoElement = newsItemElement.selectFirst("div.daGallery > p > iframe")
        }

        return news;
    }

    private static Document connectToSite(String link) throws IOException {
        return Jsoup.connect(link)
                .userAgent("Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();
    }
}
