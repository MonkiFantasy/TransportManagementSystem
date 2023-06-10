package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

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
        findViewById(R.id.btn_read).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);

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
            case R.id.btn_read:

                List<DriverInfo> driverq = new ArrayList<>();
                driverq = myDBHelper.queryAllDrivers();
                StringBuilder result =new StringBuilder();
                for (DriverInfo info : driverq) {
                    result.append(info.toString()+"\n");
                }
                Log.d("Monki","result:"+result);
                Toast.makeText(this,result.toString(),Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_query:
                Cursor cursor = myDBHelper.queryCarTypeByDriverName(et_driver_name.getText().toString());
                while (cursor.moveToNext()){
                    ToastUtil.show(this,"车型："+cursor.getString(0));
                }


        }
    }
}