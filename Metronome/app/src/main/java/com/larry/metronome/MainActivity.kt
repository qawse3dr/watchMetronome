package com.larry.metronome

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.support.wearable.activity.WearableActivity
import android.view.WindowManager
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : WearableActivity() {

    private var metronome : Metronome? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //Creates Metronome
        metronome = Metronome(getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)
        val startMetronome = findViewById<Button>(R.id.startMetronome)

        //starts or stops metronome
        startMetronome.setOnClickListener {
            if(metronome!!.isRunning()){
                metronome?.stop()
            } else {
                metronome?.start()
            }
        }

        //Decreases the bpm by 1
        val turnDown = findViewById<Button>(R.id.turnDown)
        turnDown.setOnClickListener{
            metronome?.setBPM(metronome!!.getBPM() -1)
            //if its running when a change takes place restart
            if(metronome!!.isRunning()){
                metronome?.stop()
                metronome?.start()
            }

            //updates the slider to current bpm
            updateBpmSlider()
        }

        //Increases the bpm by 1
        val turnUp = findViewById<Button>(R.id.turnUp)
        turnUp.setOnClickListener{
            metronome?.setBPM(metronome!!.getBPM() +1)
            //if its running when a change takes place restart
            if(metronome!!.isRunning()){
                metronome?.stop()
                metronome?.start()
            }

            //updates the slider to current bpm
            updateBpmSlider()
        }



        //bpm text is what displays the current bpm to the display
        val bpmText = findViewById<TextView>(R.id.bpm)
        val bpmSlider = findViewById<SeekBar>(R.id.bpmSlider)
        bpmSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                bpmText.text = "BPM: $i"
                metronome?.setBPM(i)

                //if its running when a change takes place restart
                if(metronome!!.isRunning()){
                    metronome?.stop()
                    metronome?.start()
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })


        // Enables Always-on
        setAmbientEnabled()
    }

    private fun updateBpmSlider() :  Unit{
        val bpmSlider = findViewById<SeekBar>(R.id.bpmSlider)

        //bpm text is what displays the current bpm to the display
        val bpmText = findViewById<TextView>(R.id.bpm)
        bpmSlider.progress = metronome!!.getBPM()
        bpmText.text = "BPM: ${metronome!!.getBPM()}"
    }
}
