package com.mustache.bbspractice.controller;

import com.mustache.bbspractice.domain.dto.Article;
import com.mustache.bbspractice.domain.dto.ArticleDto;
import com.mustache.bbspractice.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/articles")
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;

    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/new")
    public String createArticles(){
        return "new";
    }

    @PostMapping("/post")
    public String articles(ArticleDto articleDto){
        log.info(articleDto.getTitle());
        log.info(articleDto.getContent());
        Article savedArticle = articleDto.toEntity();
        articleRepository.save(savedArticle);
        return String.format("redirect:/articles/%d",savedArticle.getId());
    }

    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isEmpty()) {
            model.addAttribute("article",optionalArticle.get());
            return "show";
        }
        else {
            return "error";
        }
    }

    @GetMapping("")
    public String list(){
        return "redirect:/articles/list";
    }
    @GetMapping("/list")
    public String list(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "list";
    }
}
