package in.thelosergeek.community_app.Adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import in.thelosergeek.community_app.Models.ModelHome;
import in.thelosergeek.community_app.R;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder>{

   Context context;
    List<ModelHome> postlist;

    public HomeAdapter(Context context, List<ModelHome> postlist) {
        this.context = context;
        this.postlist = postlist;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_post,viewGroup,false );
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String uid = postlist.get(position).getUid();
        String uEmail = postlist.get(position).getUEmail();
        String uName = postlist.get(position).getuName();
        String uDP = postlist.get(position).getuDp();
        String pID = postlist.get(position).getpID();
        String pTitle = postlist.get(position).getpTitle();
        String pDescription = postlist.get(position).getpDescription();
        String pImage = postlist.get(position).getpImage();
        String pTimeStamp = postlist.get(position).getpTime();

        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(Long.parseLong(pTimeStamp));
        String pTime = DateFormat.format("dd/MM/yyyy hh:mm aa", calendar).toString();

        holder.tvName.setText(uName);
        holder.tvTime.setText(pTime);
        holder.tvTitle.setText(pTitle);
        holder.tvDescription.setText(pDescription);

        try {
            Picasso.get().load(uDP).placeholder(R.drawable.defaultpic).into(holder.profilepic);
        }
        catch (Exception e){

        }
        if(pImage.equals("noImage")){
            holder.postpic.setVisibility(View.GONE);//
        }
        else
        {
            try {
                Picasso.get().load(pImage).into(holder.postpic);
            }
            catch (Exception e){

            }
        }

        /*holder.btnOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"More",Toast.LENGTH_SHORT).show();
            }
        });
        holder.btnreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Reply",Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return postlist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        ImageView profilepic, postpic;
        TextView tvName, tvTime, tvTitle, tvDescription;
        ImageButton btnOptions;
        Button btnreply;

        public MyHolder(@NonNull View itemView){
            super((itemView));

            profilepic = itemView.findViewById(R.id.post_picture);
            postpic = itemView.findViewById(R.id.posts_image);
            tvName = itemView.findViewById(R.id.post_name);
            tvTime = itemView.findViewById(R.id.post_time);
            tvTitle = itemView.findViewById(R.id.posts_title);
            tvDescription = itemView.findViewById(R.id.posts_description);
           // btnOptions = itemView.findViewById(R.id.posts_option);
           // btnreply = itemView.findViewById(R.id.posts_reply);


        }
    }
}