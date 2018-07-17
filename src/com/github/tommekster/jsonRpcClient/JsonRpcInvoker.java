/*
 * The MIT License
 *
 * Copyright 2018 Tomáš Zikmund.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.tommekster.jsonRpcClient;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tomáš Zikmund <tommekster@gmail.com>
 */
public class JsonRpcInvoker
{

    private final HTTPClient client;
    private String charsetName = "UTF-8";
    private int invokeID = 0;
    private final JSONParser parser = new JSONParser();
    private final JsonRpcMapper mapper = new JsonRpcMapper();

    public JsonRpcInvoker()
    {
        this(new HTTPClient());
    }

    public JsonRpcInvoker(HTTPClient client)
    {
        this.client = client;
    }

    public <T> T invoke(Class<T> type, URL url, String method)
            throws IOException, ParseException, JsonRpcError
    {
        return this.invoke(type, url, method, null);
    }

    public <T> T invoke(Class<T> type, URL url, String method, Object... params)
            throws IOException, ParseException, JsonRpcError
    {
        Object response = this.invoke(url, method, params);
        return this.mapper.map(response, type);
    }
    
    public Object invoke(URL url, String method)
            throws IOException, ParseException, JsonRpcError
    {
        return this.invoke(url, method, null);
    }

    public Object invoke(URL url, String method, Object... params)
            throws IOException, ParseException, JsonRpcError
    {
        if(params == null){
            params = new Object[0];
        }
        JSONObject invocation = this.invokeJSON(method, params);
        byte[] response = this.client.post(url, invocation.toJSONString()
                .getBytes(Charset.forName(charsetName)), charsetName);
        return this.parseResponse(response, invocation);
    }

    public JSONObject invokeJSON(String method, Object... params)
    {
        JSONObject json = new JSONObject();
        json.put("method", method);
        json.put("params", this.makeParamsArray(params));
        json.put("id", this.getInvokeID());
        return json;
    }

    private Object parseResponse(byte[] response, JSONObject invocation)
            throws ParseException, JsonRpcError
    {
        JSONObject result = (JSONObject) this.parser.parse(new String(response));
        if (result.get("error") != null)
        {
            throw new JsonRpcError(result);
        }
        return result.get("result");
    }

    private JSONArray makeParamsArray(Object[] params)
    {
        JSONArray jsonParams = new JSONArray();
        jsonParams.addAll(Arrays.asList(params));
        return jsonParams;
    }

    private int getInvokeID()
    {
        return ++invokeID;
    }

    private class Invocation
    {

        String json;
        int id;
    }
}
