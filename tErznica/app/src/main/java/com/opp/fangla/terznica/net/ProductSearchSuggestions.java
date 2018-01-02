package com.opp.fangla.terznica.net;

import android.arch.lifecycle.MutableLiveData;
import android.database.MatrixCursor;
import android.os.AsyncTask;

import com.opp.fangla.terznica.interfaces.BuyerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by domagoj on 29.12.17..
 */

public class ProductSearchSuggestions extends AsyncTask<Void, Void, MatrixCursor> {

    private MutableLiveData<MatrixCursor> liveData;

    public ProductSearchSuggestions(MutableLiveData<MatrixCursor> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected MatrixCursor doInBackground(Void... voids) {
        MatrixCursor result = new MatrixCursor(BuyerInterface.matrixColumns);

        //to do network communication

        return result;
    }

    @Override
    protected void onPostExecute(MatrixCursor cursor) {
        liveData.postValue(cursor);
    }
}
