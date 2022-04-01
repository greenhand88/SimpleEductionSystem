package com.example.blog.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@ApiModel
public class Article {
    String id;
    String author;
    String title;
    Date uploadTime;
}
