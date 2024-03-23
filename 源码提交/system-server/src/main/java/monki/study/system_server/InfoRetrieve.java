package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InfoRetrieve extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_retrieve);
        findViewById(R.id.ib_ticket_sold_per_line).setOnClickListener(this);
        findViewById(R.id.ib_ticket_sold_per_shift).setOnClickListener(this);
        findViewById(R.id.ib_carType_per_car).setOnClickListener(this);
        findViewById(R.id.ib_database).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_carType_per_car:
                Intent intent = new Intent(this,CarTypePerLine.class);
                startActivity(intent);
                break;
            case R.id.ib_ticket_sold_per_line:
                intent = new Intent(this,TicketSoldPerLine.class);
                startActivity(intent);
                break;
            case R.id.ib_ticket_sold_per_shift:
                intent = new Intent(this,TicketSoldPerShift.class);
                startActivity(intent);
                break;
            case R.id.ib_database:
                intent = new Intent(this,DatabaseTables.class);
                startActivity(intent);
                break;
        }
    }
}