package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.util.ToastUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUser,etPassword;
    private MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser= findViewById(R.id.et_user);
        etPassword=findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.btn_database_connect).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                if(etUser.getText().toString().equals("admin")&&etPassword.getText().toString().equals("admin"))
                {
                    ToastUtil.show(this,"登录成功");
                    Intent intent = new Intent(this,HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    ToastUtil.show(this,"账号或密码输入错误，请重新输入");
                    etUser.setText("");
                    etPassword.setText("");
                }
                break;
            case R.id.btn_database_connect:
                dbHelper=MyApplication.getInstance().getMyDBHelper();
                dbHelper.getReadableDatabase();
                dbHelper.getReadableDatabase();
                ToastUtil.show(this,"数据库已连接");
                break;
        }
    }
}