package com.opp.fangla.terznica.welcome.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opp.fangla.terznica.R;
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
                    model.register();
                    getActivity().finish();
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
