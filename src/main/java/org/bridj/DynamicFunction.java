/*
 * BridJ - Dynamic and blazing-fast native interop for Java.
 * http://bridj.googlecode.com/
 *
 * Copyright (c) 2010-2013, Olivier Chafik (http://ochafik.com/)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Olivier Chafik nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY OLIVIER CHAFIK AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.bridj;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.bridj.Pointer.*;

/**
 * Generic C function which invocation involves a bit of Java reflection.<br>
 * To create a dynamic function, use {@link Pointer#asDynamicFunction(org.bridj.ann.Convention.Style, java.lang.reflect.Type, java.lang.reflect.Type[]) } or {@link CRuntime#getDynamicFunctionFactory(org.bridj.NativeLibrary, org.bridj.ann.Convention.Style, java.lang.reflect.Type, java.lang.reflect.Type[])  }.
 * @author ochafik
 * @param R Return type of the function (can be {@link java.lang.Void})
 */
public abstract class DynamicFunction<R> extends Callback {
    /// Don't GC the factory, which holds the native callback handle
    DynamicFunctionFactory factory;
    Method method;

    protected DynamicFunction() {}

    public R apply(Object... args) {
        try {
            return (R) method.invoke(this, args);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException("Failed to invoke callback" + " : " + th, th);
        }
    }

    @Override
    public String toString() {
        return method.toString();
    }


}
