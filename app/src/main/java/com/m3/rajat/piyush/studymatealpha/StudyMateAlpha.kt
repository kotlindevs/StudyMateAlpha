package com.m3.rajat.piyush.studymatealpha

import android.app.Application
import com.google.android.material.color.DynamicColors

class StudyMateAlpha : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}