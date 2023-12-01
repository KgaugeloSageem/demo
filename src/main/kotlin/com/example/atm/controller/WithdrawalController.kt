package com.example.atm.controller

import com.example.atm.models.DefaultResponse
import com.example.atm.models.VerifyResponse
import com.example.atm.models.WithdrawalModel
import com.example.atm.service.WithdrawalService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/withdrawal")
class WithdrawalController(
    private val withdrawalService: WithdrawalService
) {

    @PostMapping("/initiate")
    fun initiateWithdrawal(@RequestBody withdrawalModel: WithdrawalModel): ResponseEntity<DefaultResponse>{
       return withdrawalService.initiateWithdrawal(withdrawalModel)
    }

    @GetMapping("/validate/{withdrawalId}")
    fun validateWithdrawal(@PathVariable withdrawalId: String): ResponseEntity<VerifyResponse>{
        return withdrawalService.validateWithdrawal(withdrawalId)
    }
}