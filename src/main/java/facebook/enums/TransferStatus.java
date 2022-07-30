package facebook.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TransferStatus {
    CREATED ("Created_SUCCESS","CREATED_ERROR"),
    PENDING("PENDING_SUCCESS","PENDING_ERROR"),
    CANCELLED("CANCELLED_SUCCESS","CANCELLED_ERROR"),
    DONE("DONE_SUCCESS","DONE_ERROR"),
    REMOVED("REMOVED_SUCCESS","REMOVED_ERROR");

    private final String PositiveName;
    private final String NegativeName;

}
