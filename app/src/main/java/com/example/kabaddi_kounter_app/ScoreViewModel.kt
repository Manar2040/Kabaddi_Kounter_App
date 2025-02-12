package com.example.kabaddi_kounter_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    private val _scoreA = MutableLiveData(0)
    val scoreA: LiveData<Int> get() = _scoreA

    private val _scoreB = MutableLiveData(0)
    val scoreB: LiveData<Int> get() = _scoreB

    private val _history = MutableLiveData<List<String>>(emptyList())
    val history: LiveData<List<String>> get() = _history

    private val _winnerText = MutableLiveData<String>("")
    val winnerText: LiveData<String> get() = _winnerText

    fun incrementScore(isTeamA: Boolean, points: Int) {
        if (isTeamA) {
            _scoreA.value = _scoreA.value!! + points
            checkWinner(_scoreA.value!!, _scoreB.value!!)
        } else {
            _scoreB.value = _scoreB.value!! + points
            checkWinner(_scoreA.value!!, _scoreB.value!!)
        }
        addHistory(isTeamA, points)
    }

    private fun checkWinner(scoreA: Int, scoreB: Int) {
        when {
            scoreA >= 15 -> _winnerText.value = "Team A WIN!"
            scoreB >= 15 -> _winnerText.value = "Team B WIN!"
            else -> _winnerText.value = ""
        }
    }

    private fun addHistory(isTeamA: Boolean, points: Int) {
        val newEntry = if (isTeamA) "Team A +$points" else "Team B +$points"
        _history.value = _history.value!! + newEntry
    }

    fun resetScores() {
        _scoreA.value = 0
        _scoreB.value = 0
        _history.value = emptyList()
        _winnerText.value = ""
    }
}