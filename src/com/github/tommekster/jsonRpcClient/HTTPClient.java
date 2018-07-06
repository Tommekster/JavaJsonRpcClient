
package com.github.tommekster.jsonRpcClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/**
 *
 * @author Tomáš Zikmund <tommekster@gmail.com.>
 */
public class HTTPClient {

    public byte[] post(URL url, byte[] postData, String charsetName) throws ProtocolException, IOException {
        int postDataLength = postData.length;
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", charsetName.toLowerCase());
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
        }
        try (DataInputStream rd = new DataInputStream(conn.getInputStream())) {
            byte[] response = new byte[rd.available()];
            rd.read(response);
            return response;
        }
    }
}
