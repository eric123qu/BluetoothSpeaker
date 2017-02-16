package com.haier.ai.bluetoothspeaker.bean.Oilprice;

import java.util.List;

/**
 * Created by qx on 17-2-16.
 */

public class ResponseOilprice {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170216183905958000155566
     * data : {"domain":"oilprice","data":[{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":1,"price":"6.0","type":"1"},{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":7,"price":"6.50","type":"0"},{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":12,"price":"6.0","type":"2"}]}
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
         * domain : oilprice
         * data : [{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":1,"price":"6.0","type":"1"},{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":7,"price":"6.50","type":"0"},{"area":"北京","createTime":"2017-02-16 13:36:00.0","date":"","id":12,"price":"6.0","type":"2"}]
         */

        private String domain;
        private List<DataBean> data;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * area : 北京
             * createTime : 2017-02-16 13:36:00.0
             * date :
             * id : 1
             * price : 6.0
             * type : 1
             */

            private String area;
            private String createTime;
            private String date;
            private int id;
            private String price;
            private String type;

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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
