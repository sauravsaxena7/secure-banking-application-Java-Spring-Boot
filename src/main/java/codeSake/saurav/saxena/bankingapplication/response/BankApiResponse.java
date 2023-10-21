package codeSake.saurav.saxena.bankingapplication.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankApiResponse {
    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;

}
