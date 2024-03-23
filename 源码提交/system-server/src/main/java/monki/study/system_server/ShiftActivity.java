package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.ShiftInfo;
import monki.study.system_server.table.ShiftTable;
import monki.study.system_server.util.ToastUtil;

public class ShiftActivity extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private EditText etShiftId,etCarId,etLineId,etShiftState,etTimeStart,etTimeArrive,etticketPrice;
    private EditText etSelected;
    private MyDBHelper myDBHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);
        myDBHelper=MyApplication.getInstance().getMyDBHelper();
        etShiftId=findViewById(R.id.et_shift_id);
        etCarId = findViewById(R.id.et_car_id);
        etLineId=findViewById(R.id.et_line_id);
        etShiftState=findViewById(R.id.et_shift_state);
        etTimeStart=findViewById(R.id.et_time_start);
        etTimeArrive=findViewById(R.id.et_time_arrive);
        etticketPrice=findViewById(R.id.et_shift_ticket_price);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_retrieve).setOnClickListener(this);
        findViewById(R.id.btn_delete_by_id).setOnClickListener(this);
        findViewById(R.id.btn_update_by_id).setOnClickListener(this);
        etTimeStart.setOnClickListener(this);
        etTimeArrive.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                ShiftInfo shift = new ShiftInfo();
                shift.setShiftId(Integer.parseInt(etShiftId.getText().toString()));
                shift.setCarId(Integer.parseInt(etCarId.getText().toString()));
                shift.setLineNumber(Integer.parseInt(etLineId.getText().toString()));
                shift.setRuning(Integer.parseInt(etShiftState.getText().toString())==1?true:false);
                shift.setSetupTime(etTimeStart.getText().toString());
                shift.setArriveTime(etTimeArrive.getText().toString());
                shift.setSeat_solded(0);//默认为零
                myDBHelper.insertNewShift(shift);
                ToastUtil.show(this,"插入了一条班次信息");
                //获取本班次的车的载客量
                String sql="select carContent from carInfo where carId="+ shift.getCarId();
                Cursor cursor = myDBHelper.getReadableDatabase().rawQuery(sql, null);
                if(cursor.moveToFirst()){
                    Log.d("Test","索引"+cursor.getColumnIndex("carContent"));
                    int content =cursor.getInt(0);
                    //int content=50;
                    for (int i=1;i<=content;i++){
                        //未购票passengerId,isSold默认为0,票价默认为50
                        sql="insert into ticketInfo(passengerId,shiftId,ticketPrice,isSold,lineId) values(0,"+ shift.getShiftId()+",50,0,"+shift.getLineNumber()+")";
                        myDBHelper.getWritableDatabase().execSQL(sql);
                    }
                    ToastUtil.show(this,"生成了本班次的"+content+"张车票");
                }
                break;
            case R.id.btn_retrieve:
                Intent intent = new Intent(this, ShiftTable.class);
                startActivity(intent);
                break;
            case R.id.btn_delete_by_id:

                ContentValues values = new ContentValues();
                values.put("ticketPrice",Float.parseFloat(etticketPrice.getText().toString()));
                int affectedRows= myDBHelper.getWritableDatabase().update("ticketInfo",values,"shiftId=?",new String[]{etShiftId.getText().toString()});

                if(affectedRows>=1){
                    ToastUtil.show(this,"修改班次号号为"+etShiftId.getText().toString()+"的票价信息成功");
                }else{
                    ToastUtil.show(this,"修改失败");
                }
                break;
            case R.id.btn_update_by_id:
                values = new ContentValues();
                values.put("carId",Integer.parseInt(etCarId.getText().toString()));
                values.put("lineNumber",Integer.parseInt(etLineId.getText().toString()));
                values.put("isRunning",Integer.parseInt(etShiftState.getText().toString()));
                values.put("setupTime",etTimeStart.getText().toString());
                values.put("arriveTime",etTimeArrive.getText().toString());
                affectedRows=myDBHelper.getWritableDatabase().update("shiftInfo",values,"shiftId=?",new String[]{etShiftId.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(this,"修改班次号为"+etShiftId.getText().toString()+"的班次信息成功");
                }else{
                    ToastUtil.show(this,"修改失败");
                }

                break;
            case R.id.et_time_start:
                etSelected=etTimeStart;
                Calendar calendar1 = Calendar.getInstance();
                TimePickerDialog dialog1 = new TimePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog,this,
                        calendar1.get(Calendar.HOUR_OF_DAY),
                        calendar1.get(Calendar.MINUTE),
                        true);
                dialog1.show();
                break;
            case R.id.et_time_arrive:
                etSelected=etTimeArrive;
                Calendar calendar2 = Calendar.getInstance();
                TimePickerDialog dialog2 = new TimePickerDialog(this, android.R.style.Theme_DeviceDefault_Dialog ,this,
                        calendar2.get(Calendar.HOUR_OF_DAY),
                        calendar2.get(Calendar.MINUTE),
                        true);
                dialog2.show();
                break;


        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time =hourOfDay+ ":"+minute;
            switch (etSelected.getId()){
            case R.id.et_time_start:
                etTimeStart.setText(time);
                break;
            case R.id.et_time_arrive:
                etTimeArrive.setText(time);
                break;
        }

    }
}