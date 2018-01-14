package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.ProductCategory;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateProductCategory extends AsyncTask<ProductCategory,Void,Integer> {

    private MutableLiveData<Integer> liveData;

    public CreateProductCategory (MutableLiveData<Integer> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected Integer doInBackground(ProductCategory... productCategories) {


        ProductCategory productCategory = productCategories[0];
        String[] commands = new String[] {"createProductCategory","idKorisnika","nazivKategorije","komentar"};
        String[] values = new String[3];
        values[0] = String.valueOf(productCategory.getId());
        values[1] = productCategory.getName();
        values[2] = productCategory.getComment();
        Integer idCategory = null;

        try {

            String sResponse = Random.sendMessageToServer(commands, values, "createProdCat");
            JSONObject response = new JSONObject(sResponse);
            idCategory = response.getInt("idKategorije");

        }catch ( JSONException e) {
            e.printStackTrace();
        }

        return idCategory;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        liveData.postValue(integer);
    }
}
