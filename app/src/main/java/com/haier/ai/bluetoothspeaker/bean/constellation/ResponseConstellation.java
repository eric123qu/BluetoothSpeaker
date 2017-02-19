package com.haier.ai.bluetoothspeaker.bean.constellation;

/**
 * Created by qx on 17-2-15.
 */

public class ResponseConstellation {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170219104920020000723452
     * data : {"all":"40%","love":"40%","name":"狮子座","color":"白色","money":"40%","QFriend":"天秤座","work":"40%","number":4,"datetime":"2017年02月19日","health":"70%","date":20170219,"summary":"今天狮子在处理一般常规工作时容易有创意的完成，不过这些创意有时候需要经过一番唇枪舌战才会有灵感的激发，不过我们大狮子一向是不管别人说什么，最终还是你说说了算。","domain":"constellation"}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataEntity data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity {
        /**
         * all : 40%
         * love : 40%
         * name : 狮子座
         * color : 白色
         * money : 40%
         * QFriend : 天秤座
         * work : 40%
         * number : 4
         * datetime : 2017年02月19日
         * health : 70%
         * date : 20170219
         * summary : 今天狮子在处理一般常规工作时容易有创意的完成，不过这些创意有时候需要经过一番唇枪舌战才会有灵感的激发，不过我们大狮子一向是不管别人说什么，最终还是你说说了算。
         * domain : constellation
         */

        private String all;
        private String love;
        private String name;
        private String color;
        private String money;
        private String QFriend;
        private String work;
        private int number;
        private String datetime;
        private String health;
        private int date;
        private String summary;
        private String domain;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getLove() {
            return love;
        }

        public void setLove(String love) {
            this.love = love;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getQFriend() {
            return QFriend;
        }

        public void setQFriend(String QFriend) {
            this.QFriend = QFriend;
        }

        public String getWork() {
            return work;
        }

        public void setWork(String work) {
            this.work = work;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getHealth() {
            return health;
        }

        public void setHealth(String health) {
            this.health = health;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }
    }
}
