package com.paul.easytodo.domain;

import java.util.List;

public class WordsAllResult {

    /**
     * code : 1
     * message : 获取全部句子成功
     * dataBean : [{"type":1,"content":"那年我二十岁，每周二晚坐免费公交去旧金山。经过跨海大桥可以看到对面的灯，城市辉煌的灯。游客会多看几眼这些灯。可是我不敢看。我的灯在更远的对岸","id":1,"author":"账号已注销","addtime":"2020-02-17 10:47:54"},{"type":1,"content":"有90天 考研，听着安静的音乐在背单词，还是忍不住拿起手机留下这样一段话:你要努力改变现在的生活，似乎只有这一个途径，并且没有退路了。加油啊 我的我","id":2,"author":"鳗鱼不在雪国","addtime":"2020-02-17 10:49:07"},{"type":1,"content":"阳光，风，还有你，真好","id":3,"author":"导演我躺在这行吗","addtime":"2020-02-17 10:50:15"}]
     */

    private int code;
    private String message;
    private List<DataBeanBean> dataBean;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBeanBean> getDataBean() {
        return dataBean;
    }

    public void setDataBean(List<DataBeanBean> dataBean) {
        this.dataBean = dataBean;
    }

    public static class DataBeanBean {
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
}
