package com.example.pavilion.remind2;

import android.support.v7.widget.RecyclerView;

/**
 * Created by PAVILION on 17/05/2018.
 */

public interface RecyclerItemTouchHelperListener {
    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
