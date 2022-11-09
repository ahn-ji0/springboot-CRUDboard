package com.mustache.bbspractice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    public ArticleDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Article toEntity(){
        return new Article(title,content);
    }
    public Article toEntity(Long id){
        return new Article(id, title,content);
    }

}
