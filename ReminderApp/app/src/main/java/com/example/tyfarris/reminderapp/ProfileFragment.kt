package com.example.tyfarris.reminderapp

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.LocaleList
import android.speech.tts.TextToSpeech
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ProfileFragment : Fragment(){

    var tts:TextToSpeech? = null
    private lateinit var model: MyModelView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = activity.run {
            ViewModelProviders.of(activity!!).get(MyModelView::class.java)
        }

        //change the title of the bar
        activity?.title = "Profile"

//        Use to install voices
//        val installIntent = Intent()
//        installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
//        startActivity(installIntent)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val progressLevel = view.findViewById<ProgressBar>(R.id.progressBar)
        val quoteOfDayBtn = view.findViewById<Button>(R.id.quoteButton)
        var levelValue = view.findViewById<TextView>(R.id.textViewLevelValue)

        //set the info button to invisible
        activity?.toolbar_info_logo?.visibility = View.INVISIBLE

        if(model.progress > 100)
        {
            model.progress = model.progress - 100
            model.level = model.level + 1
        }

        progressLevel.progress = model.progress
        levelValue.text = model.level.toString()

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