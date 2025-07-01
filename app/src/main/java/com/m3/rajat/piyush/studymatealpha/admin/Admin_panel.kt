package com.m3.rajat.piyush.studymatealpha.admin

import android.annotation.SuppressLint
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.card.MaterialCardView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.m3.rajat.piyush.studymatealpha.R
import com.m3.rajat.piyush.studymatealpha.assignment.assignment_add
import com.m3.rajat.piyush.studymatealpha.assignment.assignment_view
import com.m3.rajat.piyush.studymatealpha.database.AdminModel
import com.m3.rajat.piyush.studymatealpha.database.SQLiteHelper
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityAdminPanelBinding
import com.m3.rajat.piyush.studymatealpha.faculty.faculty_add
import com.m3.rajat.piyush.studymatealpha.faculty.faculty_view
import com.m3.rajat.piyush.studymatealpha.notice.notice_add
import com.m3.rajat.piyush.studymatealpha.notice.notice_view
import com.m3.rajat.piyush.studymatealpha.student.student_add
import com.m3.rajat.piyush.studymatealpha.student.student_view
import java.io.ByteArrayOutputStream

@Suppress("DEPRECATION")
class Admin_panel : AppCompatActivity() {

    private lateinit var toggle:ActionBarDrawerToggle
    private lateinit var add_faculty : MaterialCardView
    private lateinit var add_student : MaterialCardView
    private lateinit var add_notice : MaterialCardView
    private lateinit var add_assignment : MaterialCardView
    private lateinit var byteArray: ByteArray
    private lateinit var adminSession: AdminSession
    private lateinit var  sqLiteHelper: SQLiteHelper
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var binding : ActivityAdminPanelBinding

    @SuppressLint("UseCompatLoadingForDrawables", "SuspiciousIndentation", "ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sqLiteHelper = SQLiteHelper(this)
        adminSession= AdminSession(this)

        binding.fab.setOnClickListener {
            val shake = TranslateAnimation(0f, 10f, 0f, 0f)
            shake.duration = 500
            shake.interpolator = CycleInterpolator(7f)

            val rootLayout = findViewById<View>(android.R.id.content)
            rootLayout.startAnimation(shake)

            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(500)
            }

            try{
                cacheDir.deleteRecursively()
                externalCacheDir?.deleteRecursively()
            } catch (e:Exception) {
                e.printStackTrace()
            }

            Runtime.getRuntime().gc()
            onTrimMemory(ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW)
        }


