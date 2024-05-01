package com.example.carelink.Auth.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.carelink.Auth.Admin.Fragment.Dash_admin
import com.example.carelink.Auth.Admin.Fragment.Doctor_list
import com.example.carelink.Auth.Admin.Fragment.Patient_list
import com.example.carelink.Auth.Patient.Fragments.BookAppointment
import com.example.carelink.Auth.Patient.Fragments.Dash
import com.example.carelink.Auth.Patient.Fragments.History
import com.example.carelink.R
import com.example.carelink.databinding.ActivityDashboard2Binding

class Dashboard : AppCompatActivity() {
    lateinit var binding: ActivityDashboard2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDashboard2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(Dash_admin())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard_admin -> replaceFragment(Dash_admin())
                R.id.doctor_admin-> replaceFragment(Doctor_list())
                R.id.patient_admin-> replaceFragment(Patient_list())
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