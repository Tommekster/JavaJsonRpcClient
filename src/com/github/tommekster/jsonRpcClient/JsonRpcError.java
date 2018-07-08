package com.github.tommekster.jsonRpcClient;

import org.json.simple.JSONObject;

/**
 *
 * @author Tomáš
 */
public class JsonRpcError extends Exception {

    public JSONObject result;

    public JsonRpcError(JSONObject result) {
        super("The called method finished with an error. ");
        this.result = result;
    }
}
