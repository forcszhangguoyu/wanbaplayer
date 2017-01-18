package com.wanbatv.wanbaplayer.util;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wanbatv.wanbaplayer.R;
import com.wanbatv.wanbaplayer.activity.MainActivity;

import java.io.IOException;

/**
 * Created by 俞亚楠 on 2017/1/18.
 */
public class WanbaPlayer extends FrameLayout implements MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener,SurfaceHolder.Callback {
    private Context context;
    private AttributeSet attrs;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceholder;
    private MediaPlayer mediaPlay;
    private String url;
    private  WanbaPlayerCallback wanbaPlayerCallback;
    public WanbaPlayer(Context context) {
        super(context);
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.wanba_player, this, true);
        surfaceView=(SurfaceView)findViewById(R.id.wanba_surfaceview);
        surfaceholder = surfaceView.getHolder();
        surfaceholder.setFixedSize(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

    public WanbaPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.attrs=attrs;
        LayoutInflater.from(context).inflate(R.layout.wanba_player, this, true);
        surfaceView=(SurfaceView)findViewById(R.id.wanba_surfaceview);
        surfaceholder = surfaceView.getHolder();
        surfaceholder.setFixedSize(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        surfaceholder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }

   //创建surface实例
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mediaPlay=new MediaPlayer();
        mediaPlay.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlay.setDisplay(surfaceholder);
        mediaPlay.setOnCompletionListener(this);
        mediaPlay.setOnPreparedListener(this);
        mediaPlay.reset();
        try {
            mediaPlay.setDataSource(url);
            mediaPlay.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    //播放结束的回调
    @Override
    public void onCompletion(MediaPlayer mp) {
        wanbaPlayerCallback.setEndPlayer();
    }

    //开始播放的回调
    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        int totalTime=mp.getDuration()/1000;
        wanbaPlayerCallback.setStartPlayer(totalTime);
    }
    //传url地址开始播放
    public void startPlay(String url){
        this.url=url;
        if(mediaPlay==null) {
            surfaceholder.addCallback(this);
        }else {
            mediaPlay.setOnCompletionListener(this);
            mediaPlay.setOnPreparedListener(this);
            mediaPlay.reset();
            try {
                mediaPlay.setDataSource(url);
                mediaPlay.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //暂停后的播放
    public void start(){
        mediaPlay.start();
    }
    //暂停
    public void pause(){
        mediaPlay.pause();
    }
    //获取当前播放时间时长（秒）
    public int getTime(){
        int time=mediaPlay.getCurrentPosition()/1000;
        return  time;
    }
    //回调初始化
    public void setWanbaCallback(WanbaPlayerCallback wanbaCallback){
        this.wanbaPlayerCallback=wanbaCallback;
    }
    //快进
    public void setSpeed(int speedTime){
        if(mediaPlay!=null && mediaPlay.getCurrentPosition()+speedTime*1000<mediaPlay.getDuration()){
            mediaPlay.seekTo(mediaPlay.getCurrentPosition()+speedTime*1000);
        }else {
            mediaPlay.seekTo(mediaPlay.getDuration()-100);
        }
    }
    //快退
    public void setRetreat(int retreatTime){
        if(mediaPlay!=null && mediaPlay.getCurrentPosition()-retreatTime*1000>0){
            mediaPlay.seekTo(mediaPlay.getCurrentPosition()-retreatTime*1000);
        }else {
            mediaPlay.seekTo(0);
        }
    }
    //mediaplayer释放
    public void relase(){
        if(mediaPlay!=null) {
            mediaPlay.release();
        }
    }
}
