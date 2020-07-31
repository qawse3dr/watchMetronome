package com.larry.metronome

import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log

class Metronome(vibrator:Vibrator){

    private var bpm: Int = 80
    private var isRunning: Boolean = false
    /**connected to phone vibrator device*/
    private val vibrator: Vibrator = vibrator



    /**Starts the metronome's tick*/

    fun start() : Unit {
        if (vibrator.hasVibrator()) {
            isRunning = true
            if(vibrator.hasAmplitudeControl()){
                Log.e("ERROR","not Amplitude Control")
            }

            //Finds length and starts loop
            val length: Long = 60000/bpm.toLong()
            Log.d("Log","$length")
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0,100,length-100), intArrayOf(0,255,0),0))

        }
    }

    /**stops the metronomes tick*/
    fun stop() : Unit{
        if(vibrator.hasVibrator()){
            isRunning = false
            vibrator.cancel()
        }
    }

    /**returns current BPM
     * @return BPM
     * */
    fun getBPM() : Int{
        return bpm
    }

    /**Is the metronome running
     * @return is the metronome running
     * */
    fun isRunning() : Boolean{
        return isRunning
    }


    /**Sets new bpm
     * @param bpm new bpm for metronome
     */
    fun setBPM(bpm : Int) : Unit{

        require(bpm >= 0) {"BPM must be greater than 0"}
        this.bpm = bpm
    }

}