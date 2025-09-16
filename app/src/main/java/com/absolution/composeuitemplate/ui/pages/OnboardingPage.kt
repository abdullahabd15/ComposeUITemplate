package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.absolution.composeuitemplate.ui.components.ImagePlaceholder
import kotlinx.coroutines.launch

@Composable
fun OnboardingPage(
    modifier: Modifier = Modifier
) {
    OnboardingUi(
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun OnboardingUi(
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { 3 }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(
                beyondViewportPageCount = 3,
                state = pagerState,
                userScrollEnabled = false,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f)
            ) { page ->
                val alpha =
                    if (pagerState.currentPage == page) 1f - pagerState.currentPageOffsetFraction else 0.5f
                ImagePlaceholder(
                    modifier = Modifier.fillMaxSize().graphicsLayer { this.alpha = alpha }
                )
            }
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                repeat(pagerState.pageCount) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(all = 2.dp)
                            .size(width = if (isSelected) 16.dp else 8.dp, height = 8.dp)
                            .clip(shape = CircleShape)
                            .background(color = if (isSelected) Color.Blue else Color.LightGray)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                "Create a prototype in just a few minutes.",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Enjoy this pre-made components and worry only about creating the best product ever.",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )
        }
        ElevatedButton(
            onClick = {
                coroutineScope.launch {
                    val canGoToNextPage = pagerState.currentPage + 1 != pagerState.pageCount
                    if (canGoToNextPage) {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    } else {
                        // navigate to next page
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().padding(all = 16.dp).padding(bottom = 16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
            )
        ) {
            Text("Next")
        }
    }
}

@Composable
@Preview
private fun OnboardingPreview() {
    OnboardingUi(
        modifier = Modifier.fillMaxSize()
    )
}