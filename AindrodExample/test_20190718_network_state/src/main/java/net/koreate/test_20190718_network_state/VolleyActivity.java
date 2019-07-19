package net.koreate.test_20190718_network_state;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VolleyActivity extends AppCompatActivity {

    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_date)
    TextView text_date;
    @BindView(R.id.text_content)
    TextView text_content;
    @BindView(R.id.image)
    ImageView image;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);

        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://192.168.0.253:8080/controller/resources/test.json",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            text_title.setText(response.getString("title"));
                            text_date.setText(response.getString("date"));
                            text_content.setText(response.getString("content"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(
                        VolleyActivity.this,
                        "error: " +error,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}
