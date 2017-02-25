package com.haier.ai.bluetoothspeaker.bean.menu;

/**
 * Created by qx on 17-2-25.
 */

public class ResponseMenu {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170225111155670000400838
     * data : {"name":"宫保鸡丁","desc":"鸡胸肉切丁。用料酒，食用油，白胡椒，盐，淀粉，腌十分钟。鸡丁炒至变白，放入干辣椒，葱和花椒粉，炒出香味，对入料汁，大火炒到粘稠干松。关火，拌入花生米。\r\n","data":{"createTime":"","desc":"鸡胸肉切丁。用料酒，食用油，白胡椒，盐，淀粉，腌十分钟。鸡丁炒至变白，放入干辣椒，葱和花椒粉，炒出香味，对入料汁，大火炒到粘稠干松。关火，拌入花生米。\r\n","flavor":"","id":7,"name":"宫保鸡丁","type":""}}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * name : 宫保鸡丁
         * desc : 鸡胸肉切丁。用料酒，食用油，白胡椒，盐，淀粉，腌十分钟。鸡丁炒至变白，放入干辣椒，葱和花椒粉，炒出香味，对入料汁，大火炒到粘稠干松。关火，拌入花生米。

         * data : {"createTime":"","desc":"鸡胸肉切丁。用料酒，食用油，白胡椒，盐，淀粉，腌十分钟。鸡丁炒至变白，放入干辣椒，葱和花椒粉，炒出香味，对入料汁，大火炒到粘稠干松。关火，拌入花生米。\r\n","flavor":"","id":7,"name":"宫保鸡丁","type":""}
         */

        private String name;
        private String desc;
        private DataBean data;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * createTime :
             * desc : 鸡胸肉切丁。用料酒，食用油，白胡椒，盐，淀粉，腌十分钟。鸡丁炒至变白，放入干辣椒，葱和花椒粉，炒出香味，对入料汁，大火炒到粘稠干松。关火，拌入花生米。

             * flavor :
             * id : 7
             * name : 宫保鸡丁
             * type :
             */

            private String createTime;
            private String desc;
            private String flavor;
            private int id;
            private String name;
            private String type;

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getFlavor() {
                return flavor;
            }

            public void setFlavor(String flavor) {
                this.flavor = flavor;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
