package com.example.posin.myapplication.examine;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

/**
 * Created by Beomsu on 2016. 2. 29..
 */
public class qSoundManager {
    private SoundPool q_SoundPool;
    private HashMap q_SoundPoolMap; //불러온 사운드 아이디 값을 저장
    private AudioManager q_AudioManager;
    private Context q_Activity; //어플리케이션 컨테스트
    private static qSoundManager m_instance;

    public void Init(Context context)
    {
        q_SoundPool =new SoundPool(4,AudioManager.STREAM_MUSIC,0);
        q_SoundPoolMap = new HashMap();
        q_AudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        q_Activity = context;
    }
    public void addSound(int index ,int soundID)
    {
        int id = q_SoundPool.load(q_Activity,soundID,1);//사운드로드
        q_SoundPoolMap.put(index,id);//해시맵에 아이디 값을 받아온 인덱스로 저장
    }
    public void play(int index)
    {
        //사운드 재생
        float streamVolume = q_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume/q_AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        q_SoundPool.play((Integer)q_SoundPoolMap.get(index),streamVolume,streamVolume,1,0,1f);
    }
    public static qSoundManager getInstance()
    {
        if(m_instance==null)
        {
            m_instance =new qSoundManager();
        }
            return m_instance;
    }
}
