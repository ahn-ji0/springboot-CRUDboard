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

    //새로운 article 작성 관련
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

    //기존 article 수정 관련
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

    //id에 해당하는 게시물 지우기 관련
    @GetMapping("/{id}/delete")
    public String deleteSingle(@PathVariable Long id, Model model){
        articleRepository.deleteById(id);
        return "redirect:/articles/list";
    }

    //id에 해당하는 특정 게시물 가져오기 관련
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

    //모든 게시물 가져오기 관련
    @GetMapping("/list")
    public String listAll(Model model){
        List<Article> articleList = articleRepository.findAll();
        model.addAttribute("articleList",articleList);
        return "articles/list";
    }

}
