package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import monki.study.system_server.database.MyDBHelper;

public class TicketSoldPerLine extends AppCompatActivity implements View.OnClickListener {

    private MyDBHelper myDBHelper;
    private TextView tvResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_sold_per_line);
        tvResult = findViewById(R.id.tv_result);

        findViewById(R.id.btn_query).setOnClickListener(this);
        myDBHelper=MyApplication.getInstance().getMyDBHelper();
        queryAllLineSoldTime();

    }

    private void queryAllLineSoldTime() {
        String query = "SELECT lineId, COUNT(ticketId) AS ticketCount FROM ticketInfo WHERE isSold = 1 GROUP BY lineId";
        //Cursor cursor = myDBHelper.getReadableDatabase().query("ticketInfo", new String[]{"lineId","count(ticketId)"}, null, null, "lineId", "isSold=1", null);
        Cursor cursor = myDBHelper.getReadableDatabase().rawQuery(query,null);
        if (cursor!=null&&cursor.moveToFirst()){
            StringBuilder result = new StringBuilder();
            do {
                StringBuilder sb = new StringBuilder();
                sb.append("\n线路:"+cursor.getInt(0));
                sb.append(" 乘坐次数:"+cursor.getInt(1));
                result.append(sb);
            }while (cursor.moveToNext());
            tvResult.setText(result.toString());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_result:
                queryAllLineSoldTime();
                break;
        }
    }
}