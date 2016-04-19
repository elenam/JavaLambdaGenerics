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

To call this method, one needs to provide a class that implements the Predicate interface. These classes are automatically generated from a concise anonymous function definition known as a _lambda expression_ (despite not having a keyword "lambda"):

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
We can also use it on an array list of strings to count the number of strings of a specific length: 
```java
ArrayList<String> strings = new ArrayList<>();

countElements(strings, (s) -> (s.length() < 4));
```
You can use `and` method of the Predicate interface to combine prediactes into boolean expressions:
```java
		Predicate<String> notSmall = (s) -> (s.length() >= 2);
		Predicate<String> notLarge = (s) -> (s.length() <= 4);
		int countMediumStrings = countElements(strings, notSmall.and(notLarge));
		System.out.println(countMediumStrings); 
```
Likewise you can use `or` and `negate`. These are default methods provided with the interface (i.e. they actually are implemented). Note that `and` and 'or` are shortcircuiting, so they will be evaluated left-to-right only until the answer is determined. 

Predicate is one of the interfacees provided by the java.util.function package. The package provides interfaces for other cases of functions. Because Java is statically typed and primitive types are different from each other and from object types, there is a variety of interfaces (for instance, `Function` represents and arbitrary function from one object type to another, and `DoubleFunction` is a function from a double to an arbitrary object type. There are also interfaces for functions with two arguments ("bi-functions"). A common method provided by an interface is `apply` (would be used similar to `test` in the example above). Functional interfaces for consumer/producer model operate via methods `accept\get` respectively. 

## Error checking

## Discussion
Functional features in Java are implemented using Interfaces and a _command_ design pattern. Command pattern allows you to pass an object with a predefined method name to any context in a program, and then call the method when needed. This is essentially a way of wrapping a function into an object, thus making it possible to assign it to a variable, pass it to a method, and even possibly modify it on the fly (assuming that the class you are passing around provides a method for modifications). This is a way of providing "first class citizens" behavior in a non-functional language. 

Java implementation provides a large number of predefined interfaces for the Command pattern and syntax extension that allows you to write expressions that generate anonymous classes that implement these interfaces. 

Because lambdas are implemented via Command pattern, all of their functionality was already present in Java before they were introduced. They introduced new syntax and interfaces, but no fundamentally new functionality. 

### Key benefits
* The ability to pass functions around for future use anywhere (for callbacks, storing in data structures, etc.)
* The ability to separate traversal methods from the functions that perform specific operations (traversing a tree or a graph is independent from operations performed in each node). 
* The ability to write concise anonymous functions to perform simple operations. 

### Key issues
* There is a large number of predefined interfaces that use very different ways of calling the function they encapsulate. No uniform syntax/keyword for an arbitrary function call. 
* Because of a large number of possible combinations of primitive types and object types there is a large number of interfaces (a function that takes an integer implements a different interfaces than a function that takes a a boolean or an object). 
* Since the number of arguments in a call is fixed, there are different interfaces for functions that take one and two arguments. 
* There are no general ways of composing functions. 










