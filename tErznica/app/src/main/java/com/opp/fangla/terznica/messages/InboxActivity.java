package com.opp.fangla.terznica.messages;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.util.Random;

import java.util.Collections;
import java.util.List;

public class InboxActivity extends AppCompatActivity{

    private InboxViewModel model;
    private ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = ViewModelProviders.of(this).get(InboxViewModel.class);

        setContentView(R.layout.a_inbox);
        list = findViewById(R.id.a_inbox_list);
        list.setAdapter(new ArrayAdapter<Conversation>(getApplicationContext(), R.layout.row_conversation) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final Conversation conversation = getItem(position);
                if(convertView == null){
                    ViewHolder holder = new ViewHolder();
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_search, parent);
                    holder.name = convertView.findViewById(R.id.row_conversation_name);
                    holder.message = convertView.findViewById(R.id.row_conversation_message);
                    holder.time = convertView.findViewById(R.id.row_conversation_time);
                    convertView.setTag(holder);
                }
                final ViewHolder holder = (ViewHolder) convertView.getTag();
                model.getUserName(conversation.getIdBuyer()).observe(InboxActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        holder.name.setText(s);
                    }
                });
                holder.message.setText(conversation.getFirstMessage().getMessage());
                holder.time.setText(Random.dateToNormalString(conversation.getFirstMessage().getDate()));
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putLong("conversationId", conversation.getIdConversation());
                        bundle.putInt("otherId", conversation.getIdBuyer());
                        bundle.putString("otherUsername", holder.name.getText().toString());
                        bundle.putLong("advertId", conversation.getIdAdvert());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                return convertView;
            }

            class ViewHolder{
                TextView name, message, time;
            }
        });
        model.getConversations().observe(this, new Observer<List<Conversation>>() {
            @Override
            public void onChanged(@Nullable List<Conversation> conversations) {
                if(conversations != null){
                    Collections.sort(conversations);
                    ((ArrayAdapter<Conversation>) list.getAdapter()).addAll(conversations);
                }
            }
        });
    }
}
