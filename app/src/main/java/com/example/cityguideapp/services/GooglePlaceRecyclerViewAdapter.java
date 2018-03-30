package com.example.cityguideapp.services;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cityguideapp.R;
import com.example.cityguideapp.models.GooglePlace;
import com.example.cityguideapp.models.Photo;
import com.example.cityguideapp.services.googleAPI.ImageDownloader;
import com.example.cityguideapp.views.GooglePlaceFragment.OnListFragmentInteractionListener;

import java.util.List;


public class GooglePlaceRecyclerViewAdapter extends RecyclerView.Adapter<GooglePlaceRecyclerViewAdapter.ViewHolder> {

    private final List<GooglePlace> mValues;
    private final OnListFragmentInteractionListener mListener;

    public GooglePlaceRecyclerViewAdapter(List<GooglePlace> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_googleplace, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.title.setText(mValues.get(position).getName());
        holder.address.setText(mValues.get(position).getVicinity());
        holder.type.setText(mValues.get(position).getTypesList().get(0));
        holder.rating.setText(mValues.get(position).getRating());
        holder.open_now.setText(mValues.get(position).getOpen_now());

        Photo p = mValues.get(position).getPhotoArrayList().get(0);
        if(p.getImage()!=null){
            holder.picture.setImageBitmap(p.getImage());
        } else{
            //get image url and cache
            ImageDownloader downloader = new ImageDownloader(holder.title.getContext(),200,200) {
                @Override
                protected void onPostExecute(Bitmap result) {
                    super.onPostExecute(result);

                    if(holder.getAdapterPosition() < mValues.size() && holder.getAdapterPosition()>=0 )
                        mValues.get(holder.getAdapterPosition()).getPhotoArrayList().get(0).setImage(result);
                    holder.picture.setImageBitmap(result);
                }
            };
            downloader.execute(holder.mItem.getPhotoArrayList().get(0).getReference());
        }


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView picture;
        public final TextView title;
        public final TextView address;
        public final TextView type;
        public final TextView rating;
        public final  TextView open_now;
        public GooglePlace mItem;

        public ViewHolder(View v) {
            super(v);
            mView = v;

            picture = (ImageView) v.findViewById(R.id.imageView);
            title = (TextView) v.findViewById(R.id.titleTextView);
            address = (TextView) v.findViewById(R.id.addressTextView);
            type = (TextView) v.findViewById(R.id.typeTextView);
            rating = (TextView) v.findViewById(R.id.ratingTextView);
            open_now = (TextView) v.findViewById(R.id.open_nowTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + title.getText() + "'";
        }
    }
}
