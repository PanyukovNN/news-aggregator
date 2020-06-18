package com.zylex.newsagregator.repository;

import com.zylex.newsagregator.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsEntryRepository extends JpaRepository<News, Long> {
}
