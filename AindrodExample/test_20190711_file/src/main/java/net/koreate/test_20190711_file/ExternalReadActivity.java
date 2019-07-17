package net.koreate.test_20190711_file;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExternalReadActivity extends AppCompatActivity {

    @BindView(R.id.result)
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);

        int currentApiVersion = android.os.Build.VERSION.SDK_INT;

        String dirPath = "";

        if(currentApiVersion >= 29){
            dirPath = getExternalMediaDirs()[0]
                    .getAbsolutePath()
                    + File.separator
                    +"sample"
                    +File.separator
                    +"sample.txt";
        }else{
            dirPath = Environment
                    .getExternalStorageDirectory()
                    .getAbsolutePath()
                    +File.separator
                    +"sample"
                    +File.separator
                    +"sample.txt";
        }

        File file = new File(dirPath);

        FileReader fileReader = null;
        BufferedReader reader = null;

        try {
            fileReader = new FileReader(file);
            reader = new BufferedReader(fileReader);

            int buffer = 0;
            while((buffer = reader.read()) != -1){
                String data = String.valueOf((char)buffer);
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
