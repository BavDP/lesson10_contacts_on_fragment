package com.dnipro.beldii.lesson10.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnipro.beldii.lesson10.R;
import com.dnipro.beldii.lesson10.adapters.UserAdapter;
import com.dnipro.beldii.lesson10.adapters.UserAdapterClickListener;
import com.dnipro.beldii.lesson10.model.User;
import com.dnipro.beldii.lesson10.model.UserDataContainer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserListFragment extends Fragment {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private FloatingActionButton addButton;
    private ArrayList<User> users;
    private UserDataContainer usersDataContainer;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.contactList);
        addButton = view.findViewById(R.id.addNewContactBtn);

        this.users = usersDataContainer.getUsers();
        addButton.setOnClickListener((View v) -> {
            addContactToList();
        });
        createContactRecycleList();
        registerResultListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_list, container, false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        usersDataContainer = (UserDataContainer) requireActivity();
    }

    private void createContactRecycleList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        userAdapter = new UserAdapter(this.users);
        userAdapter.setItemViewClickListener(new UserAdapterClickListener() {
            @Override
            public void nameContactClick(View view) {
                openContactDetails(view);
            }

            @Override
            public void deleteContactClick(View view) {
                deleteContactFromList(view);
            }

            @Override
            public void editContactClick(View view) {
                editContactFromList(view);
            }
        });
        recyclerView.setAdapter(userAdapter);
    }

    private void openContactDetails(View view) {
        User contact = (User)view.getTag();
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("user", contact);
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainerView, UserDetailFragment.class, userBundle)
                .addToBackStack("")
                .commit();
    }

    private void deleteContactFromList(View view) {
        User user = (User) view.getTag();
        ArrayList<User> newUsers = new ArrayList<>(this.users);
        if (newUsers.remove(user)) {
            this.userAdapter.setContacts(newUsers);
            this.users = newUsers;
            usersDataContainer.setUsers(newUsers);
        }
    }

    private void editContactFromList(View view) {
        User contact = (User)view.getTag();
        Bundle userBundle = new Bundle();
        userBundle.putSerializable("user", contact);
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("")
                .replace(R.id.fragmentContainerView, UserAddFragment.class, userBundle)
                .commit();
    }

    private void addContactToList() {
        getParentFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .addToBackStack("")
                .replace(R.id.fragmentContainerView, UserAddFragment.class, null)
                .commit();
    }

    private void registerResultListener() {
        getParentFragmentManager().setFragmentResultListener("user", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                User user = (User)result.getSerializable("user");
                if (user != null) {
                    ArrayList<User> newUsers = new ArrayList<>();
                    newUsers.addAll(users);
                    List<User> editedUser = newUsers.stream().filter(u -> u.getId().equals(user.getId())).collect(Collectors.toList());
                    if (editedUser.isEmpty()) {
                        newUsers.add(0, user);
                    } else {
                        int i = newUsers.indexOf(editedUser.get(0));
                        newUsers.set(i, user);
                    }
                    users = newUsers;
                    userAdapter.setContacts(newUsers);
                    usersDataContainer.setUsers(newUsers);
                }
            }
        });
    }
}