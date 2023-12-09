package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityStudentViewBinding

class student_view : AppCompatActivity() {
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter : StudentAdapter?= null
    private var adm : AdminModel?= null

    private lateinit var binding : ActivityStudentViewBinding

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
            startActivity(Intent(applicationContext,Admin_panel::class.java))
            finish()
        }

        if(binding.recyclerViewStudent.adapter?.itemCount!! == 0){
            val imageView = LottieAnimationView(this)
            val lv = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT)
            lv.setMargins(32,32,32,32)
            imageView.layoutParams = lv
            binding.con.addView(imageView)
            imageView.setAnimation(R.raw.not_found_stud)
            imageView.loop(true)
            imageView.playAnimation()
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