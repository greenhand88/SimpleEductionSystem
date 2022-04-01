package com.example.blog.service;

import com.example.blog.dao.Mappers.ArticleMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BlogService {
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * @param title
     * @param author
     * @return
     */
    public String addArticle(String title, String author) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        articleMapper.addArticle(title, date, author);
        return "True";
    }
}
