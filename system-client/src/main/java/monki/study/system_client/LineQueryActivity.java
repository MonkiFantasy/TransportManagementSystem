package monki.study.system_client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import monki.study.system_client.entity.LineInfo;


public class LineQueryActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_query);
        tv_result = findViewById(R.id.tv_result);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                String AUTHORITIES = "monki.study.system_server.provider.lineinfoprovider";
                String str="content://"+AUTHORITIES+"/lineInfo";
                Uri uri =Uri.parse(str);
                ContentResolver resolver = getContentResolver();
                Cursor query = resolver.query(uri, null, null, null, null);
                List<LineInfo> list= new ArrayList<>();
                while (query.moveToNext()){
                    LineInfo info = new LineInfo();
                    info.setLineNumber(query.getInt(0));
                    info.setStartPoint(query.getString(1));
                    info.setDestination(query.getString(2));
                    list.add(info);
                }
                StringBuilder sb = new StringBuilder();
                for (LineInfo lineInfo : list) {
                    sb.append(lineInfo.toString()+"\n");
                }
                tv_result.setText(sb.toString());
        }

    }

}