package com.example.vcviuaplaca.ui.game.event

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.example.vcviuaplaca.R
import com.example.vcviuaplaca.ui.utils.Constants
import kotlinx.android.synthetic.main.fragment_event.*

class EventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton.setOnClickListener {

            val intent = Intent(Constants.FILTER_EVENT_NAME)
            intent.putExtra(Constants.EVENT_NAME,inputTextBroadcast.text.toString())

            LocalBroadcastManager
                .getInstance(requireContext())
                .sendBroadcast(intent)
        }
    }
}
