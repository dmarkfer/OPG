package com.opp.fangla.terznica;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.content.Context;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.content.res.Resources.Theme;

import android.widget.TextView;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.opp.fangla.terznica.data.entities.Advert;
import com.opp.fangla.terznica.data.entities.AdvertShipment;
import com.opp.fangla.terznica.data.entities.Conversation;
import com.opp.fangla.terznica.data.entities.User;
import com.opp.fangla.terznica.interfaces.BuyerInterface;
import com.opp.fangla.terznica.interfaces.DriverInterface;
import com.opp.fangla.terznica.interfaces.VendorInterface;
import com.opp.fangla.terznica.messages.ConversationActivity;
import com.opp.fangla.terznica.messages.InboxActivity;
import com.opp.fangla.terznica.util.Random;
import com.opp.fangla.terznica.welcome.LogInActivity;
import com.opp.fangla.terznica.welcome.fragments.DriverFragment;
import com.opp.fangla.terznica.welcome.fragments.VendorSubFragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static String[] roleNames = {"buyer", "vendor", "driver", "admin"};
    public static int IMAGE_SELECTION = 123;
    private MainViewModel model;
    private FloatingActionButton messageFab, auxFab;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        model = ViewModelProviders.of(this).get(MainViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup spinner
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String prefsUsername = preferences.getString("username", "");
        //Log.d("AAAAAAAAAAAAAAA", prefsUsername);
        if(prefsUsername.equals("")){
            startActivity(new Intent(getApplicationContext(), LogInActivity.class));
            finish();
        }
        final List<String> roles = new ArrayList<>();
        for(String s : roleNames){
            if(preferences.getBoolean(s, false)){
                roles.add(s);
            }
        }
        String[] rolesArray = new String[roles.size()];
        for (int i = 0; i < roles.size(); i++){
            rolesArray[i] = roles.get(i);
        }
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new MyAdapter(toolbar.getContext(), rolesArray));

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment;
                switch (roles.get(position)){
                    case "buyer":
                        auxFab.setVisibility(View.INVISIBLE);
                        fragment = new BuyerInterface();
                        break;
                    case "vendor":
                        auxFab.setVisibility(View.VISIBLE);
                        fragment = new VendorInterface();
                        break;
                    default:
                        auxFab.setVisibility(View.INVISIBLE);
                        fragment = new DriverFragment();
                }
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        messageFab = findViewById(R.id.a_main_fab_messages);
        messageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
            }
        });

        auxFab = findViewById(R.id.a_main_fab_aux);
        auxFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new EditProductDialog(MainActivity.this, R.style.LightDialog, model, model.getNewAdvert(), true);
                dialog.show();
            }
        });

    }

    public void showEditAdvert(Advert advert){
        dialog = new EditProductDialog(MainActivity.this, R.style.LightDialog, model, advert, false);
        dialog.show();
    }

    public void showAdvert(Advert advert, boolean isVendor){
        dialog = new ProductDialog(MainActivity.this, R.style.LightDialog, model, advert, isVendor);
        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_a_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_log_out:
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DriverInterface.DEPARTURE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                model.setDeparture(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("MainActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("MainActivity", "Autocomplete cancelled");
            }
        } else if(requestCode == DriverInterface.DESTINATION_AUTOCOMPLETE){
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                model.setDestination(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i("MainActivity", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Log.i("MainActivity", "Autocomplete cancelled");
            }
        } else if(requestCode == IMAGE_SELECTION){
            if(resultCode == RESULT_OK){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    if(dialog instanceof EditProductDialog){
                        ((EditProductDialog) dialog).setImage(selectedImage);
                    } else if(dialog instanceof ProductDialog){
                        ((ProductDialog) dialog).setImage(selectedImage);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public MainViewModel getViewModel(){
        return model;
    }

    private static class MyAdapter extends ArrayAdapter<String> implements ThemedSpinnerAdapter {
        private final ThemedSpinnerAdapter.Helper mDropDownHelper;

        public MyAdapter(Context context, String[] objects) {
            super(context, android.R.layout.simple_list_item_1, objects);
            mDropDownHelper = new ThemedSpinnerAdapter.Helper(context);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            View view;

            if (convertView == null) {
                LayoutInflater inflater = mDropDownHelper.getDropDownViewInflater();
                view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            } else {
                view = convertView;
            }

            TextView textView = view.findViewById(android.R.id.text1);
            textView.setText(getItem(position));

            return view;
        }

        @Override
        public Theme getDropDownViewTheme() {
            return mDropDownHelper.getDropDownViewTheme();
        }

        @Override
        public void setDropDownViewTheme(Theme theme) {
            mDropDownHelper.setDropDownViewTheme(theme);
        }
    }

    private class EditProductDialog extends Dialog{

        private MainViewModel model;
        private boolean isNew;
        private ImageView image;
        private EditText name, description, price;
        private Spinner categories;
        private Button left, right;
        private Advert advert;

        public EditProductDialog(@NonNull Context context, int themeResId, MainViewModel model, Advert advert, boolean isNew) {
            super(context, themeResId);
            this.model = model;
            this.advert = advert;
            this.isNew = isNew;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.d_product_edit);
            image = findViewById(R.id.d_product_edit_image);
            name = findViewById(R.id.d_product_edit_name);
            description = findViewById(R.id.d_product_edit_description);
            categories = findViewById(R.id.d_product_edit_categories);
            price = findViewById(R.id.d_product_edit_price);
            left = findViewById(R.id.d_product_edit_left);
            right = findViewById(R.id.d_product_edit_right);

            if(advert.getPicture() == null){
                image.setImageResource(R.mipmap.camera_white);
            } else {
                image.setImageBitmap(advert.getPicture());
            }
            name.setText(advert.getName());
            description.setText(advert.getDescription());
            model.getProductSearchSuggestions().observe(MainActivity.this, new Observer<MatrixCursor>() {
                @Override
                public void onChanged(@Nullable MatrixCursor cursor) {
                    categories.setAdapter(new SimpleCursorAdapter(getContext(), android.R.layout.simple_spinner_item, cursor, new String[] {BuyerInterface.matrixColumns[1]}, new int[] {android.R.id.text1}));
                    if(cursor.getCount() > 0) {
                        int i = 0;
                        cursor.moveToFirst();
                        while (cursor.getInt(0) != advert.getCategoryId() && cursor.moveToNext()) {
                            cursor.move(1);
                            i++;
                        }
                        categories.setSelection(i);
                    }
                }
            });
            categories.setSelection(advert.getCategoryId());
            price.setText(Integer.toString(advert.getValue()));

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    MainActivity.this.startActivityForResult(photoPickerIntent, IMAGE_SELECTION);
                }
            });
            name.addTextChangedListener(model.getAdvertNameWatcher(advert));
            description.addTextChangedListener(model.getAdvertDescriptionWatcher(advert));
            price.addTextChangedListener(model.getAdvertPriceWatcher(advert));
            categories.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    MatrixCursor cursor = (MatrixCursor) ((SimpleCursorAdapter) categories.getAdapter()).getCursor();
                    cursor.moveToPosition(i);
                    advert.setCategoryId(cursor.getInt(0));
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            left.setText(R.string.back);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditProductDialog.this.dismiss();
                }
            });
            if(isNew){
                right.setText("Stvori");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.createProduct(advert);
                        EditProductDialog.this.dismiss();
                    }
                });
            } else {
                right.setText("Uredi");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        model.editProduct(advert);
                        EditProductDialog.this.dismiss();
                    }
                });
            }
        }

        private void setImage(Bitmap image){
            advert.setPicture(image);
            this.image.setImageBitmap(image);
        }
    }

    private class ProductDialog extends Dialog{

        private MainViewModel model;
        private boolean isVendor;
        private ImageView image;
        private TextView name, description, price, categories;
        private Button action, left, right;
        private Advert advert;

        public ProductDialog(@NonNull Context context, int themeResId, MainViewModel model, Advert advert, boolean isVendor) {
            super(context, themeResId);
            this.model = model;
            this.advert = advert;
            this.isVendor = isVendor;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            image = findViewById(R.id.d_product_image);
            name = findViewById(R.id.d_product_name);
            description = findViewById(R.id.d_product_description);
            categories = findViewById(R.id.d_product_category);
            price = findViewById(R.id.d_product_price);
            action = findViewById(R.id.d_product_action);
            left = findViewById(R.id.d_product_left);
            right = findViewById(R.id.d_product_right);

            if(advert.getPicture() == null){
                image.setImageResource(R.mipmap.camera_white);
            } else {
                image.setImageBitmap(advert.getPicture());
            }
            name.setText(advert.getName());
            description.setText(advert.getDescription());
            model.getProductSearchSuggestions().observe(MainActivity.this, new Observer<MatrixCursor>() {
                @Override
                public void onChanged(@Nullable MatrixCursor cursor) {
                    int i = 0;
                    while(!cursor.isLast() && cursor.getInt(0) != advert.getCategoryId()){
                        cursor.move(1);
                        i++;
                    }
                    categories.setText(cursor.getString(1));
                }
            });
            price.setText(advert.getValue());
            left.setText(R.string.back);
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ProductDialog.this.dismiss();
                }
            });
            if(isVendor){
                right.setText("Uredi");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity) getOwnerActivity()).showEditAdvert(advert);
                        ProductDialog.this.dismiss();
                    }
                });
            } else {
                right.setText("Kontaktiraj");
                right.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Conversation conversation = new Conversation();
                        conversation.setIdDriver(advert.getCreatorId());
                        conversation.setIdBuyer(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getInt("userId", -1));
                        if(conversation.getIdBuyer() != -1){
                            conversation.setIdAdvert(advert.getAdvertId());
                            model.createConversation(conversation).observe(MainActivity.this, new Observer<String>() {
                                @Override
                                public void onChanged(@Nullable String s) {
                                    if (s != null){
                                        conversation.setIdConversation(Integer.valueOf(s));
                                        model.getUser(advert.getCreatorId()).observe(MainActivity.this, new Observer<User>() {
                                            @Override
                                            public void onChanged(@Nullable User user) {
                                                Intent intent = new Intent(getApplicationContext(), ConversationActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putLong("conversationId", conversation.getIdConversation());
                                                bundle.putInt("otherId", conversation.getIdDriver());
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
            }
        }

        private void setImage(Bitmap image){
            advert.setPicture(image);
        }
    }
}
