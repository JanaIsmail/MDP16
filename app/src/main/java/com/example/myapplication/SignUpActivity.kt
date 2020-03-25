package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference.child("Users")

        val createAccountButton = findViewById<Button>(R.id.createAccountButton)
        createAccountButton.setOnClickListener { signUp() }
    }

    private fun signUp(){
        val emailSignUpEdit = findViewById<EditText>(R.id.emailSignUpEdit)
        val passwordSignUpEdit = findViewById<EditText>(R.id.passwordSignUpEdit)

        if(emailSignUpEdit.text.toString().isEmpty()){
            emailSignUpEdit.error = "Enter email"; emailSignUpEdit.requestFocus()
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailSignUpEdit.text.toString()).matches()){
            emailSignUpEdit.error = "Enter valid email"; emailSignUpEdit.requestFocus()
            return
        }

        if(passwordSignUpEdit.text.toString().isEmpty()){
            passwordSignUpEdit.error = "Enter password"
            return
        }

        auth.createUserWithEmailAndPassword(emailSignUpEdit.text.toString(), passwordSignUpEdit.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = UserDevice("0","0")
                    val userUID = auth.uid.toString()
                    database.child(userUID).setValue(user)
                    startActivity(Intent(this,MainActivity::class.java))
                } else {
                    Toast.makeText(baseContext, task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
    }
}