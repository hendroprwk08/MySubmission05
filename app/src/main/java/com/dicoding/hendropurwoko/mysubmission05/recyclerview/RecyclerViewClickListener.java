package com.dicoding.hendropurwoko.mysubmission05.recyclerview;

import android.view.View;

public interface RecyclerViewClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
