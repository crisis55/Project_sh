package com.example.surreal.shotpingdef.Messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.surreal.shotpingdef.PostActivity;
import com.example.surreal.shotpingdef.Profile.AccountSettingsFragment;
import com.example.surreal.shotpingdef.R;

public class MessagesFragment extends Fragment implements View.OnClickListener {

    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_messages, container, false);
        button = view.findViewById(R.id.upload_button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), PostActivity.class);
        startActivity(intent);
    }
}