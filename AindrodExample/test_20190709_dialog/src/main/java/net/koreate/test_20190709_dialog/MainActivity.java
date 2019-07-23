package net.koreate.test_20190709_dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import net.koreate.test_20190709_dialog.util.LoadingDialog;

import java.util.Calendar;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.btnAlert) Button btnAlert;
    @BindView(R.id.btnList) Button btnList;
    @BindView(R.id.btnListMulti) Button btnListMulti;
    @BindView(R.id.btnDate) Button btnDate;
    @BindView(R.id.btnTime) Button btnTime;
    @BindView(R.id.btnCustom) Button btnCustom;
    @BindView(R.id.btnLoading) Button btnLoading;

    DialogInterface.OnClickListener listener;

    AlertDialog basicDialog, listDialog, listMultiDialog;

    LoadingDialog loadingDialog;

    @BindArray(R.array.dog_list) String[] dog_list;

    boolean[] dogs;
    String dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initView();
        setDialogInterface();

    }

    public void initView(){
        btnAlert.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnListMulti.setOnClickListener(this);
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);
        btnCustom.setOnClickListener(this);
        btnLoading.setOnClickListener(this);
        loadingDialog = new LoadingDialog(MainActivity.this,R.style.whiteProgressStyle);
        loadingDialog.setCancelable(true);
    }

    private void setDialogInterface() {
        listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface == basicDialog){

                    if(i == DialogInterface.BUTTON_NEGATIVE){

                        Toast.makeText(
                                MainActivity.this,
                                "다음에 더큰 돈을 요청 할게요!",
                                Toast.LENGTH_SHORT
                        ).show();

                    }else if(i == DialogInterface.BUTTON_POSITIVE) {
                        Toast.makeText(
                                MainActivity.this,
                                "잘쓰겠습니다.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else if(i == DialogInterface.BUTTON_NEUTRAL){
                        Toast.makeText(
                                MainActivity.this,
                                "다음 기회에...",
                                Toast.LENGTH_SHORT
                        ).show();
                    }

                        Log.i("basic dialog","click");
                }else if(dialogInterface == listDialog){
                    if(i >= 0){
                        dog = dog_list[i];
                        Toast.makeText(
                                MainActivity.this,
                                dog_list[i]+"선택",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else if(i == dialogInterface.BUTTON_POSITIVE){
                        Toast.makeText(
                                MainActivity.this,
                                "선택한 강아지는 : " + dog +"입니다.",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }else if(dialogInterface == listMultiDialog){
                    for(int index = 0; index<dogs.length; index++){
                        if(dogs[index]){
                            Log.i("select Dog : ",dog_list[index]);
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onClick(View view) {
        if(view == btnAlert){
            Log.i("btn alert : " , "click" );
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("알림");
            builder.setIcon(R.drawable.puppy);
            builder.setMessage("확인을 눌리면 결제가 완료됩니다!  \n 50,000,000원");
            builder.setNegativeButton("CANCEL",listener);
            builder.setPositiveButton("OK",listener);
            builder.setNeutralButton("NEXT",listener);

            basicDialog = builder.create();
            basicDialog.show();
        }else if(view == btnList){
            //dog_list = getResources().getStringArray(R.array.dog_list);
            for(String s : dog_list){
                Log.i("dog_list",s);
            }
            dog = dog_list[0];
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("좋아하는 강아지");
            builder.setIcon(R.drawable.puppy);
            builder.setPositiveButton("확인",listener);
            builder.setSingleChoiceItems(R.array.dog_list,0,listener);
            listDialog = builder.create();
            listDialog.show();
        }else if(view == btnListMulti){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("좋아하는 강아지들 선택");
            builder.setIcon(R.drawable.puppy);
            dogs = new boolean[dog_list.length];
            dogs[0] = true;
            dogs[1] = true;
            builder.setMultiChoiceItems(dog_list, dogs, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i, boolean isChecked) {
                       if(isChecked){
                           Toast.makeText(
                                   MainActivity.this,
                                   dog_list[i]+"체크!",
                                   Toast.LENGTH_SHORT
                           ).show();
                            dogs[i] = true;
                       }else{
                           Toast.makeText(
                                   MainActivity.this,
                                   dog_list[i]+"체크 해제!",
                                   Toast.LENGTH_SHORT
                           ).show();
                           dogs[i] = false;
                       }
                }
            });
            builder.setPositiveButton("OK",listener);
            listMultiDialog = builder.create();
            listMultiDialog.show();
        }else if(view == btnDate){
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                    Log.i("datePicker : " ,year+"."+(month+1)+"."+dayOfMonth);
                }
            },year,month,day);
            dialog.show();
        }else if(view == btnTime){
            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            TimePickerDialog dialog = new TimePickerDialog(
                    MainActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            Log.i("time picker " ,hourOfDay+":"+minute);
                        }
                    },
                    hour,
                    minute,
                    false
            );
            dialog.show();
        }else if(view == btnCustom){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // View 생성  객체
            LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
            // layout xml 파일을 이용해 View java객체 생성
            View v = inflater.inflate(R.layout.custom_dialog,null);

            CheckBox box = v.findViewById(R.id.checkBox);
            box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.i("checkBox : " , b+"");
                }
            });

            builder.setView(v);
            builder.setTitle("CUSTOM");
            builder.setIcon(R.drawable.puppy);
            builder.setPositiveButton("확인",null);
            builder.setNegativeButton("취소",null);
            AlertDialog dialog = builder.create();
            dialog.show();

        }else if(view == btnLoading){
            loadingDialog.show();
        }
    }
}
