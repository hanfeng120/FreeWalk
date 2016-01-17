package club.hanfeng.freewalk.service.aidl;

interface IVoicePlayerService {

    void play();

    void pause();

    void stop();

    void notifyChanged();

    int getDuration();

    int getCurrentPosition();

    void setMode();

    void seekTo(int position);

    boolean isPlaying();

    void createMediaPlayer(String path);

    String getFilePath();

    boolean getMediaPlayerState();

}
