package com.absolution.composeuitemplate.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.absolution.composeuitemplate.ui.viewmodel.SubscriptionPlan
import com.absolution.composeuitemplate.ui.viewmodel.SubscriptionPlanOption
import com.absolution.composeuitemplate.ui.viewmodel.SubscriptionPlanUiState
import com.absolution.composeuitemplate.ui.viewmodel.SubscriptionPlansViewModel
import com.absolution.composeuitemplate.ui.viewmodel.subscriptionPlans
import org.koin.androidx.compose.koinViewModel

@Composable
fun SubscriptionPlansPage(
    modifier: Modifier = Modifier,
    viewModel: SubscriptionPlansViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SubscriptionPlansUi(
        uiState = uiState,
        onSubscriptionPlanClicked = viewModel::selectPlan,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun SubscriptionPlansUi(
    uiState: SubscriptionPlanUiState,
    onSubscriptionPlanClicked: (SubscriptionPlan) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.background(color = Color.White).padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)) {
            Text(
                "Choose your \nsubscription plan",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text("And get a 7-day free trial", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                uiState.subscriptionPlans.forEach { plan ->
                    ItemSubscriptionOption(
                        isSelected = plan.subscriptionPlanOption == uiState.selectedPlan?.subscriptionPlanOption,
                        option = plan.subscriptionPlanOption,
                        discountInPercent = plan.discountInPercent,
                        price = plan.price,
                        modifier = Modifier.padding(vertical = 8.dp).clickable {
                            onSubscriptionPlanClicked(plan)
                        }
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
            uiState.selectedPlan?.benefits?.let { benefits ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.Blue.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(all = 16.dp),
                ) {
                    Text(
                        "You'll get:",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        benefits.forEach { benefit ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.Blue
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(benefit, style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                }
            }
        }
        ElevatedButton(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
            ),
        ) {
            Text("Subscribe")
        }
    }
}

@Composable
private fun ItemSubscriptionOption(
    isSelected: Boolean,
    option: SubscriptionPlanOption?,
    discountInPercent: Int?,
    price: Double?,
    modifier: Modifier = Modifier
) {
    val border = if (isSelected) {
        Modifier.background(
            color = Color.Blue.copy(alpha = 0.1f),
            shape = RoundedCornerShape(16.dp)
        )
    } else {
        Modifier.border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(16.dp)
        )
    }
    Box(modifier = modifier.then(border)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .border(
                            width = 4.dp,
                            color = Color.Blue,
                            shape = CircleShape
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                )
            }
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.7f),
            ) {
                Text(
                    option?.title.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (discountInPercent != null) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "-${discountInPercent}% discount",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
            }
            Spacer(Modifier.width(8.dp))
            Column {
                Text(
                    "$${price ?: 0}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    option?.period.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

@Composable
@Preview
private fun SubscriptionPlansPreview() {
    SubscriptionPlansUi(
        uiState = SubscriptionPlanUiState(
            selectedPlan = subscriptionPlans.first(),
            subscriptionPlans = subscriptionPlans
        ),
        onSubscriptionPlanClicked = {},
        modifier = Modifier.fillMaxSize()
    )
}