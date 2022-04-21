package Request;

/**
 * fillRequest Class
 */
public class FillRequest {

    /**
     * This should, by taking it out of the url, get the username String
     * as well as the number of generations.
     */

    public String username;
    public int generations;

    /**
     * Constructor for fillRequest
     *
     * @param username
     * @param generations
     */


    public FillRequest(String username, int generations) {

        this.username = username;
        this.generations = generations;


    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

}
