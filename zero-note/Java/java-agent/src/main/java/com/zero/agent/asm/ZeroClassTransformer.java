package com.zero.agent.asm;


import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author zero
 */
public class ZeroClassTransformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 对类字节码进行操作,注意不能对classfileBuffer这个数组进行修改操作
        try {
            // 创建ASM ClassReader对象，导入需要增强的对象字节码
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            // 自己实现的代码增强器
            ZeroEnhancer myEnhancer = new ZeroEnhancer(classWriter);
            // 增强字节码
            reader.accept(myEnhancer, ClassReader.SKIP_FRAMES);
            // 返回MyEnhancer增强后的字节码
            return classWriter.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return null 则不会对类进行转换
        return null;
    }
}
