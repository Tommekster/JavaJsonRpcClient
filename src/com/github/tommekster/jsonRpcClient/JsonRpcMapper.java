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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Tomáš Zikmnund
 */
public class JsonRpcMapper
{

    public <T> T map(Object object, Class<T> type)
    {
        if (type.isArray())
        {
            Object[] array = this.mapArray((JSONArray) object, type.getComponentType());
            return type.cast(array);
        }
        else
        {
            return this.isTypeSimple(type) ? (T) object : this.mapObject((JSONObject) object, type);
        }
    }

    public <T> T[] mapArray(JSONArray array, Class<T> type)
    {
        Object[] objects = array.stream()
                .map(o -> this.map(o, type))
                .filter(o -> o != null)
                .toArray();
        Object[] output = (Object[]) Array.newInstance(type, objects.length);
        System.arraycopy(objects, 0, output, 0, objects.length);
        return (T[]) output;
    }

    public <T> T mapObject(JSONObject object, Class<T> type)
    {
        T dest;
        try
        {
            dest = type.newInstance();
        }
        catch (InstantiationException | IllegalAccessException ex)
        {
            Logger.getLogger(JsonRpcMapper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        Arrays.stream(type.getFields())
                .filter(f -> object.containsKey(f.getName()))
                .filter(f -> Arrays.stream(f.getAnnotations()).anyMatch(a -> a instanceof JsonRpcDataMember))
                .forEach(f ->
                {
                    Class<?> fieldType = f.getType();
                    Object value;
                    value = this.map(object.get(f.getName()), fieldType);
                    try
                    {
                        f.set(dest, fieldType.cast(value));
                    }
                    catch (IllegalArgumentException | IllegalAccessException ex)
                    {
                        Logger.getLogger(JsonRpcMapper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        return dest;
    }

    private boolean isTypeSimple(Class<?> type)
    {
        Class<?>[] simpleTypes = new Class<?>[]
        {
            String.class, Number.class, Long.class, Double.class, Boolean.class
        };
        return Stream.of(simpleTypes).anyMatch(t -> type.isAssignableFrom(t));
    }
}
