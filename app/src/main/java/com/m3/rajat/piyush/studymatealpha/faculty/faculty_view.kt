package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.isEmpty
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityFacultyViewBinding

class faculty_view : AppCompatActivity() {
    private lateinit var sqlitehelper : SQLiteHelper
    private lateinit var recyclerView : RecyclerView
    private var adapter : FacultyAdapter?= null
    private var adm : AdminModel?= null

    private lateinit var binding : ActivityFacultyViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFacultyViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqlitehelper = SQLiteHelper(this)
        initRecyclerView()

        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)

        if(binding.recyclerViewFaculty.adapter?.itemCount!! == 0){
            val imageView = LottieAnimationView(this)
            val lv = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT)
            lv.setMargins(32,32,32,32)
            imageView.layoutParams = lv
            binding.con.addView(imageView)
            imageView.setAnimation(R.raw.not_found_fac)
            imageView.loop(true)
            imageView.playAnimation()
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

    private fun getFaculty() {
        val admList = sqlitehelper.getAllFaculty()
        adapter?.addItems(admList)
    }
}