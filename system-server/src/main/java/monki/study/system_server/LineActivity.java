package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.LineInfo;
import monki.study.system_server.util.ToastUtil;

public class LineActivity extends AppCompatActivity implements View.OnClickListener {

    private MyDBHelper dbHelper;
    private EditText et_line_id;
    private EditText et_line_start;
    private EditText et_line_end;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        dbHelper=MyApplication.getInstance().getMyDBHelper();
        et_line_id = findViewById(R.id.et_line_id);
        et_line_start= findViewById(R.id.et_line_start);
        et_line_end = findViewById(R.id.et_line_end);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_read).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                LineInfo info = new LineInfo();
                info.setLineNumber(Integer.parseInt(String.valueOf(et_line_id.getText().toString())));
                info.setStartPoint(et_line_start.getText().toString());
                info.setDestination(et_line_end.getText().toString());
                dbHelper.insertNewLine(info);
                ToastUtil.show(this,"插入了一条线路信息");
        }
    }
}