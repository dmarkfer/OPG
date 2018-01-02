package com.opp.fangla.terznica;

import android.app.Application;

import com.opp.fangla.terznica.data.DataRepository;

/**
 * Created by domagoj on 21.12.17..
 */

public class FanglaApp extends Application {

    public DataRepository getRepository(){
        return DataRepository.getInstance();
    }
}
