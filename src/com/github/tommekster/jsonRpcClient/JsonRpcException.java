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

/**
 *
 * @author Tomáš
 */
public class JsonRpcException extends Exception
{

    private Exception parent;

    /**
     * Creates a new instance of <code>JsonRpcException</code> without detail
     * message.
     */
    public JsonRpcException()
    {
    }

    /**
     * Constructs an instance of <code>JsonRpcException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public JsonRpcException(String msg)
    {
        super(msg);
    }
    
    public JsonRpcException(Exception ex)
    {
        super(ex.getMessage());
        this.parent = ex;
    }

    public Exception getParent()
    {
        return parent;
    }
    
    
}
