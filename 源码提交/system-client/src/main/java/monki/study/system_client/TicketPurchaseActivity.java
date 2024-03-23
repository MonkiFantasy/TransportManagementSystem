package monki.study.system_client;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import monki.study.system_client.entity.LineInfo;
import monki.study.system_client.entity.TicketInfo;
import monki.study.system_client.util.ToastUtil;

public class TicketPurchaseActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner spStart;
    private Spinner spDestination;
    private Spinner spTicket;
    private String[] tickets;
    private String authorities;
    private String str;
    private Uri uri;
    private int passengerId;
    private TextView tvLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_purchase);
        spStart = findViewById(R.id.sp_start);
        spDestination = findViewById(R.id.sp_destination);
        spTicket = findViewById(R.id.sp_ticket);
        tvLine = findViewById(R.id.tv_line);
        findViewById(R.id.btn_search).setOnClickListener(this);
        findViewById(R.id.btn_purchase).setOnClickListener(this);
        findViewById(R.id.btn_ticket_holder).setOnClickListener(this);
        //初始化站点选择列表
        spinnerLoading();
        passengerId = getIntent().getIntExtra("id",0);


    }

    private int getPassengerId() {
        Intent intent=getIntent();
        String passengerPhone = intent.getStringExtra("phone");
        String s = "content://"+"monki.study.system_server.provider.passengerprovider"+"/passengerInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor = getContentResolver().query(uri,new String[]{"passengerId"},"passengerPhone=?",new String[]{passengerPhone},null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_search:
                List<TicketInfo> info = seachTicketInfo();
                if(info!=null)
                {

                    List<String> ticket = new ArrayList<>();
                    boolean isToastDisplayed = false;
                    for (TicketInfo ticketInfo : info) {
                        //根据班次号查询出发时间和结束时间
                        String s = "content://"+"monki.study.system_server.provider.shiftinfoprovider"+"/shiftInfo";
                        Uri uri = Uri.parse(s);
                        Cursor query = getContentResolver().query(uri, new String[]{"setupTime", "arriveTime","carId"}, "shiftId=?", new String[]{String.valueOf(ticketInfo.getShiftId())}, null);
                        if(query.moveToFirst()&&query!=null){
                            s = "content://"+"monki.study.system_server.provider.carinfoprovider"+"/carInfo";
                            uri = Uri.parse(s);
                            ToastUtil.show(this,"id:"+query.getInt(2));
                            Cursor carType = getContentResolver().query(uri, new String[]{"carType"}, "carId=?", new String[]{String.valueOf(query.getInt(2))}, null);
                            if(carType!=null&&carType.moveToFirst()){

                                ticket.add("票号:"+ticketInfo.getTicketId()+" "+carType.getString(0)+" "+query.getString(0)+"-"+query.getString(1)+"  价格:"+ticketInfo.getTicketPrice()+"元");
                            }
                        }else{
                            if(!isToastDisplayed){

                                ToastUtil.show(this,"根据班次号查询起始时间失败，可能因为班次被删除");
                                isToastDisplayed=true;
                            }
                        }

                    }
                    tickets= ticket.toArray(new String[0]);

                    ArrayAdapter<String> adapter_ticket = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ticket);
                    spTicket.setAdapter(adapter_ticket);
                }
                break;
            case R.id.btn_purchase:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this).
                        setTitle("买票")
                        .setMessage("确定要购买这张票吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                authorities= "monki.study.system_server.provider.ticketprovider";
                                str = "content://" + authorities+ "/ticketInfo";
                                uri = Uri.parse(str);
                                ContentValues values = new ContentValues();
                                values.put("passengerId",passengerId);
                                values.put("isSold",1);
                                Pattern pattern = Pattern.compile("票号:(\\d+)");
                                Matcher matcher = pattern.matcher(spTicket.getSelectedItem().toString());
                                String ticketNumber=null;
                                if(matcher.find()){
                                    ticketNumber = matcher.group(1);
                                    ToastUtil.show(TicketPurchaseActivity.this,"票号"+ticketNumber);
                                }
                                int affectedRows=getContentResolver().update(uri,values,"ticketId=?",new String[]{ticketNumber});
                                if (affectedRows==0){
                                    ToastUtil.show(TicketPurchaseActivity.this,"购票失败");
                                }else{
                                    ToastUtil.show(TicketPurchaseActivity.this,"购票成功");
                                }
                            }
                        }).setNegativeButton("取消",null);
                        dialog.show();

                break;
            case R.id.btn_ticket_holder:
                Intent intent_ticket = new Intent(this,SelfInfoActivity.class);
                intent_ticket.putExtra("id",passengerId);
                startActivity(intent_ticket);
                break;
        }
    }



    private List<TicketInfo> seachTicketInfo() {
        authorities = "monki.study.system_server.provider.lineinfoprovider";
        str = "content://" + authorities + "/lineInfo";
        uri = Uri.parse(str);
        Cursor cursor = null;
        //根据站点查找线路号
        cursor = getContentResolver().query(uri, new String[]{"lineId"}, "startPoint=? and destination =?", new String[]{spStart.getSelectedItem().toString(), spDestination.getSelectedItem().toString()}, null);

        List<TicketInfo> info = null;
        if (cursor == null || cursor.moveToFirst() == false) {//数据库未查到信息
            ToastUtil.show(this, "无当前线路");
        } else {
            cursor.moveToFirst();
            int lineNumber = cursor.getInt(0);
            //ToastUtil.show(this, String.valueOf(lineNumber));
            tvLine.setText("线路"+lineNumber);
            authorities = "monki.study.system_server.provider.ticketprovider";
            str = "content://" + authorities + "/ticketInfo";
            uri = Uri.parse(str);
            //根据线路号查未卖出车票信息


            cursor = getContentResolver().query(uri,new String[]{"shiftId","ticketPrice","ticketId"},"isSold=0 and lineId="+lineNumber,null,null);
            info = new ArrayList<>();
            if (cursor == null || cursor.moveToFirst() == false) {
                ToastUtil.show(this, "无当前线路车票");
            }else{
                //ToastUtil.show(this,"进入while语句之前");
                cursor.moveToPosition(-1);
                while (cursor.moveToNext()) {
                    //ToastUtil.show(this,"进入while语句");
                    TicketInfo ti = new TicketInfo();
                    ti.setShiftId(cursor.getInt(0));
                    ti.setTicketPrice(cursor.getFloat(1));
                    ti.setTicketId(cursor.getInt(2));
                    //ToastUtil.show(this,"测试:"+String.valueOf(cursor.getInt(0)+String.valueOf(cursor.getFloat(1))));
                    info.add(ti);
                }
            }

        }
        return info;
    }

    private void spinnerLoading() {
        // 创建一个字符串数组作为选项列表数据
        String[] starts = {"无站点"};
        String[] destinations={"无站点"};
        tickets = new String[]{"查询你的车票"};
        //list会存在相同站点,该用set存储
        /*List<String> start =new ArrayList<>();
        List<String> destination =new ArrayList<>();*/
        Set<String> start =new HashSet<>();
        Set<String> destination=new HashSet<>();
        String AUTHORITIES = "monki.study.system_server.provider.lineinfoprovider";
        String str="content://"+AUTHORITIES+"/lineInfo";
        Uri uri =Uri.parse(str);
        ContentResolver resolver = getContentResolver();
        Cursor query = resolver.query(uri, null, null, null, null);
        if (query!=null){
            while (query.moveToNext()){
                LineInfo info = new LineInfo();
                start.add(query.getString(1));
                destination.add(query.getString(2));
            }
            StringBuilder sb = new StringBuilder();
            if(!start.isEmpty()&&!destination.isEmpty()){
                starts=start.toArray(new String[0]);
                destinations= destination.toArray(new String[0]);
            }
        }
// 创建适配器将选项列表数据与 Spinner 组件关联起来
        ArrayAdapter<String> adapter_start = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,starts);

        ArrayAdapter<String> adapter_destination = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,destinations);

        ArrayAdapter<String> adapter_ticket = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,tickets);
// 设置适配器
        spStart.setAdapter(adapter_start);
        spDestination.setAdapter(adapter_destination);
        spTicket.setAdapter(adapter_ticket);

    }


}