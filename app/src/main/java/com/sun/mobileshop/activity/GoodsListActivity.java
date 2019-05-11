package com.sun.mobileshop.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.mobileshop.R;
import com.sun.mobileshop.adapter.GoodsListAdapter;
import com.sun.mobileshop.entity.GoodsEntity;
import com.sun.mobileshop.http.presenter.GoodsPresenter;
import com.sun.mobileshop.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class GoodsListActivity extends BaseActivity {

    //返回按钮
    @BindView(R.id.title_back)
    ImageView titleBack;

    //搜索关键词
    @BindView(R.id.search_keyword)
    TextView searchKeyword;

    //按销量排序
    @BindView(R.id.goodslist_orderby_sales)
    RelativeLayout orderbySales;
    @BindView(R.id.goodlist_orderby_sales_text)
    TextView orderbySalesText;
    //按品牌排序
    @BindView(R.id.goodslist_orderby_grade)
    RelativeLayout orderbyGrade;
    @BindView(R.id.goodslist_orderby_grade_text)
    TextView orderbyGradeText;
    //按价格排序
    @BindView(R.id.goodslist_orderby_price_text)
    TextView orderbyPriceText;
    @BindView(R.id.goodslist_orderby_price)
    RelativeLayout orderbyPrice;
    //按新品排序
    @BindView(R.id.goodslist_orderby_newgoods_text)
    TextView orderbyNewgoodsText;
    @BindView(R.id.goodslist_orderby_newgoods)
    RelativeLayout orderbyNewgoods;

    //商品列表
    @BindView(R.id.goodslist_recyclerview)
    RecyclerView goodslistRecyclerview;

    //下拉刷新
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refreshLayout;

    //商品列表为空时显示的界面
    @BindView(R.id.goodslist_nodata)
    TextView noData;

    private int catId;
    private String keyWord;
    private GoodsListAdapter adapter;

    //布局管理器
    private LinearLayoutManager layoutManager;

    private List<GoodsEntity> list = new ArrayList<>();

    private int lastVisibleItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        this.catId = intent.getIntExtra("catId", 0);//从categoryFragment传过来的参数

        this.keyWord = intent.getStringExtra("keyword");//从商品搜索界面传递过来的参数

        initView();
    }


    private void initView(){

        //设置列表样式
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        goodslistRecyclerview.setLayoutManager(layoutManager);

        //设置刷新样式
        refreshLayout.setColorSchemeResources(android.R.color.holo_green_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light);

        //第一次进入页面显示下拉刷新样式？？？
        refreshLayout.setProgressViewOffset(false,0,(int) TypedValue.applyDimension
                (TypedValue.COMPLEX_UNIT_DIP,24,getResources().getDisplayMetrics()));

        //下拉刷新监听器
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //有网络才刷新
                if (NetworkUtils.isNetWorkAvailable(GoodsListActivity.this)){

                    loadData();
                }else {
                    refreshLayout.setRefreshing(false);
                    Toast.makeText(GoodsListActivity.this,"网络不可用",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //初始化数据
        loadData();

        //适配数据
        adapter=new GoodsListAdapter(this,list);
        goodslistRecyclerview.setAdapter(adapter);

        //上拉加载更多! 监听滑动的状态变化
        goodslistRecyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //如果滚动停止且当前可见的最后一个item位于页面底部
                if (newState==RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem+1==adapter.getItemCount()){

                    //加载更多数据
                    loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                lastVisibleItem=layoutManager.findLastVisibleItemPosition();
            }
        });
    }


    /**
     * 上拉加载更多数据，分页
     */
    private void loadMoreData() {
    }


    //加载数据
    public void loadData(){
        //二级分类页面过来的
        if (catId!=0){
            loadListByCatId(catId);
            return;
        }

        //从搜索页面过来的
        if (!TextUtils.isEmpty(keyWord)){
            loadGoodsListByKeywords(keyWord);
            return;
        }
    }

    //点击事件
    @OnClick({R.id.title_back, R.id.search_keyword, R.id.goodslist_orderby_sales, R.id.goodslist_orderby_grade, R.id
            .goodslist_orderby_price, R.id.goodslist_orderby_newgoods})

    public void onClick(View view){
        switch (view.getId()){
            case R.id.title_back:
                finish();
                break;

            case R.id.search_keyword:
                break;

            case R.id.goodslist_orderby_sales:
                break;

            case R.id.goodslist_orderby_grade:
                break;

            case R.id.goodslist_orderby_price:
                break;

            case R.id.goodslist_orderby_newgoods:
                break;
        }
    }


    //加载二级分类ID查询商品列表
    private void loadListByCatId(int catId){
        GoodsPresenter.list(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GoodsEntity> entities) {

                //网络请求到的数据全部添加到list
                list.clear();
                list.addAll(entities);
                adapter.notifyDataSetChanged();

            }
        },catId);
    }

    private void loadGoodsListByKeywords(String keyWord){

        //根据搜索关键词来获取商品
        GoodsPresenter.listByKeywords(new Subscriber<List<GoodsEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GoodsEntity> entities) {
                list.clear();
                list.addAll(entities);
                adapter.notifyDataSetChanged();

            }
        },keyWord);

    }

}
