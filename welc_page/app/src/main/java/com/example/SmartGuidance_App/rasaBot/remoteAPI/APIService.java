package com.example.SmartGuidance_App.rasaBot.remoteAPI;

import com.example.SmartGuidance_App.rasaBot.Data.botResponse;
import com.example.SmartGuidance_App.rasaBot.Data.userMessage;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIService {

    @POST("webhook")
    Call<List<botResponse>>sendMessage(@Body userMessage message);




}
