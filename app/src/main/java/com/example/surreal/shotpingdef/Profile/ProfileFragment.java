package com.example.surreal.shotpingdef.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.surreal.shotpingdef.R;
import com.example.surreal.shotpingdef.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    ImageView image_profile,  image_back;
    TextView promoted, brands, points, fullname, username;
    Button facebook, instagram, twitter;

    FirebaseUser firebaseUser;
    String profileid;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_profile, container, false);

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            SharedPreferences prefs = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
            profileid = prefs.getString("profileid", "none");

            image_profile = view.findViewById(R.id.profileImage);
            image_back = view.findViewById(R.id.profileBack);
            promoted = view.findViewById(R.id.tvPromotion);
            brands = view.findViewById(R.id.tvBrandNumber);
            points = view.findViewById(R.id.tvPoints1);
            fullname = view.findViewById(R.id.display_name);
            username = view.findViewById(R.id.display_username);
            facebook = view.findViewById(R.id.facebook_button);
            instagram = view.findViewById(R.id.instagram_button);
            twitter = view.findViewById(R.id.twitter_button);

            userInfo();
            //getNrPosts();
            getBrands();

            Toolbar toolbar = (Toolbar) view.findViewById(R.id.profileToolbar);
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

            ImageView profileMenu = (ImageView) view.findViewById(R.id.profileMenu);
            profileMenu.setOnClickListener(this);


        return view;
    }

    private void userInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null) {
                    return;
                }
                //Terminar de configurar el usuario en su clase
                User user = dataSnapshot.getValue(User.class);

                //Glide.with(getContext()).load(user.getImageurl()).into(image_profile);
                //Glide.with(getContext()).load(user.getImageurl()).into(image_back);
                //username.setText(user.getUsername());
               // fullname.setText(user.getFullname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AccountSettingsFragment.class);
        startActivity(intent);
    }

    private void getBrands() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Brands").child(profileid).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //following.setText(""+dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /*private void getNrPosts(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Promoted");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    //Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid)){
                        i++;
                    }
                }
                posts.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}
