package com.example.myapplication.V;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Set;

public class EightRootActivity extends AppCompatActivity {
    Button line,btn,bluetooth,montage,play;
    ImageView notch;
    TextView textplay;
    ListView listView;
    BluetoothAdapter bluetoothAdapter;
    Set<BluetoothDevice> pared;
    Dialog dialog;

    int text_play=0;
    int notchcount;
    GraphView graphView1,graphView2,graphView3,graphView4,graphView5,graphView6,graphView7,graphView8;
    int playcount;
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eightroot);
        Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        notchcount=0;
        playcount=0;
        dialog=new Dialog(EightRootActivity.this);
        dialog.setContentView(R.layout.bluetooth_alert);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        FindViewById();


        IntentFilter scanintentFilter=new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        BroadcastReceiver scanmodereceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action=intent.getAction();
                if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED))
                {
                    int modevalue = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE,BluetoothAdapter.ERROR);
                    if (modevalue==BluetoothAdapter.SCAN_MODE_CONNECTABLE){
                        bluetooth.setBackgroundResource(R.drawable.bluetooth_on_foreground);

                    }else if (modevalue==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
                    {

                    }else if (modevalue==BluetoothAdapter.SCAN_MODE_NONE)
                    {
                        bluetooth.setBackgroundResource(R.drawable.bluetooth_off_foreground);


                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                    }
                }

            }
        };
        registerReceiver(scanmodereceiver,scanintentFilter);


        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter==null){
            Toast.makeText(getApplicationContext(),"null",Toast.LENGTH_LONG).show();
            finish();
        }
        if (bluetoothAdapter.isEnabled()){
        }
        textplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrator.vibrate(40);
                if (text_play==0){
                    textplay.setText("×2");
                    text_play=1;

                }
                else if (text_play==1){
                    textplay.setText("×0.5");
                    text_play=2;

                }
                else if (text_play==2){
                    textplay.setText("×1");
                    text_play=0;

                }


            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(playcount==0) {
                    play.setBackgroundResource(R.drawable.pause_root_foreground);
                    playcount=1;
                }
                else if(playcount==1) {
                    play.setBackgroundResource(R.drawable.play_foreground);
                    playcount=0;

                }
            }
        });
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetooth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        pared=bluetoothAdapter.getBondedDevices();
                        ArrayList list=new ArrayList();
                        for (BluetoothDevice bt:pared){
                            list.add(bt.getName());

                        }
                        ArrayAdapter adapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
                        listView.setAdapter(adapter);
                        dialog.show();
                    }
                });

            }
        });
        montage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(EightRootActivity.this,montage);
                popup.getMenuInflater().inflate(R.menu.montage, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        montage.setText(menuItem.getTitle());

                        return true;
                    }
                });
                popup.show();


            }
        });
        notch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(notchcount==0) {
                    notch.setBackgroundResource(R.mipmap.notch_);
                    notchcount=1;
                }
               else if(notchcount==1) {
                    notch.setBackgroundResource(R.mipmap.notch_off_);
                    notchcount=0;

                }
            }
        });
        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SingleRootActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();

            }
        });
        DrawerLayout drawerLayout=findViewById(R.id.draver_eightroot);
        btn.setOnClickListener(view -> drawerLayout.openDrawer(GravityCompat.START));
    /////////////////////////////////////////////////////////////////////////////////////////////
        graphView1 = findViewById(R.id.eightgraphview1);

        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView1.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView1.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView1.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView1.addSeries(series1);

        series1.setColor(Color.rgb(30,30,30));

        series1.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView2 = findViewById(R.id.eightgraphview2);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView2.addSeries(series2);

        series2.setColor(Color.rgb(30,30,30));

        graphView2.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView2.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView2.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        series2.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView3 = findViewById(R.id.eightgraphview3);

        LineGraphSeries<DataPoint> series3 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView3.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView3.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView3.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView3.addSeries(series3);

        series3.setColor(Color.rgb(30,30,30));

        series3.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView4 = findViewById(R.id.eightgraphview4);

        LineGraphSeries<DataPoint> series4 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView4.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView4.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView4.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView4.addSeries(series4);

        series4.setColor(Color.rgb(30,30,30));

        series4.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView5 = findViewById(R.id.eightgraphview5);

        LineGraphSeries<DataPoint> series5 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView5.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView5.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView5.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView5.addSeries(series5);

        series5.setColor(Color.rgb(30,30,30));

        series5.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView6 = findViewById(R.id.eightgraphview6);

        LineGraphSeries<DataPoint> series6 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView6.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView6.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView6.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView6.addSeries(series6);

        series6.setColor(Color.rgb(30,30,30));

        series6.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView7 = findViewById(R.id.eightgraphview7);

        LineGraphSeries<DataPoint> series7 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });
        //// graphView.setTitleTextSize(9);

        graphView7.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView7.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graphView7.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.NONE);
        graphView7.addSeries(series7);

        series7.setColor(Color.rgb(30,30,30));

        series7.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////
        graphView8 = findViewById(R.id.eightgraphview8);

        LineGraphSeries<DataPoint> series8 = new LineGraphSeries<DataPoint>(new DataPoint[]{

                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 9),
                new DataPoint(4, 6),
                new DataPoint(5, 3),
                new DataPoint(6, 6),
                new DataPoint(7, 1),
                new DataPoint(8, 2)
        });


        graphView8.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView8.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL);
        graphView8.addSeries(series8);

        series8.setColor(Color.rgb(30,30,30));
        series8.setDrawBackground(false);

////////////////////////////////////////////////////////////////////////////////////////


    }

    private void FindViewById() {
        line=findViewById(R.id.line_eightroot);
        textplay=findViewById(R.id.eighttextplay);
        play=findViewById(R.id.plsy_eightroot);
        btn=findViewById(R.id.note_eightroot);
        notch=findViewById(R.id.notch_eightroot);
        montage=findViewById(R.id.montage_eightroot);
        bluetooth=findViewById(R.id.bluetoooth_eightroot);
        listView=dialog.findViewById(R.id.list);
    }
}
