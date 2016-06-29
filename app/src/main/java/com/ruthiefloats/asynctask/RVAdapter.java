package com.ruthiefloats.asynctask;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruthiefloats.asynctask.model.Flower;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by fieldsru on 6/28/16.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {
    private List<Flower> mFlowers;
    private Context mContext;

    public RVAdapter(Context context, List<Flower> flowers) {
        mFlowers = flowers;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView flowerText;
        public ImageView flowerPhoto;

        public ViewHolder(View view) {
            super(view);
            flowerText = (TextView) view.findViewById(R.id.flower_name);
            flowerPhoto = (ImageView) view.findViewById(R.id.flower_photo);
        }

    }


    @Override
    public RVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View flowerView = inflater.inflate(R.layout.item_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(flowerView);
        return viewHolder;
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will have
     * the updated adapter position.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RVAdapter.ViewHolder holder, int position) {

        Flower flower = mFlowers.get(position);

        TextView textView = holder.flowerText;
        textView.setText(flower.getName());

        ImageView imageView = holder.flowerPhoto;
        Picasso.with(getContext()).
                load(MainActivity.PHOTOS_BASE_URL + flower.getPhoto())
                .resize(50, 50)
                .into(imageView);
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mFlowers.size();
    }
}
