package net.koreate.test_20190716_adapterview.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.koreate.test_20190716_adapterview.vo.DogVO;

import java.util.ArrayList;

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "testDB";

    public SQLiteHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE tbl_dog( "
                    + " _id integer primary key autoincrement,"
                    + " kind text ,"
                    + " name text)";
        db.execSQL(sql);
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('골든리트리버','순이')");
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('진돗개','똘이')");
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('푸들','푸순이')");
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('그레이하운드','하운드')");
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('불독','못난이')");
        db.execSQL("INSERT INTO tbl_dog(kind,name) VALUES('시바견','시바')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion == DATABASE_VERSION){
            db.execSQL("drop table tbl_dog");
            onCreate(db);
        }
    }

    public ArrayList<DogVO> getDogList(){
        ArrayList<DogVO> dogList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id,kind,name FROM tbl_dog",null);
        while(cursor.moveToNext()){
            DogVO dog = new DogVO();
            dog.set_id(cursor.getInt(0));
            dog.setKind(cursor.getString(1));
            dog.setName(cursor.getString(2));
            dogList.add(dog);
        }
        db.close();
        return dogList;
    }
}
