package com.m3.rajat.piyush.studymatealpha.admin
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminViewBinding

@Suppress("DEPRECATION")
class Admin_view : AppCompatActivity() {

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var adminSession: AdminSession
    private lateinit var id : EditText
    private lateinit var name: EditText
    private lateinit var email : EditText
    private lateinit var image : ImageView
    private lateinit var btn_update : Button
    private  lateinit var  binding : ActivityAdminViewBinding
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(this)
        adminSession = AdminSession(this)


        id = findViewById(R.id.Admin_updateId)
        name = findViewById(R.id.Admin_updatename)
        email = findViewById(R.id.Admin_updateemail)
        image = findViewById(R.id.Admin_updateimage)
        btn_update = findViewById(R.id.btnAdmin_update)

        val adminId = adminSession.sharedPreferences.getInt("id",0)

        val admin = sqLiteHelper.getAdmin(adminId)
        if(admin.isNotEmpty()){
            id.setText(admin[0].admin_id.toString())
            name.setText(admin[0].admin_name)
            email.setText(admin[0].admin_email)
            if(admin[0].admin_image!=null) {
                image.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        admin[0].admin_image,
                        0,
                        admin[0].admin_image!!.size
                    )
                )
            }else{
                image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
            }
        }

        btn_update.setOnClickListener {
            if(validation()){
                updateAdmin()
            } else{
                Toast.makeText(this,"Something Went Wrong", Toast.LENGTH_SHORT).show()
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }

    private fun validation(): Boolean {
        if(id.length() == 0){
            id.error = "Id Required"
            return false
        } else if(name.length()==0){
            name.error = "Name Required"
            return false
        } else if(email.length()==0){
            email.error = "Email Can't Be Empty"
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            Toast.makeText(this,"Email Format Wrong !",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun updateAdmin() {
        val admin = AdminModel( admin_id = id.text.toString().toInt(), admin_name = name.text.toString() , admin_email = email.text.toString())
        val rc =  sqLiteHelper.updateAdminById(admin)
        if(rc > 0){
            Toast.makeText(applicationContext,"Update",Toast.LENGTH_SHORT).show()
            this.recreate()
        }else{
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }
}