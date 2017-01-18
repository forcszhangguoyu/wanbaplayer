package com.wanbatv.wanbaplayer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.wanbatv.wanbaplayer.R;
import com.wanbatv.wanbaplayer.util.WanbaPlayer;
import com.wanbatv.wanbaplayer.util.WanbaPlayerCallback;

public class MainActivity extends Activity implements WanbaPlayerCallback{
    private WanbaPlayer wanbaPlayer;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wanbaPlayer=(WanbaPlayer)findViewById(R.id.wanba_player);
        wanbaPlayer.setWanbaCallback(this);
        button=(Button)findViewById(R.id.button);
        button.requestFocus();
        wanbaPlayer.startPlay("http://static.wanbatv.com/dogtv/welcome.MP4");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wanbaPlayer.setSpeed(10);
            }
        });
    }

    @Override
    public void setStartPlayer(int totalTime) {
        Toast.makeText(this,"开始播放"+totalTime,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setEndPlayer() {
        wanbaPlayer.startPlay("http://121.201.14.248:8062/api/bengbengtiao/program/c771f235f778778bfbf8867d4fd7f7f5?auth_token=3a0773e6c16893668b046d81137f93a5&auth_time=587dd309");
    }
}
