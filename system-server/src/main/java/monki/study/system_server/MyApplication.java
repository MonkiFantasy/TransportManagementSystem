package monki.study.system_server;

import android.app.Application;

import monki.study.system_server.database.MyDBHelper;

public class MyApplication extends Application {
    private static MyApplication mApp;
    private MyDBHelper myDBHelper;
    public static MyApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mApp=this;
    }
    public MyDBHelper getMyDBHelper(){
        myDBHelper=MyDBHelper.getInstance(this);
        return myDBHelper;
    }
}
