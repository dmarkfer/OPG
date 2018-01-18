package com.opp.fangla.terznica.messages;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.opp.fangla.terznica.FanglaApp;
import com.opp.fangla.terznica.data.DataRepository;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.User;

import java.util.List;

public class InboxViewModel extends AndroidViewModel{

    private DataRepository repository;
    private int userId;

    public InboxViewModel(@NonNull Application application) {
        super(application);
        repository = ((FanglaApp) application).getRepository();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LiveData<List<Conversation>> getConversations(){
        return repository.getConversations(userId);
    }

    public LiveData<String> getUserName(int userId){
        final MutableLiveData<String> liveData = new MutableLiveData<>();
        final LiveData<User> tempData = repository.getUser(Integer.toString(userId));
        tempData.observeForever(new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                liveData.postValue(user.getName());
                tempData.removeObserver(this);
            }
        });
        return liveData;
    }

}
