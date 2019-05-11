package com.sun.mobileshop.adapter;

import android.view.View;

import com.sun.mobileshop.entity.GoodsEntity;

/**
 * Created by Administrator on 2019/5/11 0011.
 */

public interface OnGoodsItemClickListener {

    void onItemClick(View view, GoodsEntity entity);
}
