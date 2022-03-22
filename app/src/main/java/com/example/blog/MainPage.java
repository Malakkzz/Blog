package com.example.blog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity {

    ArrayList<Blog> arrayList;
    ArrayAdapter<Blog> arrAdapter;
    ListView list;
    EditText blog;
    Button post;
    String username;
    SwipeRefreshLayout refresh;
    Blog b;
    private NotificationManager mNotifManager;
    private int NOTIFICATION_ID=1;
    private final String CHANNEL_ID = "My_first_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        createNotificationChannel(this.CHANNEL_ID);

        Toolbar t = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(t);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        setTitle(username);

        post = findViewById(R.id.post);
        blog = findViewById(R.id.blog);
        refresh = findViewById(R.id.refresh);

        arrayList = new ArrayList<>();
        arrAdapter = new ArrayAdapter<Blog>(this,
                android.R.layout.simple_list_item_1,
                arrayList);
        list = findViewById(R.id.list);
        list.setAdapter(arrAdapter);

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                b = (Blog)list.getItemAtPosition(position);
                arrayList.remove(b);
                arrAdapter.notifyDataSetChanged();
//                return false;
//                arrayList.remove(list.getItemAtPosition(position));
//                arrAdapter.notifyDataSetChanged();
                Snackbar snackbar = Snackbar.make(view, "Message is deleted",Snackbar.LENGTH_LONG) ;
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar.make(view, "Message is restored!",
                                Snackbar.LENGTH_SHORT).show();
                        arrayList.add(b);
                        arrAdapter.notifyDataSetChanged();
                    }
                });
                snackbar.show();

                return false;
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                b = (Blog) list.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
                alert.setMessage("This is an alert …");
                alert.setTitle("App Title");
                alert.setNeutralButton("May be", null);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.setPositiveButton("Yes", new
                        DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                alert.setCancelable(false);
                alert.create().show();
            }
        });

        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String blogText = blog.getText().toString();
                Blog b = new Blog(username, blogText);
                arrayList.add(b);
                arrAdapter.notifyDataSetChanged();
                blog.setText("");
            }
        });


        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainPage.this,"REFRESHED COMPLETED!",Toast.LENGTH_LONG).show();
                refresh.setRefreshing(false);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item ){
        if(item.getItemId()== R.id.profile){
            Intent intent = new Intent(this,Profile.class);
            intent.putExtra("username",username);
            startActivityForResult(intent,123);
            return true;
        }
        else if(item.getItemId()==R.id.small){
            sendNotification();
            return true;
        }
        else if(item.getItemId() == R.id.big){
            updateNotification();
            return true;
        } else if(item.getItemId() == R.id.cancel){
            cancelNotification();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int reqc, int resc,Intent intent){
        super.onActivityResult(reqc, resc, intent);
        if(reqc == 123){
            if(resc == RESULT_OK){
                if(username != intent.getStringExtra("username")){
                    username=intent.getStringExtra("username");
                    setTitle(username);
                }
            }
        }
    }

    public void createNotificationChannel(String CHANNEL_ID){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    "General notification", NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("This is my first notification channel");
            mNotifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifManager.createNotificationChannel(notificationChannel);
        }
    }
    public NotificationCompat.Builder createNotificationBuilder(){
        NotificationCompat.Builder mbuilder =  new NotificationCompat.Builder(this,this.CHANNEL_ID);
        mbuilder.setSmallIcon(R.drawable.ic_launcher_background);
        mbuilder.setContentTitle("My first Notification");
        mbuilder.setContentText("This is my first notification from this app");
        Intent notificationIntent = new Intent(this,MainPage.class);//lama ekbus aalyha
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,NOTIFICATION_ID,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mbuilder.setContentIntent(notificationPendingIntent);
        return mbuilder;
    }
    public void sendNotification(){
        NotificationCompat.Builder mbuilder = createNotificationBuilder();
        Notification mynotif = mbuilder.build();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mNotifManager.notify(this.NOTIFICATION_ID,mynotif);
        }
    }
    public void updateNotification(){
        Bitmap androidImg= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_background);
        NotificationCompat.Builder notifBuilder = createNotificationBuilder();
        notifBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImg).setBigContentTitle("This is a big content title"));
        mNotifManager.notify(NOTIFICATION_ID,notifBuilder.build());
    }
    public void cancelNotification(){
        mNotifManager.cancel(NOTIFICATION_ID);

    }


}