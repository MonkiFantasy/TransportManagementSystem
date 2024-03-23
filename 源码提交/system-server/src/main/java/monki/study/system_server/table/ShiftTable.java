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

public class ShiftTable extends AppCompatActivity {


    private MyDBHelper myDBHelper;
    private TextView tvNoneShift;
    private ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_table);
        myDBHelper= MyApplication.getInstance().getMyDBHelper();
        tvNoneShift=findViewById(R.id.tv_shift_none);
        lvList=findViewById(R.id.lv_list);
        selectAllinCarTable();
    }

    private void selectAllinCarTable() {
        List<String> list = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("shiftInfo", null, null, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){

            do{
                StringBuilder sb = new StringBuilder();
                sb.append("班次号:"+cursor.getInt(0));
                sb.append(" 车辆编号:"+cursor.getInt(1));
                sb.append(" 线路号:"+cursor.getInt(2));
                sb.append(" 已售座位数:"+cursor.getInt(3));
                sb.append(" 运营状态:"+(cursor.getInt(4)==1?"运行":"停运"));
                sb.append(" 出发时间:"+cursor.getString(5));
                sb.append(" 到站时间:"+cursor.getString(6));
                list.add(sb.toString());
            }while (cursor.moveToNext());
            TextListAdapter adapter = new TextListAdapter(this,list);
            lvList.setAdapter(adapter);
            tvNoneShift.setVisibility(View.GONE);
        }


    }

}