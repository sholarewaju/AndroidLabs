package com.example.assignment3;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private ChatDatasource datasource;
    boolean isPhone;
    DetailsFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       datasource=new ChatDatasource(this);
        datasource.open();

        setContentView(R.layout.activity_chat_room);
        sendButton = (Button)findViewById(R.id.send);
        receiveButton = (Button)findViewById(R.id.receive);
        chatText = (TextView)findViewById(R.id.editText);

        FrameLayout fl= findViewById(R.id.framelayout);

        if(fl==null){
            isPhone=true;
        }

        CustomListView = this;
        Resources res =getResources();
        simpleList = ( ListView )findViewById( R.id.listview);
        CustomListViewValuesArr= datasource.getAllChat();

        customAdapter=new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        simpleList.setAdapter( customAdapter );

        sendButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //what happens on clicking send button
                if (chatText.getText()!="") {
                    msg = new Message(true, chatText.getText().toString());
                    msg=datasource.addChatMessage(msg);
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
                    msg=datasource.addChatMessage(msg);
                    CustomListViewValuesArr.add(msg);
                    //call update method
                    customAdapter.notifyDataSetChanged();
                }
            }
        });


        simpleList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChatRoomActivity.this);
                final Message m = ( Message ) CustomListViewValuesArr.get(i);
                alertDialog.setTitle("Do you want to delete this?");
                alertDialog.setMessage("The selected row is " + i + "\n The database id is " + m.getId());
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        datasource.deleteChatMessage(m);
                        CustomListViewValuesArr.remove(m);
                        //call update method
                        customAdapter.notifyDataSetChanged();
                        if(!isPhone){
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            //ft.replace(R.id.framelayout, new DetailsFragment());
                            ft.remove(fragment);
                            ft.commit();
                        }
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDialog.setCancelable(false);
                alertDialog.show();
                return false;
            }
        });
        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Message m = ( Message ) CustomListViewValuesArr.get(position);
                Bundle args = new Bundle();
                args.putString(DetailsFragment.MESSAGE, m.getMsg());
                args.putLong(DetailsFragment.MESSAGE_ID, m.getId());
                args.putBoolean(DetailsFragment.MESSAGE_TYPE, m.getType());
                if(isPhone){
                    Intent intent=new Intent(ChatRoomActivity.this, EmptyActivity.class);
                    intent.putExtra("message",args);
                    startActivity(intent);
                }else {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    fragment = new DetailsFragment();
                    fragment.setArguments(args);
                    ft.replace(R.id.framelayout, fragment);
                    ft.commit();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        datasource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        datasource.open();
    }
}
