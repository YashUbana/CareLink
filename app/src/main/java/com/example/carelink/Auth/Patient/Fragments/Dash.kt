package com.example.carelink.Auth.Patient.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carelink.Auth.Patient.Database.DocProfile
import com.example.carelink.Auth.Patient.Database.DoclistAdapter
import com.example.carelink.Auth.Patient.Database.MyAdapter
import com.example.carelink.Auth.Patient.LoginPage
import com.example.carelink.R
import com.example.carelink.databinding.FragmentDashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Locale
import java.util.Objects

@Suppress("UNUSED_EXPRESSION")
class Dash : Fragment() {
    private var binding: FragmentDashBinding? = null
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: DatabaseReference
    private lateinit var docArrayList : ArrayList<DocProfile>
    private lateinit var mic: ImageView
    private val REQUEST_CODE_SPEECH_INPUT = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun getDocData() {
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("DocProfile")
        firebaseDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(docSnaps in snapshot.children){
                        val doc = docSnaps.getValue(DocProfile::class.java)
                        docArrayList.add(doc!!)
                    }
                    binding!!.docrecyc.adapter = DoclistAdapter(docArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        firebaseAuth = FirebaseAuth.getInstance()
        binding = FragmentDashBinding.inflate(inflater, container, false)
//        binding?.apply { registerForContextMenu(this.imageView) }
        var imgbtn = binding!!.imageView
        binding!!.textView42.paint.isUnderlineText = true

        imgbtn.setOnClickListener{
            val popupMenu =  PopupMenu(requireContext(), imgbtn )
            popupMenu.menuInflater.inflate(R.menu.dash_image, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { x->
                when(x.itemId){
                    R.id.logout ->{
                        firebaseAuth.signOut()
                        val intent = Intent(requireActivity(), LoginPage::class.java)
                        startActivity(intent)
                        Toast.makeText(activity, "Logged Out", Toast.LENGTH_SHORT).show()
                    }
                    else->{
                        false
                    }
                }
                true
            }
            popupMenu.show()
        }
        binding!!.docrecyc.layoutManager = LinearLayoutManager(requireContext())
        binding!!.docrecyc.setHasFixedSize(true)
        docArrayList = arrayListOf<DocProfile>()
        getDocData()

        thisIsMicToText()

        binding!!.imageView15.setOnClickListener {
            val searchText = binding!!.editTextText3.text
            searchText.replace("\\s+".toRegex(),"+")

            val searchUri = Uri.parse("https://www.google.com/search?q=$searchText")
            val intent = Intent(Intent.ACTION_VIEW, searchUri)

            if (searchText != null) {
                // Start the activity
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Please Type Something", Toast.LENGTH_SHORT).show()
            }
        }


        return binding!!.root
    }

    private fun thisIsMicToText() {
        mic = binding!!.imageView14

        mic.setOnClickListener{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            }catch (e:Exception){
                Toast.makeText(requireContext(), "e.message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_SPEECH_INPUT){
            if (resultCode == AppCompatActivity.RESULT_OK && data != null){
                val res : ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                binding!!.editTextText3.setText(
                    Objects.requireNonNull(res)[0]
                )
            }
        }
    }



    override fun onStart() {
        super.onStart()
        val uid = firebaseAuth.uid
        firebaseDatabase = FirebaseDatabase.getInstance().getReference("UserName")
        firebaseDatabase.child(uid!!).get().addOnSuccessListener {
            if(it.exists()){
                val name = it.child("name").value
                binding!!.textView6.text = name.toString().uppercase()
            }
        }
    }




//    override fun onCreateContextMenu(
//        menu: ContextMenu,
//        v: View,
//        menuInfo: ContextMenu.ContextMenuInfo?
//    ) {
//        super.onCreateContextMenu(menu, v, menuInfo)
//        menu.setHeaderTitle("Pick Option")
//        requireActivity().menuInflater.inflate(R.menu.dash_image, menu)
//    }
//
//    override fun onContextItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.logout ->{
//                firebaseAuth.signOut()
//                val intent  = Intent(requireContext(), LoginPage::class.java)
//                startActivity(intent)
//            }
//        }
//        return super.onContextItemSelected(item)
//    }




}