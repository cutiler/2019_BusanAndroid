package net.koreate.test_20190710_intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.koreate.test_20190710_intent.vo.MemberVO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.userId)
    EditText editText;

    @BindView(R.id.userPw)
    EditText editText2;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnResult)
    Button btnResult;

    @BindView(R.id.btnCallPhone)
    Button btnCallPhone;

    @BindView(R.id.btnCallGallery)
    Button btnCallGallery;

    @BindView(R.id.imageView)
    ImageView imageView;

    final static int REQ_CODE_SELECT_IMAGE = 2001;
    final static int RUNTIME_PERMISSIONS_REQ_CALL_PHONE = 3001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 저나 걸기
        btnCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int SDK_INT = Build.VERSION.SDK_INT;
                showToast(SDK_INT+" VERSION");
                if(SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(
                            MainActivity.this,
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                    ) {
                        System.out.println("권한 없음");
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                RUNTIME_PERMISSIONS_REQ_CALL_PHONE);
                    } else {
                        System.out.println("권한 있음");
                        startCall();
                    }
                }else{
                    startCall();
                }
            }
        });

        // 이미지
        btnCallGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callGallery();
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,ResultActivity.class);
                String id = editText.getText().toString();
                String pw = editText2.getText().toString();
                MemberVO member = new MemberVO();
                member.setUserId(id);
                member.setUserPw(pw);
                i.putExtra("member",member);
                startActivityForResult(i,1004);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText.getText().toString();
                String pw = editText2.getText().toString();
                String message = "id : " + id +" // pw" + pw;
                showToast(message);

                Intent intent = new Intent(MainActivity.this,ReceiveActivity.class);
                intent.putExtra("userId",id);
                intent.putExtra("userPw",pw);
                //intent.putExtra("uno",1);
                //intent.putExtra("isChecked",true);
                String[] 점심 = {"잡채밥","짜장면","삼겹살","목살","라면","스테이크"};
                intent.putExtra("strs",점심);

                MemberVO member = new MemberVO();
                member.setUno(1);
                member.setUserId(id);
                member.setUserPw(pw);
                intent.putExtra("memberVO",member);

                ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
                for(int i=0; i<10; i++){
                    member = new MemberVO();
                    member.setUno(i+1);
                    member.setUserId(id+i);
                    member.setUserPw(pw+i);
                    memberList.add(member);
                }
                intent.putExtra("memberList",memberList);
                startActivity(intent);

                //finish();

            }
        });
    }

    private void callGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType(MediaStore.Images.Media.CONTENT_TYPE);
        i.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i,REQ_CODE_SELECT_IMAGE);
    }

    public  void startCall(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:01094867166"));
        startActivity(intent);
    }

    public void showToast(String message){
        Toast.makeText(
                MainActivity.this,
                message,
                Toast.LENGTH_SHORT
        ).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        System.out.println("권한 확인");
        switch(requestCode){
            case RUNTIME_PERMISSIONS_REQ_CALL_PHONE : {
                // 사용자가 권한을 수락 했다면
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                   startCall();
                }else{
                    showToast("권한을 취소 하셨습니다. 설정에서 권한을 추가 할 수 있어요.");
                }
                break;
            }
            // 다른 권한 처리


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("requestCode  " , requestCode+"");
        Log.i("resultCode  " , resultCode+"");

        if(requestCode == 1004 && resultCode == RESULT_OK){
            boolean isLogin = data.getBooleanExtra("isLogin",false);
            showToast("isLogin : " + isLogin);
        }else if(requestCode == REQ_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            try {
                Bitmap image_bit_map
                        = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imageView.setImageBitmap(image_bit_map);

            } catch(FileNotFoundException e){
                System.out.println("파일을 찾을 수 없음");
            }catch(IOException e){
                System.out.println("파일 입출력 오류");
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
