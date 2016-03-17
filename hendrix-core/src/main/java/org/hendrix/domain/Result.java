package org.hendrix.domain;

public class Result {

    private ResultStatus resultStatus = ResultStatus.PASS;
    private Throwable exception = null;
    private String message = "";

    private Result() {
    }

    public ResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(ResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public static Result pass() {
        return new Result();
    }

    public static Result fail(Throwable e) {
        Result result = new Result();
        result.resultStatus = ResultStatus.FAIL;
        result.exception = e;
        result.message = e.getMessage();
        return result;
    }

    public static Result skipped() {
        Result result = new Result();
        result.resultStatus = ResultStatus.SKIPPED;
        return result;
    }

    public static Result assumeFail(Throwable e) {
        Result result = new Result();
        result.resultStatus = ResultStatus.ASSUME_FAIL;
        result.exception = e;
        result.message = e.getMessage();
        return result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return resultStatus + (resultStatus == ResultStatus.FAIL ? ": " + message : "");
    }

    public static Result ignored() {
        Result result = new Result();
        result.resultStatus = ResultStatus.IGNORED;
        return result;
    }
}
