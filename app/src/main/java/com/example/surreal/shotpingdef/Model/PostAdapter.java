package com.example.surreal.shotpingdef.Model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.surreal.shotpingdef.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;

    private FirebaseUser firebaseuser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post, parent, false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        firebaseuser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = mPost.get(position);

        //Añadir aquí fotos del perfil en recyclerview

        holder.conditions_description.setText(post.getConditions_description());
        holder.prize_description.setText(post.getPrize_description());

        PublisherInfo(holder.image_profile, holder.username, post.getPublisher());

    }

    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile, prize, conditions, comment_button;
        public RecyclerView brand_posts;
        public TextView username, number_promotions, conditions_description, prize_description, commentaries;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            prize = itemView.findViewById(R.id.prize);
            conditions = itemView.findViewById(R.id.conditions);
            comment_button = itemView.findViewById(R.id.comment_button);
            brand_posts = itemView.findViewById(R.id.brand_posts);
            username = itemView.findViewById(R.id.username);
            number_promotions = itemView.findViewById(R.id.number_promotions);
            conditions_description = itemView.findViewById(R.id.conditions_description);
            prize_description = itemView.findViewById(R.id.prize_description);
            commentaries = itemView.findViewById(R.id.commentaries);
        }
    }
    private void PublisherInfo(final ImageView image_profile, final TextView username, String userid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
