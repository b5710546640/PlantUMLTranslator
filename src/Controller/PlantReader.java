package Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import Model.Diagram;
import Model.SequenceDiagram;
import Model.StateDiagram;
import View.UMLReader;
import View.UMLReaderGUI;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.sound.midi.Sequence;

public class PlantReader {
	
	private static Map<String,LinkedList<Map<String,LinkedList<String>>>> traceData;
	private static String input="";
	private static String[] equations;
	private static int count=0;
	private static Integer STATE_DG=0;
	private static Integer SEQUENCE_DG=0;
	private static ArrayList<Map<String,ArrayList<String>>> allUML = new ArrayList<>();
	
	private static ArrayList<Diagram> diagrams;

	
	public static void main(String[]args){
 	
//    	//Add All UML to ArrayList by separate each state
    	diagrams = new ArrayList<>();
    	
//    	System.out.println(input);
//		allUML = readAllInput(input);

    	
    	for (int i = 0; i < allUML.size(); i++) {
    		System.out.println("State "+(i+1));
    		translateToDiagram(allUML.get(i));

		}
//    	
//
//    	
//    	
//    	
//    	
//    	System.out.println("******CSP Output******");
//    	System.err.print("Channel :");
    	Set<String> allProcess = showAllProcess();
//    	System.err.println(allProcess.toString().substring(1, allProcess.toString().length()-1));
//    	System.out.println();
//
//    	Object[] allDiagram = showAllDiagram().toArray();
//    	
//    	for (int i = 0; i < allDiagram.length; i++) {
//    		System.out.println(allDiagram[i]+"");
//        	System.err.println(getAllSendAndReceiveMessage(allDiagram[i]+""));
//			
//		}
//    	
//    	System.out.println("Tracedata :");
//    	for (Entry<String, LinkedList<Map<String, LinkedList<String>>>> trace : traceData.entrySet()) {
//    		System.out.println(trace.getKey());
//    		for (int i = 0; i < trace.getValue().size(); i++) {
//    			for ( Entry<String, LinkedList<String>> line : trace.getValue().get(i).entrySet()) {
//        			System.err.println(line.getValue() +" : " +line.getKey());
//					
//				}
//
//			}
//			System.out.println("===================================");
//		}
    	
		
    	UMLReaderGUI a = new UMLReaderGUI(new UMLReader());
    	a.run();
    	
    	String s = "file:/D:/Save/OkayamaPreU/3_watchdog.puml";
    	String[] ss = s.split("/");
    	String[] sss = ss[ss.length-1].split("\\.");
    	System.out.println(sss[0]);
    	
    	
    	
    	
    	
    	
    	
	}
	
	public static ArrayList<Map<String, ArrayList<String>>> readAllInput(String input,String name) {
		ArrayList<Map<String, ArrayList<String>>> allUML =  new ArrayList<>();
		equations = prepareInput(input.replace("\n", " "));
		String[] names = name.split(",");
    	System.out.println("EQ :"+equations.length);
    	Map<String,ArrayList<String>> map = new LinkedHashMap<>();
    	ArrayList<String> tmp = new ArrayList<>();
    	String t= "";
    	for (int i = 0; i < equations.length; i++) {
    		if(equations[i].equals("@startuml")){
    			count+=1;
    			if (equations[i+1].equals("[*]")) {
//					t = "M"+ ++STATE_DG+names[count-1];
					t = "M_"+names[count-1];
				}else{
//					t = "SQ"+ ++SEQUENCE_DG+names[count-1];
    				t = "SQ_"+names[count-1];
				}
    		}

    		tmp.add(equations[i]);
    		System.err.println("EQ"+i+" : "+equations[i] + equations[i].length());
    		if(equations[i].equals("@enduml")){
    			map.put(t,tmp);
    			System.out.println("Map :"+map);
    			System.out.println("AllUML :"+allUML);
    			System.out.println();
    			allUML.add(map);
    			System.out.println("ALL :"+allUML);
    			map = new LinkedHashMap<>();
    			tmp = new ArrayList<>();
    		}
			
		}
    	
    	System.out.println("In PlantUNL"+allUML);
    	return allUML;
	}
	


