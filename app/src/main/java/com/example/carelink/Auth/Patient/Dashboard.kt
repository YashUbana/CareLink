package com.example.carelink.Auth.Patient

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.carelink.Auth.Patient.Fragments.BookAppointment
import com.example.carelink.Auth.Patient.Fragments.Dash
import com.example.carelink.Auth.Patient.Fragments.History
import com.example.carelink.R
import com.example.carelink.databinding.ActivityDashboardBinding
import java.util.zip.Inflater

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(Dash())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard -> replaceFragment(Dash())
                R.id.book -> replaceFragment(BookAppointment())
                R.id.history -> replaceFragment(History())
                else->{

                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}