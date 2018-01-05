package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;


public class GeneralFragment extends Fragment {

    private View root;
    private ImageView passwordImage, confirmPasswordImage;
    private Button next, back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_general, container, false);

        passwordImage = root.findViewById(R.id.f_register_general_password_img);
        confirmPasswordImage = root.findViewById(R.id.f_register_general_confirm_img);
        next = root.findViewById(R.id.f_register_general_next);
        back = root.findViewById(R.id.f_register_general_back);

        getModel().getStrongPassword().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    passwordImage.setImageResource(R.drawable.check_white);
                } else {
                    passwordImage.setImageResource(R.drawable.close_white);
                }
            }
        });

        getModel().getPasswordsMatch().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    confirmPasswordImage.setImageResource(R.drawable.check_white);
                } else {
                    confirmPasswordImage.setImageResource(R.drawable.close_white);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return root;
    }

    private RegisterViewModel getModel(){
        return ((RegisterActivity) getActivity()).getViewModel();
    }
}
