package net.koreate.test_20190711_file;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExternalWriteActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.content)
    EditText content;

    @BindView(R.id.addBtn)
    Button addBtn;

    boolean writeExternalPermission = false;
    boolean readExternalPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external_write);
        ButterKnife.bind(this);
        addBtn.setOnClickListener(this);
    }

    public void checkPermission(){
        System.out.println("check permission");
        if(ContextCompat.checkSelfPermission(
                ExternalWriteActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
         == PackageManager.PERMISSION_GRANTED){
            writeExternalPermission = true;
        }

        if(ContextCompat.checkSelfPermission(
                ExternalWriteActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
            readExternalPermission = true;
        }

        if(!writeExternalPermission || !readExternalPermission){
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                    },0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0 && grantResults.length > 0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                writeExternalPermission = true;
            }
            if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                readExternalPermission = true;
            }
        }
    }

    @Override
    public void onClick(View view) {
        int currentApiVersion = Build.VERSION.SDK_INT;

        if(currentApiVersion >= Build.VERSION_CODES.M){
            checkPermission();
        }else{
            writeExternalPermission = true;
            readExternalPermission = true;
        }

        if(writeExternalPermission && readExternalPermission){
            String text = content.getText().toString();
            String dirPath ="";

            if(currentApiVersion >= 29){
                System.out.println("current API Version : " + currentApiVersion);
                for(File file : getExternalMediaDirs()){
                    System.out.println(file.getAbsolutePath());
                }
                //dirPath = getExternalFilesDir("sample").getAbsolutePath();
                dirPath = getExternalMediaDirs()[0].getAbsolutePath()+File.separator+"sample";
            }else {
                dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "sample";
            }
            try {
                File file = new File(dirPath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                File file1 = new File(dirPath + File.separator + "sample.txt");
                if (!file1.exists()) {
                    file1.createNewFile();
                }

                FileWriter writer = new FileWriter(file1, true);
                writer.write(text);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent i = new Intent(
                    ExternalWriteActivity.this,
                    ExternalReadActivity.class
            );
            startActivity(i);
            System.out.println("작성종료");
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(ExternalWriteActivity.this);
            builder.setTitle("권한 불충분");
            builder.setMessage("파일 권한을 허용하시겠습니까?");
            builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPermission();
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
    }
}
