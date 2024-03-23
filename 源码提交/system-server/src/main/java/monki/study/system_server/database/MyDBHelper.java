package monki.study.system_server.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import org.xmlpull.v1.sax2.Driver;

import java.util.ArrayList;
import java.util.List;

import monki.study.system_server.entity.CarInfo;
import monki.study.system_server.entity.DriverInfo;
import monki.study.system_server.entity.LineInfo;
import monki.study.system_server.entity.ShiftInfo;

public class MyDBHelper extends SQLiteOpenHelper {
    private static MyDBHelper myDBHelper = null;
    private Context context;//上下文
    private static final String DB_NAME = "transportManagementSystem.db";
    private static final int DB_VERSION = 1;
    private MyDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context=context;
    }
    //懒汉单例模式
    public static MyDBHelper getInstance(Context context){
        if(myDBHelper==null){
            myDBHelper = new MyDBHelper(context);
        }
        return myDBHelper;
    }

    public static void insertNewShift(ShiftInfo shift) {
        ContentValues values = new ContentValues();
        values.put("shiftId",shift.getShiftId());
        values.put("carId",shift.getCarId());
        values.put("lineNumber",shift.getLineNumber());
        values.put("seat_solded",shift.getSeat_solded());
        values.put("isRunning",shift.isRuning()?1:0);
        values.put("setupTime",shift.getSetupTime());
        values.put("arriveTime",shift.getArriveTime());
        myDBHelper.getWritableDatabase().insert("shiftInfo",null,values);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            //客车表
            /*String sql = "CREATE TABLE if not exists carInfo(\n" +
                    "carId integer primary key NOT NULL, \n" +
                    "carType VARCHAR(20), \n" +
                    "carContent INTEGER NOT NULL);";*/

            String sql="";//建表,视图,触发器的SQL语句
            db.execSQL(sql);//执行SQL语句

            //驾驶员表
            sql ="create table if not exists driverInfo (\n" +
                    "       driverId integer primary key not null,\n" +
                    "       driverName varchar(20),\n" +
                    "       driverSex varchar(20),\n" +
                    "       driverBirth varchar(20),\n" +
                    "       driverPhone varchar(20),\n" +
                    "       carId varchar(20) not null,\n"+
                    "foreign key (carId) references carInfo(carId));";
            db.execSQL(sql);
            //乘客信息
            sql="create table if not exists passengerInfo (\n" +
                    "       passengerId integer primary key autoincrement not null ,\n" +
                    "       passengerName varchar(20),\n" +
                    "       passengerSex varchar(20),\n" +
                    "       passengerAge integer,\n" +
                    "       passengerPhone varchar(20) unique,\n" +
                    "       passengerPassword varchar(40));";
            db.execSQL(sql);
            //线路信息
            sql="create table if not exists lineInfo (\n" +
                    "       lineId integer primary key not null,\n" +
                    "       startPoint varchar(20),\n" +
                    "       destination varchar(20)\n" +
                    "       );";
            db.execSQL(sql);
            //班次信息
            sql = "create table if not exists shiftInfo(\n" +
                    "       shiftId integer primary key not null,\n" +
                    "       carId integer not null,\n" +
                    "       lineNumber integer not null,\n" +
                    "       seat_solded integer,\n" +
                    "       isRunning integer,\n" +
                    "       setupTime datetime,\n" +
                    "       arriveTime datetime,\n" +
                    "       foreign key (carId) references carInfo(carId),\n" +
                    "       foreign key (lineNumber) references lineInfo(lineNumber));";
            db.execSQL(sql);
            //车票信息
            sql="create table if not exists ticketInfo ( \n" +
                    "       ticketId integer primary key autoincrement not null,\n" +
                    "       passengerId integer not null,\n" +
                    "       shiftId integer,\n" +
                    "       ticketPrice float,      \n" +
                    "       isSold integer,\n" +
                    "       lineId integer,"+
                    "       foreign key (passengerId) references passengerInfo(passengerId),\n" +
                    "       foreign key (shiftId) references shiftInfo(shiftId)\n," +
                    "       foreign key (lineId) references shiftInfo(lineNumber)\n" +
                    ");  ";
            db.execSQL(sql);
            //创建视图
            sql="CREATE VIEW IF NOT EXISTS VIEW_LINE_CARTYPE_NUM AS\n" +
                    "SELECT li.lineId, li.startPoint, li.destination, ci.carType, COUNT(*) AS carNumber\n" +
                    "FROM lineInfo li\n" +
                    "JOIN shiftInfo si ON li.lineId = si.lineNumber\n" +
                    "JOIN carInfo ci ON si.carId = ci.carId\n" +
                    "GROUP BY li.lineId, li.startPoint, li.destination, ci.carType;";
            db.execSQL(sql);
            // 创建触发器
            sql = "CREATE TRIGGER IF NOT EXISTS update_sold_seats\n" +
                    "AFTER UPDATE OF isSold ON ticketInfo\n" +
                    "FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "    UPDATE shiftInfo\n" +
                    "    SET seat_solded = seat_solded + (NEW.isSold - OLD.isSold)\n" +
                    "    WHERE shiftId = NEW.shiftId;\n" +
                    "END;";
            db.execSQL(sql);

            //成功提交
            db.setTransactionSuccessful();
        }catch (Exception e){
            Toast.makeText(context,"数据库初始化失败！！",Toast.LENGTH_SHORT).show();
        }finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertNewCar(CarInfo car) {
        ContentValues values=new ContentValues();
        values.put("carId",car.getCarId());
        values.put("carType",car.getCarCategory());
        values.put("carContent",car.getCarContent());

        myDBHelper.getWritableDatabase().insert("carInfo",null,values);
    }

    public void insertNewDriver(DriverInfo driver) {
        ContentValues values = new ContentValues();
        values.put("driverId",driver.getDriverId());
        values.put("driverName",driver.getDriverName());
        values.put("driverSex",driver.getDriverSex());
        values.put("driverBirth",driver.getDriverBirth());
        values.put("driverPhone",driver.getDriverPhone());
        values.put("carId",driver.getCarId());
        myDBHelper.getWritableDatabase().insert("driverInfo",null,values);

    }

    public List<DriverInfo> queryAllDrivers() {
        List<DriverInfo> driver = new ArrayList<>();
        Cursor cursor = myDBHelper.getReadableDatabase().query("driverInfo", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            DriverInfo dri = new DriverInfo();
            dri.setDriverId(cursor.getInt(0));
            dri.setDriverName(cursor.getString(1));
            dri.setDriverSex(cursor.getString(2));
            dri.setDriverBirth(cursor.getString(3));
            dri.setDriverPhone(cursor.getString(4));
            dri.setCarId(cursor.getInt(5));
            driver.add(dri);
        }
        return driver;
    }

    public void insertNewLine(LineInfo info) {
        ContentValues values = new ContentValues();
        values.put("lineId",info.getLineNumber());
        values.put("startPoint", info.getStartPoint());
        values.put("destination",info.getDestination());
        myDBHelper.getWritableDatabase().insert("lineInfo",null,values);
    }
    public Cursor queryCarTypeByDriverName(String name){
        String sql ="select carType from carInfo car,driverInfo driver \n" +
                "        where car.carId=driver.carId and driverName=\""+name+"\";";
        return myDBHelper.getReadableDatabase().rawQuery(sql,null);
    }

}
