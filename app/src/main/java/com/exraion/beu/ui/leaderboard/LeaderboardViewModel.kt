package com.exraion.beu.ui.leaderboard

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.exraion.beu.data.repository.leaderboard.LeaderboardRepository
import com.exraion.beu.data.util.Resource
import com.exraion.beu.model.Leaderboard
import com.exraion.beu.util.UIState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val repository: LeaderboardRepository
) : ViewModel() {

    private val _leaderboard = MutableStateFlow<List<Leaderboard>>(emptyList())
    val leaderboard = _leaderboard.asStateFlow()
    
    private val _myRank = MutableLiveData<Leaderboard>()
    val myRank = _myRank.asFlow()
    
    private val _uiState = MutableStateFlow(UIState.IDLE)
    val uiState = _uiState.asStateFlow()
    
    var message = ""
    
    init {
        viewModelScope.launch {
            repository.fetchLeaderboard().collect {
                when(it) {
                    is Resource.Empty -> _uiState.value = UIState.EMPTY
                    is Resource.Error -> {
                        _uiState.value = UIState.ERROR
                        message = it.message ?: ""
                    }
                    is Resource.Loading -> _uiState.value = UIState.LOADING
                    is Resource.Success -> {
                        _uiState.value = UIState.SUCCESS
                        _leaderboard.value = it.data ?: listOf()
                    }
                }
            }
        }
        
        viewModelScope.launch {
            repository.fetchMyRank().collect {
                when(it) {
                    is Resource.Empty ->{ }
                    is Resource.Error -> { }
                    is Resource.Loading -> { }
                    is Resource.Success -> _myRank.value = it.data!!
                }
            }
        }
    }
    
}