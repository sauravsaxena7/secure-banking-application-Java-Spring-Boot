package codeSake.saurav.saxena.bankingapplication.service;

import codeSake.saurav.saxena.bankingapplication.dto.CreditDebitRequest;
import codeSake.saurav.saxena.bankingapplication.dto.EnquiryRequest;
import codeSake.saurav.saxena.bankingapplication.dto.UserRequest;
import codeSake.saurav.saxena.bankingapplication.response.ApiResponse;
import codeSake.saurav.saxena.bankingapplication.response.BankApiResponse;

public interface UserService {
    BankApiResponse createAccount(UserRequest userRequest);
    BankApiResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    ApiResponse<Object> nameEnquiry(EnquiryRequest enquiryRequest);

    BankApiResponse debitAccount(CreditDebitRequest request);
    BankApiResponse creditAccount(CreditDebitRequest request);
}
