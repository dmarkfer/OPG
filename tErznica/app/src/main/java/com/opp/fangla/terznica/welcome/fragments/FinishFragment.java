package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.util.LogInCallback;
import com.opp.fangla.terznica.welcome.LogInActivity;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;

public class FinishFragment extends Fragment{

    private View root;
    private Button done, back;
    private RegisterViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_finish, container, false);
        model = ((RegisterActivity) getActivity()).getViewModel();

        done = root.findViewById(R.id.f_register_finish_done);
        back = root.findViewById(R.id.f_register_finish_back);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.hasARole()){
                    model.register().observe(FinishFragment.this, new Observer<LogInCallback>() {
                        @Override
                        public void onChanged(@Nullable LogInCallback callback) {
                            if(callback.isServerError()){
                                Snackbar.make(root, "Server nije dostupan", Snackbar.LENGTH_SHORT).show();
                            } else if(!callback.isSuccess()) {
                                Snackbar.make(root, "Neuspjela registracija", Snackbar.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                                prefs.edit().putString("username", model.getMail())
                                        .putString("password", model.getPassword())
                                        .putInt("userId", callback.getId())
                                        .putBoolean("admin", callback.isAdmin())
                                        .putBoolean("buyer", callback.isBuyer())
                                        .putBoolean("vendor", callback.isVendor())
                                        .putBoolean("driver", callback.isDriver())
                                        .apply();
                                startActivity(new Intent(getContext(), MainActivity.class));
                                getActivity().finish();
                            }
                        }
                    });
                } else {
                    Snackbar.make(root, "Odaberite bar jednu ulogu", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).changeFragment(3);
            }
        });

        return root;
    }

}
