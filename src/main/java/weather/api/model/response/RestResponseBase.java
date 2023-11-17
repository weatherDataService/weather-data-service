package weather.api.model.response;

import java.io.Serializable;
import weather.api.model.enums.Status;

public abstract class RestResponseBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private Status status;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
