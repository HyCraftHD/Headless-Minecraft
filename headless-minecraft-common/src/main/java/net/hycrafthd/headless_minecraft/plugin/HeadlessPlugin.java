package net.hycrafthd.headless_minecraft.plugin;

import net.hycrafthd.headless_minecraft.plugin.newstuff.IHeadlessMinecraft;

public interface HeadlessPlugin {
	
	void load();
	
	void enable(IHeadlessMinecraft headlessMinecraft);
	
	void register(HeadlessEventBus eventBus);
	
}
