package com.sun.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sun.mobileshop.R;
import com.sun.mobileshop.common.ImageLoaderManager;
import com.sun.mobileshop.entity.GoodsEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/5/11 0011.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<GoodsEntity> list;
    public OnGoodsItemClickListener itemClickListener;

    public GoodsListAdapter(Context context,List<GoodsEntity> data){
        this.mContext=context;
        this.list=data;
    }

    //viewHolder类
    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title;
        private TextView price;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.goodslist_img);
            title=(TextView)itemView.findViewById(R.id.goodlists_name);
            price=(TextView)itemView.findViewById(R.id.goodslist_price);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(mContext).inflate(R.layout.item_goods_list,parent,false);

        view.setOnClickListener(this);

        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //获取点击的项给他加载子项的布局
        GoodsEntity entity=list.get(position);
        ImageLoader.getInstance().displayImage(entity.getSmall(),holder.imageView, ImageLoaderManager.product_options);
        holder.title.setText(entity.getName());

        //string.format使用指定的格式字符串和参数返回一个格式化字符串。
        holder.price.setText("$"+String.format("%.2f",entity.getPrice()));

        //储存数据
        holder.itemView.setTag(entity);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onClick(View v) {

        if (itemClickListener!=null){
            itemClickListener.onItemClick(v,(GoodsEntity)v.getTag());
        }
    }

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener listener){

        this.itemClickListener=listener;

    }
}