	public static String[] prepareInput(String s){


		String[] ss = s.replace(">", "> ").replace("<", " <").split(" ");

		ArrayList<String> sl = new ArrayList<>();
		for (int i = 0; i < ss.length; i++) {
			if(ss[i].length()!=0)
			if(ss[i].contains(":")){
				if(sl.get(sl.size()-1).contains(">")||sl.get(sl.size()-1).contains("<")){
					int count =0;
					String[] g =ss[i].split(":");
					if(g.length==0){// this is :
						sl.add(ss[i]);
					}else if(g.length==2){// this is send and receive message
						sl.add(g[0]);
						sl.add(":");
						sl.add(g[1]);
					}
					else if(g.length==3){// this is send and receive message with name process
						sl.add(g[0]);
						sl.add(":");
						sl.add(g[1]+":"+g[2]);
					}
				}else{
					sl.add(ss[i]);
					System.out.println("Out :"+ss[i]);
				}

			}else sl.add(ss[i]);
			
		}
		String[] rs = new String[sl.size()] ;
		
		rs = sl.toArray(rs);
		
		return rs;
	}

	
	 public static  ArrayList<Diagram> translateToDiagram(Map<String,ArrayList<String>> map){
		 	Diagram diagram;
		 	System.out.println("IN TranslateToDiagram :");
	    	ArrayList<String> res = convertToArrayList(map.values().toString().replace("[", "").replace("]", "").split(", "));
	    	String state = map.keySet().toString().replace("[", "").replace("]", "");
	    	System.out.println("State :" +state);
	    	if(state.contains("M")){
	    		diagram = new StateDiagram(state);
	    		diagram.addProcess(processForStateDiagram(res));
	    		diagrams.add(diagram);
//	    		traceData.put(state,processForStateDiagram(res));
//	    		System.out.println(getAllSendAndReceiveMessage(state));
	    	}else if(state.contains("SQ")){
	    		diagram = new SequenceDiagram(state);
	    		diagram.addProcess(getResult(res));
	    		diagrams.add(diagram);
//	    		System.out.println("PC Sequence");
//	    		traceData.put(state,getResult(res));

	    	}
	    	return diagrams;
	 }
	 
	 public void showTheRelation(ArrayList<Map<String, LinkedList<String>>> all){
		 for (int i = 0; i < all.size(); i++) {
			for (int j = 0; j < all.size(); j++) {
				
				
			}
		}
	 }
	 
	 
	 public static Set<String> showAllProcess(){
		 Set<String> processes = new TreeSet<>();
		 for (int n = 0;n<diagrams.size();n++) {
	    		for (int i = 0; i < diagrams.get(n).getProcesses().size(); i++) {
	    			for ( Entry<String, LinkedList<String>> line : diagrams.get(n).getProcesses().get(i).entrySet()) {
	        			if(!line.getKey().equals("NaN"))
	        				processes.add(line.getKey());
					}

				}
			}
		 return processes;
	 }

	 public static Set<String> showAllStateDiagram(){
		 Set<String> processes = new TreeSet<>();
		 for (int i =0;i<diagrams.size();i++) {
		    	if (diagrams.get(i).getName().contains("M_"))
		    		processes.add(getAllSendAndReceiveMessage(diagrams.get(i).getName()));	
			}
		 return processes;
	 }
	 
	public static String getAllSendAndReceiveMessage(String nowstate){

			String res="";
			
			for (int n = 0;n<diagrams.size();n++)
			{
				String namestate = diagrams.get(n).getName();
			    LinkedList<Map<String, LinkedList<String>>> l = diagrams.get(n).getProcesses();
			    if(namestate.equals(nowstate))
			    for (int i = 0; i < l.size(); i++) {
					for (Entry<String, LinkedList<String>> entry2 : l.get(i).entrySet())
					{
					    for (int j = 0; j < entry2.getValue().size(); j++) {
							if(j%3==1) res+= "= ";
							else{
								if(entry2.getValue().get(j).equals("*")) res += namestate+" ";
								else res+= entry2.getValue().get(j)+" ";
							}
							if(j%3==1 && !entry2.getKey().equals("NaN") ) res+= entry2.getKey()+" -> ";
						}
					    res += "\n";
					}
					
				}
			}

			return res;
	}
	

    
    public static String readAction(String[] message){
    	String rs="";

    	for (int i = 0; i < message.length; i++) {
			if (message[i].length()!=0) {
				System.out.println("MN :"+message[i]);
				rs+=message[i];
				if(i<message.length-1) rs+= " -> ";
			}
			
		}
    	if(rs.length()==0) return "NaN";
    	return rs;
    }
	
	 
	 private static ArrayList<String> convertToArrayList(Object[] in) {
		ArrayList<String> res = new ArrayList<>();
		 for (int i = 0; i < in.length; i++) {
			res.add(in[i]+"");
		}
		 return res;
	}
	 
	 public static LinkedList<Map<String,LinkedList<String>>> processForStateDiagram(ArrayList<String> res) {
		System.out.println("Process State");
		char state = 'n';
		LinkedList<Map<String,LinkedList<String>>> rs = new LinkedList<>();
		for (int j = 1; j < res.size()-1; j++) {
			String tmp = res.get(j);
			LinkedList<String> ll = new LinkedList<>();

			if(tmp.contains("<")){
	    		ll.add(res.get(j+1));
	    		ll.add(tmp.replace("<", "")+">");
	    		ll.add(res.get(j-1));
	    		state = 'r';
	    	}else if(tmp.contains(">")){
	    		ll.add(res.get(j-1));
	    		ll.add(tmp);
	    		ll.add(res.get(j+1));
	    		state = 's';
	    	}
	    	else continue;
			Map<String, LinkedList<String>> map = new LinkedHashMap<>();
    		String msg = res.get(j+3);
    		if(msg.contains("<")|| msg.contains(">") ) msg="NaN";
    		System.out.println("Message :"+msg);
    		map.put(readAction(messageWithStatus(msg)), ll);
//    		rs.add(map);
    		rs.add(checkMap(map));
		}
		
		
		return rs;			

	}

