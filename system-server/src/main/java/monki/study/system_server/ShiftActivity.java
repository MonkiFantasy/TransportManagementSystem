package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.ShiftInfo;
import monki.study.system_server.util.ToastUtil;

public class ShiftActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etShiftId,etCarId,etLineId,etShiftState,etTimeStart,etTimeArrive,etShiftseatsSold;
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
        etShiftseatsSold=findViewById(R.id.et_shift_seats_sold);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_insert_ticket).setOnClickListener(this);

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
                shift.setSeat_solded(Integer.parseInt(etShiftseatsSold.getText().toString()));
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
            case R.id.btn_insert_ticket:
                ContentValues values = new ContentValues();

                break;

        }
    }
}