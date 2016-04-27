import java.util.Comparator;
import java.util.PriorityQueue;


public class PQWithComparator {
	public static void main(String args[]) {
		PriorityQueue<Animal> pq = new PriorityQueue<>(new ComparatorByName());	
		pq.add(new Animal("lion", 400));
		pq.add(new Animal("zebra", 900));
		pq.add(new Animal("mouse", 0.0001));
	
		for (Animal a: pq){
			System.out.println(a.getName());
		}
		
		// equivalent definition with "lambda" expression
		PriorityQueue<Animal> pq1 = new PriorityQueue<>(
				(Animal a1, Animal a2)->a1.getName().compareTo(a2.getName()));	
		
		pq1.add(new Animal("lion", 400));
		pq1.add(new Animal("zebra", 900));
		pq1.add(new Animal("mouse", 0.0001));
	
		for (Animal a: pq1){
			System.out.println(a.getName());
		}
		
		Comparator<Animal> weightComparator = (Animal a1, Animal a2)-> (int) ((a1.getWeight() - a2.getWeight()))*1000;
		PriorityQueue<Animal> pq2 = new PriorityQueue<Animal>(weightComparator);
				
		
		pq2.add(new Animal("lion", 400));
		pq2.add(new Animal("zebra", 900));
		pq2.add(new Animal("mouse", 0.0001));
	
		for (Animal a: pq2){
			System.out.println(a.getName());
		}
		
		PriorityQueue<Animal> pq3 = new PriorityQueue<Animal>(weightComparator.reversed());
						
		pq3.add(new Animal("lion", 400));
		pq3.add(new Animal("zebra", 900));
		pq3.add(new Animal("mouse", 0.0001));
	
		for (Animal a: pq3){
			System.out.println(a.getName());
		}
		
		
	}
}

class ComparatorByName implements Comparator<Animal> {

	@Override
	public int compare(Animal animal1, Animal animal2) {
		return animal1.getName().compareTo(animal2.getName());
	}
	
}


