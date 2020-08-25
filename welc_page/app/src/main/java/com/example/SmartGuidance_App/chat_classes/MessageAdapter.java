package com.example.SmartGuidance_App.chat_classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.SmartGuidance_App.R;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.CustomViewHolder> {

    Context context;

class CustomViewHolder extends RecyclerView.ViewHolder{

TextView textView;
    public CustomViewHolder(View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.textMessage);


    }
}



    List<ResponseMessage> ResponseMessageList;

    public MessageAdapter(List<ResponseMessage> ResponseMessageList, Context context) {

        this.ResponseMessageList=  ResponseMessageList;
         this.context=context;

    }

    @Override
    public int getItemViewType(int position) {
        if(ResponseMessageList.get(position).isUser()){

            return R.layout.user_layout;
        }
        return R.layout.bot_layout;

    }


    @Override
    public CustomViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(viewType,parent,false));
    }

    @Override
    public void onBindViewHolder( MessageAdapter.CustomViewHolder holder, int position) {


        holder.textView.setText(ResponseMessageList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        return ResponseMessageList.size();
    }
}
