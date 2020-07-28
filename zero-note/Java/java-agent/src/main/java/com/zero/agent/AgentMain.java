package com.zero.agent;

import com.zero.agent.asm.ZeroClassTransformer;
import com.zero.agent.buddy.TimeListener;
import com.zero.agent.buddy.TimeTransformer;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class AgentMain {

    public static void premain(String args, Instrumentation inst) {
        agent0(args, inst);
    }

    public static void agentmain(String args, Instrumentation inst) {
        agent2(args, inst);
    }

    private static void agent1(String args, Instrumentation inst) {
        System.out.println("Attach asm Agent is running");
        inst.addTransformer(new ZeroClassTransformer());
    }

    public static void agent0(String args, Instrumentation inst) {
        System.out.println("Pre Agent is running");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
                // JVM加载的所有类会流经这个类转换器
                // 这里找到自定义的测试类
                System.out.println("className = " + className);
                if (className.endsWith("WorkMain")) {
                    System.out.println("transform class com.zero.work.WorkMain");
                }
                // 直接返回原本的字节码
                return classfileBuffer;
            }
        });

    }

    private static void agent2(String args, Instrumentation inst) {
        System.out.println("Attach buddy Agent is running");

        new AgentBuilder
                .Default()
                .type(ElementMatchers.nameStartsWith("com.zero"))// 指定需要拦截的类
                .transform(new TimeTransformer())
                .with(new TimeListener())
                .installOn(inst);

    }
}
