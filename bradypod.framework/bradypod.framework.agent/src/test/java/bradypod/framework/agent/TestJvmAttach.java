package bradypod.framework.agent;

import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

/**
 * @see JvmAttach main
 * @see MyAgent
 * 
 *      premain-agent 和 main-agent 和 jvm attach 的用法
 * 
 * @author zengxm<http://github.com/JumperYu>
 *
 * @date 2016年9月11日
 */
public class TestJvmAttach {

	public static void main(String[] args) throws InterruptedException {
		final String jar = "E://work//new-life//bradypod//bradypod.framework//bradypod.framework.agent//target//bradypod.framework.agent.jar";
		Thread jvmAttachThread = new Thread() {
			@Override
			public void run() {
				try {
					VirtualMachine vm = null;
					List<VirtualMachineDescriptor> listBefore = VirtualMachine
							.list();
					List<VirtualMachineDescriptor> listAfter = null;
					while (true) {
						listAfter = VirtualMachine.list();
//						System.out.println("vm:" + listAfter);
						for (VirtualMachineDescriptor vmd : listAfter) {
							if (!listBefore.contains(vmd)) {
								// 如果 VM 有增加，我们就认为是被监控的 VM 启动了
								// 这时，我们开始监控这个 VM
								vm = VirtualMachine.attach(vmd);
								System.out.println("attach vm " + vmd.id());
								vm.loadAgent(jar);
								vm.detach();
								listBefore.add(vmd);
								System.out.println("load agent");
								break;
							}
						}
						Thread.sleep(3000);
					}
				} catch (Exception e) {

				}
			};
		};
		jvmAttachThread.start();
	}
}
