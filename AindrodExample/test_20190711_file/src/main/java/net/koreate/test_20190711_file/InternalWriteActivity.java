package net.koreate.test_20190711_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InternalWriteActivity extends AppCompatActivity {

    @BindView(R.id.editText)
    EditText content;

    @BindView(R.id.btnFile)
    Button btnFile;

    @BindView(R.id.btnCache)
    Button btnCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_write);
        ButterKnife.bind(this);

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = content.getText().toString();
                System.out.println("내용 : "+text);

                FileOutputStream fos = null;
                String fileName = "sample.txt";
                try {
                    fos = openFileOutput(fileName, MODE_PRIVATE | MODE_APPEND);
                    fos.write(text.getBytes());
                    fos.flush();
                    fos.close();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    content.setText("");
                    Intent i = new Intent(
                                    InternalWriteActivity.this,
                                    ReadInternalActivity.class
                                );
                    i.putExtra("data","normal");
                    startActivity(i);
                }
            }
        });


        btnCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   String text = content.getText().toString();
                   String fileName = "cache";
                   FileOutputStream fos = null;
                   File file = null;
                try {
                    file = File.createTempFile(fileName,".txt",getCacheDir());
                    System.out.println("FILE : " + file);
                    System.out.println("cache file : " + file.getName());
                    fos = new FileOutputStream(file);
                    fos.write(text.getBytes());
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    try {fos.close();} catch (IOException e) {}
                    content.setText("");
                    Intent i = new Intent(
                            InternalWriteActivity.this,
                            ReadInternalActivity.class
                    );
                    System.out.println("cache file name : " + file.getName());
                    i.putExtra("data","cache");
                    i.putExtra("cache",file.getName());
                    startActivity(i);
                }
            }
        });


    }
}
