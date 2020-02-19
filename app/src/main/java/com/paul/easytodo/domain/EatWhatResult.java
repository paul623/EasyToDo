package com.paul.easytodo.domain;

import java.util.List;

public class EatWhatResult {

    /**
     * code : 1
     * message : 获取成功
     * dataBean : [{"location":1,"id":1,"time":0,"addTime":"2020-02-18 20:50:02","food":"等待投食","author":"Paul"},{"location":1,"id":5,"time":2,"addTime":"2020-02-18 21:53:14","food":"瓦香鸡","author":"Paul"},{"location":1,"id":1,"time":0,"addTime":"2020-02-18 20:50:02","food":"等待投食","author":"Paul"}]
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
         * location : 1
         * id : 1
         * time : 0
         * addTime : 2020-02-18 20:50:02
         * food : 等待投食
         * author : Paul
         */

        private int location;
        private int id;
        private int time;
        private String addTime;
        private String food;
        private String author;

        /**
         *  * 0 家（不在学校）
         *   * 1 学校
         *   * 2 任意
         * */
        public String getLoc(){
            switch (location){
                case 0:
                    return "家";
                case 1:
                    return "学校";
                case 2:
                    return "任意";
                    default:
                        return "";
            }
        }

        public int getLocation() {
            return location;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getFood() {
            return food;
        }

        public void setFood(String food) {
            this.food = food;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }
    }
}
