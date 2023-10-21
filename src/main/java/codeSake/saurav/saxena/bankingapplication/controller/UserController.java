package codeSake.saurav.saxena.bankingapplication.controller;

import codeSake.saurav.saxena.bankingapplication.dto.CreditDebitRequest;
import codeSake.saurav.saxena.bankingapplication.dto.EnquiryRequest;
import codeSake.saurav.saxena.bankingapplication.dto.UserRequest;
import codeSake.saurav.saxena.bankingapplication.response.ApiResponse;
import codeSake.saurav.saxena.bankingapplication.response.BankApiResponse;
import codeSake.saurav.saxena.bankingapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/createAccount")
    public BankApiResponse createAccount(@RequestBody UserRequest userRequest){
        return userService.createAccount(userRequest);


    }

    @PostMapping("/balanceEnquiry")
    public BankApiResponse balanceEnquiry(@RequestBody EnquiryRequest enquiryRequest){
        return userService.balanceEnquiry(enquiryRequest);
    }

    @PostMapping("/getAccount")
    public ApiResponse<Object> getAccount(@RequestBody EnquiryRequest enquiryRequest){
        return userService.nameEnquiry(enquiryRequest);
    }

    @PostMapping("/creditAccount")
    public BankApiResponse creditAccount(@RequestBody CreditDebitRequest request){
        return userService.creditAccount(request);
    }

    @PostMapping("/debitAccount")
    public BankApiResponse debitAccount(@RequestBody CreditDebitRequest request){
        return userService.debitAccount(request);
    }



}
