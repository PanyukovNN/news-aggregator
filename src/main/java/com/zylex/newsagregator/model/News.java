package com.zylex.newsagregator.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime dateTime;

    private String title;

    @ElementCollection
    private List<String> categories = new ArrayList<>();

    private String content;

    private String link;

    @ElementCollection
    private List<String> photoLinks = new ArrayList<>();

    private int views;

    public News() {
    }

    public News(LocalDateTime dateTime, String title, List<String> categories, String content, String link, List<String> photoLinks, int views) {
        this.dateTime = dateTime;
        this.title = title;
        this.categories = categories;
        this.content = content;
        this.link = link;
        this.photoLinks = photoLinks;
        this.views = views;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String header) {
        this.title = header;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getPhotoLinks() {
        return photoLinks;
    }

    public void setPhotoLinks(List<String> photoLinks) {
        this.photoLinks = photoLinks;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(dateTime, news.dateTime) &&
                Objects.equals(title, news.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, title);
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", header='" + title + '\'' +
                ", categories=" + categories +
                ", content='" + content + '\'' +
                ", link='" + link + '\'' +
                ", photoLinks=" + photoLinks +
                ", views=" + views +
                '}';
    }
}
