package Model.Proc;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class ProcessLinkedList {
	
	private LinkedList<Map<String,LinkedList<LinkedList<String>>>> normalProcess;
	
	private LinkedList<Map<String, Map<String, Map<String, String>>>> optProcess;
	
	public ProcessLinkedList(){
		this.normalProcess = new LinkedList<>();
		this.optProcess = new LinkedList<>();
	}
	
	public int size(){
		return normalProcess.size();
	}
	
	public void addNormal(Map<String,LinkedList<LinkedList<String>>> map,String typeMap) {
		normalProcess.add(map);
		Map<String, Map<String, Map<String, String>>> m = new LinkedHashMap<>();
		if(!typeMap.equals("alt") && !typeMap.equals("else")) m.put("NaN", getForOpt(map, typeMap));
		else m.put(typeMap, getForOpt(map, typeMap));
		System.out.println(typeMap +"Add Normal Map :"+map+">>"+m);
		optProcess.add(m);
	}
	
	public Map<String, Map<String, String>> getForOpt(Map<String,LinkedList<LinkedList<String>>> map,String typeMap){
		Map<String, Map<String, String>> mm = new LinkedHashMap<>();
		if(typeMap.equals("alt")){
			Map<String, String> l  = new LinkedHashMap<>();
			for (Entry<String, LinkedList<LinkedList<String>>> s : map.entrySet()) {
				for (int i = 0; i < s.getValue().size(); i++) {
					l.put(s.getValue().get(i).get(0),s.getKey());
				}
			}
			mm.put("f1_b", l);
		}else if(typeMap.equals("else")){
			Map<String, String> l  = new LinkedHashMap<>();
			for (Entry<String, LinkedList<LinkedList<String>>> s : map.entrySet()) {
				for (int i = 0; i < s.getValue().size(); i++) {
					l.put(s.getValue().get(i).get(0),s.getKey());
				}
			}
			mm.put("f1_e", l);
		}else{
			Map<String, String> l  = new LinkedHashMap<>();
			for (Entry<String, LinkedList<LinkedList<String>>> s : map.entrySet()) {
				System.out.println("<TEST>"+s);
				String value = "";
				for (int i = 0; i < s.getValue().size(); i++) {
					value = s.getValue().get(i).get(0);
					l.put(value,s.getKey());
				}
				mm.put(l.keySet().toArray()[0]+"",l);

			}
		}
		return mm;
	}
	
	public void addOpt(Map<String, Map<String, Map<String, String>>> map) {
		optProcess.add(map);
	}

	public LinkedList<Map<String, LinkedList<LinkedList<String>>>> getNormalProcess() {
		return normalProcess;
	}

	public void setNormalProcess(LinkedList<Map<String, LinkedList<LinkedList<String>>>> normalProcess) {
		this.normalProcess = normalProcess;
	}

	public LinkedList<Map<String, Map<String, Map<String,String>>>> getOptProcess() {
		return optProcess;
	}

	public void setOptProcess(LinkedList<Map<String, Map<String, Map<String, String>>>> optProcess) {
		this.optProcess = optProcess;
	}
	
	public void testOpt(){
		System.err.println("ALT:"+this.optProcess);
	}

}