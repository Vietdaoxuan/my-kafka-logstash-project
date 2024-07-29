package Kafka.Producer.Model;

public class ErrorResponseModel<T> {
    private String message;
    private int statusCode;
    private long timeStamp;

    private T data;

    public ErrorResponseModel() {

    }

    public ErrorResponseModel(String message, int statusCode, long timeStamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
    }


    public ErrorResponseModel(String message, int statusCode, long timeStamp, T data) {
        this.message = message;
        this.statusCode = statusCode;
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
