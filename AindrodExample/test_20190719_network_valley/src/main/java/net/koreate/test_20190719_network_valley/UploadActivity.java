package net.koreate.test_20190719_network_valley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;

import net.koreate.test_20190719_network_valley.util.MainController;
import net.koreate.test_20190719_network_valley.util.URLConfig;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.uploadBtn)
    Button uploadBtn;

    @BindView(R.id.uploadImage)
    ImageView uploadImage;

    @BindView(R.id.resultImage)
    ImageView resultImage;

    public static final String UPLOAD_KEY="file";

    public String document_img ="";
    // 파일이름
    String fileName;
    //확장자
    String ext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
        uploadImage.setOnClickListener(view -> {
            selectImage();
        });
        uploadBtn.setOnClickListener(this);
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

                String []  filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(selectImage,filePath,null,null,null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);

                System.out.println(picturePath);

                ext = picturePath.substring(picturePath.lastIndexOf(".")+1);
                System.out.println("확장자 : " + ext);

                fileName = picturePath.substring(
                        picturePath.lastIndexOf(File.separator)+1,
                        picturePath.lastIndexOf(".")
                );
                System.out.println("fileName : "+fileName);

                thumbnail = getResizeBitMap(thumbnail,400);
                System.out.println("resize thumbnail - width : " + thumbnail.getWidth());
                System.out.println("resize thumbnail - height : " + thumbnail.getHeight());
                uploadImage.setImageBitmap(thumbnail);
                bitMapToString(thumbnail);
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

    // 이미지  파일 변환
    public void bitMapToString(Bitmap userImage){
        //다른곳에 입출력하기 전에 데이터를 임시로
        // byte배열에 담아서 변환등의 작업에 사용되는 스트림
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage.compress(Bitmap.CompressFormat.PNG,60,baos);
        byte[] b = baos.toByteArray();
        document_img = Base64.encodeToString(b,Base64.DEFAULT);
        System.out.println(document_img);
        //document_img = Base64.encodeToString(b,Base64.NO_WRAP);
    }

    // 이미지 data 전송(volley)
    public void sendData(){
        final ProgressDialog dialog = new ProgressDialog(UploadActivity.this);
        dialog.setMessage("잠시만 기다려 주세요..");
        dialog.setCancelable(false);
        dialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                URLConfig.TEST_UPLOAD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        dialog.dismiss();

                        //resultImage
                        Glide.with(resultImage.getContext()).load(URLConfig.BASE+response).into(resultImage);
                        uploadImage.setImageBitmap(null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("error : "+error.toString());
                        dialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                // file
                params.put(UPLOAD_KEY,document_img);
                params.put("fileName",fileName);
                params.put("ext",ext);
                return params;
            }
        };

        MainController.getInstance().addToRequestQueue(stringRequest,"IMG_UPLOAD");

    }

    @Override
    public void onClick(View view) {
        if(document_img.equals("") || document_img.equals(null)){
            AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivity.this);
            builder.setTitle("document_img Can't empty");
            builder.setMessage("이미지를 선택해주세요!");
            builder.setPositiveButton("확인",(dialog,id)->{
                uploadImage.performClick();
            });
            builder.show();
        }else{
            sendData();
        }
    }
}
