package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.ib_retrieve).setOnClickListener(this);
        findViewById(R.id.ib_manage).setOnClickListener(this);
        findViewById(R.id.ib_exit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_retrieve:
                Intent intent1 = new Intent(this,InformationRetrieveActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.ib_manage:
                Intent intent2 = new Intent(this,MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;

            case R.id.ib_exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("切换账号")
                        .setMessage("这将会退出你当前的账号，确定要切换吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent4 = new Intent(HomeActivity.this,LoginActivity.class);
                                intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent4);
                            }
                        })
                        .setNegativeButton("取消",null);
                dialog.show();

                break;
        }
    }
}