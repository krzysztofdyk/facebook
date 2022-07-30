package facebook.exeptions;

public enum ExceptionInfo {

    ID_WAS_NOT_FOUND,
    AMOUNT_WAS_PROVIDED_AS_ZERO;

    public String exceptionInfoSwitch(ExceptionInfo exceptionInfo) {
        switch (exceptionInfo) {
            case ID_WAS_NOT_FOUND:
                return "Id was not found";
            case AMOUNT_WAS_PROVIDED_AS_ZERO:
                return "Amount was provided as zero";
            default:
                return "No standard exception info";
        }
    }

}
