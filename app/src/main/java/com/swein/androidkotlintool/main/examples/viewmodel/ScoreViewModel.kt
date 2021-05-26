package com.swein.androidkotlintool.main.examples.viewmodel

import androidx.lifecycle.ViewModel

/**
 * 如果你需要 Application 上下文，则应该扩展 AndroidViewModel，它只是一个包含 Application 引用的 ViewModel。
 */
class ScoreViewModel: ViewModel() {

    // Tracks the score for Team A
    var scoreTeamA = 0

    // Tracks the score for Team B
    var scoreTeamB = 0
}