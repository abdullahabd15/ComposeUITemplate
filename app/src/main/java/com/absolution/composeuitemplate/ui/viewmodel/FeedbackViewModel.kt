package com.absolution.composeuitemplate.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FeedbackViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FeedbackUiState())
    val uiState = _uiState.asStateFlow()

    fun onRatingScoreChanged(ratingScore: Float) {
        _uiState.update {
            it.copy(ratingScore = ratingScore)
        }
    }

    fun onSomethingLikesChanged(somethingLikes: SomethingLikes) {
        _uiState.update {
            val isExist = it.somethingLikes.find { x -> x == somethingLikes.name } != null
            val somethingLikesList = if (isExist) {
                it.somethingLikes.filter { x -> x != somethingLikes.name }
            } else {
                it.somethingLikes + somethingLikes.name
            }
            it.copy(somethingLikes = somethingLikesList)
        }
    }

    fun onSomethingToImprovesChanged(somethingToImproves: SomethingToImproves) {
        _uiState.update {
            val isExist = it.somethingToImproves.find { x -> x == somethingToImproves.name } != null
            val somethingToImprovesList = if (isExist) {
                it.somethingToImproves.filter { x -> x != somethingToImproves.name }
            } else {
                it.somethingToImproves + somethingToImproves.name
            }
            it.copy(somethingToImproves = somethingToImprovesList)
        }
    }

    fun onCommentChanged(comment: String) {
        _uiState.update {
            it.copy(comment = comment)
        }
    }
}

@Immutable
data class FeedbackUiState(
    val ratingScore: Float = 0f,
    val somethingLikes: List<String> = emptyList(),
    val somethingToImproves: List<String> = emptyList(),
    val comment: String = "",
)

enum class SomethingLikes(val title: String) {
    EASY_TO_USE("EASY TO USE"),
    COMPLETE("COMPLETE"),
    HELPFUL("HELPFUL"),
    CONVENIENT("CONVENIENT"),
    LOOKS_GOOD("LOOKS GOOD"),
}

enum class SomethingToImproves(val title: String) {
    COULD_HAVE_MORE_COMPONENTS("COULD HAVE MORE COMPONENTS"),
    COMPLEX("COMPLEX"),
    NOT_INTERACTIVE("NOT INTERACTIVE"),
    ONLY_ENGLISH("ONLY ENGLISH"),
}