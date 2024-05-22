package com.example.assignment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.widget.Button;
import java.io.File;
import android.widget.TextView
import java.lang.Exception
import java.util.Calendar


class ThirdActivity : AppCompatActivity() {

    private val recorder = MediaRecorder()
    private val player = MediaPlayer()
    private lateinit var folder:File
    private var fileName = ""

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && requestCode == 0) {
            val result = grantResults[0]
            if (result == PackageManager.PERMISSION_DENIED
            )
                finish()
            else {
                setFolder()
                setListener()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        val permission = android.Manifest.permission.RECORD_AUDIO

        val next=findViewById<Button>(R.id.btn_next)
        next.setOnClickListener {
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), 0)
        }
        else {
            setFolder()
            setListener()
        }
    }

    private fun setFolder(){
        folder = File(filesDir.absoluteFile,"/record")
        if(!folder.exists()){
            folder.mkdirs()
        }
    }

    private fun setListener(){
        val btn_record = findViewById<Button>(R.id.btn_recording)
        val btn_stoprecord = findViewById<Button>(R.id.btn_stoprecording)
        val btn_playback = findViewById<Button>(R.id.btn_playback)
        val btn_stopplayback = findViewById<Button>(R.id.btn_stopplayback)
        val tv=findViewById<TextView>(R.id.tv_record)

        btn_record.setOnClickListener{
            fileName = "${Calendar.getInstance().time.time}"
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(File(folder, fileName).absolutePath)
            recorder.prepare()
            recorder.start()
            tv.text = "Recording..."
            btn_record.isEnabled = false
            btn_record.backgroundTintMode
            btn_stoprecord.isEnabled = true
            btn_playback.isEnabled = false
            btn_stopplayback.isEnabled = false
        }

        btn_stoprecord.setOnClickListener{
            try{
                val file = File(folder ,fileName)
                recorder.stop()
                tv.text = "Recorded to ${file.absolutePath}"
                btn_record.isEnabled = true
                btn_stoprecord.isEnabled = false
                btn_playback.isEnabled = true
                btn_stopplayback.isEnabled = false
            }catch (e:Exception){
                e.printStackTrace()
                recorder.reset()
                tv.text = "Recording failed"
                btn_record.isEnabled = true
                btn_stoprecord.isEnabled = false
                btn_playback.isEnabled = false
                btn_stopplayback.isEnabled = false
            }

        btn_playback.setOnClickListener{
            val file = File(folder ,fileName)
            player.setDataSource(applicationContext, Uri.fromFile(file))
            player.setVolume(1f,1f)
            player.prepare() //Step3: MediaPlayer is ready
            player.start()
            tv.text = "Playback..."
            btn_record.isEnabled = false
            btn_stoprecord.isEnabled = false
            btn_playback.isEnabled = false
            btn_stopplayback.isEnabled = true
        }

        btn_stopplayback.setOnClickListener{
            player.stop()
            player.reset()
            tv.text = "Playback stop"
            btn_record.isEnabled = true
            btn_stoprecord.isEnabled = false
            btn_playback.isEnabled = true
            btn_stopplayback.isEnabled = false
        }

        player.setOnCompletionListener {
            it.reset()
            tv.text = "Playback end"
            btn_record.isEnabled = true
            btn_stoprecord.isEnabled = false
            btn_playback.isEnabled = true
            btn_stopplayback.isEnabled = false
        }
        }
    }
}