package com.example.myapplication

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class CameraActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var cameraView: WebView
    private lateinit var image: ImageView

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val angle1Val = findViewById<SeekBar>(R.id.angle1)
        val angle2Val = findViewById<SeekBar>(R.id.angle2)
        val angle1Text = findViewById<TextView>(R.id.angle1Text)
        val angle2Text = findViewById<TextView>(R.id.angle2Text)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users").child("2svtCGJ1fbUKdSBVhOpI5Xyo5gn1").child("Device").child("Marc's Camera")

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(ds: DatabaseError) {
                println("error")
            }
            override fun onDataChange(ds: DataSnapshot) {
                val user = ds.getValue(User::class.java)
                angle1Text.text = user!!.angle1
                angle2Text.text = user.angle2
                angle1Val.progress = user.angle1.toInt()
                angle2Val.progress = user.angle2.toInt()
            }
        })

        cameraView  = findViewById(R.id.cameraView)
        cameraView.loadUrl("https://www.google.com/")

        val zoomIn = findViewById<ImageView>(R.id.zoomIn)
        zoomIn.setOnClickListener{ cameraView.zoomIn() }

        val zoomOut = findViewById<ImageView>(R.id.zoomOut)
        zoomOut.setOnClickListener { cameraView.zoomOut() }

        val capture = findViewById<ImageView>(R.id.capture)
        capture.setOnClickListener {
            val a = takeScreenshot(cameraView)
        }

        angle1Val.max = 180
        angle2Val.max = 180

        angle1Val.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                angle1Text.text = progress.toString()
                database.child("angle1").setValue(progress.toString())
            }
        })

        angle2Val.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                angle2Text.text = progress.toString()
                database.child("angle2").setValue(progress.toString())
            }
        })
    }

    private fun takeScreenshot(view: WebView): Bitmap{
        val bitmap = Bitmap.createBitmap(view.width, 1500, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        //canvas.drawBitmap(bitmap,0,0,null)
        image = findViewById(R.id.screenshot)
        image.setImageBitmap(bitmap)
        return bitmap
    }
}
