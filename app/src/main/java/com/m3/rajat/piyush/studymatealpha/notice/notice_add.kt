package com.m3.rajat.piyush.studymatealpha.notice
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityNoticeAddBinding

class notice_add : AppCompatActivity() {
    private lateinit var notice_name : EditText
    private lateinit var notice_des : EditText
    private lateinit var notice_date : EditText
    private lateinit var notice_add : Button
    private lateinit var btn_back : Button
    private lateinit var binding : ActivityNoticeAddBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticeAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
        sqLiteHelper = SQLiteHelper(this)

        notice_add.setOnClickListener {
            if(noticeValidation()){
                addNotice()
                clearNotice()
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

    private fun clearNotice() {
        notice_name.setText("")
        notice_des.setText("")
        notice_name.requestFocus()
    }

    private fun addNotice() {
        val name = notice_name.text.toString()
        val des = notice_des.text.toString()
        val ndate = notice_date.text.toString()
        val adm = AdminModel(notice_name = name,  notice_des = des ,notice_date = ndate)
        val status = sqLiteHelper.InsertNotice(adm)
        if(status > -1){
            Toast.makeText(this,"Notice Added",Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this,"Notice Exists",Toast.LENGTH_SHORT).show()
        }
    }

    private fun noticeValidation(): Boolean {
        if(notice_name.length() == 0){
            notice_name.error = "Name Required"
            return false
        } else if(notice_des.length()==0){
            notice_des.error = "Minimum 5 Words Needed"
            return false
        } else if(notice_date.length()==0){
            notice_date.error = "Date Can't Be Empty"
        }
        return true
    }

    private fun initView() {
        notice_name = findViewById(R.id.Admin_add_notice_name)
        notice_des = findViewById(R.id.Admin_add_notice_des)
        notice_date = findViewById(R.id.Admin_add_notice_date)
        notice_add = findViewById(R.id.btnAdmin_add_notice)
        btn_back = findViewById(R.id.btnBack)

        // For Date Field (Frontend Part)
        notice_date.setOnClickListener {
            val materialDatePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Notice Genrate Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                .build()

            materialDatePicker.addOnPositiveButtonClickListener {
                notice_date.setText(materialDatePicker.headerText)
            }

            materialDatePicker.show(supportFragmentManager,"Material Date Picker")
        }
    }
}