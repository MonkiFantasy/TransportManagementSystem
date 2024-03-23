package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import monki.study.system_server.table.CarTable;
import monki.study.system_server.table.DriverTable;
import monki.study.system_server.table.LineTable;
import monki.study.system_server.table.PassengerTable;
import monki.study.system_server.table.ShiftTable;
import monki.study.system_server.table.TicketTable;

public class DatabaseTables extends AppCompatActivity implements View.OnClickListener {
    //private ImageButton ibCar,ibDriver,ibLine,ibPassenger,ibShift,ibTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_tables);
        findViewById(R.id.ib_car_table).setOnClickListener(this);
        findViewById(R.id.ib_driver_table).setOnClickListener(this);
        findViewById(R.id.ib_line_table).setOnClickListener(this);
        findViewById(R.id.ib_passenger_table).setOnClickListener(this);
        findViewById(R.id.ib_shift_table).setOnClickListener(this);
        findViewById(R.id.ib_ticket_table).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_car_table:
                Intent intent = new Intent(this, CarTable.class);
                startActivity(intent);
                break;
            case R.id.ib_driver_table:
                intent=new Intent(this, DriverTable.class);
                startActivity(intent);
                break;
            case R.id.ib_line_table:
                intent=new Intent(this, LineTable.class);
                startActivity(intent);
                break;
            case R.id.ib_passenger_table:
                intent=new Intent(this, PassengerTable.class);
                startActivity(intent);
                break;
            case R.id.ib_shift_table:
                intent=new Intent(this, ShiftTable.class);
                startActivity(intent);
                break;
            case R.id.ib_ticket_table:
                intent=new Intent(this, TicketTable.class);
                startActivity(intent);
                break;

        }
    }
}