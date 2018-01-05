package com.opp.fangla.terznica.welcome;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.R;

/**
 * Created by domagoj on 29.12.17..
 */

public class LogInActivity extends AppCompatActivity{

    private EditText username, password;
    private Button logIn, register;
    private LogInViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_log_in);

        username = findViewById(R.id.a_log_in_username);
        password = findViewById(R.id.a_log_in_password);
        logIn = findViewById(R.id.a_log_in_log_in_button);
        register = findViewById(R.id.a_log_in_register_button);
        viewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        /*username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setUsername(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewModel.setPassword(charSequence.toString());
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        viewModel.getUsername().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                username.setText(s);
            }
        });
        viewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                password.setText(s);
            }
        });*/
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.logIn().observe(LogInActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        if(s != null){
                            if(s.equals("error")){
                                Toast.makeText(getApplicationContext(), "Neuspjela prijava", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                prefs.edit().putString("username", username.getText().toString())
                                        .putString("password", password.getText().toString()).apply();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
                finish();
            }
        });

    }
}
