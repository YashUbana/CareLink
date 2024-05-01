package com.example.carelink

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.carelink.Auth.Admin.AdminLoginPage
import com.example.carelink.Auth.Doctor.LoginDoc
import com.example.carelink.Auth.Patient.LoginPage
import com.example.carelink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageView7.setOnClickListener{
            val intent = Intent(this, AdminLoginPage::class.java )
            startActivity(intent)
        }
        binding.imageView8.setOnClickListener{
            val intent = Intent(this, LoginDoc::class.java )
            startActivity(intent)
        }
        binding.imageView9.setOnClickListener{
            val intent = Intent(this, LoginPage::class.java )
            startActivity(intent)
        }


    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {

    }

}