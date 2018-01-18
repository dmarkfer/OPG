package com.opp.fangla.terznica.messages;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextWatcher;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.data.entities.Message;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.util.SimpleTextWatcher;

import java.util.Date;
import java.util.List;

public class ConversationViewModel extends AndroidViewModel{

    private DataRepository repository;
    private int otherId, myId, conversationId, advertId;
    private String otherUsername = "", myUsername = "";
    private Message newMessage = new Message();
    private AdvertShipment advertShipment;
    private MutableLiveData<String> addressMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Advert> advertMutableLiveData = new MutableLiveData<>();


    public ConversationViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
        newMessage.setIdChat(conversationId);
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }

    public void setAdvertId(int advertId) {
        this.advertId = advertId;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

    public int getOtherId() {
        return otherId;
    }

    public String getOtherUsername() {
        return otherUsername;
    }

    public LiveData<List<Message>> getMessages(){
        return repository.getMessages(conversationId);
    }

    public void createNewMessage(){
        newMessage.setDate(new Date());
        repository.sendMessage(newMessage);
        newMessage = new Message();
        newMessage.setIdChat(conversationId);
        newMessage.setIdSender(myId);
    }

    public void setOtherUsername(String username){
        otherUsername = username;
    }

    public TextWatcher getMessageWatcher(){
        return new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newMessage.setMessage(charSequence.toString());
            }
        };
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

    public AdvertShipment getAdvertShipment() {
        if(advertShipment == null){
            advertShipment = new AdvertShipment();
        }
        return advertShipment;
    }

    public void prepareShipmentInfo(){
        final LiveData<List<Advert>> liveList = repository.getAdverts();
        liveList.observeForever(new Observer<List<Advert>>() {
            @Override
            public void onChanged(@Nullable List<Advert> adverts) {
                if(adverts != null) {
                    for (Advert a : adverts) {
                        if(a.getAdvertId() == advertId){
                            advertMutableLiveData.postValue(a);
                            liveList.removeObserver(this);
                        }
                    }
                }
            }
        });
        final LiveData<User> liveUser = repository.getUser(Integer.toString(advertMutableLiveData.getValue().getCreatorId()));
        liveUser.observeForever(new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if(user != null){
                    addressMutableLiveData.postValue(user.getVendorData().getAddress().getValue().toString());
                    liveUser.removeObserver(this);
                }
            }
        });
    }

    public MutableLiveData<String> getAddressMutableLiveData() {
        return addressMutableLiveData;
    }

    public MutableLiveData<Advert> getAdvertMutableLiveData() {
        return advertMutableLiveData;
    }


}
