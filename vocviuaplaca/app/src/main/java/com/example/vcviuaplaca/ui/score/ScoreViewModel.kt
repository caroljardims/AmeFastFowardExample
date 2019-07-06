package com.example.vcviuaplaca.ui.score

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    val homeTeamGoals = MutableLiveData<Int>()
    var awayTeamGoals = MutableLiveData<Int>()

    init {
        initGame()
    }

    fun initGame() {
        homeTeamGoals.value = 0
        awayTeamGoals.value = 0
    }

    fun setHomeGoal() {
        homeTeamGoals.value = homeTeamGoals.value?.plus(1)
    }

    fun setAwayGoal() {
        awayTeamGoals.value = awayTeamGoals.value?.plus(1)
    }
}