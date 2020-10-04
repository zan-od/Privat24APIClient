package zan.api.privat24.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Optional;

@JacksonXmlRootElement(localName = "response")
public class ApiErrorResponse {

    private Data data;
    private String version;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return Optional.ofNullable(getData())
                .map(Data::getError)
                .map(Error::getMessage)
                .orElse(null);
    }

    public static class Data {
        private Error error;

        public Error getError() {
            return error;
        }

        public void setError(Error error) {
            this.error = error;
        }
    }

    public static class Error {
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
