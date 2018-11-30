package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import java.util.*

class ProfileFragment : Fragment(){

    var tts:TextToSpeech? = null
    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val progressLevel = view.findViewById<ProgressBar>(R.id.progressBar)
        val quoteOfDayBtn = view.findViewById<Button>(R.id.quoteButton)

        progressLevel.progress = 50

        tts = TextToSpeech(activity?.applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                tts!!.setLanguage(Locale.UK)
            }
        })

        quoteOfDayBtn!!.setOnClickListener{
            tts!!.speak("Just Keep Swimming", TextToSpeech.QUEUE_FLUSH, null, null)
        }
        return view
    }
}