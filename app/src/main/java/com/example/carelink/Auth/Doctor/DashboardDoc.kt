package com.example.carelink.Auth.Doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.carelink.Auth.Doctor.Fragment.Patient_list_doc
import com.example.carelink.Auth.Doctor.Fragment.Dash_doc
import com.example.carelink.Auth.Doctor.Fragment.EditProfileDetail
import com.example.carelink.Auth.Doctor.Fragment.Profile_doc
import com.example.carelink.R
import com.example.carelink.databinding.ActivityDashboard3Binding
import com.example.carelink.databinding.FragmentProfileDocBinding

class DashboardDoc : AppCompatActivity() {
    lateinit var binding: ActivityDashboard3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDashboard3Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        replaceFragment(Dash_doc())



        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.dashboard_doc -> replaceFragment(Dash_doc())
                R.id.plist-> replaceFragment(Patient_list_doc())
                R.id.doc_profile-> replaceFragment(Profile_doc())
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