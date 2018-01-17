package com.opp.fangla.terznica;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.interfaces.BuyerInterface;
import com.opp.fangla.terznica.interfaces.DriverInterface;
import com.opp.fangla.terznica.interfaces.VendorInterface;
import com.opp.fangla.terznica.util.Random;
import com.opp.fangla.terznica.welcome.LogInActivity;
import com.opp.fangla.terznica.welcome.fragments.DriverFragment;
import com.opp.fangla.terznica.welcome.fragments.VendorSubFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String[] roleNames = {"buyer", "vendor", "driver", "admin"};
    private MainViewModel model;
    private FloatingActionButton messageFab, auxFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String prefsUsername = preferences.getString("username", "");
        Log.d("AAAAAAAAAAAAAAA", prefsUsername);
        if(prefsUsername.equals("")){
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();
        }
        final List<String> roles = new ArrayList<>();
        for(String s : roleNames){
            if(preferences.getBoolean(s, false)){
                roles.add(s);
            }
        }
        String[] rolesArray = new String[roles.size()];
        for (int i = 0; i < roles.size(); i++){
            rolesArray[i] = roles.get(i);
        }
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), rolesArray));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                switch (roles.get(position)){
                    case "buyer":
                        auxFab.setVisibility(View.INVISIBLE);
                        fragment = new BuyerInterface();
                        break;
                    case "vendor":
                        auxFab.setVisibility(View.VISIBLE);
                        fragment = new VendorInterface();
                        break;
                    default:
                        auxFab.setVisibility(View.INVISIBLE);
                        fragment = new DriverFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        messageFab = findViewById(R.id.a_main_fab_messages);
        messageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Otvaram razgovor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        auxFab = findViewById(R.id.a_main_fab_aux);
        auxFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_log_out:
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DriverInterface.DEPARTURE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                model.setDeparture(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("MainActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("MainActivity", "Autocomplete cancelled");
            }
        } else if(requestCode == DriverInterface.DESTINATION_AUTOCOMPLETE){
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                model.setDestination(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("MainActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("MainActivity", "Autocomplete cancelled");
            }
        }
    }

    public MainViewModel getViewModel(){
        return model;
    }

    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                // Inflate the drop down using the helper's LayoutInflater
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }

    private class EditProductDialog extends Dialog{

        private MainViewModel model;
        private boolean isNew;
        private ImageView image;
        private EditText name, description, price;
        private Spinner categories;
        private Button left, right;
        private Advert advert;

        public EditProductDialog(@NonNull Context context, int themeResId, MainViewModel model, Advert advert, boolean isNew) {
            super(context, themeResId);
            this.model = model;
            this.advert = advert;
            this.isNew = isNew;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            image = findViewById(R.id.d_product_edit_image);
            name = findViewById(R.id.d_product_edit_name);
            description = findViewById(R.id.d_product_edit_description);
            categories = findViewById(R.id.d_product_edit_categories);
            price = findViewById(R.id.d_product_edit_price);
            left = findViewById(R.id.d_product_edit_left);
            right = findViewById(R.id.d_product_edit_right);


        }
    }
}
