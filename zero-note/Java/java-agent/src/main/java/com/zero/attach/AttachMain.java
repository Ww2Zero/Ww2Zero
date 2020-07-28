package com.zero.attach;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class AttachMain {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        // 获取运行中的JVM列表
        List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
        // 需要agent的jar包路径
        String agentJar = "target/zero-java-agent.jar";
        for (VirtualMachineDescriptor vmd : vmList) {
            // 找到测试的JVM
            if (vmd.displayName().endsWith("WorkMain")) {
                // attach到目标ID的JVM上
                VirtualMachine virtualMachine = VirtualMachine.attach(vmd.id());
                // agent指定jar包到已经attach的JVM上
                virtualMachine.loadAgent(agentJar);
                virtualMachine.detach();
            }
        }
    }
}
