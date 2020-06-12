package in.thelosergeek.community_app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyHolder> {

    Context context;
    List<UserModel> userList;

    public UsersAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }



    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_user,viewGroup,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int o) {
        final String senderUID = userList.get(o).getUid();
        String userImage = userList.get(o).getImage();
        String userName = userList.get(o).getName();
        final String userEmail = userList.get(o).getEmail();

        holder.ua_name.setText(userName);
        holder.ua_email.setText(userEmail);
        try{
            Picasso.get().load(userImage).placeholder(R.drawable.defaultpic).into(holder.ua_profile);
        }
        catch (Exception e){

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityChat.class );
                intent.putExtra("senderUID", senderUID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView ua_profile;
        TextView ua_name, ua_email;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ua_profile = itemView.findViewById(R.id.su_image);
            ua_email = itemView.findViewById(R.id.su_email);
            ua_name = itemView.findViewById(R.id.su_name);

        }
    }
}
