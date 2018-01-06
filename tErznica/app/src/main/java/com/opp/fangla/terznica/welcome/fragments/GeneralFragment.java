package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
    private EditText name, surname, mail, phone, password, confirmPassword;
    private ImageView passwordImage, confirmPasswordImage;
    private Button next, back;
    private RegisterViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_general, container, false);
        model = ((RegisterActivity) getActivity()).getViewModel();

        name = root.findViewById(R.id.f_register_general_name);
        surname = root.findViewById(R.id.f_register_general_surname);
        mail = root.findViewById(R.id.f_register_general_mail);
        phone = root.findViewById(R.id.f_register_general_phone);
        password = root.findViewById(R.id.f_register_general_password);
        confirmPassword = root.findViewById(R.id.f_register_general_confirm);
        passwordImage = root.findViewById(R.id.f_register_general_password_img);
        confirmPasswordImage = root.findViewById(R.id.f_register_general_confirm_img);
        next = root.findViewById(R.id.f_register_general_next);
        back = root.findViewById(R.id.f_register_general_back);

        name.setText(model.getName());
        surname.setText(model.getSurname());
        mail.setText(model.getMail());
        phone.setText(model.getPhone());
        password.setText(model.getPassword());
        confirmPassword.setText(model.getConfirmPassword());

        name.addTextChangedListener(model.getNameWatcher());
        surname.addTextChangedListener(model.getSurnameWatcher());
        mail.addTextChangedListener(model.getMailWatcher());
        phone.addTextChangedListener(model.getPhoneeWatcher());
        password.addTextChangedListener(model.getPasswordWatcher());
        confirmPassword.addTextChangedListener(model.getConfirmPasswordWatcher());

        model.getStrongPasswordObs().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean.booleanValue()){
                    passwordImage.setImageResource(R.drawable.check_white);
                } else {
                    passwordImage.setImageResource(R.drawable.close_white);
                }
            }
        });

        model.getPasswordsMatchObs().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean.booleanValue()){
                    confirmPasswordImage.setImageResource(R.drawable.check_white);
                } else {
                    confirmPasswordImage.setImageResource(R.drawable.close_white);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!model.generalFieldsFilled()){
                    Snackbar.make(root, "Molimo popunite sva polja", Snackbar.LENGTH_SHORT).show();
                } else if(!model.getStrongPassword()){
                    Snackbar.make(root, "Prekratka lozinka", Snackbar.LENGTH_SHORT).show();
                } else if(!model.getPasswordsMatch()){
                    Snackbar.make(root, "Lozinke se ne podudaraju", Snackbar.LENGTH_SHORT).show();
                } else {
                    ((RegisterActivity) getActivity()).changeFragment(1);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        return root;
    }
}
