package com.opp.fangla.terznica.interfaces;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.MainViewModel;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Advert;

public class DriverInterface extends Fragment{

    public static final int DEPARTURE_AUTOCOMPLETE = 798, DESTINATION_AUTOCOMPLETE = 465;

    private View root;
    private TextView departure, destination;
    private ListView advertList;
    private MainViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        model = ((MainActivity)getActivity()).getViewModel();

        root = inflater.inflate(R.layout.f_driver_interface, container, false);

        departure = root.findViewById(R.id.f_driver_int_departure);
        destination = root.findViewById(R.id.f_driver_int_destination);
        advertList = root.findViewById(R.id.f_driver_int_list);

        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    getActivity().startActivityForResult(intent, DEPARTURE_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        departure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                    .build(getActivity());
                    getActivity().startActivityForResult(intent, DESTINATION_AUTOCOMPLETE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        advertList.setAdapter(new ArrayAdapter<Advert>(getContext(), R.layout.row_product_search) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                /*Advert advert = getItem(position);
                if(convertView == null){
                    ViewHolder holder = new ViewHolder();
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_search, parent);
                    holder.image = convertView.findViewById(R.id.row_product_search_image);
                    holder.title = convertView.findViewById(R.id.row_product_search_title);
                    holder.description = convertView.findViewById(R.id.row_product_search_description);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                if(advert.getPicture() == null) {
                    holder.image.setImageBitmap(advert.getPicture());
                }
                holder.title.setText(advert.getName());
                holder.description.setText(advert.getDescription());*/
                return convertView;
            }

            class ViewHolder{
                ImageView image;
                TextView title, description;
            }
        });
        advertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Zamijeniti kad se napravi activity za prikaz proizvoda, ocito
                Toast.makeText(getContext(), "Otvaram " + ((Advert) adapterView.getItemAtPosition(i)).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        model.getDeparture().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(@Nullable Place place) {
                if(place == null){
                    departure.setText(R.string.add_address);
                } else {
                    departure.setText(place.getAddress());
                }
            }
        });

        model.getDestination().observe(this, new Observer<Place>() {
            @Override
            public void onChanged(@Nullable Place place) {
                if(place == null){
                    destination.setText(R.string.add_address);
                } else {
                    destination.setText(place.getAddress());
                }
            }
        });

        return root;
    }
}
