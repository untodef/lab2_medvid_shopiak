package com.teslyuk.android.androidtutorial.lab3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editLogin,editPassword,editToken,editLocation;

    Button addButton,resetButton;

    RecyclerView recyclerView;

    List<UserData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editLogin = findViewById(R.id.editLoginText);
        editPassword = findViewById(R.id.editPasswordText);
        editToken = findViewById(R.id.editTokenText);
        editLocation = findViewById(R.id.editLocationText);

        addButton = findViewById(R.id.addButton);
        resetButton = findViewById(R.id.resetButton);

        recyclerView = findViewById(R.id.recyclerView);

        database = RoomDB.getInstance(this);
        dataList = database.userDao().getAll();

        linearLayoutManager = new LinearLayoutManager( this);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new MainAdapter(MainActivity.this,dataList);

        recyclerView.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                String sLogin = editLogin.getText().toString().trim();
                String sPassword = editPassword.getText().toString().trim();
                String sToken = editToken.getText().toString().trim();
                String SLocation = editLocation.getText().toString().trim();

                if(!sLogin.equals("") && !sPassword.equals("") && !sToken.equals("") && !SLocation.equals("")) {
                    UserData data = new UserData();

                    data.setLogin(sLogin);
                    data.setPassword(sPassword);
                    data.setToken(sToken);
                    data.setLocation(SLocation);

                    database.userDao().insert(data);
                    editLogin.setText("");
                    editPassword.setText("");
                    editToken.setText("");
                    editLocation.setText("");

                    dataList.clear();
                    dataList.addAll(database.userDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                database.userDao().reset(dataList);
                dataList.addAll(database.userDao().getAll());
                dataList.clear();
                adapter.notifyDataSetChanged();

            }
        });
    }

}