package com.haier.ai.bluetoothspeaker.bean.weather;

/**
 * Created by qx on 17-2-15.
 */

public class ResponseWeather {

    /**
     * retCode : 00000
     * retInfo : 操作成功
     * sn : 20170221133154389000839221
     * data : {"id":1,"date":"2017-02-20 17:21:12.0","city":"北京","time":null,"temperature":"3","humidity":"13%","info":"说明","winddirect":"东北风","windpower":"2级","chuanyi":"较冷，建议着大衣、呢外套加毛衣、卫衣等服装。","ganmao":"极易发，将有一次强降温过程，且风力较强，极易发生感冒，请特别注意增加衣服保暖防寒。","kongtiao":"少开。","wuran":"无污染。","xiche":"较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。","yundong":"天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。","ziwanxian":"属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。"}
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
         * id : 1
         * date : 2017-02-20 17:21:12.0
         * city : 北京
         * time : null
         * temperature : 3
         * humidity : 13%
         * info : 说明
         * winddirect : 东北风
         * windpower : 2级
         * chuanyi : 较冷，建议着大衣、呢外套加毛衣、卫衣等服装。
         * ganmao : 极易发，将有一次强降温过程，且风力较强，极易发生感冒，请特别注意增加衣服保暖防寒。
         * kongtiao : 少开。
         * wuran : 无污染。
         * xiche : 较不宜洗车，未来一天无雨，风力较大，如果执意擦洗汽车，要做好蒙上污垢的心理准备。
         * yundong : 天气较好，但考虑风力较大，天气寒冷，推荐您进行室内运动，若在户外运动须注意保暖。
         * ziwanxian : 属弱紫外线辐射天气，无需特别防护。若长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
         */

        private int id;
        private String date;
        private String city;
        private Object time;
        private String temperature;
        private String humidity;
        private String info;
        private String winddirect;
        private String windpower;
        private String chuanyi;
        private String ganmao;
        private String kongtiao;
        private String wuran;
        private String xiche;
        private String yundong;
        private String ziwanxian;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public Object getTime() {
            return time;
        }

        public void setTime(Object time) {
            this.time = time;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getWinddirect() {
            return winddirect;
        }

        public void setWinddirect(String winddirect) {
            this.winddirect = winddirect;
        }

        public String getWindpower() {
            return windpower;
        }

        public void setWindpower(String windpower) {
            this.windpower = windpower;
        }

        public String getChuanyi() {
            return chuanyi;
        }

        public void setChuanyi(String chuanyi) {
            this.chuanyi = chuanyi;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public String getKongtiao() {
            return kongtiao;
        }

        public void setKongtiao(String kongtiao) {
            this.kongtiao = kongtiao;
        }

        public String getWuran() {
            return wuran;
        }

        public void setWuran(String wuran) {
            this.wuran = wuran;
        }

        public String getXiche() {
            return xiche;
        }

        public void setXiche(String xiche) {
            this.xiche = xiche;
        }

        public String getYundong() {
            return yundong;
        }

        public void setYundong(String yundong) {
            this.yundong = yundong;
        }

        public String getZiwanxian() {
            return ziwanxian;
        }

        public void setZiwanxian(String ziwanxian) {
            this.ziwanxian = ziwanxian;
        }
    }
}
