package com.m3.rajat.piyush.studymatealpha.student

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty
import com.m3.rajat.piyush.studymatealpha.faculty.FacultySession
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty_panel

class Student : AppCompatActivity() {

    private lateinit var userId : EditText
    private lateinit var userPasswd : EditText
    private lateinit var userLogin : Button
    private lateinit var userBack : Button
    private lateinit var  sqLiteHelper: SQLiteHelper
    private lateinit var facultySession: FacultySession
    private lateinit var studentSession: StudentSession

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        userId = findViewById(R.id.EdtUserId)
        userPasswd = findViewById(R.id.EdtUserPassword)
        userLogin = findViewById(R.id.userLogin)
        userBack = findViewById(R.id.userBack)

        sqLiteHelper = SQLiteHelper(this)
        facultySession = FacultySession(this)
        studentSession = StudentSession(this)

        val student = intent.getStringExtra("student_email")
        val faculty = intent.getStringExtra("faculty_email")

        if (student != null) {
            val yesIsStudent = sqLiteHelper.isStudent(student)
            userId.setText(yesIsStudent[0].student_id.toString())
        }

        if (faculty != null) {
            val yesIsFaculty = sqLiteHelper.isFaculty(faculty)
            userId.setText(yesIsFaculty[0].faculty_id.toString())
        }

        val id = userId.text.toString()

        userLogin.setOnClickListener {
            if (validation_student()) {
                val valFac = sqLiteHelper.chkPasswdFaculty(id.toInt())
                if (valFac.isNotEmpty()) {
                    if (userPasswd.text.toString() == valFac[0].faculty_password) {
                        facultySession.facultyLogin(userId.text.toString().toInt())
                        showToast("Login Successfully !")
                        startActivity(Intent(applicationContext, Faculty_panel::class.java)
                            .putExtra("id",userId.text))
                        userId.text.clear()
                        userPasswd.text.clear()
                    } else {
                        showToast("Incorrect Password !")
                    }
                }else if (sqLiteHelper.chkPasswdStudent(id.toInt()).isNotEmpty()) {
                    if (userPasswd.text.toString() == sqLiteHelper.chkPasswdStudent(id.toInt())[0].student_password) {
                        studentSession.studentLogin(userId.text.toString().toInt())
                        showToast("Login Successfully !")
                        startActivity(Intent(applicationContext, Student_panel::class.java)
                            .putExtra("id",userId.text))
                        userId.text.clear()
                        userPasswd.text.clear()
                    } else {
                        showToast("Incorrect Password !")
                    }
                }
            }
        }

        userBack.setOnClickListener {
            startActivity(Intent(applicationContext, Faculty::class.java))
        }

        onBackPressedDispatcher.addCallback {}
    }

    private fun validation_student(): Boolean {
        if(userPasswd.length()==0){
            userPasswd.error = "Password cann't be empty"
            return false
        }
        return true
    }

    private fun showToast(str : String){
        Toast.makeText(applicationContext,str,Toast.LENGTH_SHORT).show()
    }
}