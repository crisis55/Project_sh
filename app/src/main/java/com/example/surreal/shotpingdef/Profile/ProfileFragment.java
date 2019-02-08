package com.example.surreal.shotpingdef.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.surreal.shotpingdef.R;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_profile, container, false);

            Toolbar toolbar = (Toolbar) view.findViewById(R.id.profileToolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            ImageView profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
            profileMenu.setOnClickListener(this);

            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.profileProgressBar);
            progressBar.setVisibility(View.GONE);


        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AccountSettingsFragment.class);
        startActivity(intent);
    }
}
