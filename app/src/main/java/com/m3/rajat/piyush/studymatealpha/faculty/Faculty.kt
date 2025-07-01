package com.m3.rajat.piyush.studymatealpha.faculty
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.m3.rajat.piyush.studymatealpha.MainActivity
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.student.Student
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.student.StudentSession
import com.m3.rajat.piyush.studymatealpha.student.Student_panel

class Faculty : AppCompatActivity() {

    private lateinit var email : EditText
    private lateinit var btnNext : Button
    private lateinit var btnBack : Button
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var facultySession: FacultySession
    private lateinit var studentSession: StudentSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faculty)

        email = findViewById(R.id.EdtFacultyEmail)
        btnNext = findViewById(R.id.btnCheck)
        btnBack = findViewById(R.id.btnBack)
        sqLiteHelper = SQLiteHelper(this)
        facultySession = FacultySession(this)
        studentSession = StudentSession(this)

        if(facultySession.login()){
            startActivity(Intent(applicationContext, Faculty_panel::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }
        if(studentSession.login()){
            startActivity(Intent(applicationContext, Student_panel::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            finish()
        }

        btnNext.setOnClickListener {
            if (validation_faculty()){
                val faculty = sqLiteHelper.isFaculty(email.text.toString())
                if(faculty.isNotEmpty()){
                    Toast.makeText(this,"Faculty",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Student::class.java).putExtra("faculty_email", email.text.toString()))
                }else if(sqLiteHelper.isStudent(email.text.toString()).isNotEmpty()){
                    Toast.makeText(this,"Student",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, Student::class.java).putExtra("student_email",
                        email.text.toString()))
                }else{
                    Toast.makeText(this,"User not found ",Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBack.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        onBackPressedDispatcher.addCallback {}
    }
    private fun validation_faculty(): Boolean {
        if(email.length()==0){
            email.error = "Faculty email required"
            return false
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            Toast.makeText(this,"Enter correct email",Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}