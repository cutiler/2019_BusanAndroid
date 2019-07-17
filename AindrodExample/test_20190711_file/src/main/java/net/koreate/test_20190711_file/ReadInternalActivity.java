package net.koreate.test_20190711_file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReadInternalActivity extends AppCompatActivity {

    @BindView(R.id.result)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        Intent i = getIntent();
        String data = i.getStringExtra("data");
        System.out.println("data : "+data);

        if(data.equals("normal")){
            getNormalFile();
        }else{
            String cache = i.getStringExtra("cache");
            getCacheFile(cache);
        }
    }

    // 일반 파일
    public void getNormalFile(){
        File file = getFilesDir();
        String fileName = "sample.txt";
        System.out.println("내부 저장소 경로 : " + file.getPath());
        System.out.println("파일 절대 경로 : " + getFileStreamPath(fileName));

        File normalFile = getFileStreamPath(fileName);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(normalFile));
            int buffer = 0;

            while((buffer = reader.read()) != -1  ){
                String text = String.valueOf((char)buffer);
                System.out.println(text);
                result.append(text);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }


    }

    // 캐쉬 파일
    public void getCacheFile(String fileName){
        File cacheFile = new File(getCacheDir().getAbsolutePath(),fileName);

        BufferedReader reader =  null;
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(cacheFile);
            reader  = new BufferedReader(fileReader);

            int buffer = 0;
            while((buffer = reader.read()) != -1){
                String data = String.valueOf((char)buffer);
                System.out.println("cache data : " + data);
                result.append(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                reader.close();
                fileReader.close();
            } catch (IOException e) {}
        }

    }

}


