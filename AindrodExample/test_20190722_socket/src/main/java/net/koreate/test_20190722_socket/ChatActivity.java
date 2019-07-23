package net.koreate.test_20190722_socket;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190722_socket.util.URLConfig;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.msgEdit)
    EditText msgEdit;
    @BindView(R.id.listView)
    ListView listView;

    Socket socket;

    // 서버와 연결을 위한 스레드 상태 정보
    boolean flagConnection = true;
    // 소켓 연결 상태 정보
    boolean isConnected = false;
    //일기 스레드 상태 정보
    boolean flagRead = true;

    SocketThread socketThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("ChatActivity onStart");
        socketThread = new SocketThread();
        socketThread.start();
    }

    class SocketThread extends Thread{
        @Override
        public void run() {
            while(flagConnection){
                if(!isConnected){
                    try {
                        socket = new Socket();
                        SocketAddress remoteAddress = new InetSocketAddress(
                                URLConfig.SERVER_IP,
                                URLConfig.SERVER_PORT
                        );
                        socket.connect(remoteAddress, 10000);
                        isConnected = true;
                    }catch(Exception e){
                        e.printStackTrace();
                        SystemClock.sleep(10000);
                    }
                }
            }
        }
    }


}