        //Navigation Drawer
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)
        val view : View = navView.getHeaderView(0)
        val name : TextView = view.findViewById(R.id.admin_name_head)
        val email : TextView = view.findViewById(R.id.admin_email_head)
        val image : ImageView = view.findViewById(R.id.admin_photo)

        val adminId = adminSession.sharedPreferences.getInt("id",0)

        val admin = sqLiteHelper.getAdmin(adminId)
        if(admin.isNotEmpty()){
            name.text = admin[0].admin_name
            email.text = admin[0].admin_email
            if(admin[0].admin_image!=null) {
                image.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                        admin[0].admin_image,
                        0,
                        admin[0].admin_image!!.size
                    )
                )
            }else{
                image.setImageDrawable(resources.getDrawable(R.drawable.add_img))
            }
        }

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
            drawerLayout.addDrawerListener(toggle)

            image.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.setType("image/*")
                ActivityLauncher.launch(intent)
            }


            //BackPressed CallBack
            onBackPressedDispatcher.addCallback {
                Toast.makeText(applicationContext, "Please Logout To GoBack", Toast.LENGTH_SHORT)
                    .show()
            }

        actionBarDrawerToggle = ActionBarDrawerToggle(this,binding.drawerLayout,binding.topAppBar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle)

        actionBarDrawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.admin_nav_profile -> startActivity(Intent(applicationContext, Admin_view::class.java).putExtra("admin_email",adminSession.sharedPreferences.getString("email","")))

                    R.id.admin_nav_addfaculty -> {
                        val faculty_add = Intent(applicationContext, faculty_add::class.java)
                        startActivity(faculty_add)
                    }

                    R.id.admin_nav_addstudent -> {
                        val student_add = Intent(applicationContext, student_add::class.java)
                        startActivity(student_add)
                    }

                    R.id.admin_nav_notices -> {
                        val notice_add = Intent(applicationContext, notice_add::class.java)
                        startActivity(notice_add)
                    }

                    R.id.admin_nav_assignments -> {
                        val assignment_add = Intent(applicationContext, assignment_add::class.java)
                        startActivity(assignment_add)
                    }

                    R.id.admin_nav_logout -> {
                        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                            .setMessage("Are you sure want to logout ?")
                            .setTitle("Information")
                            .setCancelable(true)
                            .setPositiveButton("Yes"){
                                    dialog, _ ->
                                adminSession.adminLogout()
                                startActivity(Intent(applicationContext, Admin::class.java))
                                finish()
                                dialog.dismiss()
                            }
                            .setNegativeButton("No"){
                                    dialog, _ ->
                                dialog.dismiss()
                            }
                        materialAlertDialogBuilder.create().show()
                    }

                }
                true
            }

            //Admin Adds Faculty
            add_faculty = findViewById(R.id.admin_view_faculty)
            add_faculty.setOnClickListener {
                startActivity(Intent(applicationContext, faculty_view::class.java))
            }

            //Admin Adds Student
            add_student = findViewById(R.id.admin_view_student)
            add_student.setOnClickListener {
                val i  = Intent(applicationContext, student_view::class.java)
                startActivity(i)
            }

            //Admin Adds Notices
            add_notice = findViewById(R.id.admin_view_notice)
            add_notice.setOnClickListener {
                startActivity(Intent(applicationContext, notice_view::class.java))
            }

            //Admin Adds Assignments
            add_assignment = findViewById(R.id.admin_view_assignment)
            add_assignment.setOnClickListener {
                startActivity(Intent(applicationContext, assignment_view::class.java))
            }

    }

    private val ActivityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Activityresult ->
        if(Activityresult.resultCode == RESULT_OK){
            val uri = Activityresult.data!!.data
            val navView : NavigationView = findViewById(R.id.nav_view)
            val view : View = navView.getHeaderView(0)
            val image : ImageView = view.findViewById(R.id.admin_photo)
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG,50,stream)
                byteArray = stream.toByteArray()
                //size validation
                if (byteArray.size / 1024 < 1024){
                    image.setImageBitmap(bitmap)
                    inputStream!!.close()

                    val email : String  =  adminSession.sharedPreferences.getString("email","").toString()
                    Log.d("rc-mail",email)
                    val images =  byteArray

                    val adminModel = AdminModel(admin_email = email , admin_image = images)
                    Log.d("rc-model",adminModel.toString())
                    val ic = sqLiteHelper.updateImage(adminModel)
                    Log.d("rc-query",ic.toString())

                    if(ic  > -1){
                        Toast.makeText(applicationContext,"Profile picture update",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext,"Not Update",Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(this,"Please choose image below 500K",Toast.LENGTH_SHORT).show()
                }
            }catch (e : Exception){
                Toast.makeText(applicationContext,e.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

//    @RequiresApi(Build.VERSION_CODES.TIRAMISU) :: ALSO ADD IN MANIFEST READ_IMAGE PERMISSION FOR API 33 =>
//    private fun reqPermission(){
//        isPermissionGrantedForReadImageAPI33 = ContextCompat.checkSelfPermission(
//            this,android.Manifest.permission.READ_MEDIA_IMAGES)==PackageManager.PERMISSION_GRANTED
//
//        val permissionRequest : MutableList<String> = ArrayList()
//
//        if (!isPermissionGrantedForReadImageAPI33){
//            permissionRequest.add(android.Manifest.permission.READ_MEDIA_IMAGES)
//        }
//
//        if (permissionRequest.isNotEmpty()){
//            permissionLauncher.launch(permissionRequest.toTypedArray())
//        }
//    }

    //Navigation Drawer OnSelect Event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return actionBarDrawerToggle.onOptionsItemSelected(item)
    }
}