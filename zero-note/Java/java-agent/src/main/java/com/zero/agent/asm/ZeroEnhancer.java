package com.zero.agent.asm;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author zero
 */
public class ZeroEnhancer extends ClassVisitor {

    ZeroEnhancer(ClassVisitor classVisitor) {
        super(Opcodes.ASM5, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
        if (isIgnore(methodVisitor, access, name)) {
            return methodVisitor;
        }
        return new ZeroChangeAdapter(Opcodes.ASM4, methodVisitor, access, name, desc);
    }

    private boolean isIgnore(MethodVisitor methodVisitor, int access, String methodName) {
        return null == methodVisitor
                || isAbstract(access)
                || isFinalMethod(access)
                || "<clinit>".equals(methodName)
                || "<init>".equals(methodName);
    }


    private boolean isFinalMethod(int access) {
        return (Opcodes.ACC_ABSTRACT & access) == Opcodes.ACC_ABSTRACT;
    }

    private boolean isAbstract(int access) {
        return (Opcodes.ACC_ABSTRACT & access) == Opcodes.ACC_ABSTRACT;
    }
}
