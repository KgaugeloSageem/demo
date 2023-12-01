package com.example.atm.repository

import com.azure.cosmos.models.CosmosItemRequestOptions
import com.azure.cosmos.models.PartitionKey
import com.example.atm.azure.AzureInitializer
import com.example.atm.models.WithdrawalModel
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.stereotype.Repository

interface WithdrawalRepository{

    fun createWithdrawalItem(withdrawalModel: WithdrawalModel): Int?

    fun getWithdrawalItem(withdrawalId: String): WithdrawalModel?
}

@Repository
class WithdrawalRepositoryImpl(
    private val azureInitializer: AzureInitializer
): WithdrawalRepository {

   private val withdrawal = "withdrawal"

    override fun createWithdrawalItem(withdrawalModel: WithdrawalModel): Int? {
        withdrawalModel.withdrawal = withdrawal
        val response = azureInitializer.withdrawalContainer?.createItem(
            withdrawalModel,
            PartitionKey(withdrawalModel.withdrawal),
            CosmosItemRequestOptions()
        )

        return response?.statusCode
    }

    override fun getWithdrawalItem(withdrawalId: String): WithdrawalModel? {
        val response = azureInitializer.withdrawalContainer?.readItem(
            withdrawalId,
            PartitionKey(withdrawal),
            WithdrawalModel::class.java
        )

        return response?.item
    }


}