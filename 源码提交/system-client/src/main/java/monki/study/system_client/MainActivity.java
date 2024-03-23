package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import monki.study.system_client.util.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_user;
    private EditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        et_user = findViewById(R.id.et_user);
        et_password = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_signup:
                Intent intent_signup = new Intent(this,SignUpActivity.class);
                intent_signup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent_signup);
                break;
            case R.id.btn_login:
                if(verifyPassengerInfo()){
                    Intent intent_login = new Intent(this,HomeActivity.class);
                    intent_login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent_login.putExtra("phone",et_user.getText().toString());
                    startActivity(intent_login);
                }else{
                    ToastUtil.show(this,"账号或密码有误，请重新输入");
                }

                break;
        }
    }

    private boolean verifyPassengerInfo() {
        String user=et_user.getText().toString();
        String password=et_password.getText().toString();

        String s = "content://"+"monki.study.system_server.provider.passengerprovider"+"/passengerInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor = getContentResolver().query(uri,null,"passengerPhone=? and passengerPassword=?",new String[]{user,password},null);
        return cursor.moveToFirst();
    }
}