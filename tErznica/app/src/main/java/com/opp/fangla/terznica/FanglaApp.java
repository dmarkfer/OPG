package com.opp.fangla.terznica;

import android.app.Application;

import com.opp.fangla.terznica.data.DataRepository;

public class FanglaApp extends Application {

    public DataRepository getRepository(){
        return DataRepository.getInstance();
    }
}
