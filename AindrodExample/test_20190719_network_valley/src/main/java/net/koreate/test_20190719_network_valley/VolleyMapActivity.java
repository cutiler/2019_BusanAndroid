package net.koreate.test_20190719_network_valley;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.koreate.test_20190719_network_valley.util.CustomListViewAdapter;
import net.koreate.test_20190719_network_valley.util.MainController;
import net.koreate.test_20190719_network_valley.util.URLConfig;
import net.koreate.test_20190719_network_valley.vo.BoardVO;
import net.koreate.test_20190719_network_valley.vo.TestVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolleyMapActivity extends AppCompatActivity {
    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_map);
        ButterKnife.bind(this);

        volleyNetwork();
    }

    private void volleyNetwork() {
        final String request_tag = "TEST_MAP";

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URLConfig.TEST1_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("map ",response);
                        JsonObject json = new JsonParser().parse(response).getAsJsonObject();
                        JsonArray testArray = json.getAsJsonArray("testList");
                        JsonArray boardArray = json.getAsJsonArray("boardList");

                        Gson gson = new Gson();

                        ArrayList<TestVO> testList  = new ArrayList<>();
                        for(int i=0; i<testArray.size(); i++){
                            JsonObject obj = (JsonObject) testArray.get(i);
                            TestVO testVO = gson.fromJson(obj,TestVO.class);
                            System.out.println(testVO);
                            testList.add(testVO);
                        }

                        ArrayList<BoardVO> boardList = new ArrayList<>();
                        for(int i=0;i<boardArray.size();i++){
                            JsonObject obj = (JsonObject) boardArray.get(i);
                            BoardVO boardVO = gson.fromJson(obj,BoardVO.class);
                            System.out.println(boardVO);
                            boardList.add(boardVO);
                        }

                        listView.setAdapter(new CustomListViewAdapter(boardList, R.layout.item_layout));





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                VolleyMapActivity.this,
                                "error : "+error.toString(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("title","나의 첫 안드로이드");
                        return params;
                    }
                };
        MainController.getInstance().addToRequestQueue(request,request_tag);
    }
}
