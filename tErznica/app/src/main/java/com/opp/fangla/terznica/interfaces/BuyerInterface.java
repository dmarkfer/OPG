package com.opp.fangla.terznica.interfaces;

import android.arch.lifecycle.Observer;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.MainViewModel;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.Category;

import java.util.List;

public class BuyerInterface extends Fragment {

    private View root;
    private SearchView searchView;
    private ListView listView;
    private MainViewModel viewModel;
    public static final String[] matrixColumns = {"_id", "name"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root = inflater.inflate(R.layout.f_buyer_interface, container, false);
        searchView = root.findViewById(R.id.f_buyer_int_search);
        listView = root.findViewById(R.id.f_buyer_int_list);
        viewModel = ((MainActivity)getActivity()).getViewModel();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.getProductSearchResults(query).observe(BuyerInterface.this, new Observer<List<Advert>>() {
                    @Override
                    public void onChanged(@Nullable List<Advert> adverts) {
                        ((ArrayAdapter) listView.getAdapter()).clear();
                        if(adverts != null) {
                            ((ArrayAdapter) listView.getAdapter()).addAll(adverts);
                        }
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setSuggestionsAdapter(new CursorAdapter(getContext(), new MatrixCursor(matrixColumns), false) {
            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.row_product_suggestion, parent, false);
            }
            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                TextView text = view.findViewById(R.id.row_product_suggestion_text);
                text.setText(cursor.getString(cursor.getColumnIndexOrThrow(matrixColumns[1])));
            }
        });

        listView.setAdapter(new ArrayAdapter<Advert>(getContext(), R.layout.row_product_search) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                final Advert advert = getItem(position);
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
                holder.description.setText(advert.getDescription());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) getActivity()).showAdvert(advert,
                                PreferenceManager.getDefaultSharedPreferences(
                                        getContext()).getInt("userId", -1) == advert.getVendorId());
                    }
                });
                return convertView;
            }

            class ViewHolder{
                ImageView image;
                TextView title, description;
            }
        });
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });*/

        viewModel.getCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categoryList) {
                if(categoryList != null) {
                    MatrixCursor cursor = new MatrixCursor(matrixColumns);
                    for (Category category : categoryList) {
                        Object[] row = new Object[2];
                        row[0] = category.getId();
                        row[1] = category.getName();
                        cursor.addRow(row);
                    }
                    searchView.getSuggestionsAdapter().swapCursor(cursor);
                }
            }
        });

        return root;
    }
}
