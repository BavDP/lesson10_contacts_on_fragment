package com.dnipro.beldii.lesson10.model;

import java.util.ArrayList;

public interface UserDataContainer {
    void setUsers(ArrayList<User> users);
    ArrayList<User> getUsers();
}
