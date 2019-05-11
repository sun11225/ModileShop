package com.sun.mobileshop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sun.mobileshop.R;
import com.sun.mobileshop.common.ImageLoaderManager;
import com.sun.mobileshop.entity.CategoryEntity;
import com.sun.mobileshop.util.DesityUtils;

import java.util.List;

/**
 * Created by Administrator on 2019/5/6 0006.
 */

public class CategoryRightListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<CategoryEntity> mRightData;

    private OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;

    public CategoryRightListAdapter(Context context, List<CategoryEntity> rightData){
        this.mContext=context;
        this.mRightData=rightData;

    }

    //ViewHodler类  重复利用item布局，节省手机内存资源
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title1;
        private ImageView image1;

        public ViewHolder(View view){
            super(view);
            title1=(TextView)view.findViewById(R.id.category_item_list_have_picture_text_1);
            image1=(ImageView)view.findViewById(R.id.category_item_list_have_picture_image_1);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //直接用onCreateViewHolder返回的holder进行操作
//        instanceof 运算符是用来在运行时指出对象是否是特定类的一个实例
        if (holder instanceof CategoryRightListAdapter.ViewHolder){

            final CategoryRightListAdapter.ViewHolder newHolder=(CategoryRightListAdapter.ViewHolder)holder;
            //获取选项位置
            CategoryEntity entity=mRightData.get(position);

            //适配item数据
            newHolder.title1.setText(entity.getName());

                        ImageLoader.getInstance().displayImage(entity.getImage(), newHolder.image1,
                        ImageLoaderManager.product_options, new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {

                            }

                            @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                    //设置图片的高度
                    int image_width=(int)mContext.getResources().getDimension(R.dimen.category_list_right_image_width);

                    //重新计算图片，让图片始终为正方形
                    if (loadedImage!=null){
                        Bitmap bitmap=Bitmap.createBitmap(loadedImage,0,0,loadedImage.getWidth(),loadedImage.getHeight());
                        bitmap=Bitmap.createScaledBitmap(bitmap,image_width,image_width,false);
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {


                }
            });

            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(entity);
        }

    }

    @Override
    public int getItemCount() {
        return mRightData.size();
    }



    //初始化操作 点击事件等等
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_list_right,parent,false);

        //点击事件
        view.setOnClickListener(this);

        //获取左侧列表宽度
        int left_width=(int)mContext.getResources().getDimension(R.dimen.category_list_left_width);

        //获取手机屏幕宽度
        int width= DesityUtils.getWidth(mContext);

        //设置右侧列表每个选项的高宽度
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                (width-left_width)/3,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        view.setLayoutParams(params);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewItemClickListener!=null){
            //getTag获取数据
            onRecyclerViewItemClickListener.onItemClick(v,(CategoryEntity)v.getTag());
        }
    }


    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener listener){

        this.onRecyclerViewItemClickListener=listener;
    }
}
