package com.example.blog.dao.Mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

public interface ArticleMapper {
    /**
     * Create a new article
     *
     * @param title
     * @param uploadTime
     * @param userName
     */
    @Insert("insert into article(title,time,author) values (#{title},#{uploadTime},#{userName})")
    public void addArticle(String title, String uploadTime, String userName);
}
