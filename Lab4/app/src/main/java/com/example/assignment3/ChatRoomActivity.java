package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {
    //ListView chatView;
    Button sendButton;
    Button receiveButton;
    TextView chatText;
    Message msg;
    ListView simpleList;
    private static CustomAdapter customAdapter;
    public  ChatRoomActivity CustomListView = null;
    public ArrayList<Message> CustomListViewValuesArr = new ArrayList<Message>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        sendButton = (Button)findViewById(R.id.send);
        receiveButton = (Button)findViewById(R.id.receive);
        chatText = (TextView)findViewById(R.id.editText);

        CustomListView = this;
        Resources res =getResources();
        simpleList = ( ListView )findViewById( R.id.listview);

        customAdapter=new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        simpleList.setAdapter( customAdapter );

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //what happens on clicking send button
                if (chatText.getText()!="") {
                    msg = new Message(true, chatText.getText().toString());
                    CustomListViewValuesArr.add(msg);
                    //call update method
                    customAdapter.notifyDataSetChanged();
                }
            }
        });

        receiveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //what happens on clicking receive button
                if (chatText.getText()!="") {
                    msg = new Message(false, chatText.getText().toString());
                    CustomListViewValuesArr.add(msg);
                    customAdapter.notifyDataSetChanged();
                }
            }
        });


        simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatRoomActivity.this);
                //Message m = ( Message ) CustomListViewValuesArr.get(i);
                alertDialog.setTitle("Do you want to delete this?");
                alertDialog.setMessage("The selected row is " + i + "\n The database id is " + view.getId());
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                return false;
            }
        });
        //simpleList.

    }

}
