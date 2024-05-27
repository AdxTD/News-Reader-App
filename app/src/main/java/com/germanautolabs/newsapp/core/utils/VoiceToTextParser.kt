package com.germanautolabs.newsapp.core.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VoiceToTextParser(
    private val context: Context
): RecognitionListener {

    private val _state = MutableStateFlow(VoiceToTextParserState())
    val state = _state.asStateFlow()

    val recognizer = SpeechRecognizer.createSpeechRecognizer(context)


    fun startListening(langCode: String = "en"){
        _state.update { VoiceToTextParserState() }

        if(!SpeechRecognizer.isRecognitionAvailable(context)){
            _state.update {
                it.copy( error = "Recognition is not available")
            }

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, langCode)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Listening...")
            }

            recognizer.setRecognitionListener(this)
            recognizer.startListening(intent)

            _state.update { it.copy(isSpeaking = true) }

        }
    }

    fun stopListening(){
        _state.update {
            it.copy(
                isSpeaking = false,
            )
        }
        recognizer.stopListening()
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        _state.update {
            it.copy(
                error = null,
            )
        }
    }

    override fun onBeginningOfSpeech() = Unit

    override fun onRmsChanged(p0: Float) = Unit

    override fun onBufferReceived(p0: ByteArray?) = Unit

    override fun onEndOfSpeech() {
        _state.update {
            it.copy(
                isSpeaking = false
            )
        }
    }

    override fun onError(p0: Int) {
        if (p0 == SpeechRecognizer.ERROR_CLIENT){
            return
        }
        _state.update {
            it.copy(
                error = "Error: $p0"
            )
        }
    }

    override fun onResults(results: Bundle?) {
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)?.let { result ->
                _state.update {
                    it.copy(
                        spokenText = result
                    )
                }
            }
    }

    override fun onPartialResults(p0: Bundle?) = Unit

    override fun onEvent(p0: Int, p1: Bundle?) = Unit
}

data class VoiceToTextParserState(
    val spokenText: String = "",
    val isSpeaking: Boolean = false,
    val error: String? = null
)