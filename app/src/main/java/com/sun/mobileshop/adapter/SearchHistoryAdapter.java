package com.sun.mobileshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sun.mobileshop.R;
import com.sun.mobileshop.entity.GoodsEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/11 0011.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<String> list;
    //自定义的监听器
    private OnHistoryItemClickListener mOnItemClickListener;


    public SearchHistoryAdapter(Context context, List<String> list) {
        this.list = list;
        this.mContext = context;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.keyword)
        TextView keywords;
        @BindView(R.id.keyword_correct_layout)
        RelativeLayout searchLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_history, parent, false);

        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder itemViewHolder = (ViewHolder) holder;

            itemViewHolder.keywords.setText(list.get(position));
            itemViewHolder.itemView.setTag(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {

        mOnItemClickListener.onItemClick(v, (String) v.getTag());
    }


    public void setOnItemClickListener(OnHistoryItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
