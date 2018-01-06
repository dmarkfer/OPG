package com.opp.fangla.terznica.welcome;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.util.CustomViewPager;
import com.opp.fangla.terznica.welcome.fragments.BuyerFragment;
import com.opp.fangla.terznica.welcome.fragments.DriverFragment;
import com.opp.fangla.terznica.welcome.fragments.FinishFragment;
import com.opp.fangla.terznica.welcome.fragments.GeneralFragment;
import com.opp.fangla.terznica.welcome.fragments.VendorFragment;

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

    private class RegisterPagerAdapter extends FragmentPagerAdapter{

        private int currentPosition;

        public RegisterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            currentPosition = position;
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
    }
}
