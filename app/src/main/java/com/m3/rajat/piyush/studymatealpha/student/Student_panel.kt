package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentPanelBinding
import com.m3.rajat.piyush.studymatealpha.student.StudentSession

class Student_panel : AppCompatActivity() {
    private lateinit var binding: ActivityStudentPanelBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var studentSession: StudentSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(this)
        studentSession = StudentSession(this)

        val studentId = studentSession.sharedPreferences.getInt("id", 0)
        Log.d("sid",studentId.toString())

        if (studentId != null) {
            val student = sqLiteHelper.getStudent(studentId)
            if (student.isNotEmpty()) {
                binding.studentId.setText(student[0].student_id.toString())
                binding.studentName.setText(student[0].student_name)
                binding.studentEmail.setText(student[0].student_email)
                binding.studentClass.setText(student[0].student_class)
                if (student[0].student_image != null) {
                    binding.studentImage.setImageBitmap(
                        BitmapFactory.decodeByteArray(
                            student[0].student_image,
                            0,
                            student[0].student_image!!.size
                        )
                    )
                } else {
                    binding.studentImage.setImageDrawable(resources.getDrawable(R.drawable.add_img))
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                .setMessage("Are you sure want to logout ?")
                .setTitle("Information")
                .setCancelable(true)
                .setPositiveButton("Yes"){
                        dialog,msg ->
                    studentSession.studentLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                        dialog,msg ->
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
                        dialog,msg ->
                    studentSession.studentLogout()
                    startActivity(Intent(applicationContext, Faculty::class.java))
                    finish()
                    dialog.dismiss()
                }
                .setNegativeButton("No"){
                        dialog,msg ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }

        onBackPressedDispatcher.addCallback { }
    }
}