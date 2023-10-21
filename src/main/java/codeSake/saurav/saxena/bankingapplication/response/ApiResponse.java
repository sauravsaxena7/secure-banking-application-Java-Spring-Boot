package codeSake.saurav.saxena.bankingapplication.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponse<T> {
    private String responseCode;
    private String responseMessage;
    private T data;
}
