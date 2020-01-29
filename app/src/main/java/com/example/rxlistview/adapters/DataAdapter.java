package com.example.rxlistview.adapters;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rxlistview.R;
import com.example.rxlistview.model.User;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.UserViewHolder>  implements Filterable {
    private Context mContext;

    private List<User> listData,listFiltered;

    public DataAdapter(Context mContext, List<User> listData) {
        this.listData = listData;
        this.listFiltered = listData;
        this.mContext = mContext;
    }


    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate( R.layout.card_design, parent, false);
        return new UserViewHolder(itemView);
    }

    public void setData(ArrayList<User> data){
        //listFiltered.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        Random rnd = new Random();
        int currentColor = Color.argb(250, rnd.nextInt(100), rnd.nextInt(50), rnd.nextInt(100));
        holder.linear.setBackgroundColor(currentColor);

        holder.txt_pname.setText(listFiltered.get(position).getProductName ());
        holder.txt_price.setText(listFiltered.get(position).getPrice ());
        holder.txt_likes.setText(listFiltered.get(position).getLikes ());
        Glide.with( mContext).load( listFiltered.get(position).getImage ()).into( holder.img_prd);
        if(listFiltered.get(position).getFav ()=="0"){
            holder.btn_like.setEnabled ( false );

        }else if(listFiltered.get(position).getFav ()=="1"){
            holder.btn_like.setEnabled ( true );

        }
        holder.img_prd.setOnClickListener ( v -> {
            Toast.makeText ( mContext, position+"", Toast.LENGTH_SHORT ).show ( );
        });
        holder.btn_like.setOnLikeListener(new OnLikeListener () {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLikeDrawableRes(R.mipmap.heart);


            }
            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLikeDrawableRes(R.mipmap.unlike);


            }
        });
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listFiltered = listData;
                } else {
                    List<User> filteredList = new ArrayList<>();
                    for (User row : listData) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name match
                        if (row.getProductName ().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    listFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = listFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listFiltered = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public int getItemCount() {
        return listFiltered.size();
    }
    /**
     * ViewHolder class
     */


    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView txt_pname,txt_price,txt_likes;
        ImageView img_prd;
        LinearLayout linear;
        LikeButton btn_like;


        public UserViewHolder(View view) {
            super(view);

            txt_pname = view.findViewById( R.id.txt_pname);
            txt_price = view.findViewById( R.id.txt_price);
            txt_likes = view.findViewById( R.id.txt_likes);
            img_prd = view.findViewById( R.id.img_prd);
            btn_like = view.findViewById( R.id.btn_like);
            linear = view.findViewById( R.id.linear);

        }
    }
}
