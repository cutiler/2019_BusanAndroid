package net.koreate.test_20190719_network_valley;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.koreate.test_20190719_network_valley.util.MainController;
import net.koreate.test_20190719_network_valley.util.URLConfig;
import net.koreate.test_20190719_network_valley.vo.TestVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolleyListActivity extends AppCompatActivity {

    @BindView(R.id.listView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_list);
        ButterKnife.bind(this);
        startNetworkVolley();
    }

    private void startNetworkVolley() {
        final String request_tag = "TEST_LIST";

        StringRequest strReq = new StringRequest(
                Request.Method.POST,
                URLConfig.TEST_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Volley List",response);
                        JsonArray testArray = new JsonParser().parse(response).getAsJsonArray();
                        ArrayList<TestVO> testList = new ArrayList<>();
                        Gson gson = new Gson();
                        if(testArray != null && !testArray.isJsonNull()){
                            for(int i=0; i<testArray.size(); i++){
                                JsonObject obj = (JsonObject)testArray.get(i);
                                TestVO testVO = gson.fromJson(obj,TestVO.class);
                                testList.add(testVO);
                            }
                        }

                        for(TestVO testVO : testList){
                            Log.i("test List",testVO.toString());
                        }

                        ArrayList<Map<String,String>> testSimpleList = new ArrayList<>();
                        for(TestVO testVO : testList){
                            Map<String, String> map = new HashMap<>();
                            map.put("id",testVO.getId());
                            map.put("pw",testVO.getPw());
                            testSimpleList.add(map);
                        }

                        SimpleAdapter adapter =
                                new SimpleAdapter(
                                        VolleyListActivity.this,
                                        testSimpleList,
                                        android.R.layout.simple_list_item_2,
                                        new String[]{"id","pw"},
                                        new int[]{android.R.id.text1,android.R.id.text2}
                                );

                        listView.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("test_list",error+"");
                    }
                }
        );

        MainController.getInstance().addToRequestQueue(strReq,request_tag);

        /*
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(strReq);
        */
        /*
        *
        * */
    }
}
