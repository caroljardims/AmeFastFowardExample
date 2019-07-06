package com.example.vcviuaplaca.ui.game

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.vcviuaplaca.R
import com.example.vcviuaplaca.ui.game.awayteam.AwayTeamFragment
import com.example.vcviuaplaca.ui.game.event.EventFragment
import com.example.vcviuaplaca.ui.game.hometeam.HomeTeamFragment
import com.example.vcviuaplaca.ui.score.ScoreActivity
import com.example.vcviuaplaca.ui.utils.Constants
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    lateinit var gameViewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        back.setOnClickListener { onBackPressed() }

        if (savedInstanceState == null)
            showEventFragment()
        registerBroadcastReceiver()
    }

    private fun showEventFragment() {
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.containerGame, EventFragment())
        ft.commit()
    }

    private fun registerBroadcastReceiver() {
        val intentFilter = IntentFilter(Constants.FILTER_EVENT_NAME)
        intentFilter.addAction(Constants.FILTER_HOME_TEAM_NAME)
        intentFilter.addAction(Constants.FILTER_AWAY_TEAM_NAME)

        LocalBroadcastManager.getInstance(this)
            .registerReceiver(gameReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(gameReceiver)
    }

    private val gameReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.hasExtra(Constants.EVENT_NAME)) {
                gameViewModel.eventName = intent.getStringExtra(Constants.EVENT_NAME)
                next(HomeTeamFragment())
            }
            if (intent.hasExtra(Constants.HOME_TEAM_NAME)) {
                gameViewModel.homeTeamName = intent.getStringExtra(Constants.HOME_TEAM_NAME)
                next(AwayTeamFragment())
            }
            if (intent.hasExtra(Constants.AWAY_TEAM_NAME)) {
                gameViewModel.awayTeamName = intent.getStringExtra(Constants.AWAY_TEAM_NAME)
                showScore()
            }
        }
    }

    private fun showScore() {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra(Constants.EVENT_NAME, gameViewModel.eventName)
        intent.putExtra(Constants.HOME_TEAM_NAME, gameViewModel.homeTeamName)
        intent.putExtra(Constants.AWAY_TEAM_NAME, gameViewModel.awayTeamName)
        startActivity(intent)
        finish()
    }

    private fun next(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.containerGame, fragment)
        ft.addToBackStack(null)
        ft.commit()
    }
}
