package com.mustache.bbspractice.repository;

import com.mustache.bbspractice.domain.dto.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
