# Functional features of Java 
### (added in Java 8)

## Motivation and approach
Java functional features were added to allow passing functions to functions and use of anonymous functions. 

For example, you need to: 

  * visit every node of a tree to find all nodes with a given date of last modification,
  * visit every node of the tree to find all nodes with a specific pattern in a name.

Before functional features: you would need to write two traversal methods. 

With functional: write a tree traversal that takes a predicate (a function from the element type to a true/false value) and returns the number of nodes that satisfy the predicate. 

Java has a static type system: all types of variables (including intermediate results) must be declared; they are checked at compilation time. Object types are different from primitive types. This makes it tricky to provide a type-safe way of calling an unpredictable function.

## Java approach

An approach: make function types implement an interface. Some relevant interfaces:

[java.util.function package](https://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html)

For instance, [Predicate interface](https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html) defines a function from any type to a boolean. It provides methods `and,or,negate` to combine the boolean values in a short-circuiting ("lazy") manner. You need to implement a `test` method that returns true/false. 

Let's say I want to count the number of elements in an array list that satisfy a certain condition that's not known ahead of time: 
```java
	public static <T> int countElements(ArrayList<T> theList, Predicate<T> cond) {
		int count = 0;
		for (T element: theList) {
			if (cond.test(element)) {
				count++;
			}
		}
		return count;
	}
```




 









