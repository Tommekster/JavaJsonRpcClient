
package com.github.tommekster.jsonRpcClient;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 *
 * @author Tomáš Zikmund <tommekster@gmail.com.>
 */
public class JsonRpcInvoker {

    HTTPClient client;
    String charsetName = "UTF-8";

    public JsonRpcInvoker() {
        this.client = new HTTPClient();
    }

    public JsonRpcInvoker(HTTPClient client) {
        this.client = client;
    }

    public Object invoke(URL url, String method) throws IOException {
        return this.invoke(url, method, new Object[0]);
    }

    public Object invoke(URL url, String method, Object... params) throws IOException {
        String payload = this.invokeJSON(method, params);
        byte[] response = this.client.post(url, payload.getBytes(Charset.forName(charsetName)), charsetName);
        return response;
    }

    public String invokeJSON(String method, Object... params) {
        //return "{ \"method\": \"echo\", \"params\": [\"Hello JSON-RPC\"], \"id\": 1}";
        return "{ \"method\": \"hello\", \"params\": [\"Hello JSON-RPC\"], \"id\": 1}";
    }

}
