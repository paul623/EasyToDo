package com.paul.easytodo.domain;

public class WordsResult {
    /**
     * type : 1
     * content : 那年我二十岁，每周二晚坐免费公交去旧金山。经过跨海大桥可以看到对面的灯，城市辉煌的灯。游客会多看几眼这些灯。可是我不敢看。我的灯在更远的对岸
     * id : 1
     * author : 账号已注销
     * addtime : 2020-02-17 10:47:54
     */

    private int type;
    private String content;
    private int id;
    private String author;
    private String addtime;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
