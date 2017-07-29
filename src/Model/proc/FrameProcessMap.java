package Model.proc;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class FrameProcessMap {
	
	private LinkedList<FrameProcess> frameProcesslist;
	private String name="";
	
	
	public FrameProcessMap() {
		this.frameProcesslist = new LinkedList<>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public LinkedList<FrameProcess> getFrameProcesslist() {
		return frameProcesslist;
	}

	public void setFrameProcesslist(LinkedList<FrameProcess> frameProcesslist) {
		this.frameProcesslist = frameProcesslist;
	}

	public void addFrameProcess(FrameProcess frameProcess) {
		frameProcesslist.add(frameProcess);
	}
	

}
