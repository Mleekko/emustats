package com.mleekko.test.domain;

import java.util.Objects;

/**
 * A response that is sent from controller. contains data and (if applicable) error code
 * @author Mleekko
 */
public class JsonPackage {

    private Object data;
    private int errorCode;

    public JsonPackage(Object data) {
        this.data = data;
    }

    public JsonPackage(int errorCode) {
        this.errorCode = errorCode;
    }


    public Object getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public static JsonPackage withData(Object data) {
        return new JsonPackage(data);
    }

    public static JsonPackage withError(int errorCode) {
        return new JsonPackage(errorCode);
    }

}
