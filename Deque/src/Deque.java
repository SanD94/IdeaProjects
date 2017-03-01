import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque <Item> implements Iterable<Item>{
	private int N;
	private Node first;
	private Node last;
	
	private class Node {
		Node next = null;
		Node prev = null;
		Item item;
	}
	
	public Deque(){
		first = null;
		last = null; 
		N = 0;
	}
	
	public boolean isEmpty(){
		return first == null;
	}
	
	public int size(){
		return N;
	}
	
	public void addFirst(Item item){
		if(item == null) throw new NullPointerException();
		if(isEmpty()){
			first = new Node();
			last = first;
			first.item = item;
			
		}else{
			Node now = new Node();
			now.item = item;
			now.next = first;
			first.prev = now;
			first = now;
		}
		N++;
	}
	
	public void addLast(Item item){
		if(item == null) throw new NullPointerException();
		if(isEmpty()){
			last = new Node();
			first = last;
			last.item = item;
		}else {
			Node now = new Node();
			now.item = item;
			now.prev = last;
			last.next = now;
			last = now;
		}
		N++;
	}
	
	public Item removeFirst(){
		if(isEmpty()) throw new NoSuchElementException();
		Item item = first.item;
		first = first.next;
		if(first != null ){
			first.prev.next = null;
			first.prev = null;
		} else
			last = null;
		N--;
		return item;
	}

	public Item removeLast(){
		if(isEmpty()) throw new NoSuchElementException();
		Item item = last.item;
		last = last.prev;
		if(last != null){
			last.next.prev = null;
			last.next = null;
		} else 
			first = null;
		
		N--;
		return item;
	}
	
	@Override
	public Iterator<Item> iterator() {
		// TODO Auto-generated method stub
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		

		@Override
		public boolean hasNext() {	return current != null;	}
		
		@Override
		public void remove() { throw new UnsupportedOperationException(); }
		
		@Override
		public Item next() {
			if (!hasNext()) throw new NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	
	public static void main(String[] args){
		
	}

}
