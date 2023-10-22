package codeSake.saurav.saxena.bankingapplication.controller;

import codeSake.saurav.saxena.bankingapplication.dto.CreditDebitRequest;
import codeSake.saurav.saxena.bankingapplication.dto.EnquiryRequest;
import codeSake.saurav.saxena.bankingapplication.dto.TransferRequest;
import codeSake.saurav.saxena.bankingapplication.dto.UserRequest;
import codeSake.saurav.saxena.bankingapplication.response.ApiResponse;
import codeSake.saurav.saxena.bankingapplication.response.BankApiResponse;
import codeSake.saurav.saxena.bankingapplication.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/codeSake/secure-bank/user")
@Tag(name = "CodeSake Banking Application Controller.")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(
            summary = "Register User",
            description = "New User Registration Api."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "201 Account Created SuccessFully."
    )
    @PostMapping("/createAccount")
    public BankApiResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);


    }

    @Operation(
            summary = "Balance Enquiry",
            description = "Checking Available Balance."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "200 Success."
    )
    @PostMapping("/balanceEnquiry")
    public BankApiResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }


    @Operation(
            summary = "Get Account Info",
            description = "Account Detials."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "200 Success."
    )
    @PostMapping("/getAccount")
    public ApiResponse<Object> getAccount(@RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry(enquiryRequest);
    }


    @Operation(
            summary = "Credit To Account",
            description = "Add amount to bank account."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "200 Success."
    )
    @PostMapping("/creditAccount")
    public BankApiResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }


    @Operation(
            summary = "Debit To Account",
            description = "Debit From bank account."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "200 Success."
    )
    @PostMapping("/debitAccount")
    public BankApiResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }


    @Operation(
            summary = "Transfer",
            description = "Transfer to other account."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "200 Success."
    )
    @PostMapping("/transferToOtherAccount")
    public BankApiResponse transferToOtherAccount(@RequestBody TransferRequest request){
        return userService.transferToOtherAccount(request);
    }



}
