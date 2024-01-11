package com.sro.guessit.Service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.sro.guessit.R

class MusicService : Service() {
    private lateinit var sound: MediaPlayer
    private val binder = LocalBinder()

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    override fun onBind(intent: Intent?): IBinder? {
        sound = MediaPlayer.create(this, R.raw.gamebackmusic)
        sound.isLooping = true
        playMusic()
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    fun playMusic() {
        if (!sound.isPlaying) {
            sound.start()
            Log.d("MusicService", "Music started")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (sound.isPlaying) {
            sound.stop()
            Log.d("MusicService", "Music stopped")
        }
        sound.release()
    }


    fun pauseMusic() {
        if (sound.isPlaying) {
            sound.pause()
        }
    }


}
