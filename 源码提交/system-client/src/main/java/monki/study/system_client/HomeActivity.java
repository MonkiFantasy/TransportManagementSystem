package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import monki.study.system_client.util.ToastUtil;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private String passengerPhone;
    private int passengerId;

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
        passengerId = getPassengerId();
    }

    private int getPassengerId() {
        String s = "content://"+"monki.study.system_server.provider.passengerprovider"+"/passengerInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor = getContentResolver().query(uri,new String[]{"passengerId"},"passengerPhone=?",new String[]{passengerPhone},null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_ticket:
                Intent intent1 = new Intent(this, TicketPurchaseActivity.class);
                intent1.putExtra("id",passengerId);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
            case R.id.ib_selfinfo:
                Intent intent2 = new Intent(this,SelfInfoActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("id",passengerId);
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