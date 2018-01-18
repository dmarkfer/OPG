package com.opp.fangla.terznica.net;


import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import com.opp.fangla.terznica.data.entities.AdminTask;
import com.opp.fangla.terznica.data.entities.Person;
import com.opp.fangla.terznica.data.entities.ProductCategory;
import com.opp.fangla.terznica.data.entities.Report;
import com.opp.fangla.terznica.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAdminTasks extends AsyncTask<Void,Void,AdminTask> {

    private MutableLiveData<AdminTask> liveData;

    public GetAdminTasks (MutableLiveData<AdminTask> liveData) {
        this.liveData = liveData;
    }

    @Override
    protected AdminTask doInBackground(Void... voids) {

        String[] commands = new String[]{"getAdminTasks"};
        String[] values = new String[0];
        AdminTask adminTask=null;

        String sResponse = Random.sendMessageToServer(commands,values,"getAdminTasksArgs");
        try {
            JSONObject jsonObject = new JSONObject(sResponse);


            JSONArray array = jsonObject.getJSONArray("noveKategorije");
            JSONObject element;
            adminTask = new AdminTask();

            List<ProductCategory> productCategoryList = new ArrayList<>();
                    for(int i=0; i< array.length(); i++) {
                        element = array.getJSONObject(i);
                        ProductCategory productCategory = new ProductCategory();
                        productCategory.setId(element.getInt("idKategorije"));
                        productCategory.setName(element.getString("nazivKategorije"));
                        productCategory.setUserId(element.getInt("idKorisnika"));
                        productCategory.setComment(element.getString("komentar"));
                        productCategoryList.add(productCategory);
                    }

            adminTask.setProductCategories(productCategoryList);
            List<Person> personList = new ArrayList<>();
            array = jsonObject.getJSONArray("noviKorisnici");
                    for (int i=0; i< array.length(); i++){
                        Person person = new Person();
                        element = array.getJSONObject(i);
                        person.setName(element.getString("ime"));
                        person.setUserId(element.getInt("idKorisnika"));
                        person.setSurname(element.getString("prezime"));
                        personList.add(person);
                    }
            //    getAdminTasks() -> (noveKategorije[idKategorije, idKorisnika, nazivKategorije, komentar],
            //    noviKorisnici[idKorisnika, ime, prezime],
            //    prijave[idPrijave, idVrstePrijave, idKorisnika, komentar, idPrijavljeneStavke, tipPrijave])
            adminTask.setPersons(personList);
            List<Report> reportList = new ArrayList<>();
            array = jsonObject.getJSONArray("prijave");
                    for (int i=0; i< array.length();i++) {
                        Report report = new Report();
                        element = array.getJSONObject(i);
                        report.setComment(element.getString("komentar"));
                        report.setReportId(element.getInt("idPrijave"));
                        report.setReportTypeId(element.getInt("idVrstePrijave"));
                        report.setUserId(element.getInt("idKorisnika"));
                        report.setReportedItemId(element.getInt("idPrijavljeneStavke"));
                        reportList.add(report);
                        report.setReportType(element.getInt("tipPrijave"));

                    }
            adminTask.setReportList(reportList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return adminTask;
    }

    @Override
    protected void onPostExecute(AdminTask adminTask) {
        liveData.postValue(adminTask);
    }
}
