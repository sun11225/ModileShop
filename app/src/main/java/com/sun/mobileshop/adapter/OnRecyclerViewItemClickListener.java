package com.sun.mobileshop.adapter;

import android.view.View;

import com.sun.mobileshop.entity.CategoryEntity;

/**
 * Created by Administrator on 2019/5/6 0006.
 */

public interface OnRecyclerViewItemClickListener {

    void onItemClick(View view, CategoryEntity data);

}
