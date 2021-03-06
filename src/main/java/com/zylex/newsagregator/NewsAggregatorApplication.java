package com.zylex.newsagregator;

import com.zylex.newsagregator.service.BnkParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class NewsAggregatorApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(NewsAggregatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        context.getBean(BnkParser.class).parse();
    }
}
