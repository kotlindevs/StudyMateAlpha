package com.m3.rajat.piyush.studymatealpha.faculty
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyViewBinding

@Suppress("DEPRECATION")
class faculty_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : FacultyAdapter?= null

    private lateinit var binding : ActivityFacultyViewBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)

        if (binding.recyclerViewFaculty.adapter?.itemCount == 0) {
            val dynamicColor = MaterialColors.getColor(
                this@faculty_view,
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
                    startActivity(Intent(applicationContext, faculty_add::class.java))
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


        adapter?.setOnClickItem{
            startActivity(Intent(applicationContext, faculty_update::class.java)
                .putExtra("faculty_id",it.faculty_id)
                .putExtra("faculty_image",it.faculty_image)
                .putExtra("faculty_name",it.faculty_name)
                .putExtra("faculty_email",it.faculty_email)
                .putExtra("faculty_pass",it.faculty_password)
                .putExtra("faculty_sub",it.faculty_sub))
        }


        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }

    }
    private fun initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewFaculty)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FacultyAdapter()
        recyclerView.adapter = adapter
    }
}