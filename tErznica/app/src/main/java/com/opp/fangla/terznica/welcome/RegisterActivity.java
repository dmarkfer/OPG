package com.opp.fangla.terznica.welcome;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.opp.fangla.terznica.R;

/**
 * Created by domagoj on 30.12.17..
 */

public class RegisterActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private RegisterViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_register);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        frameLayout = findViewById(R.id.a_register_container);


    }

    void changeFragment(int position){

    }

    public RegisterViewModel getViewModel(){
        return viewModel;
    }
}
