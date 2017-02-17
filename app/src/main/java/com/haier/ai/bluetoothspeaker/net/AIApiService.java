package com.haier.ai.bluetoothspeaker.net;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.bean.Oilprice.RequestOilprice;
import com.haier.ai.bluetoothspeaker.bean.Oilprice.ResponseOilprice;
import com.haier.ai.bluetoothspeaker.bean.holiday.RequestHoliday;
import com.haier.ai.bluetoothspeaker.bean.holiday.ResponseHoliday;
import com.haier.ai.bluetoothspeaker.bean.limit.RequestLimit;
import com.haier.ai.bluetoothspeaker.bean.limit.ResponseLimit;
import com.haier.ai.bluetoothspeaker.bean.movie.RequestMovie;
import com.haier.ai.bluetoothspeaker.bean.movie.ResponseMovie;
import com.haier.ai.bluetoothspeaker.bean.music.RequestMusic;
import com.haier.ai.bluetoothspeaker.bean.music.ResponseMusic;
import com.haier.ai.bluetoothspeaker.bean.news.RequestNews;
import com.haier.ai.bluetoothspeaker.bean.news.ResponseNews;
import com.haier.ai.bluetoothspeaker.bean.stock.RequestStock;
import com.haier.ai.bluetoothspeaker.bean.stock.ResponseStock;
import com.haier.ai.bluetoothspeaker.bean.translation.RequestTrans;
import com.haier.ai.bluetoothspeaker.bean.translation.ResponseTrans;
import com.haier.ai.bluetoothspeaker.bean.weather.RequestAqi;
import com.haier.ai.bluetoothspeaker.bean.weather.ResponseAqi;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * author: qu
 * date: 17-1-13
 * introduce:
 */

public interface AIApiService {
    @POST(Const.URL_CONTENT)
    public Call<ResponseMusic> getMusicContent(@Header("accessToken") String accessToken,
                                               @Body RequestMusic requestMusic);

    /**
     * 获取新闻
     * @param accessToken
     * @param requestNews
     * @return
     */
    @POST(Const.URL_CONTENT)
    public Call<ResponseNews> getNewsContent(@Header("accessToken") String accessToken,
                                             @Body RequestNews requestNews);

    @POST(Const.URL_CONTENT)
    public Call<ResponseLimit> getLimitContent(@Header("accessToken") String accessToken,
                                               @Body RequestLimit requestLimit);

    @POST(Const.URL_CONTENT)
    public Call<ResponseOilprice> getOilPrice(@Header("accessToken") String accessToken,
                                              @Body RequestOilprice requestOilprice);

    @POST(Const.URL_CONTENT)
    public Call<ResponseStock> getStockInfo(@Header("accessToken") String accessToken,
                                           @Body RequestStock requestStock);

    @POST(Const.URL_CONTENT)
    public Call<ResponseTrans> getTransferResult(@Header("accessToken") String accessToken,
                                            @Body RequestTrans requestTrans);

    @POST(Const.URL_CONTENT)
    public Call<ResponseAqi> getAqiResult(@Header("accessToken") String accessToken,
                                               @Body RequestAqi requestAqi);

    @POST(Const.URL_CONTENT)
    public Call<ResponseMovie> getMovieInfo(@Header("accessToken") String accessToken,
                                            @Body RequestMovie requestMovie);

    @POST(Const.URL_CONTENT)
    public Call<ResponseHoliday> getHolidayInfo(@Header("accessToken") String accessToken,
                                              @Body RequestHoliday requestHoliday);
}
