package com.example.blog.controller;

import com.example.blog.VO.ArticleInfo;
import com.example.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@RequestMapping("/blog")
@Controller
public class BlogController {
    @Autowired
    BlogService blogService;

    @PostMapping("/addArticle")
    @ResponseBody
    public String addArticle(@RequestBody ArticleInfo articleInfo) {
        return blogService.addArticle(articleInfo.getTitle(), articleInfo.getAuthor());
    }
}
