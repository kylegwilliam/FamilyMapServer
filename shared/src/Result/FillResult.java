package Result;

/**
 * fillResult Class
 */

public class FillResult {

    /**
     * Variables that will be used in fillResult
     */

    public String message;
    public boolean success;

    /**
     * Constructor for fillResult
     * @param message
     * @param success
     */

    public FillResult(String message, boolean success) {

        this.message = message;
        this.success = success;

    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
