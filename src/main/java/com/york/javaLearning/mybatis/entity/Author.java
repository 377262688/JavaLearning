package com.york.javaLearning.mybatis.entity;

import com.york.javaLearning.mybatis.enums.SexEnum;

import java.util.List;

/**
 * @author york
 * @create 2020-07-14 09:33
 **/
public class Author {
    private Integer id;
    private String name;
    private Integer age;
    private SexEnum sex;
    private String email;
    private List<ArticleDO> articles;
}
