package com.dnipro.beldii.lesson10.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnipro.beldii.lesson10.databinding.FragmentUserAddBinding;
import com.dnipro.beldii.lesson10.model.User;

public class UserAddFragment extends Fragment {

    private FragmentUserAddBinding binding;
    private User inUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAddBinding.inflate(inflater);
        binding.addSaveBtn.setOnClickListener((View view) -> {
            saveData();
        });
        return binding.getRoot();
    }

    private void saveData() {
        String contactName = binding.addContactNameEditText.getText().toString();
        if (!contactName.isEmpty()) {
            User user = new User(inUser != null ? inUser.getId() : "", contactName);
            String age = binding.addAgeEditText.getText().toString();
            user.setAge(Integer.parseInt(age.isEmpty() ? "0" : age));
            user.setEmail(binding.addEmailEditText.getText().toString());
            user.setCompany(binding.companyEditText.getText().toString());
            user.setGender(binding.addGenderEditText.getText().toString());
            user.setPhoto(inUser != null ? inUser.getPhoto() : "");
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user);
            getParentFragmentManager().setFragmentResult("user", bundle);
            getParentFragmentManager().popBackStack();
        }
    }

    private void showUserData() {
        if (this.getArguments() != null) {
            User user = (User) this.getArguments().getSerializable("user");
            if (user != null) {
                binding.addContactNameEditText.setText(user.getName());
                binding.addAgeEditText.setText(String.valueOf(user.getAge()));
                binding.addEmailEditText.setText(user.getEmail());
                binding.addGenderEditText.setText(user.getGender());
                binding.companyEditText.setText(user.getCompany());
                inUser = user;
            }
        }
    }
}