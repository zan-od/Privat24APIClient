package zan.api.privat24.dto;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseInfo<T> {
    @JacksonXmlProperty(localName = "oper")
    String operationCode;
    T info;

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }
}
