package server;

import org.springframework.hateoas.ResourceSupport;

public class DataResponse extends ResourceSupport{
    private Object data;

    /**
     * The data object that is used as a response in the api.
     * @param data The API data that is to be included in the '{"data": {}}' attribute.
     */
    public DataResponse(Object data) {
        this.data = data;
    }

    /**
     * Getter for the data object. Used by Spring to set the data attribute.
     * @return
     */
    public Object getData() {
        return data;
    }
}
