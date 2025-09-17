package com.absolution.composeuitemplate.ui.pages

import android.content.res.ColorStateList
import android.widget.RatingBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.absolution.composeuitemplate.ui.viewmodel.FeedbackUiState
import com.absolution.composeuitemplate.ui.viewmodel.FeedbackViewModel
import com.absolution.composeuitemplate.ui.viewmodel.SomethingLikes
import com.absolution.composeuitemplate.ui.viewmodel.SomethingToImproves
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeedbackPage(
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FeedbackViewModel = koinViewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    FeedbackUi(
        uiState = uiState.value,
        onBackClicked = onBackClicked,
        onRatingScoreChanged = viewModel::onRatingScoreChanged,
        onSomethingLikesChanged = viewModel::onSomethingLikesChanged,
        onSomethingToImprovesChanged = viewModel::onSomethingToImprovesChanged,
        onCommentChanged = viewModel::onCommentChanged,
        modifier = modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackUi(
    uiState: FeedbackUiState,
    onBackClicked: () -> Unit,
    onRatingScoreChanged: (Float) -> Unit,
    onSomethingLikesChanged: (SomethingLikes) -> Unit,
    onSomethingToImprovesChanged: (SomethingToImproves) -> Unit,
    onCommentChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Feedback")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBackIos,
                            contentDescription = null,
                            tint = Color.Blue
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Spacer(Modifier.height(24.dp))
            Text(
                "Your project is finished.",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Text("How would you rate the prototyping kit?")
            Spacer(Modifier.height(16.dp))
            RatingBar(
                rating = uiState.ratingScore,
                onRatingChanged = onRatingScoreChanged
            )
            Spacer(Modifier.height(24.dp))
            Text("What did you like about it?", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            FlowRow(modifier = Modifier.fillMaxWidth()) {
                SomethingLikes.entries.forEach { e ->
                    val isChecked = uiState.somethingLikes.find { it == e.name } != null
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { onSomethingLikesChanged(e) },
                        label = {
                            Text(e.title)
                        },
                        shape = RoundedCornerShape(50),
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (isChecked) Color.Blue else Color.Blue.copy(alpha = 0.08f),
                            labelColor = if (isChecked) Color.White else Color.Blue
                        ),
                        border = BorderStroke(0.dp, color = Color.Transparent)
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("What could be improved?", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            FlowRow(modifier = Modifier.fillMaxWidth()) {
                SomethingToImproves.entries.forEach { e ->
                    val isChecked = uiState.somethingToImproves.find { it == e.name } != null
                    SuggestionChip(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        onClick = { onSomethingToImprovesChanged(e) },
                        label = {
                            Text(e.title)
                        },
                        shape = RoundedCornerShape(50),
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = if (isChecked) Color.Blue else Color.Blue.copy(alpha = 0.08f),
                            labelColor = if (isChecked) Color.White else Color.Blue
                        ),
                        border = BorderStroke(0.dp, color = Color.Transparent),
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            Text("Anything else?", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.comment,
                onValueChange = onCommentChanged,
                placeholder = {
                    Text("Tell us everything.")
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                minLines = 5
            )
            Spacer(Modifier.height(36.dp))
            ElevatedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text("Submit", modifier = Modifier.padding(vertical = 8.dp))
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            RatingBar(context).apply {
                numStars = 5
                stepSize = 0.5f
                this.rating = rating
                progressTintList = ColorStateList.valueOf(Color.Blue.hashCode())
                setOnRatingBarChangeListener { _, value, _ ->
                    onRatingChanged(value)
                }
            }
        },
        update = {
            it.rating = rating
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun FeedbackPreview() {
    FeedbackUi(
        modifier = Modifier.fillMaxSize(),
        uiState = FeedbackUiState(),
        onBackClicked = {},
        onRatingScoreChanged = {},
        onSomethingLikesChanged = {},
        onSomethingToImprovesChanged = {},
        onCommentChanged = {}
    )
}