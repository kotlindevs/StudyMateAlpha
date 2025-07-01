package com.m3.rajat.piyush.studymatealpha.admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.m3.rajat.piyush.studymatealpha.MainActivity

class AdminSession(var context: Context) {

    var sharedPreferences:SharedPreferences
    private var editor : SharedPreferences.Editor
    private var modePrivate = 0

    init {
        this.sharedPreferences = context.getSharedPreferences("Admin",modePrivate)
        this.editor =sharedPreferences.edit()
    }

    fun adminLogin(email : String,name : String,id : Int){
        editor.putInt("id",id)
        editor.putBoolean("login",true)
        editor.putString("email",email)
        editor.putString("name",name)
        editor.commit()
    }
    fun login() : Boolean{
        return sharedPreferences.getBoolean("login",false)
    }

    fun adminLogout(){
        editor.clear()
        editor.commit()
        val i : Intent = Intent(context, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
}