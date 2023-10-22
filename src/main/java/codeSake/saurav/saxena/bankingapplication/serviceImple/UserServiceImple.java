package codeSake.saurav.saxena.bankingapplication.serviceImple;

import codeSake.saurav.saxena.bankingapplication.POJO.User;
import codeSake.saurav.saxena.bankingapplication.constants.BankConstant;
import codeSake.saurav.saxena.bankingapplication.dao.UserDao;
import codeSake.saurav.saxena.bankingapplication.dto.CreditDebitRequest;
import codeSake.saurav.saxena.bankingapplication.dto.EnquiryRequest;
import codeSake.saurav.saxena.bankingapplication.dto.TransferRequest;
import codeSake.saurav.saxena.bankingapplication.dto.UserRequest;
import codeSake.saurav.saxena.bankingapplication.response.AccountInfo;
import codeSake.saurav.saxena.bankingapplication.response.ApiResponse;
import codeSake.saurav.saxena.bankingapplication.response.BankApiResponse;
import codeSake.saurav.saxena.bankingapplication.service.EmailService;
import codeSake.saurav.saxena.bankingapplication.service.UserService;
import codeSake.saurav.saxena.bankingapplication.utils.AccountUtils;
import codeSake.saurav.saxena.bankingapplication.utils.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;


