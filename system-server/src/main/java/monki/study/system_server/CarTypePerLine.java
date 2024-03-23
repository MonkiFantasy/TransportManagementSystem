package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import monki.study.system_server.database.MyDBHelper;

public class CarTypePerLine extends AppCompatActivity implements View.OnClickListener {

    private MyDBHelper myDBHelper;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_type_per_line);
        tvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_query).setOnClickListener(this);
        myDBHelper=MyApplication.getInstance().getMyDBHelper();
        queryAllLineInView();

    }

    private void queryAllLineInView() {
        Cursor cursor = myDBHelper.getReadableDatabase().query("VIEW_LINE_CARTYPE_NUM", null, null, null, null, null, null);
        if (cursor!=null&&cursor.moveToFirst()){
            StringBuilder result = new StringBuilder();
            do {
                StringBuilder sb = new StringBuilder();

                sb.append("\n线路:"+cursor.getInt(0));
                sb.append(" "+cursor.getString(1));
                sb.append(" to "+cursor.getString(2));
                sb.append("\n"+cursor.getString(3));
                sb.append(" "+cursor.getInt(4)+"辆");
                result.append(sb);
            }while (cursor.moveToNext());
            tvResult.setText(result.toString());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_result:
                queryAllLineInView();
                break;
        }
    }


}