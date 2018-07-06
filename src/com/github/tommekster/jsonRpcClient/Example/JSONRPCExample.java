/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.tommekster.jsonRpcClient.Example;

import com.github.tommekster.jsonRpcClient.JsonRpcInvoker;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tomáš
 */
public class JSONRPCExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8000/rpcdemo/default/call/jsonrpc");
            JsonRpcInvoker invoker = new JsonRpcInvoker();
            invoker.invoke(url, "hello");
        } catch (MalformedURLException ex) {
            Logger.getLogger(JSONRPCExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JSONRPCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
