package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import monki.study.system_client.util.ToastUtil;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private String passengerPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.ib_ticket).setOnClickListener(this);
        findViewById(R.id.ib_selfinfo).setOnClickListener(this);
        findViewById(R.id.ib_line).setOnClickListener(this);
        findViewById(R.id.ib_exit).setOnClickListener(this);

        Intent intent = getIntent();
        passengerPhone=intent.getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_ticket:
                Intent intent1 = new Intent(this, TicketPurchaseActivity.class);
                intent1.putExtra("phone",passengerPhone);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.ib_selfinfo:
                Intent intent2 = new Intent(this,SelfInfoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("phone",passengerPhone);
                startActivity(intent2);
                ToastUtil.show(this,passengerPhone);
                break;
            case R.id.ib_line:
                Intent intent3 = new Intent(this,LineQueryActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent3);
                break;
            case R.id.ib_exit:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this)
                        .setTitle("切换账号")
                        .setMessage("这将会退出你当前的账号，确定要切换吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent4 = new Intent(HomeActivity.this,MainActivity.class);
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