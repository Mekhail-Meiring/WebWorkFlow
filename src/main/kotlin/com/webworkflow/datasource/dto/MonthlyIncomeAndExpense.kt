package com.webworkflow.datasource.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Data class to represent the monthly income and expense of a user
 *
 * @property month The month of the income and expense
 * @property income The income of the user in that month
 * @property expense The expense of the user in that month
 */
data class MonthlyIncomeAndExpense(
    @JsonProperty("month")
    val month: String,
    @JsonProperty("income")
    val income: String,
    @JsonProperty("expense")
    val expense: String
)
