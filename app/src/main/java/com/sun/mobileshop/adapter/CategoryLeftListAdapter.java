package com.sun.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sun.mobileshop.R;
import com.sun.mobileshop.entity.CategoryEntity;

import java.util.List;

/**
 * Created by Administrator on 2019/5/6 0006.
 * 左边数据适配器
 */

public class CategoryLeftListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {


    //选中的索引
    private int selectedCategoryId = 0;

    private Context mContext;
    private List<CategoryEntity> leftData;
    private OnRecyclerViewItemClickListener mOnItemClickListener;


    public CategoryLeftListAdapter(Context context,List<CategoryEntity> leftData){

        this.mContext=context;
        this.leftData=leftData;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
        }
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            CategoryEntity entity = (CategoryEntity) v.getTag();
            mOnItemClickListener.onItemClick(v, entity);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof CategoryLeftListAdapter.ViewHolder){
           CategoryLeftListAdapter.ViewHolder newHolder=(CategoryLeftListAdapter.ViewHolder)holder;

           //填充数据
           CategoryEntity entity=leftData.get(position);
           newHolder.textView.setText(entity.getName());
           //将数据保存在itemView的Tag中，以便点击时进行获取
           newHolder.itemView.setTag(entity);


           //修改状态
           if (entity.getCat_id() == selectedCategoryId) {
               newHolder.itemView.setBackgroundResource(R.drawable.category_left_bg_focus);
               newHolder.textView.setTextColor(holder.itemView.getResources().getColor(R.color
                       .category_left_red_font));
           } else {
               holder.itemView.setBackgroundResource(R.drawable.category_left_bg_normal);
               newHolder.textView.setTextColor(holder.itemView.getResources().getColor(R.color
                       .category_left_dark_font));
           }
       }

    }

    /**
     * 设置选中行
     *
     * @param categoryId
     */
    public void setSelection(int categoryId) {
        selectedCategoryId = categoryId;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return leftData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list_left,parent,false);
        ViewHolder holder=new ViewHolder(view);

        view.setOnClickListener(this);
        return holder;
    }
}
