package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class SelfInfoActivity extends AppCompatActivity {

    private TextView tvShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_info);
        tvShow=findViewById(R.id.tv_show);
        Intent intent=getIntent();
        String passengerPhone = intent.getStringExtra("phone");
        String s = "content://"+"monki.study.system_server.provider.passengerprovider"+"/passengerInfo";
        Uri uri = Uri.parse(s);
        Cursor cursor = getContentResolver().query(uri,new String[]{" passengerName","passengerAge","passengerSex"},"passengerPhone=?",new String[]{passengerPhone},null);
        StringBuilder sb = new StringBuilder();
        while (cursor.moveToNext()){
            sb.append("姓名:"+cursor.getString(0)+"\t年龄"+String.valueOf(cursor.getInt(1))+"\t性别："+cursor.getString(2)+"\n");
        }
        tvShow.setText(sb.toString());

    }
}