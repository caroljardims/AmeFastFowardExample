package com.example.vcviuaplaca.ui.score

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vcviuaplaca.R
import com.example.vcviuaplaca.ui.utils.Constants
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

lateinit var scoreViewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        scoreViewModel = ViewModelProviders.of(this).get(ScoreViewModel::class.java)

        eventNameLabel.text = intent.getStringExtra(Constants.EVENT_NAME)
        homeTeamLabel.text = intent.getStringExtra(Constants.HOME_TEAM_NAME)
        awayTeamLabel.text = intent.getStringExtra(Constants.AWAY_TEAM_NAME)

        homeGoalLabel.text = scoreViewModel.homeTeamGoals.value.toString()
        awayGoalLabel.text = scoreViewModel.awayTeamGoals.value.toString()

        homeGoalButton.setOnClickListener {
            scoreViewModel.setHomeGoal()
        }

        awayGoalButton.setOnClickListener {
            scoreViewModel.setAwayGoal()
        }

        restartMatchButton.setOnClickListener {
            scoreViewModel.initGame()
        }

        scoreViewModel.homeTeamGoals.observe(this, Observer { homeGoalLabel.text = it.toString() })
        scoreViewModel.awayTeamGoals.observe(this, Observer { awayGoalLabel.text= it.toString() })

        finishMatchButton.setOnClickListener { finish() }
    }
}
