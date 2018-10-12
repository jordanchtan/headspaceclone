package com.example.jordan.smiletribematerial;

import android.content.Context;
import android.support.design.card.MaterialCardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DiscoverCardAdapter extends RecyclerView.Adapter<DiscoverCardAdapter.ViewHolder> {
    private ArrayList<String> mDataset;
    private ArrayList<String> packIDs;

    private Context context;
    private static RecyclerViewClickListener itemListener;


    public DiscoverCardAdapter(Context context, RecyclerViewClickListener itemListener, ArrayList<String> mDataset, ArrayList packIDs) {
        this.context = context;
        this.itemListener = itemListener;
        this.mDataset = mDataset;
        this.packIDs = packIDs;
    }

    public DiscoverCardAdapter(ArrayList<String> mDataset) {
        this.mDataset = mDataset;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public MaterialCardView mMaterialCardView;
        public TextView mTitleTextView;
        String packID;

        public ViewHolder(View itemView) {
            super(itemView);
            mMaterialCardView = (MaterialCardView) itemView.findViewById(R.id.cardViewID);
            mTitleTextView = (TextView) itemView.findViewById(R.id.titleTextViewID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition(), packID);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_pack_item, viewGroup, false);
        ViewHolder pvh = new ViewHolder(v);
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitleTextView.setText(mDataset.get(position));
        holder.packID = packIDs.get(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
