package com.terrencealuda.sound_test_hci

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var iv_mic: Button
    private lateinit var tv_Speech_to_text: TextView
    private lateinit var textToSpeech:TextToSpeech
    private lateinit var rootView: ConstraintLayout
    private lateinit var speechToText:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        iv_mic = findViewById(R.id.btnMic)
        tv_Speech_to_text = findViewById(R.id.tv_speech_to_text)
        rootView = findViewById(R.id.constraintView)
        textToSpeech = TextToSpeech(
            applicationContext
        ) { i ->
            // if No error is found then only it will run
            if (i != TextToSpeech.ERROR) {
                // To Choose language of speech
                textToSpeech.setLanguage(Locale.UK)
            }
        }

        iv_mic.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text")
            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        this@MainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                val result = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS
                )
                //tv_Speech_to_text.text = "Here is the ${Objects.requireNonNull(result)!![0]} screen"
                if(Objects.requireNonNull(result)!![0] == "red"){
                     speechToText = "Here is the red screen"
                    //textToSpeech.speak(speechToText),TextToSpeech.QUEUE_FLUSH,null)

                    rootView.setBackgroundResource(R.color.red)
                }else if(Objects.requireNonNull(result)!![0] == "blue") {
                    speechToText = "Here is the blue screen"
                    //textToSpeech.speak(speechToText),TextToSpeech.QUEUE_FLUSH,null)

                    rootView.setBackgroundResource(R.color.blue)
                }else{
                    speechToText = "Sorry. Kindly say red or blue"
                }
                textToSpeech.speak(speechToText,TextToSpeech.QUEUE_FLUSH,null)
            }
        }
    }

    companion object{
        private val REQUEST_CODE_SPEECH_INPUT = 1
    }
}