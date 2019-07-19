package net.koreate.test_20190719_network_valley;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends AppCompatActivity {

    @BindView(R.id.uploadBtn)
    Button uploadBtn;

    @BindView(R.id.uploadImage)
    ImageView uploadImage;

    public static final String UPLOAD_KEY="file";

    public String document_img ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        uploadImage.setOnClickListener(view -> {
            selectImage();
        });
    }
    // Image pick
    private void selectImage() {
        // dialog
        final CharSequence[] options = {"Choose Image gallery","cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
        builder.setTitle("ADD PHOTO!!");
        builder.setItems(options,(dialog,id)->{
            if(options[id].equals("Choose Image gallery")){
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType(MediaStore.Images.Media.CONTENT_TYPE);
                i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,1);
            }else if((options[id].equals("cancel"))){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){

            try {
                Uri selectImage = data.getData();
                Bitmap thumbnail
                    = MediaStore.Images.Media.getBitmap(getContentResolver(), selectImage);

                thumbnail = getResizeBitMap(thumbnail,400);
                System.out.println("resize thumbnail - width : " + thumbnail.getWidth());
                System.out.println("resize thumbnail - height : " + thumbnail.getHeight());
                uploadImage.setImageBitmap(thumbnail);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public Bitmap getResizeBitMap(Bitmap image,int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        System.out.println("width : " + width);
        System.out.println("height : " + height);

        float bitmapRatio = (float)width/(float)height;
        if(bitmapRatio > 1){
            width = maxSize;
            height= (int)(width/bitmapRatio);
        }else{
            height = maxSize;
            width = (int)(height/bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }

}
