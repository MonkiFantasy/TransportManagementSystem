package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.DriverInfo;
import monki.study.system_server.table.DriverTable;
import monki.study.system_server.util.ToastUtil;

public class DriverActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_driver_id;
    private EditText et_driver_name;
    private EditText et_driver_sex;
    private EditText et_driver_birth;
    private EditText et_driver_phone;
    private EditText et_driver_car_id;
    private MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        myDBHelper=MyApplication.getInstance().getMyDBHelper();
        et_driver_id = findViewById(R.id.et_driver_id);
        et_driver_name = findViewById(R.id.et_driver_name);
        et_driver_sex = findViewById(R.id.et_driver_sex);
        et_driver_birth = findViewById(R.id.et_driver_birth);
        et_driver_phone = findViewById(R.id.et_driver_phone);
        et_driver_car_id = findViewById(R.id.et_driver_car_id);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
        findViewById(R.id.btn_retrieve).setOnClickListener(this);
        findViewById(R.id.btn_delete_by_id).setOnClickListener(this);
        findViewById(R.id.btn_update_by_id).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                DriverInfo driveri = new DriverInfo();
                driveri.setDriverId(Integer.parseInt(et_driver_id.getText().toString()));
                driveri.setDriverName(et_driver_name.getText().toString());
                driveri.setDriverSex(et_driver_sex.getText().toString());
                driveri.setDriverBirth(et_driver_birth.getText().toString());
                driveri.setDriverPhone(et_driver_phone.getText().toString());
                driveri.setCarId(Integer.parseInt(et_driver_car_id.getText().toString()));
                myDBHelper.insertNewDriver(driveri);
                ToastUtil.show(this,"插入驾驶员数据成功");
                break;
            case R.id.btn_delete_by_id:
                int affectedRows=myDBHelper.getWritableDatabase().delete("driverInfo","driverId=?",new String[]{et_driver_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(DriverActivity.this,"删除驾驶员编号为"+et_driver_id.getText().toString()+"的驾驶员信息成功");
                }else{
                    ToastUtil.show(DriverActivity.this,"删除失败");
                }
                break;
            case R.id.btn_retrieve:

                Intent intent = new Intent(this, DriverTable.class);
                startActivity(intent);
                break;
            case R.id.btn_query:
                Cursor cursor = myDBHelper.queryCarTypeByDriverName(et_driver_name.getText().toString());
                while (cursor.moveToNext()){
                    ToastUtil.show(this,"车型："+cursor.getString(0));
                }
                break;
            case R.id.btn_update_by_id:
                ContentValues values = new ContentValues();
                values.put("driverName",et_driver_name.getText().toString());
                values.put("driverSex",et_driver_sex.getText().toString());
                values.put("driverBirth",et_driver_birth.getText().toString());
                values.put("driverPhone",et_driver_phone.getText().toString());
                values.put("carId",Integer.parseInt(et_driver_car_id.getText().toString()));
                affectedRows=myDBHelper.getWritableDatabase().update("driverInfo",values,"driverId=?",new String[]{et_driver_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(DriverActivity.this,"修改驾驶员编号为"+et_driver_id.getText().toString()+"的驾驶员信息成功");
                }else{
                    ToastUtil.show(DriverActivity.this,"修改失败");
                }
                break;


        }
    }
}