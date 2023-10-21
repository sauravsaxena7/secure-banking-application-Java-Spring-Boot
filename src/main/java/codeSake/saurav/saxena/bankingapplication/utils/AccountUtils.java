package codeSake.saurav.saxena.bankingapplication.utils;

import java.time.Year;

public class AccountUtils {

    public static String generateAccountNumber(){
        /**
         * 2023 + randomSixDigits
         */
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //generate random number
        int randomNumber = (int)Math.floor(Math.random() * (max-min+1) +min);

        //convert the current randomNumber ti strings, then concoatenate the n

        String year =String.valueOf(currentYear);

        String randomNumberr = String.valueOf(randomNumber);

        StringBuilder accountNumber = new StringBuilder();
        return accountNumber.append(year).append(randomNumberr).toString();

    }

}
