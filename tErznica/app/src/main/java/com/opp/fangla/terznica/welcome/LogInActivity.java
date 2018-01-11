package com.opp.fangla.terznica.welcome;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opp.fangla.terznica.MainActivity;
import com.opp.fangla.terznica.R;
import com.opp.fangla.terznica.util.LogInCallback;

public class LogInActivity extends AppCompatActivity{

    private EditText username, password;
    private Button logIn, register;
    private LogInViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_log_in);
        viewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        username = findViewById(R.id.a_log_in_username);
        password = findViewById(R.id.a_log_in_password);
        logIn = findViewById(R.id.a_log_in_log_in_button);
        register = findViewById(R.id.a_log_in_register_button);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.logIn().observe(LogInActivity.this, new Observer<LogInCallback>() {
                    @Override
                    public void onChanged(@Nullable LogInCallback callback) {
                        if(!callback.isSuccess()){
                            Toast.makeText(getApplicationContext(), "Server nije dostupan", Toast.LENGTH_SHORT).show();
                        } else if(!callback.isSuccess()) {
                            Toast.makeText(getApplicationContext(), "Neuspjela prijava", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("username", viewModel.getUsername())
                                    .putString("password", viewModel.getPassword())
                                    .putInt("userId", callback.getId())
                                    .putBoolean("admin", callback.isAdmin())
                                    .putBoolean("buyer", callback.isBuyer())
                                    .putBoolean("vendor", callback.isVendor())
                                    .putBoolean("driver", callback.isDriver())
                                    .apply();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        username.setText(viewModel.getUsername());
        password.setText(viewModel.getPassword());
        username.addTextChangedListener(viewModel.usernameWatcher());
        password.addTextChangedListener(viewModel.passwordWatcher());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.edit().putString("username", "")
                .putString("password", "")
                .apply();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Niste prijavljeni", Toast.LENGTH_SHORT).show();
    }
}
