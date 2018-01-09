package com.opp.fangla.terznica.welcome.fragments;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Vehicle;
import com.opp.fangla.terznica.welcome.RegisterActivity;
import com.opp.fangla.terznica.welcome.RegisterViewModel;

import java.util.List;

public class DriverSubFragment extends Fragment {

    public static final int VEHICLE_IMAGE = 494;

    private LinearLayout list;
    private ImageView add;
    private View root;
    private RegisterViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        model = ((RegisterActivity) getActivity()).getViewModel();
        root = inflater.inflate(R.layout.f_register_driver_sub, container, false);
        list = root.findViewById(R.id.f_register_driver_sub_list);
        add = root.findViewById(R.id.f_register_driver_sub_add);

        model.getVehicles().observe(this, new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                if(vehicles != null){
                    list.removeAllViews();
                    for(Vehicle vehicle : vehicles){
                        list.addView(getVehicleView(vehicle));
                    }
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext(), R.style.DarkDialog);
                dialog.setContentView(R.layout.f_new_vehicle);

                final RegisterViewModel viewModel = ((RegisterActivity) getActivity()).getViewModel();
                final Vehicle vehicle = viewModel.getNewVehicle();

                EditText registration = dialog.findViewById(R.id.f_new_vehicle_registration);
                EditText model = dialog.findViewById(R.id.f_new_vehicle_model);
                EditText description = dialog.findViewById(R.id.f_new_vehicle_description);
                Spinner categories = dialog.findViewById(R.id.f_new_vehicle_category);
                final ImageView image = dialog.findViewById(R.id.f_new_vehicle_image);
                Button add = dialog.findViewById(R.id.f_new_vehicle_add);
                Button back = dialog.findViewById(R.id.f_new_vehicle_back);

                registration.setText(vehicle.getRegistration());
                model.setText(vehicle.getModel());
                description.setText(vehicle.getDescription());
                registration.addTextChangedListener(viewModel.getVehicleRegistrationWatcher());
                model.addTextChangedListener(viewModel.getVehicleModelWatcher());
                description.addTextChangedListener(viewModel.getVehicleDescriptionWatcher());

                final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.vehicle_categories, R.layout.row_simple_item_dark);
                adapter.setDropDownViewResource(R.layout.row_simple_item_dark);
                categories.setAdapter(adapter);
                categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        vehicle.setCategory(adapter.getItem(i).toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        vehicle.setCategory("B");
                    }
                });

                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                        photoPickerIntent.setType("image/*");
                        getActivity().startActivityForResult(photoPickerIntent, VEHICLE_IMAGE);
                    }
                });

                vehicle.getImage().observeForever(new Observer<Bitmap>() {
                    @Override
                    public void onChanged(@Nullable Bitmap bitmap) {
                        image.setImageBitmap(bitmap);
                    }
                });

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!viewModel.newVehicleValid()){
                            Toast.makeText(getContext(), "Molimo, unesite sve stavke", Toast.LENGTH_SHORT).show();
                        } else {
                            viewModel.addNewVehicle();
                            dialog.dismiss();
                        }
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.cancelNewVehicle();
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Novo vozilo");
                dialog.show();
            }
        });

        return root;
    }

    private View getVehicleView(Vehicle vehicle){
        final View convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_vehicle, list, false);

        ImageView image = convertView.findViewById(R.id.row_vehicle_image);
        TextView text = convertView.findViewById(R.id.row_vehicle_registration);
        ImageView cancel = convertView.findViewById(R.id.row_vehicle_cancel);

        image.setImageBitmap(vehicle.getImage().getValue());
        text.setText(vehicle.getRegistration());
        cancel.setImageResource(R.drawable.close_white);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.removeView(convertView);
            }
        });

        return convertView;
    }
}
