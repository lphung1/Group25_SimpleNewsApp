package com.example.loiphung.group25_inclass05;

/**
 * Created by LoiPhung on 2/19/18.
 */

public class Source {
    public String id, name, url, description;
    public Article article = null;

    public Source() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
