package club.hanfeng.freewalk.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;

import java.io.File;

import club.hanfeng.freewalk.service.aidl.IVoicePlayerService;

public class VoicePlayerService extends Service {

    private MediaPlayer mediaPlayer;
    private String filePath;

    @Override
    public IBinder onBind(Intent intent) {
        return service.asBinder();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public IVoicePlayerService.Stub service = new IVoicePlayerService.Stub() {
        VoicePlayerService voicePlayerService = VoicePlayerService.this;

        @Override
        public void play() throws RemoteException {
            voicePlayerService.play();
        }

        @Override
        public void pause() throws RemoteException {
            voicePlayerService.pause();
        }

        @Override
        public void stop() throws RemoteException {
            voicePlayerService.stop();
        }

        @Override
        public void notifyChanged() throws RemoteException {
            voicePlayerService.notifyChanged();
        }

        @Override
        public int getDuration() throws RemoteException {
            return voicePlayerService.getDuration();
        }

        @Override
        public int getCurrentPosition() throws RemoteException {
            return voicePlayerService.getCurrentPosition();
        }

        @Override
        public void setMode() throws RemoteException {
            voicePlayerService.setMode();
        }

        @Override
        public void seekTo(int position) throws RemoteException {
            voicePlayerService.seekTo(position);
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            return voicePlayerService.isPlaying();
        }

        @Override
        public void createMediaPlayer(String path) throws RemoteException {
            voicePlayerService.createMediaPlayer(path);
        }

        @Override
        public String getFilePath() throws RemoteException {
            return voicePlayerService.getFilePath();
        }


    };

    private String getFilePath() {
        return filePath;
    }

    private int getDuration() {
        return mediaPlayer.getDuration();
    }

    private void createMediaPlayer(String path) {
        this.filePath = path;
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(path)));
        } else {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(new File(path)));
        }
    }

    private boolean isPlaying() {
        return mediaPlayer == null ? false : mediaPlayer.isPlaying();
    }

    private void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    private void setMode() {
    }

    private int getCurrentPosition() {
        return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
    }

    private void notifyChanged() {

    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    private void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
