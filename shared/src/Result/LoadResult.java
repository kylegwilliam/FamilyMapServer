package Result;

/**
 * LoadResult Class
 */

public class


LoadResult {

    /**
     * Variables that will be used in LoadRequest
     */

    public String message;
    public boolean success;

    /**
     * Constructor for LoadResult
     *
     * @param message
     * @param success
     */

    public LoadResult(String message, boolean success) {

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
