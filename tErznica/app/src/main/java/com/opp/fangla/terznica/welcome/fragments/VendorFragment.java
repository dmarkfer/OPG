package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.support.v4.app.FragmentTransaction;
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

public class VendorFragment extends Fragment{

    private View root;
    private Button next, back;
    private AppCompatCheckBox checkBox;
    private RegisterViewModel model;
    private FragmentTransaction transaction;
    private VendorSubFragment vendorSubFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_vendor, container, false);
        model = ((RegisterActivity) getActivity()).getViewModel();

        checkBox = root.findViewById(R.id.f_register_vendor_check);
        next = root.findViewById(R.id.f_register_vendor_next);
        back = root.findViewById(R.id.f_register_vendor_back);
        vendorSubFragment = new VendorSubFragment();
        transaction = getFragmentManager().beginTransaction();

        checkBox.setChecked(model.isVendor());
        model.getVendor().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) {
                    transaction.add(R.id.f_register_vendor_container, vendorSubFragment);
                    transaction.commit();
                } else {
                    transaction.remove(vendorSubFragment);
                    transaction.commit();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(model.getVendorCheckedListener());
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).hideKeyBoard();
                ((RegisterActivity) getActivity()).changeFragment(3);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RegisterActivity) getActivity()).hideKeyBoard();
                ((RegisterActivity) getActivity()).changeFragment(1);
            }
        });

        return root;
    }
}