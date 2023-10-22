package codeSake.saurav.saxena.bankingapplication.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class AccountInfo {

    @Schema(
            name = "Account Name"
    )
    private String accountName;
    private String accountNumber;
    private String accountBalance;

}
