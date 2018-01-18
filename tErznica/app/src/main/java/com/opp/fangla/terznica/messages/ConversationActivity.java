package com.opp.fangla.terznica.messages;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.Message;
import com.opp.fangla.terznica.util.Random;
import com.opp.fangla.terznica.welcome.LogInActivity;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ConversationActivity extends AppCompatActivity {

    public static final int PLACE_AUTOCOMPLETE = 951;
    private ConversationViewModel model;
    private TextView name;
    private ListView list;
    private EditText edit;
    private ImageView send;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = ViewModelProviders.of(this).get(ConversationViewModel.class);
        Bundle bundle = getIntent().getExtras();
        model.setConversationId(bundle.getInt("conversationId"));
        model.setOtherId(bundle.getInt("otherId"));
        model.setOtherUsername(bundle.getString("otherUsername"));
        model.setAdvertId(bundle.getInt("advertId"));
        model.setMyId(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("userId", -1));
        model.setMyUsername(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("username", ""));

        name = findViewById(R.id.a_conversation_name);
        list = findViewById(R.id.a_conversation_list);
        edit = findViewById(R.id.a_conversation_edit);
        send = findViewById(R.id.a_conversation_send);
        timer = new Timer();

        name.setText(model.getOtherUsername());
        list.setAdapter(new ArrayAdapter<Message>(getApplicationContext(), R.layout.row_conversation) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final Message message = getItem(position);
                if(convertView == null){
                    ViewHolder holder = new ViewHolder();
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_search, parent);
                    holder.name = convertView.findViewById(R.id.row_message_name);
                    holder.message = convertView.findViewById(R.id.row_message_message);
                    holder.time = convertView.findViewById(R.id.row_message_time);
                    convertView.setTag(holder);
                }
                final ViewHolder holder = (ViewHolder) convertView.getTag();
                if(message.getIdSender() == model.getOtherId()) {
                    holder.name.setText(model.getOtherUsername());
                } else {
                    holder.name.setText(model.getMyUsername());
                }
                holder.message.setText(message.getMessage());
                holder.time.setText(Random.dateToNormalString(message.getDate()));
                return convertView;
            }

            class ViewHolder{
                TextView name, message, time;
            }
        });
        timer.schedule(new RefreshMessages(), 0);
        edit.addTextChangedListener(model.getMessageWatcher());
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.createNewMessage();
                edit.setText("");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.conversation_menu_profile:
                //startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                return true;
            case R.id.conversation_menu_request:
                model.prepareShipmentInfo();
                new NewShipmentOffer(getApplicationContext(), R.style.LightDialog).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class RefreshMessages extends TimerTask{

        @Override
        public void run() {
            model.getMessages().observe(ConversationActivity.this, new Observer<List<Message>>() {
                @Override
                public void onChanged(@Nullable List<Message> messages) {
                    if(messages != null) {
                        Collections.sort(messages);
                        ((ArrayAdapter<Message>) list.getAdapter()).addAll(messages);
                    }
                }
            });
            timer.schedule(new RefreshMessages(), 5000);
        }
    }

    private class NewShipmentOffer extends Dialog{

        private TextView departure, destination, productName;
        private Button back, ok;

        public NewShipmentOffer(@NonNull Context context, int themeResId) {
            super(context, themeResId);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.d_new_shipment);
            departure = findViewById(R.id.d_new_ship_departure);
            destination = findViewById(R.id.d_new_ship_destination);
            productName = findViewById(R.id.d_new_ship_product);
            back = findViewById(R.id.d_new_ship_back);
            ok = findViewById(R.id.d_new_ship_ok);

            model.getAddressMutableLiveData().observe(ConversationActivity.this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    departure.setText(s);
                }
            });
            model.getAdvertMutableLiveData().observe(ConversationActivity.this, new Observer<Advert>() {
                @Override
                public void onChanged(@Nullable Advert advert) {
                    productName.setText(advert.getName());
                }
            });

            destination.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent =
                                new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                        .build(getOwnerActivity());
                        getOwnerActivity().startActivityForResult(intent, PLACE_AUTOCOMPLETE);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
