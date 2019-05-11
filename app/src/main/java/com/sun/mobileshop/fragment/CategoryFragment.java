package com.sun.mobileshop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.mobileshop.R;
import com.sun.mobileshop.activity.GoodsListActivity;
import com.sun.mobileshop.adapter.CategoryLeftListAdapter;
import com.sun.mobileshop.adapter.CategoryRightListAdapter;
import com.sun.mobileshop.adapter.OnRecyclerViewItemClickListener;
import com.sun.mobileshop.common.Constants;
import com.sun.mobileshop.entity.CategoryEntity;
import com.sun.mobileshop.http.presenter.CategoryPresenter;
import com.sun.mobileshop.util.AndroidUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Administrator on 2019/5/3 0003.
 */

public class CategoryFragment extends BaseFragment {

    //返回图标
    @BindView(R.id.title_back)
    ImageView titleBack;
    //搜索关键词
    @BindView(R.id.search_keyword)
    TextView searchKeyword;
    //搜索布局
    @BindView(R.id.search_layout)
    RelativeLayout searchLayout;
    //左右列表
    @BindView(R.id.left_list)
    RecyclerView leftList;
    @BindView(R.id.right_list)
    RecyclerView rightList;
    private List<CategoryEntity> leftData = new ArrayList<>();
    private List<CategoryEntity> rightData = new ArrayList<>();

    private CategoryLeftListAdapter leftAdapter;
    private CategoryRightListAdapter rightAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        //调整搜索栏的样式
        titleBack.setVisibility(View.GONE);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, AndroidUtils.dp2px(this.getActivity(), 30));
        layoutParams.setMargins(10, 3, 10, 0);
        searchLayout.setLayoutParams(layoutParams);


        //设置列表样式
        LinearLayoutManager leftManager = new LinearLayoutManager(getActivity());
        leftManager.setOrientation(OrientationHelper.VERTICAL);
        //垂直表格
        GridLayoutManager rightManager = new GridLayoutManager(getActivity(),
                Constants.SPAN_COUNT,
                OrientationHelper.VERTICAL, false);
        leftList.setLayoutManager(leftManager);
        rightList.setLayoutManager(rightManager);
        //适配数据
        leftAdapter = new CategoryLeftListAdapter(getActivity(), leftData);
        rightAdapter = new CategoryRightListAdapter(getActivity(), rightData);
        leftList.setAdapter(leftAdapter);
        rightList.setAdapter(rightAdapter);


        //加载左侧列表数据和item0对应的右侧列表数据
        CategoryPresenter.getTopList(new Subscriber<List<CategoryEntity>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {
                if (categoryEntities.size() > 0) {
                    leftData.addAll(categoryEntities);
                    leftAdapter.notifyDataSetChanged();
                    //载入item0的右侧列表数据
                    int cat_id = categoryEntities.get(0).getCat_id();
                    loadRight(cat_id);
                    //默认选中第一项
                    leftAdapter.setSelection(cat_id);
                }
            }
        });
        //左侧列表点击事件
        leftAdapter.setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryEntity entity) {
               /* Toast.makeText(getActivity(), "name:" + entity.getName() + "\r\ncat_id:" + entity.getCat_id(), Toast
                        .LENGTH_SHORT)
                        .show();*/
                //加载右侧数据
                loadRight(entity.getCat_id());
                leftAdapter.setSelection(entity.getCat_id());
            }
        });
        //右侧列表点击事件
        rightAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, CategoryEntity entity) {
               /* Toast.makeText(getActivity(), "name:" + entity.getName() + "\r\ncat_id:" + entity.getCat_id(), Toast
                        .LENGTH_SHORT)
                        .show();*/
                //跳转到商品列表界面
                Intent intent = new Intent(getActivity(), GoodsListActivity.class);
                intent.putExtra("cat_id", entity.getCat_id());
                startActivity(intent);
            }
        });
    }

    /**
     * 加载右侧列表数据
     *
     * @param cat_id
     */
    private void loadRight(int cat_id) {
        CategoryPresenter.getSecondList(new Subscriber<List<CategoryEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<CategoryEntity> categoryEntities) {


                if (categoryEntities.size() > 0) {

                    for(int i = 0; i < categoryEntities.size(); i++){
                        Log.i("CategoryFragment", "rightlist-->" + categoryEntities.get(i).toString());
                    }
                    rightData.clear();
                    rightData.addAll(categoryEntities);
                    rightAdapter.notifyDataSetChanged();
                }
            }
        }, cat_id);
    }
}
