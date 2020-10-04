package zan.api.privat24.connector;

/**
 * HTTP response wrapper
 * @author azlatov
 */
public class HttpApiResponse {
    /**
     * HTTP code
     */
    private int code;

    /**
     * HTTP response body
     */
    private String response;

    public HttpApiResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
