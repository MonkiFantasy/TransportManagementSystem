package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import monki.study.system_client.adapter.TextListAdapter;
import monki.study.system_client.util.ToastUtil;

public class SelfInfoActivity extends AppCompatActivity {

    private TextView tvPassenger,tvTicket;
    private int passengerId;
    private ListView lv_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_info);
        tvPassenger=findViewById(R.id.tv_passengerInfo);
        tvTicket=findViewById(R.id.tv_ticket_none);
        lv_ticket = findViewById(R.id.lv_ticket);
        Intent intent=getIntent();
        passengerId = intent.getIntExtra("id",0);
        //乘客个人基本信息查询
        querySelfInfo();

        //查询乘客所看到的车票信息
        queryTicketInfo();


    }

    private void queryTicketInfo() {
        //根据乘客号查班次号线路号票价
        List<String> list = new ArrayList<>();
        String s = "content://"+"monki.study.system_server.provider.ticketprovider"+"/ticketInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor=getContentResolver().query(uri,new String[]{"shiftId","lineId","ticketPrice"},"passengerId=?",new String[]{String.valueOf(passengerId)},null);
        /*while (cursor.moveToFirst()!=false){
            StringBuilder sb=new StringBuilder();
            int shiftId =cursor.getInt(0);
            int lineId = cursor.getInt(1);
            float ticketPrice = cursor.getFloat(2);
            //根据线路号查线路起始站
            s = "content://"+"monki.study.system_server.provider.lineinfoprovider"+"/lineInfo";
            uri = Uri.parse(s);
            //创建一个新的cursor对象,否则会影响查询车票的cursor移动
            Cursor point=getContentResolver().query(uri,new String[]{"startPoint","destination"},"lineId=?",new String[]{String.valueOf(lineId)},null);
            if(point.moveToFirst()){
                String start = point.getString(0);
                String destination = point.getString(1);
                sb.append("出发站:"+start+" 目的站:"+destination);
            }
            //根据班次号查起始时间
            s = "content://"+"monki.study.system_server.provider.shiftinfoprovider"+"/shiftInfo";
            uri = Uri.parse(s);
            Cursor time =getContentResolver().query(uri,new String[]{"setupTime","arriveTime"},"shiftId=?",new String[]{String.valueOf(shiftId)},null);
            if(time.moveToFirst()){
                String sTime=time.getString(0);
                String eTime=time.getString(1);
                sb.append("\n出发时间"+sTime+" 到站时间:"+eTime);
            }
            sb.append("\n价格:"+ticketPrice+"元");
            list.add(sb.toString());
            cursor.moveToNext();
        }*/
        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder sb = new StringBuilder();
                int shiftId = cursor.getInt(0);
                int lineId = cursor.getInt(1);
                float ticketPrice = cursor.getFloat(2);
                //根据线路号查线路起始站
                s = "content://"+"monki.study.system_server.provider.lineinfoprovider"+"/lineInfo";
                uri = Uri.parse(s);
                //创建一个新的cursor对象,否则会影响查询车票的cursor移动
                Cursor point=getContentResolver().query(uri,new String[]{"startPoint","destination"},"lineId=?",new String[]{String.valueOf(lineId)},null);
                if(point!=null|point.moveToFirst()){
                    String start = point.getString(0);
                    String destination = point.getString(1);
                    sb.append("From "+start+" to "+destination);
                }
                //根据班次号查起始时间以及车辆号
                s = "content://"+"monki.study.system_server.provider.shiftinfoprovider"+"/shiftInfo";
                uri = Uri.parse(s);
                String type=null;
                Cursor time =getContentResolver().query(uri,new String[]{"setupTime","arriveTime","carId"},"shiftId=?",new String[]{String.valueOf(shiftId)},null);
                if(time!=null&&time.moveToFirst()){
                    String sTime=time.getString(0);
                    String eTime=time.getString(1);
                    int carId = time.getInt(2);
                    ToastUtil.show(this,"车辆号："+carId);
                    //根据车辆号查车型
                    s = "content://"+"monki.study.system_server.provider.carinfoprovider"+"/carInfo";
                    uri = Uri.parse(s);
                    Cursor carType = getContentResolver().query(uri, new String[]{"carType"}, "carId=?", new String[]{String.valueOf(carId)}, null);
                    if(carType!=null&&carType.moveToFirst()){
                         type = carType.getString(0);
                    }
                    sb.append("\n"+sTime+"    线路"+lineId+"   "+eTime);
                }
                sb.append("\n车型:"+type+" 票价:"+ticketPrice+"元");
                list.add(sb.toString());

            } while (cursor.moveToNext());

            cursor.close();
        }

        // 将所有查询结果设置给TextView或其他UI元素
        //String[] tickets = list.toArray(new String[0]);
        //tvTicket.setText(result.toString());
        TextListAdapter adapter = new TextListAdapter(this, list);
        lv_ticket.setAdapter(adapter);
        lv_ticket.setVisibility(View.VISIBLE);
        tvTicket.setVisibility(View.GONE);
        /*for (String ticket : list) {
            tvTicket.setText(ticket);
        }*/
    }

    private void querySelfInfo() {
        String s = "content://"+"monki.study.system_server.provider.passengerprovider"+"/passengerInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor = getContentResolver().query(uri,new String[]{"passengerName","passengerPhone","passengerAge","passengerSex"},"passengerId=?",new String[]{String.valueOf(passengerId)},null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()){
            sb.append("姓名:"+cursor.getString(0)+"\n电话:"+cursor.getString(1)+"\n年龄:"+String.valueOf(cursor.getInt(2))+"\n性别："+cursor.getString(3)+"\n");
        }
        tvPassenger.setText(sb.toString());
        lv_ticket.setVisibility(View.GONE);
    }
}