package codeSake.saurav.saxena.bankingapplication.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AccountInfo {
    private String accountName;
    private String accountNumber;
    private String accountBalance;

}
