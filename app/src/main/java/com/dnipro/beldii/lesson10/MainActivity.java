package com.dnipro.beldii.lesson10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dnipro.beldii.lesson10.fragments.UserListFragment;
import com.dnipro.beldii.lesson10.helpers.JsonReader;
import com.dnipro.beldii.lesson10.model.User;
import com.dnipro.beldii.lesson10.model.UserDataContainer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity implements UserDataContainer {
    private final String USER_LIST_TAG = "userList";
    private ArrayList<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle usersBundle = new Bundle();
        if (savedInstanceState == null) {
            getUsersFromJson();
            usersBundle.putSerializable(USER_LIST_TAG, users);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragmentContainerView, UserListFragment.class, usersBundle)
                    .commit();
        } else {
            this.users = (ArrayList<User>) savedInstanceState.getSerializable(USER_LIST_TAG);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(USER_LIST_TAG, users);
    }

    @Override
    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public ArrayList<User> getUsers() {
        return this.users;
    }

    private ArrayList<User> getUsersFromJson() {
        InputStream fruitJsonStream = getResources().openRawResource(R.raw.contacts);
        JsonReader jsonReader = new JsonReader(fruitJsonStream);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Type contactType = new TypeToken<Collection<User>>(){}.getType();
        Collection<User> users = gson.fromJson(jsonReader.getAsString(), contactType);
        this.users = new ArrayList<>(users);
        return this.users;
    }

}