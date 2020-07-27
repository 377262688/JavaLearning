package com.york.javaLearning.mybatis.entity;

import com.york.javaLearning.mybatis.enums.ArticleTypeEnum;

import java.util.Date;

/**
 * @author york
 * @create 2020-07-14 09:33
 **/
public class Article {

    private Integer id;
    private String title;
    private ArticleTypeEnum type;
    private AuthorDO author;
    private String content;
    private Date createTime;

}
