package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.util.ToastUtil;

public class TicketSoldPerShift extends AppCompatActivity implements View.OnClickListener {
    private MyDBHelper myDBHelper;
    private TextView tvResult;
    private Spinner spStart,spEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sold_per_shift);
        tvResult = findViewById(R.id.tv_result);
        spStart=findViewById(R.id.sp_start);
        spEnd=findViewById(R.id.sp_end);
        findViewById(R.id.btn_query).setOnClickListener(this);
        myDBHelper=MyApplication.getInstance().getMyDBHelper();
        queryAllShiftSoldTime();
        spinnerLoadering();
    }

    private void spinnerLoadering() {
        Set<String> sTimes=new HashSet<>();
        Set<String> eTimes=new HashSet<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("shiftInfo", new String[]{"setupTime", "arriveTime"}, null, null, null, null, null);
        if(cursor.moveToFirst()&&cursor!=null){
            do{
                sTimes.add(cursor.getString(0));
                eTimes.add(cursor.getString(1));
            }while (cursor.moveToNext());
        }
        String[] start = sTimes.toArray(new String[0]);
        String[] end =eTimes.toArray(new String[0]);
        ArrayAdapter<String> adp_start = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,start);
        ArrayAdapter<String> adp_end = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,end);
        spStart.setAdapter(adp_start);
        spEnd.setAdapter(adp_end);

    }

    private void queryAllShiftSoldTime() {
        String query = "SELECT shiftId, COUNT(ticketId) AS ticketCount FROM ticketInfo WHERE isSold = 1 GROUP BY shiftId";
        Cursor cursor = myDBHelper.getReadableDatabase().rawQuery(query,null);
        if (cursor!=null&&cursor.moveToFirst()){
            StringBuilder result = new StringBuilder();
            do {
                StringBuilder sb = new StringBuilder();
                sb.append("\n班次:"+cursor.getInt(0));
                sb.append(" 乘坐次数:"+cursor.getInt(1));
                result.append(sb);
            }while (cursor.moveToNext());
            tvResult.setText(result.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                querySelectedShiftSoldTime();
                break;
        }
    }

    private void querySelectedShiftSoldTime()
    {
        if(spStart.getSelectedItem()!=null&&spEnd.getSelectedItem()!=null){
        String query = "SELECT ti.shiftId, COUNT(ti.ticketId) AS ticketCount FROM ticketInfo ti,shiftInfo si" +
                " WHERE ti.isSold = 1 and ti.shiftId=si.shiftId and si.setupTime>=\""+spStart.getSelectedItem().toString()+"\" and si.arriveTime<=\""+spEnd.getSelectedItem().toString()+"\" GROUP BY ti.shiftId";
            Cursor cursor = myDBHelper.getReadableDatabase().rawQuery(query,null);
            if (cursor!=null&&cursor.moveToFirst()){
                StringBuilder result = new StringBuilder();
                do {
                    StringBuilder sb = new StringBuilder();

                    sb.append("\n班次:"+cursor.getInt(0));
                    sb.append(" 乘坐次数:"+cursor.getInt(1));
                    result.append(sb);
                }while (cursor.moveToNext());
                tvResult.setText(result.toString());
            }
        }else{
            ToastUtil.show(this,"当前选择的时间为空，请检查是否存在班次信息");
        }
        //tvResult.setText(query);



    }
}