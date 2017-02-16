package com.haier.ai.bluetoothspeaker.bean.box;

import java.util.List;

/**
 * Created by qx on 17-2-10.
 */

public class boxNluBean {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170210101915873000469234
     * data : {"query":"打开空调","type":"air","contextid":"","semantic":{"client_type_id":100,"device_id":"","device_type_id":0,"domain":"Air_conditioner","intent":"开关控制","is_dialog":false,"parser_text":"{ops} 空调 ","row_text":"打开空调","user_id":"000000000000","paras":[{"key":"{ops}","value":"打开"}]},"result":{"res_text":"我是测试返回语句，稍后修正！","res_type":"","res_down":""}}
     */

    private String retCode;
    private String retInfo;
    private String sn;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * query : 打开空调
         * type : air
         * contextid :
         * semantic : {"client_type_id":100,"device_id":"","device_type_id":0,"domain":"Air_conditioner","intent":"开关控制","is_dialog":false,"parser_text":"{ops} 空调 ","row_text":"打开空调","user_id":"000000000000","paras":[{"key":"{ops}","value":"打开"}]}
         * result : {"res_text":"我是测试返回语句，稍后修正！","res_type":"","res_down":""}
         */

        private String query;
        private String type;
        private String contextid;
        private SemanticBean semantic;
        private ResultBean result;

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContextid() {
            return contextid;
        }

        public void setContextid(String contextid) {
            this.contextid = contextid;
        }

        public SemanticBean getSemantic() {
            return semantic;
        }

        public void setSemantic(SemanticBean semantic) {
            this.semantic = semantic;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class SemanticBean {
            /**
             * client_type_id : 100
             * device_id :
             * device_type_id : 0
             * domain : Air_conditioner
             * intent : 开关控制
             * is_dialog : false
             * parser_text : {ops} 空调
             * row_text : 打开空调
             * user_id : 000000000000
             * paras : [{"key":"{ops}","value":"打开"}]
             */

            private int client_type_id;
            private String device_id;
            private int device_type_id;
            private String domain;
            private String intent;
            private boolean is_dialog;
            private String parser_text;
            private String row_text;
            private String user_id;
            private String response;
            private List<ParasBean> paras;

            public String getResponse() {
                return response;
            }

            public void setResponse(String response) {
                this.response = response;
            }

            public int getClient_type_id() {
                return client_type_id;
            }

            public void setClient_type_id(int client_type_id) {
                this.client_type_id = client_type_id;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public int getDevice_type_id() {
                return device_type_id;
            }

            public void setDevice_type_id(int device_type_id) {
                this.device_type_id = device_type_id;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getIntent() {
                return intent;
            }

            public void setIntent(String intent) {
                this.intent = intent;
            }

            public boolean isIs_dialog() {
                return is_dialog;
            }

            public void setIs_dialog(boolean is_dialog) {
                this.is_dialog = is_dialog;
            }

            public String getParser_text() {
                return parser_text;
            }

            public void setParser_text(String parser_text) {
                this.parser_text = parser_text;
            }

            public String getRow_text() {
                return row_text;
            }

            public void setRow_text(String row_text) {
                this.row_text = row_text;
            }

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public List<ParasBean> getParas() {
                return paras;
            }

            public void setParas(List<ParasBean> paras) {
                this.paras = paras;
            }

            public static class ParasBean {
                /**
                 * key : {ops}
                 * value : 打开
                 */

                private String key;
                private String value;

                public String getKey() {
                    return key;
                }

                public void setKey(String key) {
                    this.key = key;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }

        public static class ResultBean {
            /**
             * res_text : 我是测试返回语句，稍后修正！
             * res_type :
             * res_down :
             */

            private String res_text;
            private String res_type;
            private String res_down;

            public String getRes_text() {
                return res_text;
            }

            public void setRes_text(String res_text) {
                this.res_text = res_text;
            }

            public String getRes_type() {
                return res_type;
            }

            public void setRes_type(String res_type) {
                this.res_type = res_type;
            }

            public String getRes_down() {
                return res_down;
            }

            public void setRes_down(String res_down) {
                this.res_down = res_down;
            }
        }
    }
}
