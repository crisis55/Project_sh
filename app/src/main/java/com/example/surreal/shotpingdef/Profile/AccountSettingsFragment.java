package com.example.surreal.shotpingdef.Profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.surreal.shotpingdef.R;

import java.util.ArrayList;


public class AccountSettingsFragment extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_settings);
        mContext = AccountSettingsFragment.this;

        setupSettingsList();

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setupSettingsList() {
        ListView listView = (ListView) findViewById(R.id.lvAccountSettings);

        ArrayList<String> options = new ArrayList<>();
        options.add("Editar Perfil");
        options.add("Sign Out");

        ArrayAdapter adapter = new ArrayAdapter(mContext, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(adapter);
    }
}
