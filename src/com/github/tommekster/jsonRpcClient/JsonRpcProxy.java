/*
 * The MIT License
 *
 * Copyright 2018 Tomáš.
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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;

/**
 *
 * @author Tomáš Zikmund
 */
public class JsonRpcProxy implements InvocationHandler
{

    private final JsonRpcInvoker invoker;
    private URL url;

    private JsonRpcProxy(URL url, JsonRpcInvoker invoker)
    {
        this.invoker = invoker;
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        return this.invoker.invoke(method.getReturnType(), this.url, method.getName(), args);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProxy(URL url, Class<T> clazz)
    {
        return getProxy(url, new JsonRpcInvoker(), clazz);
    }

    public static <T> T getProxy(URL url, JsonRpcInvoker invoker, Class<T> clazz)
    {
        return (T) Proxy.newProxyInstance(JsonRpcProxy.class.getClassLoader(),
                new Class[]{clazz}, new JsonRpcProxy(url, invoker));
    }

}