@Service
public class UserServiceImple implements UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    EmailService emailService;

    @Override
    public BankApiResponse createAccount(UserRequest userRequest) {
        /**
         * Creating New Account - saving a new user in db
         * Check If User Had Alreaddy An Account
         */

        if(userDao.existsByEmail(userRequest.getEmail())){
            return BankApiResponse.builder()
                    .responseCode(BankConstant.ACCOUNT_EXISTS_CODE)
                    .responseMessage(BankConstant.ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(null)
                    .build();
        }

        User newUser = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .gender(userRequest.getGender())
                .address(userRequest.getAddress())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .email(userRequest.getEmail())
                .phoneNumber(userRequest.getPhoneNumber())
                .status("ACTIVE")
                .alternatePhoneNumber(userRequest.getAlternatePhoneNumber())
                .build();
        User saveUser = userDao.save(newUser);

        //Send Email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(saveUser.getEmail())
                .messageBody("Congratulations! Your Account Has Been Successfully Created "+"\n"+
                        "Acoount Name: "+saveUser.getFirstName()+" "+saveUser.getLastName()+"\n"+
                        "AccountNumber "+saveUser.getAccountNumber())
                .subject("ACCOUNT CREATION")
                .build();
        emailService.sendEmailAlert(emailDetails);
        return BankApiResponse.builder()
                .responseCode(BankConstant.ACCOUNT_CREATION_SUCCESS_CODE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(saveUser.getAccountBalance().toString())
                        .accountName(saveUser.getFirstName()+" "+saveUser.getLastName())
                        .accountNumber(saveUser.getAccountNumber())
                        .build())
                .responseMessage(BankConstant.ACCOUNT_CREATION_SUCCESS_MESSAGE)
                .build();
    }

    @Override
    public BankApiResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
        //check if the provided account number exists in the db
        boolean isAccountExists = userDao.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExists){
            return  BankApiResponse.builder()
                    .responseMessage(BankConstant.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(BankConstant.ACCOUNT_NOT_EXISTS_CODE)
                    .accountInfo(null)
                    .build();
        }

        User foundUser = userDao.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankApiResponse.builder()
                .accountInfo(AccountInfo.builder()
                        .accountBalance(foundUser.getAccountBalance().toString())
                        .accountNumber(foundUser.getAccountNumber())
                        .accountNumber(foundUser.getAccountNumber() )
                        .build())
                .responseCode(BankConstant.ACCOUNT_FOUND_CODE)
                .responseMessage(BankConstant.ACCOUNT_FOUND_MESSAGE)
                .build();
    }

    @Override
    public ApiResponse<Object> nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean isAccountExists = userDao.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!isAccountExists){
            return ApiResponse.builder()
                    .data(null)
                    .responseCode(BankConstant.ACCOUNT_NOT_EXISTS_CODE)
                    .responseMessage(BankConstant.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .build();
        }
        User fondUser = userDao.findByAccountNumber(enquiryRequest.getAccountNumber());

        return ApiResponse.builder()
                .data(fondUser)
                .responseCode(BankConstant.ACCOUNT_FOUND_CODE)
                .responseMessage(BankConstant.ACCOUNT_FOUND_MESSAGE)
                .build();

    }

    @Override
    public BankApiResponse debitAccount(CreditDebitRequest request) {
        //check if the provided account number exists in the db
        boolean isAccountExists = userDao.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExists){
            return  BankApiResponse.builder()
                    .responseMessage(BankConstant.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(BankConstant.ACCOUNT_NOT_EXISTS_CODE)
                    .accountInfo(null)
                    .build();
        }
        User userToDebit = userDao.findByAccountNumber(request.getAccountNumber());
        BigInteger availableBalance=userToDebit.getAccountBalance().toBigInteger();
        BigInteger debitAmount=request.getAmount().toBigInteger();

        if(availableBalance.intValue()<debitAmount.intValue()){
            return BankApiResponse.builder()
                    .responseCode(BankConstant.ACCOUNT_DEBITED_FAILED_CODE)
                    .responseMessage(BankConstant.ACCOUNT_DEBITED_FAILED_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
        User saveDebitedUser = userDao.save(userToDebit);

        return BankApiResponse.builder()
                .responseMessage(BankConstant.ACCOUNT_DEBITED_SUCCESS_MESSAGE+saveDebitedUser.getAccountBalance().toString())
                .responseCode(BankConstant.ACCOUNT_CREDITED_SUCCESS_CODE)

                .accountInfo(AccountInfo.builder()
                        .accountName(saveDebitedUser.getFirstName()+" "+saveDebitedUser.getLastName()+" "+saveDebitedUser.getOtherName())
                        .accountBalance(saveDebitedUser.getAccountBalance().toString())
                        .accountNumber(saveDebitedUser.getAccountNumber())
                        .build())
                .build();

    }

    @Override
    public BankApiResponse creditAccount(CreditDebitRequest request) {

        //check if the provided account number exists in the db
        boolean isAccountExists = userDao.existsByAccountNumber(request.getAccountNumber());
        if(!isAccountExists){
            return  BankApiResponse.builder()
                    .responseMessage(BankConstant.ACCOUNT_NOT_EXISTS_MESSAGE)
                    .responseCode(BankConstant.ACCOUNT_NOT_EXISTS_CODE)
                    .accountInfo(null)
                    .build();
        }
        User userToCredit = userDao.findByAccountNumber(request.getAccountNumber());
        userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
        User saveCreditedUser = userDao.save(userToCredit);

        return BankApiResponse.builder()
                .responseMessage(BankConstant.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                .responseCode(BankConstant.ACCOUNT_CREDITED_SUCCESS_CODE)

                .accountInfo(AccountInfo.builder()
                        .accountName(saveCreditedUser.getFirstName()+" "+saveCreditedUser.getLastName()+" "+saveCreditedUser.getOtherName())
                        .accountBalance(saveCreditedUser.getAccountBalance().toString())
                        .accountNumber(saveCreditedUser.getAccountNumber())
                        .build())
                .build();

    }

    @Override
    public BankApiResponse transferToOtherAccount(TransferRequest request) {

        //get the account to debit
        //check debiting account have sufficient balance
        //debit the account
        //get the account to credit
        //credit the account

        boolean isSourceAccountExist = userDao.existsByAccountNumber(request.getSourceAccountNumber());
        boolean isDestinationAccountExist = userDao.existsByAccountNumber(request.getDestinationAccountNumber());

        if(!isSourceAccountExist){
            return  BankApiResponse.builder()
                    .responseMessage(BankConstant.TRANSFER_SOURCE_ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(BankConstant.TRANSFER_SOURCE_ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }

        if(!isDestinationAccountExist){
            return  BankApiResponse.builder()
                    .responseMessage(BankConstant.TRANSFER_DESTINATION_ACCOUNT_NOT_EXIST_MESSAGE)
                    .responseCode(BankConstant.TRANSFER_DESTINATION_ACCOUNT_NOT_EXIST_CODE)
                    .accountInfo(null)
                    .build();
        }


        User sourceUser = userDao.findByAccountNumber(request.getSourceAccountNumber());
        if(request.getAmount().compareTo(sourceUser.getAccountBalance())<0){

            return BankApiResponse.builder()
                    .responseMessage(BankConstant.ACCOUNT_DEBITED_FAILED_MESSAGE)
                    .accountInfo(null)
                    .responseCode(BankConstant.ACCOUNT_DEBITED_FAILED_CODE)
                    .build();
        }

        sourceUser.setAccountBalance(sourceUser.getAccountBalance().subtract(request.getAmount()));
        userDao.save(sourceUser);

        //Send Email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(sourceUser.getEmail())
                .messageBody("Your Account is Debited With Amount "+request.getAmount()+"\n"+
                        "Aval Balance: "+sourceUser.getAccountBalance()+"\n"+
                        "AccountNumber "+sourceUser.getAccountNumber())
                .subject("ACCOUNT DEBIT")
                .build();
        emailService.sendEmailAlert(emailDetails);

        User destinationUser = userDao.findByAccountNumber(request.getDestinationAccountNumber());
        destinationUser.setAccountBalance(destinationUser.getAccountBalance().add(request.getAmount()));
        userDao.save(destinationUser);


        emailDetails = EmailDetails.builder()
                .recipient(destinationUser.getEmail())
                .messageBody("Your Account is Credited With Amount "+request.getAmount()+"\n"+
                        "Current Balance: "+destinationUser.getAccountBalance()+"\n"+
                        "AccountNumber "+destinationUser.getAccountNumber())
                .subject("ACCOUNT DEBIT")
                .build();
        emailService.sendEmailAlert(emailDetails);

        return  BankApiResponse.builder()
                .responseCode(BankConstant.TRANSFER_SUCCESS_CODE)
                .responseMessage(BankConstant.TRANSFER_SUCCESS_MESSAGE)
                .accountInfo(null)
                .build();






    }
}
