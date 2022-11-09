package com.mustache.bbspractice.controller;

import com.mustache.bbspractice.domain.dto.Article;
import com.mustache.bbspractice.domain.dto.ArticleDto;
import com.mustache.bbspractice.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public String list(){
        return "redirect:/articles/list";
    }

    @GetMapping("/new")
    public String createArticles(){
        return "articles/new";
    }

    @PostMapping("/post")
    public String postArticles(ArticleDto articleDto){
        log.info(articleDto.getTitle());
        log.info(articleDto.getContent());
        Article savedArticle = articleDto.toEntity();
        articleRepository.save(savedArticle);
        return String.format("redirect:/articles/%d",savedArticle.getId());
    }

    @GetMapping("/{id}/edit")
    public String editSingle(@PathVariable Long id, Model model){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isEmpty()){
            model.addAttribute("article",optionalArticle.get());
            return "articles/edit";
        }
        else{
            return "articles/error";
        }
    }
    @PostMapping("/{id}/update")
    public String updateSingle(@PathVariable Long id, ArticleDto articleDto){
        log.info("title:{} content:{}",articleDto.getTitle(),articleDto.getContent());
        Article savedArticle = articleDto.toEntity(id);
        articleRepository.save(savedArticle);
        return "redirect:/articles/list";
    }
    @GetMapping("/{id}")
    public String selectSingle(@PathVariable Long id, Model model){
        Optional<Article> optionalArticle = articleRepository.findById(id);
        if(!optionalArticle.isEmpty()) {
            model.addAttribute("article",optionalArticle.get());
            return "articles/show";
        }
        else {
            return "articles/error";
        }
    }

    @GetMapping("/list")
    public String listAll(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "articles/list";
    }
}
