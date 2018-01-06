package com.opp.fangla.terznica.welcome.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;

public class DriverFragment extends Fragment {

    private View root;
    private Button next, back;
    private AppCompatCheckBox checkBox;
    private FrameLayout container;
    private RegisterViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_driver, container, false);
        model = ((RegisterActivity) getActivity()).getViewModel();

        checkBox = root.findViewById(R.id.f_register_driver_check);
        container = root.findViewById(R.id.f_register_driver_container);
        next = root.findViewById(R.id.f_register_driver_next);
        back = root.findViewById(R.id.f_register_driver_back);

        checkBox.setChecked(model.isDriver());

        checkBox.setOnCheckedChangeListener(model.getDriverCheckedListener());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).changeFragment(4);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).changeFragment(2);
            }
        });

        return root;
    }

}
