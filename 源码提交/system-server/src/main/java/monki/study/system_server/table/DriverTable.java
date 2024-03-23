package monki.study.system_server.table;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import monki.study.system_server.MyApplication;
import monki.study.system_server.R;
import monki.study.system_server.adapter.TextListAdapter;
import monki.study.system_server.database.MyDBHelper;

public class DriverTable extends AppCompatActivity {

    private MyDBHelper myDBHelper;
    private TextView tvNoneDriver;
    private ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_table);
        myDBHelper= MyApplication.getInstance().getMyDBHelper();
        tvNoneDriver=findViewById(R.id.tv_driver_none);
        lvList=findViewById(R.id.lv_list);
        selectAllinCarTable();
    }

    private void selectAllinCarTable() {
        List<String> list = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("driverInfo", null, null, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){

            do{
                StringBuilder sb = new StringBuilder();
                sb.append("驾驶员号:"+cursor.getInt(0));
                sb.append(" 姓名:"+cursor.getString(1));
                sb.append(" 性别:"+cursor.getString(2));
                sb.append(" 出生日期:"+cursor.getString(3));
                sb.append(" 电话:"+cursor.getString(4));
                sb.append(" 驾驶车辆号:"+cursor.getInt(5));
                list.add(sb.toString());
            }while (cursor.moveToNext());
            TextListAdapter adapter = new TextListAdapter(this,list);
            lvList.setAdapter(adapter);
            tvNoneDriver.setVisibility(View.GONE);
        }


    }
}