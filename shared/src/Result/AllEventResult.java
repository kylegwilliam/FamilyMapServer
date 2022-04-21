package Result;



import model.Event;

import java.util.List;

/**
 * AllEventResult Class
 */

public class AllEventResult {

    /**
     * Variables that will be used in AllEventsResult
     */

    public List<Event> data;
    public boolean success;
    public String message;


    /**
     * Constructor for AllEventResult
     *
     * @param data
     * @param success
     */


    public AllEventResult(List<Event> data, boolean success, String message) {

        this.data = data;
        this.success = success;
        this.message = message;

    }

    public List<Event> getData() {
        return data;
    }

    public void setData(List<Event> data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() { return message;}

    public void setMessage(String message) { this.message = message; }

}
