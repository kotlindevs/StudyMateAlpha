package com.m3.rajat.piyush.studymatealpha.admin

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminCreateBinding

class AdminCreate : AppCompatActivity() {
    private lateinit var binding : ActivityAdminCreateBinding
    private val adminId : Int = (2100000..2200000).random()
    private lateinit var adminSession: AdminSession
    private lateinit var sqLiteHelper: SQLiteHelper

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAdminCreateBinding.inflate(layoutInflater)
        sqLiteHelper = SQLiteHelper(this)
        adminSession = AdminSession(this)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun onStart() {
        super.onStart()
        binding.btnCreateAccount.setOnClickListener {
            validateUser()
        }

        binding.btnBack.setOnClickListener{
            startActivity(Intent(this, Admin::class.java))
        }

        binding.topAppBar.setOnClickListener{
            startActivity(Intent(this, Admin::class.java))
        }

        onBackPressedDispatcher.addCallback {}
    }

    private fun validateUser() : Boolean{
        if(binding.EdtAdminName.length()==0){
            binding.EdtAdminName.error = "Name Required"
            return false
        }
        else if(binding.EdtAdminEmail.length()==0){
            binding.EdtAdminEmail.error = "Email ID Required"
            return false
        } else if(!Patterns.EMAIL_ADDRESS.matcher(binding.EdtAdminEmail.text.toString()).matches()){
            Toast.makeText(this,"Email Format  Wrong !", Toast.LENGTH_SHORT).show()
            return false
        } else if(binding.EdtAdminPasswd.length()==0){
            binding.EdtAdminPasswd.error = "Password Required"
            return false
        } else {
            addAdmin()
        }
        return true
    }

    private fun addAdmin() {

        val name = binding.EdtAdminName.text.toString()
        val email = binding.EdtAdminEmail.text.toString()
        val pass = binding.EdtAdminPasswd.text.toString()

        val admin = AdminModel(
            admin_id = adminId,
            admin_name = name,
            admin_email = email,
            admin_password = pass,
        )

        val addAdmin = sqLiteHelper.InsertAdmin(admin)

        if (addAdmin > -1) {
            Toast.makeText(applicationContext, "SignUp Successfully", Toast.LENGTH_SHORT).show()
            clearAdmin()
            finish()
        } else {
            Toast.makeText(applicationContext, "Account Already Exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearAdmin() {
        binding.EdtAdminEmail.setText("")
        binding.EdtAdminName.setText("")
        binding.EdtAdminPasswd.setText("")
    }
}


