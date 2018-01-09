package com.opp.fangla.terznica.welcome;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.util.CustomViewPager;
import com.opp.fangla.terznica.welcome.fragments.BuyerFragment;
import com.opp.fangla.terznica.welcome.fragments.DriverFragment;
import com.opp.fangla.terznica.welcome.fragments.FinishFragment;
import com.opp.fangla.terznica.welcome.fragments.GeneralFragment;
import com.opp.fangla.terznica.welcome.fragments.VendorFragment;
import com.opp.fangla.terznica.welcome.fragments.VendorSubFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private CustomViewPager pager;
    private RegisterPagerAdapter adapter;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_register);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        pager = findViewById(R.id.a_register_pager);
        adapter = new RegisterPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.swipingEnabled(false);
    }

    public RegisterViewModel getViewModel(){
        return viewModel;
    }

    public void changeFragment(int position){
        pager.setCurrentItem(position, true);
        adapter.setCurrentPosition(position);
    }

    @Override
    public void onBackPressed() {
        switch (adapter.getCurrentPosition()){
            case 0:
                //super.onBackPressed();
                finish();
                break;
            default:
                changeFragment(adapter.getCurrentPosition()-1);
                break;
        }
    }

    public void hideKeyBoard(){
        try {
            ((InputMethodManager) this.getSystemService(
                    Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    this.getCurrentFocus().getWindowToken(), 0);

        } catch (NullPointerException e){
            Log.d("RegisterActivity", "Hide keyboard unsuccessful");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Log.d("RegisterActivity", "AAAAAAAAAAAA returned from intent, result code: " + resultCode + ", request code: " + requestCode);
        if (requestCode == VendorSubFragment.PLACE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                viewModel.setAddress(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("RegisterActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("RegisterActivity", "Autocomplete cancelled");
            }
        } else if(requestCode == VendorSubFragment.VENDOR_IMAGE){
            //Log.d("RegisterActivity", "AAAAAAAAAAAA picker finished");
            if(resultCode == RESULT_OK){
                //Log.d("RegisterActivity", "AAAAAAAAAAAA image changed");
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    viewModel.setVendorImage(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class RegisterPagerAdapter extends FragmentPagerAdapter{

        private int currentPosition = 0;

        public RegisterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new GeneralFragment();
                case 1:
                    return new BuyerFragment();
                case 2:
                    return new VendorFragment();
                case 3:
                    return new DriverFragment();
                case 4:
                    return new FinishFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        public int getCurrentPosition(){
            return currentPosition;
        }

        public void setCurrentPosition(int position){
            this.currentPosition = position;
        }
    }
}
