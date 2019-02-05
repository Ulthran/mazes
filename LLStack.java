import java.util.*;

/**
 * LLStack.java
 * A simple linked-list implementation of a Stack.
 * CS 201, Winter 2019
 *
 * @author Eric Alexander
 * @author Sneha Narayan (modified Jan 31, 2019)
 */
public class LLStack<E> {
    private Node<E> top;
    private int numItems;

    /**
     * Private Node class for internal linked list
     */
    private static class Node<E> {
	private E data;
	private Node<E> next;

	private Node(E item) {
	    data = item;
	    next = null;
	}

	private Node(E item, Node nextNode) {
	    data = item;
	    next = nextNode;
	}
    }

    /**
     * Construct an empty LLStack
     */
    public LLStack() {
	top = null;
	numItems = 0;
    }

    /**
     * Push an item to the top of the LLStack
     *
     * @param item the item to be pushed
     */
    public void push(E item) {
	if (numItems == 0) {
	    top = new Node(item);
     	} else {
	    top = new Node(item, top);
	}

	numItems++;
    }

    /**
     * Return the item at the top of the LLStack
     *
     * @return the top item
     * @throws EmptyStackException if the stack is empty
     */
    public E peek() {
	if (numItems == 0) {
	    throw new EmptyStackException();
	}

	return top.data;
    }

    /**
     * Return and remove the item at the top of the LLStack
     *
     * @return the top item (or null if there isn't one)
     * @throws EmptyStackException if the stack is empty
     */
    public E pop() {
	if (numItems == 0) {
	    throw new EmptyStackException();
	}

	E temp = top.data;
	top = top.next;
	numItems--;
	return temp;
    }

    /**
     * Return the number of items in the LLStack
     *
     * @return the number of items contained in the LLStack
     */
    public int size() {
	return numItems;
    }

    /**
     * Return whether or not the LLStack is empty
     *
     * @return true if LLStack is empty, false otherwise
     */
    public boolean isEmpty() {
	return numItems == 0;
    }

    /**
     * Get a String representation of LLStack
     * with the top at the left-most spot
     *
     * @return String representation of LLStack
     */
    public String toString() {
	StringJoiner sj = new StringJoiner(", ", "[ ", " ]");
	Node<E> currNode = top;
	for (int i = 0; i < numItems; i++) {
	    sj.add(currNode.data.toString());
	    currNode = currNode.next;
	}
	return sj.toString();
    }

    /**
     * Check to see whether a given item is contained in the LLStack
     *
     * @param item the item to look for in the LLStack
     * @return true if item is in the LLStack, false otherwise
     */
    public boolean contains(E item) {
	// Traverse list, looking for item
	Node temp = top;
	while (temp != null) {
	    if (temp.data.equals(item)) {
		return true;
	    }
	    temp = temp.next;
	}

	// If we get here without returning, we didn't find it
	return false;
    }

    // Tester code: Remember, this main method won't run
    // unless LLStack is run with the command java LLStack
    public static void main(String[] args) {
	LLStack<String> fab = new LLStack<String>();
	fab.push("John");
	System.out.println(fab);
	fab.push("Ringo");
	fab.push("George");
	fab.push("Paul");
	System.out.println(fab);
	System.out.format("Peek: %s\n", fab.peek());
	System.out.format("Pop:  %s\n", fab.pop());
	System.out.println(fab);
	if (fab.contains("Paul")) {
	    System.out.println("Found Paul!");
	}
	if (fab.contains("John")) {
	    System.out.println("Found John!");
	}
    }
}
