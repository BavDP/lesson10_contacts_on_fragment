package com.dnipro.beldii.lesson10.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dnipro.beldii.lesson10.R;
import com.dnipro.beldii.lesson10.databinding.FragmentUserDetailBinding;
import com.dnipro.beldii.lesson10.helpers.AsyncPhotoLoader;
import com.dnipro.beldii.lesson10.model.User;

public class UserDetailFragment extends Fragment {
    private FragmentUserDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDetailBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUserDetails();
    }

    private void showUserDetails() {
        Bundle userBundle = this.getArguments();
        if (userBundle == null) return;
        User user = (User)userBundle.getSerializable("user");
        if (user != null) {
            binding.contactNameTextView.setText(user.getName());
            binding.ageTextView.setText(Integer.toString(user.getAge()));
            binding.companyTextView.setText(user.getCompany());
            binding.genderTextView.setText(user.getGender());
            binding.emailTextView.setText(user.getEmail());
            AsyncPhotoLoader photoLoader = new AsyncPhotoLoader();
            photoLoader.load(user.getPhoto(), binding.photoView);
        }
    }
}