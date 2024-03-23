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

public class TicketTable extends AppCompatActivity {

    private MyDBHelper myDBHelper;
    private TextView tvNoneTicket;
    private ListView lvList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_table);
        myDBHelper= MyApplication.getInstance().getMyDBHelper();
        tvNoneTicket=findViewById(R.id.tv_ticket_none);
        lvList=findViewById(R.id.lv_list);
        selectAllinTicketTable();
    }

    private void selectAllinTicketTable() {
        List<String> list = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("ticketInfo", null, null, null, null, null, null);
        if(cursor!=null&&cursor.moveToFirst()){

            do{
                StringBuilder sb = new StringBuilder();
                sb.append("车票号:"+cursor.getInt(0));
                sb.append(" 乘客号:"+cursor.getString(1));
                sb.append(" 线路号:"+cursor.getInt(5));
                sb.append(" 班次号:"+cursor.getInt(2));
                sb.append(" 票价:"+cursor.getFloat(3));
                sb.append(" 是否卖出:"+(cursor.getInt(4)==1?"是":"否"));

                list.add(sb.toString());
            }while (cursor.moveToNext());
            TextListAdapter adapter = new TextListAdapter(this,list);
            lvList.setAdapter(adapter);
            tvNoneTicket.setVisibility(View.GONE);
        }


    }
}