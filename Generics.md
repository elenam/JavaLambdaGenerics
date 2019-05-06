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
A generic type is a type that has a type parameter, such as an `ArrayList<E>`. Here `E` is the type parameter: the type of actual elements of the array list. When we create `ArrayList<String>`, we provide an actual concrete type fro teh type parameter. Then Java compiler checks that we are not putting anything other than strings into this array list and also allows our program to use elements that we are taking out of `ArrayList<String>` as strings without further typechcking. 

A _type bound_ is a restriction on a type parameter. In the KVPair the bound for the type K is `Comparable`. There is no bound specified for `V`, so its bound is `Object`.  Specifying a bound allows us to call methods specific to the bound on objects of the generic type. For instance, since the bound for K is `Comparable`, I can call `compareTo` on keys. Since the boound on `V` is `Object`, I cannot call  `compareTo` on values since they are not guaranteed to have this method.  

Java generics were first added in Java 5 and are implemented via _type erasure_. Type erasure means that after all the type checking in a program is done and the program is verified to have correct types for all generics, the type inside a generic class becomes just the type of its bound at runtime.

For KVPair the K type is `Comparable`, the V type is `Object`, so at runtime the pair becomes just a pair of a Comparable and an Object, regardless of what types the program created `KVPair` with: `KVPair<Integer,String>` would have exactly the same code at execution time as `KVPair<String,Vehicle>`.  

This is why `instanceof` isn't parameterized to K, V: `other instanceof KVPair` 

You cannot meaningfully check `x instanceof K`, it will just be checking that `x` is  `Comparable`.

Before erasing the types, the compiler inserts typechecks for objects returned from a parameterized structure, to check at runtime that it is of the right type. 

For instance, if you write:
```java
		ArrayList<String> mylist = new ArrayList<>();
		mylist.add("hi");
		String s = mylist.get(0);
```
The compiler turns this into:
```java
		ArrayList<Object> mylist = new ArrayList<>();
		mylist.add("hi"); // have checked at compilation time that it's a string
		String s = (String) mylist.get(0); 
```
Here the parameter type for the array list is erased to its bound `Object` and a runtime typecheck is inserted for the assignment of the array list element to a `String` variable since this typecheck is required for compatibility with earlier versions of Java. 

### Subtyping and wildcards
If A is a type and B is its subtype, then an `ArrayList<B>` is not a subtype of an `ArrayList<A>`:
I cannot use `add` of an `ArrayList<B>` instead of that of an `ArrayList<A>` because I may be putting As that aren't B's into an `ArrayList<B>`. 
Thus I can't write a method that takes an `ArrayList<Object>` if I want to have a method that would take any array list.  

Wildcard `?` is used to specify "any type": 
```java
public static void printList(ArrayList<?> list) {
    for (Object elem: list)
        System.out.print(elem + " ");
    System.out.println();
}
```
This takes a list of any type. 

You can have bounds on wildcards:
```java
	public static void printList(ArrayList<? extends Serializable> list) {
	    for (Serializable elem: list)
	        serialize(elem);
	}

```
This would take a list of any type that implements `Serializable`.

Sometimes we need to guarantee that the given type is a supertype of the parameter type in a collection. For instance, if I want to add integers to a list,
I want to make sure that the list someone passed to me is of Integers or their supertype (but not, say, of Strings):
```java
public static void addInteger(ArrayList<? super Integer> list) {
    for (int i = 1; i <= 10; i++) {
        list.add(i);
    }
}
```
Here passing `ArrayList<integer>, ArrayList<Number>`, or an `ArrayList<Object>` to the method would work, but passing `ArrayList<String>` doesn't. 
