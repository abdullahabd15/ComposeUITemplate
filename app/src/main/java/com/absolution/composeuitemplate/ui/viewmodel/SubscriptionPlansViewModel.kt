package com.absolution.composeuitemplate.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SubscriptionPlansViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionPlanUiState())
    val uiState: StateFlow<SubscriptionPlanUiState> = _uiState.asStateFlow()

    init {
        fetchSubscriptionPlans()
    }

    fun selectPlan(plan: SubscriptionPlan) {
        _uiState.update {
            it.copy(selectedPlan = plan)
        }
    }

    fun fetchSubscriptionPlans() {
        _uiState.update {
            it.copy(subscriptionPlans = subscriptionPlans)
        }
    }
}

data class SubscriptionPlanUiState(
    val selectedPlan: SubscriptionPlan? = null,
    val subscriptionPlans: List<SubscriptionPlan> = emptyList(),
)

data class SubscriptionPlan(
    val subscriptionPlanOption: SubscriptionPlanOption? = null,
    val discountInPercent: Int? = null,
    val price: Double? = null,
    val benefits: List<String> = emptyList(),
)

enum class SubscriptionPlanOption(val title: String, val period: String) {
    YEARLY("Yearly", "every year"),
    MONTHLY("Monthly", "every month"),
    WEEKLY("Weekly", "every week")
}

val subscriptionPlans = listOf(
    SubscriptionPlan(
        subscriptionPlanOption = SubscriptionPlanOption.YEARLY,
        discountInPercent = 66,
        price = 94.99,
        benefits = listOf(
            "Unlimited access to all content",
            "200GB storage",
            "sync all your devices",
            "24 hours support",
        )
    ),
    SubscriptionPlan(
        subscriptionPlanOption = SubscriptionPlanOption.MONTHLY,
        discountInPercent = 53,
        price = 10.90,
        benefits = listOf(
            "Unlimited access to all content",
            "100GB storage",
            "sync all your devices",
            "24 hours support",
        )
    ),
    SubscriptionPlan(
        subscriptionPlanOption = SubscriptionPlanOption.WEEKLY,
        discountInPercent = null,
        price = 5.90,
        benefits = listOf(
            "Unlimited access to all content",
            "20GB storage",
            "24 hours support",
        )
    ),
)