	 public static Map<String, LinkedList<String>> checkMap(Map<String, LinkedList<String>> map){
		 Map<String, LinkedList<String>> resMap = new LinkedHashMap<>();
		 for (Entry<String, LinkedList<String>> emap : map.entrySet()) {
			if(emap.getKey().contains("->")) {
				String[] arr = emap.getKey().split(" -> ");
				LinkedList<String> origin = emap.getValue();
				LinkedList<String> left = new LinkedList<>();
				left.add(origin.get(0));
				left.add(origin.get(1));
				left.add(origin.get(0));
				LinkedList<String> right = new LinkedList<>();
				right.add(origin.get(0));
				right.add(origin.get(1));
				right.add(origin.get(2));
				
				 resMap.put(arr[0], left);
				 resMap.put(arr[1], right);
 			}else
			 resMap.put(emap.getKey(), emap.getValue());
			
		}
		 return resMap;
	 }
	 
	 
	 public static LinkedList<Map<String, LinkedList<String>>> getResult(ArrayList<String> res){
		 String newMsg="",left="",right="",state="";
		 boolean isWait;
		 LinkedList<Map<String, LinkedList<String>>> list=new LinkedList<>();
		 for (int i = 1; i < res.size()-1; i++) {
			 if (res.get(i+1).contains(">")) {
				left = res.get(i);
				right = res.get(i+2);
				newMsg = res.get(i+4)+"s";
			}else if(res.get(i+1).contains("<")){
				left = res.get(i+2);
				right = res.get(i);
				newMsg = res.get(i+4)+"r";
			}else continue;

			 
			 System.out.println(left+" > "+newMsg+" > "+right);
			 System.out.println("LEFT -> RIGHT by "+newMsg);
			 System.out.println(getLinkedFromtrace(left,newMsg));
			 Map<String, LinkedList<String>> m = new LinkedHashMap<>();
			 LinkedList<String> leftL = getLinkedFromtrace(left, newMsg.substring(0, newMsg.length()-1));

			 System.out.println("===============================");
			 System.out.println("RIGHT -> LEFT by "+newMsg);
			 System.out.println(getLinkedFromtrace(right,newMsg));
			 LinkedList<String> rightL = getLinkedFromtrace(right, newMsg.substring(0, newMsg.length()-1));

			 System.out.println("===============================");
			 
			 if (newMsg.charAt(newMsg.length()-1)=='s') {
				m.put(newMsg, leftL);
				m.put(newMsg.substring(0,newMsg.length()-1)+"r", rightL);
			}else if(newMsg.charAt(newMsg.length()-1)=='r'){
				m.put(newMsg.substring(0,newMsg.length()-1)+"s", leftL);
				m.put(newMsg, rightL);
			}
			 
			 list.add(m);
			 
//			 System.out.println(getLinkedFromtrace(right,left, newMsg));
		}
		 
		 System.out.println("List :"+list);
		 
		 return list;
		 
	 }
	 

	 
	 
	 
	 public static LinkedList<String> getLinkedFromtrace(String left,String message) {
		 LinkedList<String> res = new LinkedList<>();
//		 Map<String, LinkedList<String>> map = new LinkedHashMap<>();
			for (int i=0;i<diagrams.size();i++) {
				System.out.println("Get Link from trace :"+diagrams.get(i).getName());
				if(diagrams.get(i).getName().contains(left)){
					System.out.println("Name :"+diagrams.get(i).getName());
					for (int j = 0; j < diagrams.get(i).getProcesses().size(); j++) {
						for (Entry<String, LinkedList<String>> eachentry:diagrams.get(i).getProcesses().get(j).entrySet()) {
							System.out.println("Key :"+eachentry.getKey());
							System.out.println("LinkList :"+eachentry.getValue());
							if(!eachentry.getKey().equals("NaN") && eachentry.getKey().contains(message)){
								for (int k = 0; k < eachentry.getValue().size(); k++) {
									res.add(eachentry.getValue().get(k));
									System.out.println();
								}
							}

						}

						
					}
				}

			}
			return res;
	}
	 







	public static String[] messageWithStatus(String s){
		 String[] res = new String[]{"",""};
		 if(s.contains(":")){
			 System.out.println("if has : "+s);
			 res = s.split(":")[1].split("/");
		 }else{
			 res = s.split("/");
		 }
		 if(s.equals("NaN")) return res;
		 System.out.println("Res1:"+res[0]);
		 if(res[0].length()!=0){
			 res[0]+="r";
		 }
		 System.out.println(res.length);
		 if(res.length==2){
			 System.out.println("Res2:"+res[1]);
			 res[1]+="s";
		 }
		 
		 for (int i = 0; i < res.length; i++) {
				System.out.println("MM :"+res[i]);
				
			}
		 return res;
	 }
}
