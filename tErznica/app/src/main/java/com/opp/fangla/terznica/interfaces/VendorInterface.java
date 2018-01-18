package com.opp.fangla.terznica.interfaces;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.MainViewModel;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.Person;

import java.util.List;

public class VendorInterface extends Fragment {

    private View root;
    private ListView list;
    private AdvertAdapter adapter;
    private MainViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_vendor_interface, container, false);

        list = root.findViewById(R.id.f_vendor_int_list);
        adapter = new AdvertAdapter(getContext(), R.layout.row_product_vendor);
        model = ((MainActivity)getActivity()).getViewModel();
        model.getVendorAdverts(
                PreferenceManager.getDefaultSharedPreferences(
                        getContext()).getInt("userId", -1))
                .observe(this, new Observer<List<Advert>>() {
            @Override
            public void onChanged(@Nullable List<Advert> adverts) {
                if(adverts != null) {
                    adapter.addAll(adverts);
                }
            }
        });
        list.setAdapter(adapter);

        return root;
    }

    private class AdvertAdapter extends ArrayAdapter<Advert>{

        public AdvertAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            final Advert advert = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_vendor, parent);
                ViewHolder holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.row_product_vendor_img);
                holder.name = convertView.findViewById(R.id.row_product_vendor_name);
                convertView.setTag(holder);
            }

            final ViewHolder holder = (ViewHolder) convertView.getTag();
            if(advert.getPicture() != null) {
                holder.img.setImageBitmap(advert.getPicture());
            }
            holder.name.setText(advert.getName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).showAdvert(advert, true);
                }
            });

            return convertView;
        }

        private class ViewHolder{
            ImageView img;
            TextView name;
        }

    }

}
