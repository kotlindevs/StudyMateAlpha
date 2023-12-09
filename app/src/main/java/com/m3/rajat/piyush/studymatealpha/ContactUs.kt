package com.m3.rajat.piyush.studymatealpha
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.m3.rajat.piyush.studymatealpha.databinding.ActivityContactUsBinding

class ContactUs : AppCompatActivity() {
    private lateinit var binding : ActivityContactUsBinding
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var adminSession: AdminSession
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqLiteHelper = SQLiteHelper(this)
        adminSession = AdminSession(this)

        binding.nameContact.setText(adminSession.sharedPreferences.getString("name",""))
        binding.emailContact.setText(adminSession.sharedPreferences.getString("email",""))

        binding.contactUs.setOnClickListener{
            if(binding.nameContact.length() == 0){
                binding.nameContact.setError("Name Required")
            } else if(binding.emailContact.length()==0) {
                binding.emailContact.setError("Email Can't Be Empty")
            }else if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailContact.text.toString()).matches()){
                Toast.makeText(this,"Email Format Wrong !", Toast.LENGTH_SHORT).show()
            } else if(binding.desContact.length()==0){
                binding.desContact.setError("Please Fill What You Required")
            } else {

                val contact = AdminModel(cname = binding.nameContact.text.toString() , cemail = binding.emailContact.text.toString() , cdesc = binding.desContact.text.toString())
                val ok = sqLiteHelper.InsertContactUs(contact)
                if(ok!=null){
                    val builder = MaterialAlertDialogBuilder(this)
                    builder.setTitle("Thanks")
                    builder.setMessage("Your Request Successfully Received Thanks For Contacting Our Company. \n \n If You Had More Interest In Material Please Contact Us Without Any Hesitation. \n")
                    builder.setNegativeButton("Ok"){ dialog,_->
                        dialog.dismiss()
                    }
                    val alert = builder.create()
                    alert.show()
                    binding.desContact.text!!.clear()
                }else{
                    Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.notContactUs.setOnClickListener {
            startActivity(Intent(applicationContext,Admin_panel::class.java))
        }

        binding.topAppBar.setNavigationOnClickListener {
            startActivity(Intent(applicationContext, Admin_panel::class.java))
            finish()
        }

        onBackPressedDispatcher.addCallback {  }
    }
}