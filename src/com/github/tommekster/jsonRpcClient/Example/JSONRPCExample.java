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
package com.github.tommekster.jsonRpcClient.Example;

import com.github.tommekster.jsonRpcClient.JsonRpcError;
import com.github.tommekster.jsonRpcClient.JsonRpcInvoker;
import com.github.tommekster.jsonRpcClient.JsonRpcProxy;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Tomáš Zikmund <tommekster@gmail.com>
 */
public class JSONRPCExample
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try {
            URL url = new URL("http://localhost:8000/rpcdemo/default/call/jsonrpc");
            JsonRpcInvoker invoker = new JsonRpcInvoker();

            Object message = invoker.invoke(url, "hello");
            Object addition = invoker.invoke(url, "add", 1, 2);

            SimpleService service = JsonRpcProxy.getProxy(url, SimpleService.class);
            SimpleObject simpleObject = service.getSimpleObject();
            SimpleObject[] objectsArray = service.getSimpleObjects(3);
            //SimpleObject simpleObject = invoker.invoke(SimpleObject.class, url, "getSimpleObject");
            //SimpleObject[] objectsArray = invoker.invoke(SimpleObject[].class, url, "getSimpleObjects",3);

            
            System.out.println(message);
            System.out.println(addition);
            System.out.println(simpleObject);
            System.out.println(Arrays.toString(objectsArray));
        } catch (MalformedURLException ex) {
            Logger.getLogger(JSONRPCExample.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ParseException | JsonRpcError ex) {
            Logger.getLogger(JSONRPCExample.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
