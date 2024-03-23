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

public class PassengerTable extends AppCompatActivity {

    private MyDBHelper myDBHelper;
    private TextView tvNonePassenger;
    private ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_table);
        myDBHelper= MyApplication.getInstance().getMyDBHelper();
        tvNonePassenger=findViewById(R.id.tv_passenger_none);
        lvList=findViewById(R.id.lv_list);
        selectAllinCarTable();
    }

    private void selectAllinCarTable() {
        List<String> list = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("passengerInfo", null, null, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){

            do{
                StringBuilder sb = new StringBuilder();
                sb.append("乘客编号:"+cursor.getInt(0));
                sb.append(" 姓名:"+cursor.getString(1));
                sb.append(" 性别:"+cursor.getString(2));
                sb.append(" 年龄:"+cursor.getInt(3));
                sb.append(" 电话:"+cursor.getString(4));
                sb.append(" 密码:"+cursor.getString(5));
                list.add(sb.toString());
            }while (cursor.moveToNext());
            TextListAdapter adapter = new TextListAdapter(this,list);
            lvList.setAdapter(adapter);
            tvNonePassenger.setVisibility(View.GONE);
        }


    }
}