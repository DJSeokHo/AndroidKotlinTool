package com.swein.androidkotlintool.main.examples.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ScoreAndroidViewModel(application: Application): AndroidViewModel(application) {

    val appDelegate = application

    // Tracks the score for Team A
    var scoreTeamA = 0

    // Tracks the score for Team B
    var scoreTeamB = 0
}