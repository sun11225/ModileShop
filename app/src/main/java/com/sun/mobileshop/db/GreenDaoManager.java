package com.sun.mobileshop.db;

import android.database.sqlite.SQLiteDatabase;

import com.sun.mobileshop.common.MyApplication;
import com.sun.mobileshop.gen.DaoMaster;
import com.sun.mobileshop.gen.DaoSession;

/**
 * Created by Administrator on 2019/5/3 0003.
 * <p>
 * 数据库配置
 */

public class GreenDaoManager {

    // DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
    // DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
    // DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API


    private static GreenDaoManager mInstance;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;


    public GreenDaoManager() {
        if (mInstance == null) {

            //创建数据库mydb
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getContext(), "mydb", null);
            //打开一个数据库，并且返回一个可以对数据库读写的对象
            SQLiteDatabase db = devOpenHelper.getWritableDatabase();
            //获取数据库对象
            mDaoMaster = new DaoMaster(db);
            //获取Dao对象管理者
            mDaoSession = mDaoMaster.newSession();
        }
    }

    //单例模式
    public static GreenDaoManager getmInstance(){
        if (mInstance==null){
            synchronized (GreenDaoManager.class){
                if (mInstance==null){
                    mInstance=new GreenDaoManager();
                }
            }
        }
        return mInstance;
    }

    public static DaoMaster getmDaoMaster() {
        return mDaoMaster;
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public static DaoSession getNewSession(){
        mDaoSession=mDaoMaster.newSession();
        return mDaoSession;
    }

}
