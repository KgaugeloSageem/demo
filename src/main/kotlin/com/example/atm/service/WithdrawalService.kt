package com.example.atm.service

import com.azure.cosmos.CosmosException
import com.example.atm.models.DefaultResponse
import com.example.atm.models.VerifyResponse
import com.example.atm.models.WithdrawalModel
import com.example.atm.repository.WithdrawalRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class WithdrawalService(
    private val withdrawalRepository: WithdrawalRepository
){

    fun initiateWithdrawal(withdrawalModel: WithdrawalModel): ResponseEntity<DefaultResponse>{
        return try {
            withdrawalRepository.createWithdrawalItem(withdrawalModel)
            ResponseEntity(DefaultResponse(message = "Withdrawal Initiated"), HttpStatus.OK)
        } catch (e: CosmosException){
            ResponseEntity(DefaultResponse(message = e.shortMessage), HttpStatusCode.valueOf(e.statusCode))
        }
    }

    fun validateWithdrawal(withdrawalId: String): ResponseEntity<VerifyResponse>{
        val header = HttpHeaders()
        header.set("Content-Type", "application/json")
        header.set( "Access-Control-Allow-Origin", "*")
        header.set("Access-Control-Allow-Methods","GET")
        return try {
            val withdrawalObject = withdrawalRepository.getWithdrawalItem(withdrawalId)
            if (withdrawalObject?.id == withdrawalId){
                ResponseEntity.ok()
                    .headers(header)
                    .body(VerifyResponse(verified = true, message = "Withdrawal is verified", amount = withdrawalObject.amount))
            }else {
                ResponseEntity(VerifyResponse(verified = false, message = "Withdrawal is not verified", amount = ""), HttpStatus.NOT_ACCEPTABLE)
            }
        } catch (e: CosmosException){
            ResponseEntity(VerifyResponse(verified = false, message = e.shortMessage, amount = ""), HttpStatusCode.valueOf(e.statusCode))
        }
    }
}