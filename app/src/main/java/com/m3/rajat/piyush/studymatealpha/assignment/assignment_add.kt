package com.m3.rajat.piyush.studymatealpha.assignment
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAssignmentAddBinding

class assignment_add : AppCompatActivity() {
    private lateinit var assignment_name: EditText
    private lateinit var assignment_sdate: EditText
    private lateinit var assignment_type: EditText
    private lateinit var add_assignment: Button
    private lateinit var btn_back: Button
    private lateinit var binding : ActivityAssignmentAddBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignmentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        sqLiteHelper = SQLiteHelper(this)

        add_assignment.setOnClickListener {
            if (assignment_validation()) {
                addAssignment()
                clearAssignment()
                startActivity(Intent(applicationContext, Admin_panel::class.java))
            }
        }

        btn_back.setOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
        }


        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }

    }

    private fun addAssignment() {
        val name = assignment_name.text.toString()
        val sdate = assignment_sdate.text.toString()
        val stype = assignment_type.text.toString()
        val adm = AdminModel(assignment_name = name, assignment_sdate = sdate, assignment_type = stype)
        val status = sqLiteHelper.InsertAssignment(adm)
        if(status > -1){
            Toast.makeText(this,"Assignment Added",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Assignment Exists",Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearAssignment() {
        assignment_name.setText("")
        assignment_type.setText("")
        assignment_name.requestFocus()
    }

    private fun assignment_validation(): Boolean {
        if(assignment_name.length() == 0){
            assignment_name.error = "Name Required"
            return false
        } else if(assignment_sdate.length()==0){
            assignment_name.error = "Date Needed"
            return false
        } else if(assignment_type.length()==0){
            assignment_type.error = "Type Can't Be Empty"
        }
        return true
    }

    private fun initView() {
        assignment_name = findViewById(R.id.Admin_add_assignment_name)
        assignment_sdate = findViewById(R.id.Admin_add_assignment_sdate)
        assignment_type = findViewById(R.id.Admin_add_assignment_type)
        add_assignment = findViewById(R.id.btnAdmin_add_assignment)
        btn_back = findViewById(R.id.btnBack)

        // For Date (Frontend)
        assignment_sdate.setOnClickListener {
            val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Assignment Submission Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build()

            materialDatePicker.addOnPositiveButtonClickListener {
                assignment_sdate.setText(materialDatePicker.headerText)
            }

            materialDatePicker.show(supportFragmentManager,"Material Date Picker")
        }
    }
}