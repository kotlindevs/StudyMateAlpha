package com.m3.rajat.piyush.studymatealpha.admin
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.m3.rajat.piyush.studymatealpha.MainActivity
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminBinding

class Admin : AppCompatActivity() {
    private lateinit var binding : ActivityAdminBinding
    private lateinit var adminSession: AdminSession
    private lateinit var sqLiteHelper: SQLiteHelper

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqLiteHelper = SQLiteHelper(this)
        adminSession = AdminSession(this)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    }

    override fun onStart() {
        super.onStart()
        binding.btnLogin.setOnClickListener {
            validateUser()
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this,AdminCreate::class.java))
        }
        if(adminSession.login()){
            startActivity(Intent(applicationContext, Admin_panel::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {}
    }

    private fun validateUser(): Boolean {
        if (binding.EdtAdminEmail.length() == 0) {
            binding.EdtAdminEmail.error = "Email ID Required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.EdtAdminEmail.text.toString()).matches()) {
            Toast.makeText(this, "Email Format Wrong!", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.EdtAdminPasswd.length() == 0) {
            binding.EdtAdminPasswd.error = "Password Required"
            return false
        } else {
            val email = binding.EdtAdminEmail.text.toString()
            val pass = binding.EdtAdminPasswd.text.toString()

            val admin = sqLiteHelper.checkAdmin(email)

            if (admin.isNotEmpty()) {
                if (email == admin[0].admin_email && pass == admin[0].admin_password) {
                    Toast.makeText(applicationContext, "SignIn Successfully", Toast.LENGTH_SHORT).show()
                    adminSession.adminLogin(email, admin[0].admin_name, admin[0].admin_id!!)
                    startActivity(Intent(this, Admin_panel::class.java))
                    return true
                } else {
                    Toast.makeText(applicationContext, "Wrong Password", Toast.LENGTH_SHORT).show()
                    return false
                }
            } else {
                Toast.makeText(applicationContext, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                Toast.makeText(applicationContext, "Please Try To Create A Admin First !!", Toast.LENGTH_LONG).show()
                clearAdmin()
                return false
            }
        }
    }


    private fun clearAdmin() {
        binding.EdtAdminEmail.setText("")
        binding.EdtAdminPasswd.setText("")
    }
}