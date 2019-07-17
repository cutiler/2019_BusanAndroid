package net.koreate.test_20190716_adapterview;

import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ArrayAdapterActivity extends AppCompatActivity {

    @BindView(R.id.array_adapter_listView)
    ListView listView;

    @BindArray(R.array.dog_list)
    String[] dog_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_array_adapter);
        ButterKnife.bind(this);

        ArrayAdapter adapter = new ArrayAdapter(
                this,
                // 라디오버튼
                //android.R.layout.simple_list_item_single_choice,
                //체크박스
                android.R.layout.simple_list_item_multiple_choice,
                dog_list
        );

        listView.setAdapter(adapter);
        // 단일 선택
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        //다중선택
        //listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener((parent, view, pos, id)->{
            Toast.makeText(
                    ArrayAdapterActivity.this,
                    "dog : "+dog_list[pos],
                    Toast.LENGTH_SHORT
            ).show();

            SparseBooleanArray sp = listView.getCheckedItemPositions();
            for(int i=0; i<sp.size(); i++){
                System.out.println("key : "+sp.keyAt(i) +" // value : " + sp.valueAt(i));
                if(sp.valueAt(i) == true){
                    String s = ((CheckedTextView)listView.getChildAt(pos)).getText().toString();
                    Log.i("Checked dog -"+i,s);
                }
            }

        });
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        */
    }
}
