package com.opp.fangla.terznica.interfaces;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
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

import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.Person;

import java.util.List;

public class VendorInterface extends Fragment {

    private View root;
    private ListView list;
    private AdvertAdapter adapter;
    private VendorViewModel model;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.f_vendor_interface, container, false);

        list = root.findViewById(R.id.f_vendor_int_list);
        adapter = new AdvertAdapter(getContext(), R.layout.row_product_vendor);
        model = ViewModelProviders.of(this).get(VendorViewModel.class);
        model.getAdverts().observe(this, new Observer<List<Advert>>() {
            @Override
            public void onChanged(@Nullable List<Advert> adverts) {
                if(adverts != null) {
                    adapter.addAll(adverts);
                }
            }
        });

        return root;
    }

    private class AdvertAdapter extends ArrayAdapter<Advert>{

        public AdvertAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Advert advert = getItem(position);

            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_vendor, parent);
                ViewHolder holder = new ViewHolder();
                holder.img = convertView.findViewById(R.id.row_product_vendor_img);
                holder.name = convertView.findViewById(R.id.row_product_vendor_name);
                //holder.num = convertView.findViewById(R.id.row_product_vendor_num);
                //holder.list = convertView.findViewById(R.id.row_product_vendor_container);
                convertView.setTag(holder);
            }

            final ViewHolder holder = (ViewHolder) convertView.getTag();
            if(advert.getPicture() != null) {
                holder.img.setImageBitmap(advert.getPicture());
            }
            holder.name.setText(advert.getName());
            /*holder.num.setText(advert.getPersons().size());
            holder.list.setVisibility(View.INVISIBLE);
            holder.num.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(holder.list.getVisibility() == View.VISIBLE){
                        holder.list.setVisibility(View.INVISIBLE);
                    } else {
                        holder.list.setVisibility(View.VISIBLE);
                    }
                }
            });
            for(Person person : advert.getPersons()){
                View view = getListItemView(person, holder.list);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO slozi activity s porukama
                    }
                });
                list.addView(view);
            }*/
            return convertView;
        }

        private View getListItemView(Person person, LinearLayout parent){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.row_simple_user, parent);
            TextView name = view.findViewById(R.id.row_simple_user_name);
            ImageView img = view.findViewById(R.id.row_simple_user_img);
            name.setText(person.getName());
            img.setImageBitmap(person.getPicture());
            return view;
        }

        private class ViewHolder{
            ImageView img;
            TextView name;// num;
            //LinearLayout list;
        }

    }

}
