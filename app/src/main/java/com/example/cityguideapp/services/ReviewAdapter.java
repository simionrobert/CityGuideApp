package com.example.cityguideapp.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.Photo;
import com.example.cityguideapp.models.Review;
import com.example.cityguideapp.services.googleAPI.ImageDownloader;

import java.util.ArrayList;

/**
 * Created by Baal on 3/6/2018.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private  Context context;
    private ArrayList<Review> items;

    public ReviewAdapter(Context context, ArrayList<Review> items) {
        this.items = items;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.description_review_template, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Review review = items.get(position);
        holder.name.setText(review.getAuthor());
        holder.ratingBar.setRating(Float.parseFloat(review.getRating()));
        holder.time.setText(review.getRelativeTime());
        holder.text.setText(review.getText());

        Photo p = review.getImage();
        if (p.getImage() != null) {
            holder.picture.setImageBitmap(p.getImage());
        } else {
            ImageDownloader downloader = new ImageDownloader(this.context, 200, 200) {
                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);

                    if (holder.getAdapterPosition() < items.size())
                        items.get(holder.getAdapterPosition()).getImage().setImage(result);
                    holder.picture.setImageBitmap(result);
                }
            };

            downloader.execute(review.getProfilePhotoURL());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public TextView name;
        public RatingBar ratingBar;
        public TextView text;
        public TextView time;

        public ViewHolder(View view) {
            super(view);
            picture = view.findViewById(R.id.desc_temp_image);
            name = view.findViewById(R.id.desc_temp_name);
            ratingBar = view.findViewById(R.id.desc_temp_ratingBar);
            text = view.findViewById(R.id.desc_temp_text);
            time = view.findViewById(R.id.desc_temp_time);
        }
    }
}