package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.CarInfo;
import monki.study.system_server.table.CarTable;
import monki.study.system_server.util.ToastUtil;

public class CarActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDBHelper dbHelper;
    private EditText et_car_id;
    private EditText et_car_content;
    private EditText et_car_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        dbHelper=MyApplication.getInstance().getMyDBHelper();
        et_car_id = findViewById(R.id.et_car_id);
        et_car_content = findViewById(R.id.et_car_content);
        et_car_type = findViewById(R.id.et_car_type);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_retrieve).setOnClickListener(this);
        findViewById(R.id.btn_delete_by_id).setOnClickListener(this);
        findViewById(R.id.btn_update_by_id).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                CarInfo info =new CarInfo();
                info.setCarId(Integer.parseInt(et_car_id.getText().toString()));
                info.setCarContent(Integer.parseInt(et_car_content.getText().toString()));
                info.setCarCategory(et_car_type.getText().toString());
                dbHelper.insertNewCar(info);
                ToastUtil.show(this,"插入了一条车辆信息");
                break;
            case R.id.btn_retrieve:
                Intent intent = new Intent(this, CarTable.class);
                startActivity(intent);
                break;
            case R.id.btn_delete_by_id:
                int affectedRows=dbHelper.getWritableDatabase().delete("carInfo","carId=?",new String[]{et_car_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(CarActivity.this,"删除车辆编号为"+et_car_id.getText().toString()+"的车辆信息成功");
                }else{
                    ToastUtil.show(CarActivity.this,"删除失败");
                }
                break;
            case R.id.btn_update_by_id:
                ContentValues values = new ContentValues();
                values.put("carType",et_car_type.getText().toString());
                values.put("carContent",et_car_content.getText().toString());

                affectedRows=dbHelper.getWritableDatabase().update("carInfo",values,"carId=?",new String[]{et_car_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(CarActivity.this,"修改车辆编号为"+et_car_id.getText().toString()+"的车辆信息成功");
                }else{
                    ToastUtil.show(CarActivity.this,"修改失败");
                }
                break;
        }
    }
}