package com.m3.rajat.piyush.studymatealpha.faculty
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyUpdateBinding

class faculty_update : AppCompatActivity() {

    private lateinit var upd_name : EditText
    private lateinit var upd_email : EditText
    private lateinit var upd_password : EditText
    private lateinit var upd_sub : EditText
    private lateinit var upd_image : ImageView
    private lateinit var upd_id : EditText
    private lateinit var btn_upd : Button
    private lateinit var btn_delete : Button

    private lateinit var binding : ActivityFacultyUpdateBinding

    private lateinit var sqLiteHelper: SQLiteHelper
    private var adapter : FacultyAdapter?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        //Getting Data From View For Update
        upd_name.setText(intent.getStringExtra("faculty_name"))
        upd_email.setText(intent.getStringExtra("faculty_email"))
        upd_password.setText(intent.getStringExtra("faculty_pass"))
        upd_sub.setText(intent.getStringExtra("faculty_sub"))
        upd_id.setText(intent.getIntExtra("faculty_id",0).toString())
        if(intent.getByteArrayExtra("faculty_image")!=null){
            upd_image.setImageBitmap(BitmapFactory.decodeByteArray(intent.getByteArrayExtra("faculty_image"),0,intent.getByteArrayExtra("faculty_image")!!.size))
        }


        btn_upd.setOnClickListener {
            if(validation()){
                updateFaculty()
            } else{
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        btn_delete.setOnClickListener {
            val email = intent.getStringExtra("faculty_email")
            if(email!=null) {
                deleteFaculty(email)
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, faculty_view::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }

    private fun validation(): Boolean {
        if(upd_name.length() == 0){
            upd_name.error = "Name Required"
            return false
        } else if(upd_password.length()==0){
            upd_password.error = "Password Not Be Null"
            return false
        } else if(upd_sub.length()==0){
            upd_sub.error = "Subject Can't Be Empty"
            return false
        }
        return true
    }

    private fun updateFaculty() {
        val faculty = AdminModel( faculty_id = upd_id.text.toString().toInt(),faculty_name = upd_name.text.toString() , faculty_email = upd_email.text.toString(), faculty_sub = upd_sub.text.toString(), faculty_password = upd_password.text.toString())
        Log.d("apd",faculty.faculty_email)
        val rc =  sqLiteHelper.updateFacultyById(faculty)
        Log.d("apd",rc.toString())
        if(rc > 0){
            getFaculty()
            Toast.makeText(applicationContext,"Update",Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, faculty_view::class.java))
        }else{
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFaculty() {
        val admList = sqLiteHelper.getAllFaculty()
        adapter?.addItems(admList)
    }

    private fun deleteFaculty(email: String)
    {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Faculty")
        builder.setMessage("Do You Want To Delete This Faculty ?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog,_->
            sqLiteHelper.DeleteFaculty(email)
            getFaculty()
            dialog.dismiss()
            startActivity(Intent(applicationContext, faculty_view::class.java))
        }
        builder.setNegativeButton("No"){ dialog,_->
            dialog.dismiss()
        }
        val alert = builder.create()
        alert.show()
    }

    private fun initView() {
        upd_name = findViewById(R.id.Admin_update_faculty_name)
        upd_email = findViewById(R.id.Admin_update_faculty_email)
        upd_password = findViewById(R.id.Admin_update_faculty_pass)
        upd_sub = findViewById(R.id.Admin_update_faculty_sub)
        upd_id = findViewById(R.id.Admin_update_faculty_Id)
        upd_image = findViewById(R.id.Admin_update_faculty_image)
        btn_upd = findViewById(R.id.btnAdmin_update_faculty)
        btn_delete = findViewById(R.id.btnDeleteFaculty)
    }
}