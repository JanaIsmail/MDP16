package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.system.exitProcess
import android.view.Menu
import android.view.MenuItem


class Devices : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    interface MyCallback { fun onCallback(value:ArrayList<User>) }

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devices)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        val users = ArrayList<User>()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users").child("2svtCGJ1fbUKdSBVhOpI5Xyo5gn1").child("Device")

        fun getUsers(myCall: MyCallback){
            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(ds: DatabaseError) {
                    println("error")
                }
                override fun onDataChange(ds: DataSnapshot) {
                    for (snap in ds.children) {
                        val user = snap.getValue(User::class.java)
                        users.add(user!!)
                    }
                    myCall.onCallback(users)
                }
            })
        }

        val context = this as Context

        getUsers(object : MyCallback {
            override fun onCallback(value:ArrayList<User>) {
                val adapter = CustomAdapter(value,context)
                recyclerView.adapter = adapter
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.pairDeviceItem) {
            startActivity(Intent(this,PairDeviceActivity::class.java))
            finish()
            return true
        }
        if (id == R.id.signOutItem) {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
            moveTaskToBack(true)
            exitProcess(-1)
    }
}
