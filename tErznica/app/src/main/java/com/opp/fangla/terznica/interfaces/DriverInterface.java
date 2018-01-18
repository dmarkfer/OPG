package com.opp.fangla.terznica.interfaces;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.MainViewModel;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.messages.ConversationActivity;
import com.opp.fangla.terznica.util.Random;

import java.util.List;

public class DriverInterface extends Fragment{

    public static final int DEPARTURE_AUTOCOMPLETE = 798, DESTINATION_AUTOCOMPLETE = 465;

    private View root;
    private TextView departure, destination;
    private ListView advertList;
    private Button search;
    private MainViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        model = ((MainActivity)getActivity()).getViewModel();

        root = inflater.inflate(R.layout.f_driver_interface, container, false);

        departure = root.findViewById(R.id.f_driver_int_departure);
        destination = root.findViewById(R.id.f_driver_int_destination);
        search = root.findViewById(R.id.f_driver_int_search);
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                model.getShipmentAdverts().observe(DriverInterface.this, new Observer<List<AdvertShipment>>() {
                    @Override
                    public void onChanged(@Nullable List<AdvertShipment> adverts) {
                        if(adverts != null) {
                            ((ArrayAdapter<AdvertShipment>) advertList.getAdapter()).addAll(adverts);
                        }
                    }
                });
            }
        });

        advertList.setAdapter(new ArrayAdapter<AdvertShipment>(getContext(), R.layout.row_shipment) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                AdvertShipment advert = getItem(position);
                if(convertView == null){
                    ViewHolder holder = new ViewHolder();
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_shipment, parent);
                    holder.name = convertView.findViewById(R.id.row_shipment_name);
                    holder.from = convertView.findViewById(R.id.row_shipment_from);
                    holder.to = convertView.findViewById(R.id.row_shipment_to);
                    convertView.setTag(holder);
                }
                ViewHolder holder = (ViewHolder) convertView.getTag();
                //holder.name.setText(advert.getAdvert().getName());
                holder.from.setText(R.string.departure + advert.getStartLocation());
                holder.to.setText(R.string.destination + advert.getEndLocation());
                return convertView;
            }

            class ViewHolder{
                TextView name, from, to;
            }
        });
        advertList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final Dialog dialog = new Dialog(getContext(), R.style.DarkDialog);
                dialog.setContentView(R.layout.f_shipment);

                final AdvertShipment advert = ((ArrayAdapter<AdvertShipment>) advertList.getAdapter()).getItem(i);

                TextView name = dialog.findViewById(R.id.f_shipment_name);
                TextView description = dialog.findViewById(R.id.f_shipment_description);
                final TextView category = dialog.findViewById(R.id.f_shipment_category);
                TextView date = dialog.findViewById(R.id.f_shipment_date);
                TextView from = dialog.findViewById(R.id.f_shipment_from);
                TextView to = dialog.findViewById(R.id.f_shipment_to);
                Button back = dialog.findViewById(R.id.f_shipment_back);
                Button contact = dialog.findViewById(R.id.f_shipment_accept);

                name.setText(advert.getAdvert().getName());
                description.setText(advert.getAdvert().getDescription());
                model.getProductSearchSuggestions().observe(DriverInterface.this, new Observer<MatrixCursor>() {
                    @Override
                    public void onChanged(@Nullable MatrixCursor cursor) {
                        if(cursor != null) {
                            cursor.moveToPosition(i);
                            category.setText("Kategorija: " + cursor.getString(1));
                        }
                    }
                });
                date.setText(Random.dateToString(advert.getDate()));
                from.setText(R.string.departure + advert.getStartLocation());
                to.setText(R.string.destination + advert.getEndLocation());

                contact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Conversation conversation = new Conversation();
                        conversation.setIdDriver(PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("userId", -1));
                        if(conversation.getIdDriver() != -1) {
                            conversation.setIdBuyer(advert.getCreatorId());
                            conversation.setIdAdvert(advert.getAdvertId());
                            model.createConversation(conversation).observe(getActivity(), new Observer<String>() {
                                @Override
                                public void onChanged(@Nullable String s) {
                                    if (s != null) {
                                        conversation.setIdConversation(Integer.valueOf(s));
                                        model.getUser(advert.getCreatorId()).observe(getActivity(), new Observer<User>() {
                                            @Override
                                            public void onChanged(@Nullable User user) {
                                                Intent intent = new Intent(getContext(), ConversationActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putLong("conversationId", conversation.getIdConversation());
                                                bundle.putInt("otherId", conversation.getIdBuyer());
                                                bundle.putString("otherUsername", user.getMail());
                                                bundle.putLong("advertId", conversation.getIdAdvert());
                                                intent.putExtras(bundle);
                                                startActivity(intent);
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.setTitle("Oglas za prijevoz");
                dialog.show();
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
