package com.m3.rajat.piyush.studymatealpha.faculty
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyPanelBinding

@Suppress("DEPRECATION")
class Faculty_panel : AppCompatActivity() {
    private lateinit var binding: ActivityFacultyPanelBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var facultySession: FacultySession
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(this)
        facultySession = FacultySession(this)

        val facultyId = facultySession.sharedPreferences.getInt("id", 0)
        Log.d("fid",facultyId.toString())

        val faculty = sqLiteHelper.getFaculty(facultyId)
        if (faculty.isNotEmpty()) {
            binding.facultyId.setText(faculty[0].faculty_id.toString())
            binding.facultyName.setText(faculty[0].faculty_name)
            binding.facultyEmail.setText(faculty[0].faculty_email)
            binding.facultySub.setText(faculty[0].faculty_sub)
            if (faculty[0].faculty_image != null) {
                binding.facultyImage.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        faculty[0].faculty_image,
                        0,
                        faculty[0].faculty_image!!.size
                    )
                )
            } else {
                binding.facultyImage.setImageDrawable(resources.getDrawable(R.drawable.add_img))
            }
        }

        binding.btnLogout.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure want to logout ?")
                .setTitle("Information")
                .setCancelable(true)
                .setPositiveButton("Yes"){
                        dialog, _ ->
                    facultySession.facultyLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                        dialog, _ ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }

        binding.topAppBar.setNavigationOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure want to logout ?")
                .setTitle("Information")
                .setCancelable(true)
                .setPositiveButton("Yes"){
                        dialog, _ ->
                    facultySession.facultyLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                        dialog, _ ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }

        onBackPressedDispatcher.addCallback { }
    }
}