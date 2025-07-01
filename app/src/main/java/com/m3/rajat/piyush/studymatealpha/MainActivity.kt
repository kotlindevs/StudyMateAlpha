package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.admin.Admin
import com.m3.rajat.piyush.studymatealpha.faculty.Faculty

class MainActivity : AppCompatActivity() {
    private lateinit var btnAdmin: Button
    private lateinit var btnOther: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdmin = findViewById(R.id.btnAdmin)
        btnOther = findViewById(R.id.btnOther)
        btnAdmin.setOnClickListener {
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                requestRuntimePermissionForLowerThanApi33()
            }else{
                requestRuntimePermissionForUpperOrUpperApi33()
            }
        }
        btnOther.setOnClickListener {
            startActivity(Intent(applicationContext, Faculty::class.java))
        }

        onBackPressedDispatcher.addCallback {  }
    }

    private fun requestRuntimePermissionForLowerThanApi33() {
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
//        Toast.makeText(applicationContext,"Permission Granted !", Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext, Admin::class.java))
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
            materialAlertDialogBuilder.setMessage("This app require READ_IMAGES permission from particular feature to work as  excepted !")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("Ok"){
                        dialog, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){
                        dialog, _ ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),100)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(applicationContext,"Permission Granted !", Toast.LENGTH_LONG).show()
                startActivity(Intent(applicationContext, Admin::class.java))
            }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                materialAlertDialogBuilder.setMessage("This feature is unavailable because this feature permission that you have denied."+ "Please allow Image permission from setting to proceed further")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("Setting"){
                            dialog, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package",packageName,null)
                        intent.data= uri
                        startActivity(intent)

                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel"){
                            dialog, _ ->
                        dialog.dismiss()
                    }
                materialAlertDialogBuilder.create().show()
            }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_IMAGES)) {
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
                materialAlertDialogBuilder.setMessage("This feature is unavailable because this feature permission that you have denied." + "Please allow Image permission from setting to proceed further")
                    .setTitle("Permission Required")
                    .setCancelable(false)
                    .setPositiveButton("Setting") { dialog, _ ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)

                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                materialAlertDialogBuilder.create().show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestRuntimePermissionForUpperOrUpperApi33() {
        if(checkSelfPermission(android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED){
//        Toast.makeText(applicationContext,"Permission Granted !", Toast.LENGTH_LONG).show()
            startActivity(Intent(applicationContext, Admin::class.java))
        }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_MEDIA_IMAGES)){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
            materialAlertDialogBuilder.setMessage("This app require READ_IMAGES permission from particular feature to work as  excepted !")
                .setTitle("Permission Required")
                .setCancelable(false)
                .setPositiveButton("Ok"){
                        dialog, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),100)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel"){
                        dialog, _ ->
                    dialog.dismiss()
                }
            materialAlertDialogBuilder.create().show()
        }else{
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),100)
        }
    }
}