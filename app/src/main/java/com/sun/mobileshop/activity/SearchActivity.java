package com.sun.mobileshop.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.sun.mobileshop.R;
import com.sun.mobileshop.adapter.OnHistoryItemClickListener;
import com.sun.mobileshop.adapter.SearchHistoryAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.search_btn)
    Button searchBtn;
    @BindView(R.id.search_keyword)
    AutoCompleteTextView searchKeyword;
    @BindView(R.id.search_history_list)
    RecyclerView searchHistoryList;
    @BindView(R.id.del_history_button)
    Button delHistoryButton;

    //储存搜索历史记录
    private SharedPreferences sharedPreferences;

    private List<String> list = new ArrayList<>();

    private SearchHistoryAdapter searchHistoryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        //初始化控件
        initView();

    }


    private void initView() {

        sharedPreferences = getSharedPreferences("user", 0);
        //搜索历史记录点击事件
        searchHistoryAdapter.setOnItemClickListener(new OnHistoryItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Intent intent=new Intent();
                intent.putExtra("keyword",data);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


        //载入历史记录列表
        int historySize = sharedPreferences.getInt("history_size", 0);
        for (int i = 0; i < historySize; i++) {
            list.add(sharedPreferences.getString("history_" + i, ""));
        }
        if (list.size() > 0) {

            searchHistoryAdapter = new SearchHistoryAdapter(this, list);
            searchHistoryList.setAdapter(searchHistoryAdapter);

            delHistoryButton.setVisibility(View.VISIBLE);
        } else {
            delHistoryButton.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.title_back, R.id.search_btn, R.id.product_list_search_clean, R.id
            .del_history_button})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:   //返回
                finish();
                break;
            case R.id.search_btn: //搜索
                beginSearch();
                break;
            case R.id.del_history_button:   //删除历史记录
                deleteHistory();
                break;
        }
    }

    //删除历史记录
    private void deleteHistory() {
        //获得历史记录的条数
        int historySize = sharedPreferences.getInt("history_size", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (int i = 0; i < historySize; i++) {
            editor.remove("history_" + i);
        }

        editor.putInt("history_size",0);
        editor.commit();
        list.clear();
        searchHistoryAdapter.notifyDataSetChanged();
        Toast.makeText(SearchActivity.this,"删除历史记录成功",Toast.LENGTH_SHORT).show();
    }



    //开始搜索
    private void beginSearch(){
        //本地验证
        String keyWord=searchKeyword.getText().toString();
        if (TextUtils.isEmpty(keyWord)){
            Toast.makeText(SearchActivity.this,"请输入要搜索的关键词!",Toast.LENGTH_SHORT).show();
            return;
        }
        //取出前九条历史记录
        int historySize=sharedPreferences.getInt("history_size",0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        List<String> mList=new ArrayList<>();
        for (int i=0;i<historySize;i++) {
            if (historySize < 9) {
                mList.add(sharedPreferences.getString("history_" + i, ""));
            }
            editor.remove("history_" + i);
        }

        //增加新的历史记录
        mList.add(0,keyWord);

        //将历史记录保存到本地
        for (int i=0;i<historySize;i++){
            editor.putString("history_"+i,mList.get(i));
        }
        editor.putInt("history_size",mList.size());
        editor.commit();

        //开始搜索 点击按钮开始搜索
        Intent intent=new Intent();
        intent.putExtra("keyword",keyWord);
        setResult(RESULT_OK,intent);
        finish();
    }

}
