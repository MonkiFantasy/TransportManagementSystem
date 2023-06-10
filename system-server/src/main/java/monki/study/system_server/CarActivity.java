package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.CarInfo;
import monki.study.system_server.util.ToastUtil;

public class CarActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDBHelper dbHelper;
    private EditText et_car_id;
    private EditText et_car_content;
    private EditText et_cat_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        dbHelper=MyApplication.getInstance().getMyDBHelper();
        et_car_id = findViewById(R.id.et_car_id);
        et_car_content = findViewById(R.id.et_car_content);
        et_cat_type = findViewById(R.id.et_car_type);
        findViewById(R.id.btn_save).setOnClickListener(this);

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
                info.setCarCategory(et_cat_type.getText().toString());
                dbHelper.insertNewCar(info);
                ToastUtil.show(this,"插入了一条车辆信息");
                break;
        }
    }
}