package net.xwdoor.selectarea.db;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static net.xwdoor.selectarea.base.BaseActivity.showLog;

/**
 * Created by XWdoor on 2016/3/24 024 13:43.
 * 博客：http://blog.csdn.net/xwdoor
 */
public class DbManager {
    public static final String DB_NAME = "area.db";

    private static DbManager dbManager = null;
    private final Context mContext;

    private DbManager(Context context) {
        this.mContext = context;
    }
    public static DbManager getInstance(Context context){
        if(dbManager==null){
            synchronized (DbManager.class){
                dbManager = new DbManager(context);
            }
        }
        return dbManager;
    }

    /** 拷贝数据库文件 */
    public void copyDbFile(String dbName) {

        File filesDir = mContext.getFilesDir();
        File desFile = new File(filesDir, dbName);

        if(desFile.exists()){
            showLog("copyDb()","数据库已存在");
            return;
        }
        InputStream in = null;
        FileOutputStream out = null;
        try {
            AssetManager assets = mContext.getAssets();
            //in=context.getClass().getClassLoader().getResourceAsStream("assets/"+names[i]);
            in = assets.open(dbName);
            out = new FileOutputStream(desFile);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }
        } catch (IOException e) {
            showLog("copyDb error",e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                showLog("copyDb io error",e.getMessage());
            }
        }
    }

    public ArrayList<String> getProvince(){
        ArrayList<String> province = new ArrayList<>();
        String path = new File(mContext.getFilesDir(), DB_NAME).getAbsolutePath();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select distinct province from school_new", null);
        if(cursor!=null) {
            while (cursor.moveToNext()){
                String pro = cursor.getString(0);
                province.add(pro);
            }
            cursor.close();
        }
        return province;
    }

    public ArrayList<String> getCitis(String province){
        ArrayList<String> citis = new ArrayList<>();
        String path = new File(mContext.getFilesDir(), DB_NAME).getAbsolutePath();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select distinct city from school_new where province=?", new String[]{province});
        if(cursor!=null) {
            while (cursor.moveToNext()){
                String city = cursor.getString(0);
                citis.add(city);
            }
            cursor.close();
        }
        return citis;
    }

    public ArrayList<String> getDistrict(String city){
        ArrayList<String> districts = new ArrayList<>();
        String path = new File(mContext.getFilesDir(), DB_NAME).getAbsolutePath();
        SQLiteDatabase database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor cursor = database.rawQuery("select dist from school_new where city=?", new String[]{city});
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                String dis = cursor.getString(0);
                districts.add(dis);
            }
            cursor.close();
        }
        return districts;
    }

}
