package com.m3.rajat.piyush.studymatealpha.faculty

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.m3.rajat.piyush.studymatealpha.Faculty_panel
import com.m3.rajat.piyush.studymatealpha.MainActivity

class FacultySession(var context: Context) {
    var sharedPreferences: SharedPreferences
    var editor : SharedPreferences.Editor
    var modePrivate = 0

    init {
        this.sharedPreferences = context.getSharedPreferences("Faculty",modePrivate)
        this.editor =sharedPreferences.edit()
    }

    fun facultyLogin(id : Int){
        editor.putInt("id",id)
        editor.putBoolean("login",true)
        editor.commit()
    }
    fun login() : Boolean{
        return sharedPreferences.getBoolean("login",false)
    }

    fun isLogin(){
        if(!this.login()){
            val i: Intent = Intent(context, Faculty_panel::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }

    fun facultyLogout(){
        editor.clear()
        editor.commit()
        val i : Intent = Intent(context, MainActivity::class.java)
            .setAction(Intent.ACTION_VIEW)
            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    }
}