package com.example.SmartGuidance_App.chat_classes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.SmartGuidance_App.MainActivity;
import com.example.SmartGuidance_App.R;
import com.example.SmartGuidance_App.rasaBot.Data.botResponse;
import com.example.SmartGuidance_App.rasaBot.Data.userMessage;
import com.example.SmartGuidance_App.rasaBot.remoteAPI.APIService;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class chat extends AppCompatActivity {
    EditText userInput;
    RecyclerView recycleView;
    APIService requestAPI;

    List<ResponseMessage> responseMessageList;
    MessageAdapter messageAdapter;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuinflater=getMenuInflater();
        menuinflater.inflate(R.menu.chat_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(chat.this, MainActivity.class);
            startActivity(intent);



        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_layout);
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


        userInput=findViewById(R.id.userInput);
        recycleView=findViewById(R.id.conversation);
        responseMessageList= new ArrayList<>();
        messageAdapter=new MessageAdapter(responseMessageList,this);
        recycleView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false
        ));
        recycleView.setAdapter(messageAdapter);

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {



                    ResponseMessage message = new ResponseMessage(userInput.getText().toString(), true);
                    responseMessageList.add(message);
                    messageAdapter.notifyDataSetChanged();

                    if (!isLastVisible())
                        recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);

                    String user_message = userInput.getText().toString();
                    userMessage new_Message = new userMessage("TOURIST", user_message);
                    Call<List<botResponse>> call_response = requestAPI.sendMessage(new_Message);
                    call_response.enqueue(new Callback<List<botResponse>>() {
                        @Override
                        public void onResponse(Call<List<botResponse>> call, Response<List<botResponse>> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(chat.this, "Not Successful! " + response.message()
                                        , Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(response.body().isEmpty()){
                                ResponseMessage message2 = new ResponseMessage("عذرا لم افهم ماقلت حاول مرة اخرى.", false);
                                responseMessageList.add(message2);
                                messageAdapter.notifyDataSetChanged();
                                if (!isLastVisible())
                                    recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                                return;
                            }
                            botResponse apiResponse = response.body().get(0);
                            String bot_reply1 = apiResponse.getText();
                            ResponseMessage message2 = new ResponseMessage(bot_reply1, false);
                            responseMessageList.add(message2);
                            messageAdapter.notifyDataSetChanged();
                            String bot_reply2;
                            if (response.body().size() > 1) {
                                for(int i=1;i<response.body().size();i++){
                                bot_reply2 = response.body().get(i).getText();
                                message2 = new ResponseMessage(bot_reply2, false);
                                responseMessageList.add(message2);
                                }
                            }
                            messageAdapter.notifyDataSetChanged();
                            if (!isLastVisible())
                                recycleView.smoothScrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                        @Override
                        public void onFailure(Call<List<botResponse>> call, Throwable t) {
                            Toast.makeText(chat.this, t.getMessage()
                                    , Toast.LENGTH_SHORT).show();
                        }

                    });

                }
                v.setText("");
                return true;
            }
            });

        }





   public boolean isLastVisible() {
        LinearLayoutManager layoutManager = ((LinearLayoutManager) recycleView.getLayoutManager());
        int pos = layoutManager.findLastCompletelyVisibleItemPosition();
        int numItems = recycleView.getAdapter().getItemCount();
        return (pos >= numItems);
    }
}



