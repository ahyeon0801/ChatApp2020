package com.ahyeon.chatapp2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    private static final String TAG ="##ChatActivity##";
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    EditText etText;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Button btnFinish = (Button)findViewById(R.id.btnFinish);
        btnSend = (Button)findViewById(R.id.btnSend);
        etText = (EditText)findViewById(R.id.etText);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();   //굳이 intent를 써서 main으로 넘어가게끔 안해도, 이걸 사용하면 맨처음화면으로 돌아가게 된다.
                //Intent intent = new Intent(ChatActivity.this, MainActivity.class);
                //startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        String[] myDataset = {"test1", "test2", "test3", "test4", "test5"};
        mAdapter = new MyAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

        Log.d(TAG, "onCreate: HERE!!!!!");
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strText = etText.getText().toString();
                Toast.makeText(ChatActivity.this, "msg: "+strText, Toast.LENGTH_LONG).show();
            }
        });
    }
}
