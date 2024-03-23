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

public class LineTable extends AppCompatActivity {

    private MyDBHelper myDBHelper;
    private TextView tvNoneLine;
    private ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_table);
        myDBHelper= MyApplication.getInstance().getMyDBHelper();
        tvNoneLine=findViewById(R.id.tv_line_none);
        lvList=findViewById(R.id.lv_list);
        selectAllinCarTable();
    }

    private void selectAllinCarTable() {
        List<String> list = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("lineInfo", null, null, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){

            do{
                StringBuilder sb = new StringBuilder();
                sb.append("线路号:"+cursor.getInt(0));
                sb.append(" 出发站:"+cursor.getString(1));
                sb.append(" 终点站:"+cursor.getString(2));
                list.add(sb.toString());
            }while (cursor.moveToNext());
            TextListAdapter adapter = new TextListAdapter(this,list);
            lvList.setAdapter(adapter);
            tvNoneLine.setVisibility(View.GONE);
        }


    }
}