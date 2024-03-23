package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import monki.study.system_server.database.MyDBHelper;
import monki.study.system_server.entity.LineInfo;
import monki.study.system_server.table.LineTable;
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
        findViewById(R.id.btn_retrieve).setOnClickListener(this);
        findViewById(R.id.btn_delete_by_id).setOnClickListener(this);
        findViewById(R.id.btn_update_by_id).setOnClickListener(this);
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
                break;
            case R.id.btn_retrieve:
                Intent intent = new Intent(this, LineTable.class);
                startActivity(intent);
                break;
            case R.id.btn_delete_by_id:
                int affectedRows=dbHelper.getWritableDatabase().delete("lineInfo","lineId=?",new String[]{et_line_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(this,"删除线路编号为"+et_line_id.getText().toString()+"的线路信息成功");
                }else{
                    ToastUtil.show(this,"删除失败");
                }
                break;
            case R.id.btn_update_by_id:
                ContentValues values = new ContentValues();
                values.put("startPoint",et_line_start.getText().toString());
                values.put("destination",et_line_end.getText().toString());

                affectedRows=dbHelper.getWritableDatabase().update("lineInfo",values,"lineId=?",new String[]{et_line_id.getText().toString()});
                if(affectedRows==1){
                    ToastUtil.show(this,"修改线路编号为"+et_line_id.getText().toString()+"的线路信息成功");
                }else{
                    ToastUtil.show(this,"修改失败");
                }
                break;
        }
    }
}