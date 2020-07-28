package com.zero.agent.buddy;


import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class TimeInterceptor {

    @RuntimeType
    public static Object interceptor(@Origin Class clazz,
                                     @Origin Method method,
                                     @SuperCall Callable<?> callable) throws Exception {

        if (!method.getName().endsWith("test")) {
            return callable.call();
        }
        long start = System.currentTimeMillis();
        try {
            // 原有函数执行
            return callable.call();
        } finally {
            System.out.println(clazz.getSimpleName() + "#" + method.getName() + " cost " + (System.currentTimeMillis() - start) + "ms");
        }
    }

}
