package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;

public class DriverFragment extends Fragment {

    private View root;
    private Button next, back;
    private AppCompatCheckBox checkBox;
    private RegisterViewModel model;
    private DriverSubFragment driverSubFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_driver, container, false);
        model = ((RegisterActivity) getActivity()).getViewModel();

        checkBox = root.findViewById(R.id.f_register_driver_check);
        next = root.findViewById(R.id.f_register_driver_next);
        back = root.findViewById(R.id.f_register_driver_back);

        checkBox.setChecked(model.isDriver());
        driverSubFragment = new DriverSubFragment();
        model.getDriver().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if(aBoolean) {
                    transaction.add(R.id.f_register_driver_container, driverSubFragment);
                    transaction.commit();
                } else {
                    transaction.remove(driverSubFragment);
                    transaction.commit();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(model.getDriverCheckedListener());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.getDriver().getValue() && !model.hasAVehicle()){
                    Snackbar.make(root, "Unesite bar jedno vozilo", Snackbar.LENGTH_SHORT).show();
                } else {
                    ((RegisterActivity) getActivity()).hideKeyBoard();
                    ((RegisterActivity) getActivity()).changeFragment(4);
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).hideKeyBoard();
                ((RegisterActivity) getActivity()).changeFragment(2);
            }
        });

        return root;
    }

}
