# Java Generics
### An overview of implementation of Java generic types

Generic types are types with type parameters
```java
/**
 * A class for storing key-value pairs generated during
 * traversals of a Binary Search Tree
 * mypair.key gives the key, mypair.value gives the value
 * 
 * @author Elena Machkasova
 *
 * @param <K> - key type (must be comparable to itself)
 * @param <V> - value type 
 */

public class KVPair<K extends Comparable<K>, V> {
	final public K key;
	final public V value;
	
	public KVPair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public boolean equals(Object other) {
		if (! (other instanceof KVPair)) return false;
		KVPair<K,V> otherPair = (KVPair<K,V>) other;
		return (key.equals(otherPair.key) && value.equals(otherPair.value));
	}
}
```
Usage:

```java
		KVPair<String,Integer> pair1 = new KVPair<>("hello",1);
		KVPair<String,ArrayList<Integer>> pair2 = new KVPair<>("hello",new ArrayList<>());
		// Doesn't compile since ArrayList isn't Comparable
		//KVPair<ArrayList<Integer>, String> pair3 = new KVPair<>(new ArrayList<>(),"hello");
```

### Type erasure
Java generics are implemented via type erasure: after all the type chcking is done, the type inside 
a generic class becomes just the bound type.

For KVPair the K type is `Comparable`, the V type is `Object`. 

This is why instanceof isn't parameterized to K, V: `other instanceof KVPair` 

You cannot meaningfully check 'x instanceof K`, it will just be checking that `x` is  `Comparable`.

When an object is returned from a parameterized structure, a typecheck is inserted to check at runtime that it is of the right type. 

You write:
```java
		ArrayList<String> mylist = new ArrayList<>();
		mylist.add("hi");
		String s = mylist.get(0);
```
The compiler sees:
```java
		ArrayList<Object> mylist = new ArrayList<>();
		mylist.add("hi"); // checks at compilation time
		String s = (String) mylist.get(0); 
```

### Subtyping and wildcards
If A is a type and B is its subtype, then an `ArrayList<B>` is not a subtype of an `ArrayList<A>`:
I cannot use `add` of an `ArrayList<B>` instead of that of an `ArrayList<A>` because I may be putting As that aren't B's. 
Thus I can't write a method that takes an `ArrayList<Object>` so that it would take any array list.  

Wildcard `?` is used to specify "any type": 
```java
public static void printList(List<?> list) {
    for (Object elem: list)
        System.out.print(elem + " ");
    System.out.println();
}
```
This takes a list of any type. 

You can have bounds on wildcards:
```java
	public static void printList(List<? extends Serializable> list) {
	    for (Serializable elem: list)
	        serialize(elem);
	}

```
This would take a list of any type that implements `Serializable`.

Sometimes we need to guarantee that the given type is a supertype of the collection type. For instance, if I want to add integers to a list,
I want to make sure that the list someone passes to me is of Integers or their supertype (but not, say, of Strings):
```java
public static void addNumbers(List<? super Integer> list) {
    for (int i = 1; i <= 10; i++) {
        list.add(i);
    }
}
```
 
