package com.example.SmartGuidance_App.chat_classes;




import com.example.SmartGuidance_App.R;
import com.example.SmartGuidance_App.rasaBot.Data.botResponse;
import com.example.SmartGuidance_App.rasaBot.Data.userMessage;
import com.example.SmartGuidance_App.rasaBot.remoteAPI.APIService;


        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.inputmethod.EditorInfo;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import okhttp3.OkHttpClient;
        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Retrofit;
        import retrofit2.Response;
        import retrofit2.converter.gson.GsonConverterFactory;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText userInput;
    RecyclerView recyclerView;
    List<ResponseMessage> responseMessagesList;
    MessageAdapter messageAdapter;
    APIService requestAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //HTTP Client
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        //Defining retrofit api service
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.8.111:5005/webhooks/rest/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        requestAPI = retrofit.create(APIService.class);

        userInput = findViewById(R.id.userInput);
        recyclerView = findViewById(R.id.conversation);
        responseMessagesList = new ArrayList<>();
        messageAdapter = new MessageAdapter(responseMessagesList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(messageAdapter);



        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND){
                    ResponseMessage message = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessagesList.add(message);
                    messageAdapter.notifyDataSetChanged();

                    if(!isVisible())
                        recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                    String user_message = userInput.getText().toString();
                    userMessage new_Message = new userMessage("TOURIST",user_message);
                    Call<List<botResponse>> call_response = requestAPI.sendMessage(new_Message);
                    call_response.enqueue(new Callback<List<botResponse>>() {
                        @Override
                        public void onResponse(Call<List<botResponse>> call, Response<List<botResponse>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Not Successful! " + response.message()
                                        , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(response.body().isEmpty()){
                                ResponseMessage message2 = new ResponseMessage("\n" +"Sorry I did not understand what you said try again", false);
                                responseMessagesList.add(message2);
                                messageAdapter.notifyDataSetChanged();
                                if (!isVisible())
                                    recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                                return;
                            }
                            botResponse apiResponse = response.body().get(0);
                            String bot_reply1 = apiResponse.getText();
                            ResponseMessage message2 = new ResponseMessage(bot_reply1, false);
                            responseMessagesList.add(message2);
                            messageAdapter.notifyDataSetChanged();
                            String bot_reply2;
                            if (response.body().size() > 1) {
                                for(int i=1;i<response.body().size();i++){
                                    bot_reply2 = response.body().get(i).getText();
                                    message2 = new ResponseMessage(bot_reply2, false);
                                    responseMessagesList.add(message2);
                                }}
                            messageAdapter.notifyDataSetChanged();
                            if (!isVisible())
                                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }

                        @Override
                        public void onFailure(Call<List<botResponse>> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Something went wrong and tried again\uD83D\uDE25 "
                                    , Toast.LENGTH_SHORT).show();
                        }


                    });

                }

                return true;
            }

        });
    }
    public boolean isVisible(){
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager)recyclerView.getLayoutManager();
        int positionOfLastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
        int itemCount = recyclerView.getAdapter().getItemCount();
        return (positionOfLastVisibleItem>=itemCount);
    }
}
