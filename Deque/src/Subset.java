
public class Subset {

    public static void main(String[] args){
	int k = Integer.parseInt(args[0]);
	RandomizedQueue<String> rq = new RandomizedQueue<String> ();
	int sze = 0;
	while(!StdIn.isEmpty())  {
	    int rnd = StdRandom.uniform(sze+1);
	    String nope = StdIn.readString();
	    if(rnd < k)	{
		if(rq.size() == k) rq.dequeue();
		rq.enqueue(nope);
	    }	
	    sze++;
	}
	for(int i=0;i<k;i++)
	    StdOut.println(rq.dequeue());	
    }
}
