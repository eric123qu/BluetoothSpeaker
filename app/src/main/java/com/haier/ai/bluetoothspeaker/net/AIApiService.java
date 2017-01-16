package com.haier.ai.bluetoothspeaker.net;

import com.haier.ai.bluetoothspeaker.Const;
import com.haier.ai.bluetoothspeaker.bean.music.RequestMusic;
import com.haier.ai.bluetoothspeaker.bean.music.ResponseMusic;

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

}
