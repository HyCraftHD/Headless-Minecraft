package net.hycrafthd.headless_minecraft.impl.handle;

import net.hycrafthd.headless_minecraft.impl.HeadlessLevel;
import net.hycrafthd.headless_minecraft.plugin.newstuff.IHeadlessLevel;

public class HeadlessLevelImpl implements IHeadlessLevel {
	
	private HeadlessLevel level;
	
	public HeadlessLevelImpl(HeadlessLevel level) {
		this.level = level;
	}
	
	@Override
	public int getEntityCount() {
		return level.getEntityCount();
	}
	
}
