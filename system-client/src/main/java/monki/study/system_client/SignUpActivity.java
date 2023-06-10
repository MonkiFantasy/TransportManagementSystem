package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import monki.study.system_client.util.ToastUtil;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText et_signup_name;
    private EditText et_signup_age;
    private CheckBox ck_signup_male;
    private CheckBox ck_signup_female;
    private EditText et_signup_phone;
    private EditText et_signup_password;
    private EditText et_verify_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findViewById(R.id.btn_signup_summit).setOnClickListener(this);
        et_signup_name = findViewById(R.id.et_signup_name);
        et_signup_age = findViewById(R.id.et_signup_age);
        ck_signup_male = findViewById(R.id.ck_signup_male);
        ck_signup_female = findViewById(R.id.ck_signup_female);
        et_signup_phone = findViewById(R.id.et_signup_phone);
        et_signup_password = findViewById(R.id.et_signup_password);
        et_verify_password = findViewById(R.id.et_verify_password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_signup_summit:
                if (!(ck_signup_female.isChecked()^ck_signup_male.isChecked())){
                    ToastUtil.show(this,"请选择正确的性别");
                }/*else if(true){//判空

                    ToastUtil.show(this,"请正确填写所有信息");
                }*/else if(!et_signup_password.getText().toString().equals(et_verify_password.getText().toString())){
                    ToastUtil.show(this,"两次输入的账号密码不匹配，请重新输入");
                }
                else{
                    writePassengerInfo();
                    ToastUtil.show(this,"注册成功");
                    //跳转
                }

                break;
/*
                Intent intent = new Intent(this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
*/
        }
    }

    private void writePassengerInfo() {
        String AUTHORITIES = "monki.study.system_server.provider.passengerprovider";
        String str="content://"+AUTHORITIES+"/passengerInfo";
        Uri uri = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(AUTHORITIES)
                .appendPath("passengerInfo")
                .build();
        ContentResolver resolver = getContentResolver();
        ContentValues cv = new ContentValues();
        cv.put("passengerName",et_signup_name.getText().toString());
        cv.put("passengerSex",ck_signup_male.isChecked()&&!ck_signup_female.isChecked()?"男":"女");
        cv.put("passengerAge",Integer.parseInt(et_signup_age.getText().toString()));
        cv.put("passengerPhone",et_signup_phone.getText().toString());
        cv.put("passengerPassword",et_signup_password.getText().toString());
        resolver.insert(uri,cv);
    }
}