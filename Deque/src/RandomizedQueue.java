import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int N;

    private class RandomizedQueueIterator implements Iterator<Item>{
    	private int[] index;
   		private int current = 0;
   	
   		public RandomizedQueueIterator() {
   		    index = new int[N];
   		    for(int i=0;i<N;i++) index[i] = i;
   		    StdRandom.shuffle(index);
   		}
   	
   		@Override
   		public boolean hasNext() {
   		    // TODO Auto-generated method stub
   		    return current < N;
   		}
   	
   		@Override
   		public Item next() {
   		    if(!hasNext()) throw new NoSuchElementException(); 
   		    return arr[index[current++]];
   		}
   	
   		@Override
   		public void remove() {
   		    // TODO Auto-generated method stub
   		    throw new UnsupportedOperationException();
   	
   		}

    }
    public RandomizedQueue(){
    	arr = (Item[]) new Object[1];
    	N = 0;
    }

    public boolean isEmpty(){ return N==0;}
    public int size(){ return N;}

    private void resize(int capacity){
    	Item[] temp = (Item[]) new Object[capacity];
   		for(int i=0;i<N;i++)
   		    temp[i] = arr[i];
   		arr = temp;
    }

    public void enqueue(Item item){
    	if(item == null) throw new NullPointerException();
    	
    	if(N == arr.length) resize(2*arr.length);
    	arr[N++] = item; 
    }

    public Item dequeue() {
    	if(isEmpty()) throw new NoSuchElementException();
    	if(arr.length > 1 && N <= arr.length/4) resize(arr.length/2);
    	int remIndex = StdRandom.uniform(N);

    	Item item = arr[remIndex];
    	arr[remIndex] = arr[--N];
    	arr[N] = null;

    	return item;
    }

    public Item sample() {
    	if(isEmpty()) throw new NoSuchElementException();
    	int fill = StdRandom.uniform(N);
    	return arr[fill];
    }


    @Override
    public Iterator<Item> iterator() {
	// TODO Auto-generated method stub
    	return new RandomizedQueueIterator();
    }



    public static void main(String[] args){
    	RandomizedQueue<String> rq = new RandomizedQueue<String>();
    	rq.enqueue("A");
    	rq.enqueue("B");
    	rq.enqueue("C");
    	rq.enqueue("D");
    	rq.enqueue("E");
    
    	Iterator<String> it = rq.iterator();
    	for(int i=0;i<3;i++)
    	    StdOut.println( it.next() );
    }
}



