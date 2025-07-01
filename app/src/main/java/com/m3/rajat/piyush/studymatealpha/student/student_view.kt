package com.m3.rajat.piyush.studymatealpha.student
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.color.MaterialColors
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.admin.Admin_panel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentViewBinding

@Suppress("DEPRECATION")
class student_view : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : StudentAdapter?= null

    private lateinit var binding : ActivityStudentViewBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqLiteHelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqLiteHelper.getAllStudent()
        adapter?.addItems(admList)

        adapter?.setOnClickItem{
            Log.d("checkSid",it.student_id.toString())
            startActivity(
                Intent(applicationContext, student_update::class.java)
                    .putExtra("student_id",it.student_id)
                    .putExtra("student_image",it.student_image)
                    .putExtra("student_name",it.student_name)
                    .putExtra("student_email",it.student_email)
                    .putExtra("student_pass",it.student_password)
                    .putExtra("student_class",it.student_class)
            )
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        if (binding.recyclerViewStudent.adapter?.itemCount == 0) {
            val dynamicColor = MaterialColors.getColor(
                this@student_view,
                com.google.android.material.R.attr.colorPrimary,
                Color.BLACK
            )
            val textView = TextView(this).apply {
                id = View.generateViewId() // ✅ Important!
                text = "Please Add Some \n Records First"
                textSize = 24f
                setTextColor(dynamicColor)
                gravity = Gravity.CENTER
            }
            val button = MaterialButton(this).apply {
                id = View.generateViewId()
                text = "Add Now"
                setBackgroundColor(dynamicColor)
                setTextColor(Color.WHITE)

                setOnClickListener {
                    startActivity(Intent(applicationContext, student_add::class.java))
                }
            }
            val layoutParamsText = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }


            val layoutParamsButton = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                topToBottom = textView.id
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                topMargin = 32
            }

            binding.con.addView(textView, layoutParamsText)
            binding.con.addView(button, layoutParamsButton)
        }


        onBackPressedDispatcher.addCallback {  }

    }

    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewStudent)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter
    }
}