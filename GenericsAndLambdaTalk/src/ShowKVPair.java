import java.util.ArrayList;



public class ShowKVPair {

	public static void main(String[] args) {
		KVPair<String,Integer> pair1 = new KVPair<>("hello",1);
		KVPair<String,ArrayList<Integer>> pair2 = new KVPair<>("hello",new ArrayList<>());
		// Doesn't compile since ArrayList isn't Comparable
		//KVPair<ArrayList<Integer>, String> pair3 = new KVPair<>(new ArrayList<>(),"hello");
		
		ArrayList<String> mylist = new ArrayList<>();
		mylist.add("hi");
		String s = mylist.get(0);
		
		
	}
	

}
