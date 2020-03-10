package inclass3_21;

import java.util.ListIterator;
import java.util.NoSuchElementException;

public class LinkedList {
	
	private Node first;
	
		class Node
	{
		public Object data;
		public Node next;
	}

	public LinkedList() {
		first=null;
	}
	
	public Object getFirst() {
		if (first==null) {
			throw new NoSuchElementException();
		}
		return first.data;
	}
	
	public void addFirst(Object element) {
		Node newNode = new Node();
		newNode.data=element;
		newNode.next=first;
		first=newNode;
	}
	
	public Object removeFirst() {
		if (first==null) {throw new NoSuchElementException();}
		Object element = first.data;
		first=first.next;
		return element;
	}
	
	public ListIterator listIterator() {
		return new LinkedListIterator();
	}
	class LinkedListIterator implements ListIterator
	{
		private Node position;
		private Node previous;
		private boolean isAfterNext;
		public LinkedListIterator()
		{
			position=null;
			previous=null;
			isAfterNext=false;
		}
		public Object next() {
			if(!hasNext()) {throw new NoSuchElementException();}
			previous=position;
			isAfterNext=true;
			if(position==null) {
				position=first;
			}
			else {
				position=position.next;
			}
			return position.data;
		}
		public boolean hasNext() {
			if (position==null) {
				return first!=null;
			}
			else {
				return position.next!=null;
			}
		}
		public  void remove() {
			if (!isAfterNext) {throw new IllegalStateException();}
			if (position==first) {
				removeFirst();
			}
			else {
				previous.next=position.next;
			}
			position=previous;
			isAfterNext=false;
		}
		public void add(Object element) {
			if(position==null) {
				addFirst(element);
				position=first;
			}
			else {
				Node newNode=new Node();
				newNode.data=element;
				newNode.next=position.next;
				position.next=newNode;
				position=newNode;
			}
			isAfterNext=false;
		}
		public void set(Object element) {
			if(!isAfterNext) {throw new IllegalStateException();}
			position.data=element;
		}
	}


public static void main(String[] args) {
	
	}
}

