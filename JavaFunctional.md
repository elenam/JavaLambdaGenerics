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
Here `countElements` is a function that takes an array list of type `T` and a predicate `cond` that works on `T` and returns the number of elements in the array list for which the predicate returns `true`. 

The expression `cond.test(element)` calls that method `test` declared in the Predicate interface on an array list element. 

To call this method, one needs to provide a class that implements the Predicate interface. These classes are automatically generated from a concise anonymous function definition:

```java
(int n) -> (n % 2 == 0)
```
is an anonymous function that takes an int and returns `true` if it's even and `false` otherwise. In cases when the type of the parameter is uniquely determined from the body of the function, we don't need to specify the type:
```java
(n) -> (n % 2 == 0)
``` 
This works because `%` is defined only on ints. 

Putting it all together, we can count even numbers in an array list of ints as follows:
```java
ArrayList<Integer> numbers = new ArrayList<>();

countElements(numbers, (n) -> (n % 2 == 0));
``` 
If we want to count odd numbers, we just change the anonymous function: 
```java
countElements(numbers, (n) -> (n % 2 != 0));
```





 









