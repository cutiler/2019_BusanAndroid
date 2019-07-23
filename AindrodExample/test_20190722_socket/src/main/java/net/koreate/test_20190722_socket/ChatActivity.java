package net.koreate.test_20190722_socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import net.koreate.test_20190722_socket.util.ChatListViewAdapter;
import net.koreate.test_20190722_socket.util.URLConfig;
import net.koreate.test_20190722_socket.vo.ChatMessage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

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

    WriteThread writeThread;

    ReadThread readThread;

    Handler writeHandler;

    // 입출력
    BufferedInputStream bis;
    BufferedOutputStream bos;

    // listView data binding
    ArrayList<ChatMessage> list;

    ChatListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        list  = new ArrayList<>();
        adapter = new ChatListViewAdapter(this,R.layout.item_chat,list);
        listView.setAdapter(adapter);

        btnSend.setEnabled(false);
        msgEdit.setEnabled(false);

        btnSend.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("ChatActivity onStart");
        socketThread = new SocketThread();
        socketThread.start();
    }

    @Override
    public void onClick(View view) {
        if(!msgEdit.getText().toString().trim().equals("")){
            Message msg = new Message();
            msg.obj = msgEdit.getText().toString();
            writeHandler.sendMessage(msg);
        }
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

                        bis = new BufferedInputStream(socket.getInputStream());
                        bos = new BufferedOutputStream(socket.getOutputStream());

                        if(writeThread != null){
                            writeHandler.getLooper().quit();
                        }

                        // 데이터 전송 스레드 실행
                        writeThread  = new WriteThread();
                        writeThread.start();

                        readThread = new ReadThread();
                        readThread.start();

                        isConnected = true;

                        Message msg = new Message();
                        msg.what = 10;
                        mainHandler.sendMessage(msg);

                    }catch(Exception e){
                        e.printStackTrace();
                        SystemClock.sleep(10000);
                    }
                }
            }
        }
    }

    Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(msg.what == 10){
                System.out.println("서버 연결 성공");
                btnSend.setEnabled(true);
                msgEdit.setEnabled(true);
            }else if(msg.what == 20){
                System.out.println("서버 연결 실패");
                btnSend.setEnabled(false);
                msgEdit.setEnabled(false);
            }else if(msg.what == 100){
                // 상대방이 전달한 메시지
                addMessage("you",(String)msg.obj);
            }else if(msg.what == 200){
                // 내가 작성한 메시지
                System.out.println("내가 작성한 메시지");
                addMessage("me",(String)msg.obj);
            }


        }
    };

    public void addMessage(String who,String msg){
        ChatMessage vo = new ChatMessage();
        vo.setWho(who);
        vo.setMsg(msg);
        list.add(vo);
        adapter.notifyDataSetChanged();
        listView.setSelection(list.size()-1);
        msgEdit.setText("");
        System.out.println("message 전달 : " + msg);
    }

    class WriteThread extends Thread{
        @Override
        public void run() {
            Looper.prepare();
            writeHandler = new Handler(){
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    try {
                        // 서버에 chat 내용 전송
                        Object o = msg.obj;
                        bos.write(((String) o).getBytes());
                        bos.flush();

                        // 리스트 뷰 데이터 갱신
                        Message m = new Message();
                        m.what =200;
                        m.obj = msg.obj;
                        mainHandler.sendMessage(m);
                    }catch(Exception e){
                        e.printStackTrace();
                        isConnected = false;
                        writeHandler.getLooper().quit();
                        flagRead = false;
                    }
                }
            };
            Looper.loop();
        }
    }


    class ReadThread extends Thread{
        @Override
        public void run() {
            byte[] buffer = null;

            while(flagRead){
                try {
                    buffer = new byte[1024];
                    String message = null;
                    int size = bis.read(buffer);
                    if(size > 0){
                        message = new String(buffer,0,size,"UTF-8");
                        if(message != null && !message.equals("")){
                            Message msg = new Message();
                            msg.what = 100;
                            msg.obj = message;
                            mainHandler.sendMessage(msg);
                        }
                    }else{
                        flagRead = false;
                        isConnected = false;
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    System.out.println("소켓 연결 오류");
                    flagRead = false;
                    isConnected = false;
                }
            }
            Message m =  new Message();
            m.what =20;
            mainHandler.sendMessage(m);
        }
    }



}
