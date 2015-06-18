package com.example.binyamin.fakebook;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;


public class LoginActivity extends ActionBarActivity {
    static String currentUser;
    AccountManager accountManager;
    private Account[] accounts;
    Spinner spinner;
    DefaultHttpClient httpClient = new DefaultHttpClient();
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.get(getApplicationContext());
        accounts = accountManager.getAccountsByType("com.google");

        ArrayList<String> accountList = new ArrayList<String>();
        for (Account account : accounts) {
            accountList.add(account.name);
        }

        spinner = (Spinner) findViewById(R.id.account);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, accountList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        Button startAuth = (Button) findViewById(R.id.startAuth);
        startAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner = (Spinner) findViewById(R.id.account);
                account = accounts[spinner.getSelectedItemPosition()];
                currentUser=account.toString();
                accountManager.getAuthToken(account, "ah", null, false, new OnTokenAcquired(httpClient, LoginActivity.this), null);
                            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            accountManager.getAuthToken(account, "ah", null, false, new OnTokenAcquired(httpClient, LoginActivity.this), null);
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            LoginActivity.this.startActivity(loginIntent);
            LoginActivity.this.finish();
        } else if (resultCode == RESULT_CANCELED) {
            // user canceled
        }
    }
    public static String getCurrentUser(){
        return currentUser;
    }
}
