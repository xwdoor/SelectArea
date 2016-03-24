package net.xwdoor.selectarea.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by XWdoor on 2016/2/23.
 * 博客：http://blog.csdn.net/xwdoor
 */
public abstract class BaseActivity extends AppCompatActivity {
	public static final String TAG_LOG = "123123";

    /** 显示提示框 */
    public void showToast(CharSequence text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    /** 打印日志 */
    public static void showLog(String title, String msg){
        Log.i(TAG_LOG, title + "-->" + msg);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initViews(savedInstanceState);
        loadData();
    }

    /** 初始化变量，包括启动Activity传过来的变量和Activity内的变量 */
    protected abstract void initVariables();

    /** 初始化视图，加载layout布局文件，初始化控件，为控件挂上事件 */
    protected abstract void initViews(Bundle savedInstanceState);

    /** 加载数据，包括网络数据，缓存数据，用户数据，调用服务器接口获取的数据 */
    protected abstract void loadData();


}
