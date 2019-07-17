package net.koreate.test_20190711_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Map;

public class SharedPreferencesActivity extends AppCompatActivity {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        pref = getSharedPreferences("pref",MODE_PRIVATE);

        System.out.println(pref.getBoolean("isChecked",false));
        System.out.println(pref.getInt("uno",0));
        System.out.println(pref.getString("hi","defaultValue"));
        System.out.println(pref.getString("bye","defaultValue"));

        savePref("안녕","잘가",1,true);

        printListData();

        savePref("어서오세요","안녕히 가세요",10,true);

        removePreferences("bye");

        System.out.println(getPreferences("hi"));
        System.out.println(getPreferences("bye"));
        System.out.println(getPreferencesInteger("uno"));
        System.out.println(getPreferencesBoolean("isChecked"));

        // 전체 데이터 삭제
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();

        printListData();
   }

   private void removePreferences(String key){
       SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
       SharedPreferences.Editor editor = pref.edit();
       editor.remove(key);
       editor.commit();
   }

   private String getPreferences(String key){
       SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        String value = pref.getString(key,"null string");
        return value;
   }

   private boolean getPreferencesBoolean(String key){
       SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
       boolean value = pref.getBoolean(key,false);
       return value;
   }

    private int getPreferencesInteger(String key){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        int value = pref.getInt(key,0);
        return value;
    }



    private void printListData(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        Map<String,?> total = pref.getAll();
        for(Map.Entry<String,?> entry : total.entrySet()){
            System.out.println("key : " + entry.getKey() + " value : "+ entry.getValue());
        }
    }

    private void savePref(String hi, String bye, int uno, boolean isChecked){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("hi",hi);
        editor.putString("bye",bye);
        editor.putInt("uno",uno);
        editor.putBoolean("isChecked",isChecked);
        editor.commit();
    }




}
