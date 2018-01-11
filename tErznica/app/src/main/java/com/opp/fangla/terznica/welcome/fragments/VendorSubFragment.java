package com.opp.fangla.terznica.welcome.fragments;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;

public class VendorSubFragment extends Fragment{

    public static final int PLACE_AUTOCOMPLETE = 616;
    public static final int VENDOR_IMAGE = 978;

    private View root;
    private EditText name, number, description, iban;
    private TextView address;
    private ImageView image;
    private RegisterViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_register_vendor_sub, container, false);
        name = root.findViewById(R.id.f_register_vendor_sub_name);
        number = root.findViewById(R.id.f_register_vendor_sub_number);
        address = root.findViewById(R.id.f_register_vendor_sub_address);
        description = root.findViewById(R.id.f_register_vendor_sub_description);
        iban = root.findViewById(R.id.f_register_vendor_sub_bank_account);
        image = root.findViewById(R.id.f_register_vendor_sub_image);
        model = ((RegisterActivity) getActivity()).getViewModel();

        name.setText(model.getVendorName());
        number.setText(model.getVendorNumber());
        description.setText(model.getVendorDescription());
        iban.setText(model.getVendorIban());

        name.addTextChangedListener(model.getVendorNameWatcher());
        number.addTextChangedListener(model.getVendorNumberWatcher());
        description.addTextChangedListener(model.getVendorDescriptionWatcher());
        iban.addTextChangedListener(model.getVendorIbanWatcher());

        model.getAddress().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(@Nullable Place place) {
                if(place == null){
                    address.setText(R.string.add_address);
                } else {
                    address.setText(place.getAddress());
                }
            }
        });
        model.getVendorImage().observe(this, new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap bitmap) {
                //Log.d("VendorSubFragment", "AAAAAAAAAAAA image changed");
                image.setImageBitmap(bitmap);
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    getActivity().startActivityForResult(intent, PLACE_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("VendorSubFragment", "AAAAAAAAAAAA image change requested");
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                getActivity().startActivityForResult(photoPickerIntent, VENDOR_IMAGE);
                //Log.d("VendorSubFragment", "AAAAAAAAAAAA activity started");
            }
        });

        return root;
    }
}
