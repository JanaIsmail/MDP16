package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.EditText
import android.widget.Toast
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

//        val manager = PreferencesManager(this)
//
//        val intent = Intent(this,WelcomeActivity::class.java)
//        intent.putExtra("manager",manager)
//        startActivity(intent)

//        if (manager.isFirstRun()) {
//            startActivity(Intent(this,WelcomeActivity::class.java))
//        }

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener { login() }

        val pairDeviceButton = findViewById<Button>(R.id.pairButton)
        pairDeviceButton.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }

    private fun login(){
        val emailEdit = findViewById<EditText>(R.id.emailEdit)
        val passwordEdit = findViewById<EditText>(R.id.passwordEdit)

        if(emailEdit.text.toString().isEmpty()){
            emailEdit.error = "Enter email"
            emailEdit.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailEdit.text.toString()).matches()){
            emailEdit.error = "Enter valid email"
            emailEdit.requestFocus()
            return
        }

        if(passwordEdit.text.toString().isEmpty()){
            passwordEdit.error = "Enter password"
            return
        }

        auth.signInWithEmailAndPassword(emailEdit.text.toString(), passwordEdit.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){ startActivity(Intent(this,Devices::class.java)) }
    }

    private fun updateUI(currentUser: FirebaseUser?){
        if(currentUser != null){ startActivity(Intent(this,Devices::class.java)) }
        else{ Toast.makeText(baseContext, "Wrong password", Toast.LENGTH_SHORT).show() }
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
        exitProcess(-1)
    }
}
