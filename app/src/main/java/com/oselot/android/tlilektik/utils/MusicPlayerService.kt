package com.oselot.android.tlilektik.utils

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import androidx.annotation.Nullable
import com.oselot.android.tlilektik.R

class MusicPlayerService: Service()  {

    private lateinit var player: MediaPlayer

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, R.raw.music)
        player.isLooping = true
        player.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }

}