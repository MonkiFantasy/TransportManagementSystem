package monki.study.system_server;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.ib_car).setOnClickListener(this);
        findViewById(R.id.ib_driver).setOnClickListener(this);
        findViewById(R.id.ib_line).setOnClickListener(this);
        findViewById(R.id.ib_shift).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.ib_car:
                Intent intent_car = new Intent(this,CarActivity.class);
                startActivity(intent_car);
                break;
            case R.id.ib_driver:
                Intent intent_driver = new Intent(this,DriverActivity.class);
                intent_driver.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_driver);
                break;
            case R.id.ib_line:
                Intent intent_line = new Intent(this,LineActivity.class);
                intent_line.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_line);
                break;
            case R.id.ib_shift:
                Intent intent_shift = new Intent(this,ShiftActivity.class);
                intent_shift.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_shift);
                break;
            default:
        }
    }
}