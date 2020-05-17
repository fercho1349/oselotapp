package com.oselot.android.tlilektik.activities.musicPlayer

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.oselot.android.tlilektik.R
import com.oselot.android.tlilektik.utils.MusicPlayerService
import kotlinx.android.synthetic.main.activity_music_player.*

class MusicPlayerActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        playBtn.setOnClickListener(this)
        stopBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if(v!!.id == R.id.playBtn){
            startService(Intent(this, MusicPlayerService::class.java))
        }else{
            stopService(Intent(this, MusicPlayerService::class.java))
        }
    }

}