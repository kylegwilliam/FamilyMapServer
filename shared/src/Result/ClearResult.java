package Result;

/**
 * clearResult Class
 */

public class ClearResult {

    /**
     * Variables that will be used in clearResult
     */

    public String message;
    public boolean success;

    /**
     * Constructor for clearResult
     *
     * @param message
     * @param success
     */


    public ClearResult(String message, boolean success) {

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
