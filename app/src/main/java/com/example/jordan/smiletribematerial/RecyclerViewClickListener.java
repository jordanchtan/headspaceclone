package com.example.jordan.smiletribematerial;

import android.view.View;

public interface RecyclerViewClickListener {
    public void recyclerViewListClicked(View v, int position);
    public void recyclerViewListClicked(View v, int position, String packID);

}
