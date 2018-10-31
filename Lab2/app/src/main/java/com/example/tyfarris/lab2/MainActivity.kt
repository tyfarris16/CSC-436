package com.example.tyfarris.lab2

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                seekBarValue.text = seekBar?.progress.toString()

                when (p1)
                {
                     in 0 .. 33 -> status.setTextColor(Color.RED)
                     in 34 .. 66 -> status.setTextColor(Color.YELLOW)
                     else -> status.setTextColor(Color.GREEN)
                }
                
//                if (seekBar.progress <= 33){
//
//                    status.setTextColor(Color.RED)
//                }
//                else if (seekBar.progress in 33 .. 66) {
//
//                    status.setTextColor(Color.YELLOW)
//                }
//                else
//                {
//                    status.setTextColor(Color.GREEN)
//                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })

        editText.addTextChangedListener(object: TextWatcher {

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enteredValue.text = editText.text
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    fun onRadioButtonClicked(view: View){
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.choice1 -> if (checked) {
                    textView.text = "Choice 1 Selected"
                }

                R.id.choice2 -> if (checked) {
                    textView.text = "Choice 2 Selected"
                }

                R.id.choice3 -> if (checked) {
                    textView.text = "Choice 3 Selected"
                }
            }
        }
    }
}
