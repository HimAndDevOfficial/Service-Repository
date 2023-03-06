package com.example.servicesample

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings

class MyService : Service() {
    private lateinit var player : MediaPlayer

     override fun onStartCommand(init : Intent , flag : Int , startId: Int):Int{
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        player.setLooping(true)
        player.start()
        return  START_STICKY
         //START_NOT_STICKY basically means you don’t want your Service restarted by the system
         //START_STICKY means the system will eventually restart your Service after it has been killed by the system
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }


    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}