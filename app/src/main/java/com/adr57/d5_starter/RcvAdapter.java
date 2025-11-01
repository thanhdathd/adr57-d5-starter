package com.adr57.d5_starter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import java.util.zip.Inflater;

public class RcvAdapter extends RecyclerView.Adapter<RcvAdapter.MyViewHolder> {

    private List<CardItem> items;
    private OnItemClickListener onItemClickListener;
    private OnLikeClickListener onLikeClickListener;

    public interface OnItemClickListener {
        void onItemClick(CardItem item);
    }

    public interface OnLikeClickListener {
        void onLikeClick(CardItem item);
    }

    // constructor
    public RcvAdapter(List<CardItem> items, OnItemClickListener itemClickListener, OnLikeClickListener likeClickListener) {
        this.items = items;
        this.onItemClickListener = itemClickListener;
        this.onLikeClickListener = likeClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get view using layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_rcv, parent, false);

        // create view Holder and return
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CardItem item = items.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView titleTextView;
        private TextView descriptionTextView;
        private TextView likesTextView;
        private ImageButton likeButton;
        private CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivImage);
            titleTextView = itemView.findViewById(R.id.tvTitle);
            descriptionTextView = itemView.findViewById(R.id.tvDescription);
            likesTextView = itemView.findViewById(R.id.tvLikes);
            likeButton = itemView.findViewById(R.id.btnLike);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void bind(final CardItem item) {
            // Load image using Glide
            Glide.with(itemView.getContext())
                    .load(item.getImageUrl())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .override(300, getHeight(item.getImageUrl()))
                    .into(imageView);

            // set title and description
            //titleTextView....
            titleTextView.setText(item.getTitle());

            //descriptionTextView....
            descriptionTextView.setText(item.getDescription());

            // set number of likes
            //likesTextView....
            likesTextView.setText(String.valueOf(item.getLikes()));

            // Set like button state
            int likeIcon = item.isLiked() ? R.drawable.ic_heart_filled : R.drawable.ic_heart_outline;
            likeButton.setImageResource(likeIcon);

            // Handle item click
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(item);
                    }
                }
            });

            // Handle like button click
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLikeClickListener != null) {
                        onLikeClickListener.onLikeClick(item);
                    }
                }
            });
        }

        private int getHeight(String imageUrl) {
            // https://picsum.photos/300/400
            String[] parts = imageUrl.split("/");
            if (parts.length >= 3) {
                try {
                    return Integer.parseInt(parts[parts.length - 1]);
                } catch (NumberFormatException e) {
                    return 300;
                }
            } else {
                return 300;
            }
        }
    }
}
