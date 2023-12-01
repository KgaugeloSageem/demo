package com.example.atm.models

import com.fasterxml.jackson.annotation.JsonProperty

class WithdrawalModel(
    @JsonProperty("id") val id: String,
    @JsonProperty("amount") val amount: String,
    @JsonProperty("withdrawal") var withdrawal: String? = "withdrawal"
)