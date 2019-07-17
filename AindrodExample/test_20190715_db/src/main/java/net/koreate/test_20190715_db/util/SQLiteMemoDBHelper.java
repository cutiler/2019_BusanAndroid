package net.koreate.test_20190715_db.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.koreate.test_20190715_db.vo.MemoVO;

import java.util.ArrayList;

public class SQLiteMemoDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME ="db_memo";
    public static final String TABLE_NAME = "tbl_memo";

    SQLiteDatabase db;

    public SQLiteMemoDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String memoSql = "CREATE TABLE "+TABLE_NAME+ " ("
                        + "id integer primary key autoincrement ,"
                        + "title text,"
                        +"content text)";
        sqLiteDatabase.execSQL(memoSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    // memo 삽입
    public void insertMemo(String title, String content){
        db = getWritableDatabase();
        //String sql = "INSERT INTO "+TABLE_NAME +"(title,content) VALUES('"+title+"','"+content+"')";
        /*String sql = "INSERT INTO "+TABLE_NAME +"(title,content) VALUES(?,?)";
        db.execSQL(sql,new String[]{title,content});*/

        ContentValues cv =  new ContentValues();
        cv.put("title",title);
        cv.put("content",content);

        db.insert(TABLE_NAME,null,cv);
        //db = getReadableDatabase();
        db.close();
    }

    public MemoVO readLastMemo(){
        MemoVO memo = new MemoVO();
        db = getReadableDatabase();

        String sql = "SELECT id,title,content FROM "+ TABLE_NAME+" ORDER BY id DESC limit 1";
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            memo.setId(cursor.getInt(0));
            memo.setTitle(cursor.getString(1));
            memo.setContent(cursor.getString(2));
        }
        db.close();
        return memo;
    }

    // title 목록
    public ArrayList<String> readTitleList(){
        ArrayList<String> titleList = new ArrayList<>();
        db = getReadableDatabase();
        String sql = "SELECT title FROM " + TABLE_NAME +" ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            titleList.add(cursor.getString(0));
        }
        return titleList;
    }

    //전체 리스트
    public ArrayList<MemoVO> readMemoList(){
        ArrayList<MemoVO> memoList = new ArrayList<>();
        db = getReadableDatabase();
        String sql = "SELECT id,title,content FROM "+TABLE_NAME + " ORDER BY id DESC";
        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
            MemoVO memo = new MemoVO();
            memo.setId(cursor.getInt(0));
            memo.setTitle(cursor.getString(1));
            memo.setContent(cursor.getString(2));
            memoList.add(memo);
        }
        db.close();
        return memoList;
    }

    // 메모 수정
    public void updateMemo(MemoVO memo){
        db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("title",memo.getTitle());
        cv.put("content",memo.getContent());
        db.update(TABLE_NAME,cv,"id=?",new String[]{String.valueOf(memo.getId())});
        db.close();
    }

    public void deleteMemo(int id){
        db = getWritableDatabase();
        db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
        db.close();
    }

}
