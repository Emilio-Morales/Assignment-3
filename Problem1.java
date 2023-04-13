import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.*;

public class Problem1 
{
	static List<Integer> gifts;// Unordered gifts
	static ChainOfPresents<Node> presentChain = new ChainOfPresents<Node>();// Ordered gifts
	static Servant[] servants = new Servant[4];
	public static void main(String[] args) throws InterruptedException 
	{
		// Creating the bag of presents.  
		ArrayList<Integer> tempGifts = new ArrayList<Integer>();

		// Going up to 500000 because to simulate 500000 presents
		for (int i = 0; i < 500000; i ++)
		{
			tempGifts.add(i);
		}
			
		// Shuffles the gifts so that the threads remove gifts from the bag
		// in no particular order. 
		Collections.shuffle(tempGifts);
		
		// Ensures that multiple threads can alter this.
		gifts = Collections.synchronizedList(tempGifts);
	
		for (int i = 0; i < 4; i++)
		{
			Servant temp = new Servant(i);
			temp.start();
			servants[i] = temp;
		}
		
		for (Servant servant: servants)
		{
			servant.join();
		}
	
		System.out.println("We have written thank you cards for all presents");
		
	}// END OF Main
}// END OF Problem1

// Node class based off of the textbook.
// The "marked" boolean is used to determine whether node is in the set. 
// Marked nodes are not in the set.
class Node
{
	int tag;
	Node next;
	Lock lock;
	boolean marked;
	
	Node (int newTag)
	{
		tag = newTag;
		next = null;
		marked = false;
		lock = new ReentrantLock();
	}
	
	void lock()
	{
		lock.lock();
	}
	
	void unlock()
	{
		lock.unlock();
	}
	
}// END OF Node

// Based off of the LazyList provided in the textbook
class ChainOfPresents<T>
{
	Node head;
	int size;
	
	public ChainOfPresents()
	{
		head = new Node(Integer.MIN_VALUE);
		head.next = new Node(Integer.MAX_VALUE);
		size = 0;
	}
	
	public int size()
	{
		return size;
	}
	
	private boolean validate(Node pred, Node curr) 
	{
		return !pred.marked && !curr.marked && pred.next == curr;
	}
	
	public boolean add(int present) 
	{
		int tag = present;
		while (true) 
		{
			Node pred = head;
			Node curr = head.next;
			while (curr.tag < tag) 
			{
				pred = curr; 
				curr = curr.next;
			}
			pred.lock();
			try 
			{
				curr.lock();
				try 
				{
					if (validate(pred, curr)) 
					{
						if (curr.tag == tag) 
						{
							return false;
						} 
						else 
						{
							Node node = new Node(tag);
							node.next = curr;
							pred.next = node;
							size++;
							return true;
						}
					}
				} 
				finally 
				{
					curr.unlock();
				}
			} 
			finally
			{
				pred.unlock();
			}
		}
	}// END OF add

	public boolean remove(int present) 
	{
		int tag = present;
		while (true) 
		{
			Node pred = head;
			Node curr = head.next;
			
			while (curr.tag < tag) 
			{
				pred = curr; 
				curr = curr.next;
			}
			pred.lock();
			try 
			{
				curr.lock();
				try 
				{
					if (validate(pred, curr)) 
					{
						if (curr.tag != tag) 
						{
							return false;
						} 
						else 
						{
							curr.marked = true;
							pred.next = curr.next;
							size--;
							return true;
						}
					}
				} 
				finally 
				{
					curr.unlock();
				}
			} 
			finally 
			{
				pred.unlock();
			}
		}
	}
	
	// Experimental
	public boolean removeHead() 
	{
			Node pred = head;
			Node curr = head.next;
			
			pred.lock();
			try 
			{
				curr.lock();
				try 
				{
					if (validate(pred, curr)) 
					{
						curr.marked = true;
						pred.next = curr.next;
						size--;
						return true;
					}
				} 
				finally 
				{
					curr.unlock();
				}
			} 
			finally 
			{
				pred.unlock();
			}
		
			return false;	
	}

	public boolean contains(int present) 
	{
		int tag = present;
		Node curr = head;
		while (curr.tag < tag)
			curr = curr.next;
		return curr.tag == tag && !curr.marked;
	}
	
	public boolean isEmpty()
	{
		if (size == 0)
		{
			return true;
		}
		
		return false;
	}
}// END OF ChainOfPresents

class Servant extends Thread
{
	int threadNum;
	
	public Servant(int num)
	{
		threadNum = num;
	}
	
	// If returns -1, then no gift was grabbed because none are left. 
	static synchronized int grabGift()
	{
		int length = Problem1.gifts.size();
		int gift = -1;
		
		if (!Problem1.gifts.isEmpty())
		{
			gift = Problem1.gifts.get(length - 1);
		}
	
		return gift;
	}
	
	@Override
	public void run()
	{
		while (!Problem1.presentChain.isEmpty() && !Problem1.gifts.isEmpty())
		{
			int action = new Random().nextInt(3) + 1;
			
			switch(action) 
			{
				// Put present in chain
				case 1:
					
					int gift = grabGift(); 
					if (gift != -1)
					{
						Problem1.presentChain.add(gift);
					}
				
					break;
				
				// Remove present from chain
				case 2:
					if (!Problem1.presentChain.removeHead())
						System.out.println("Did not remove!");
					break;
					
				// Minotaur's request to check for a present	
				case 3:
					int giftToLookFor = new Random().nextInt(5000000);
					boolean result = Problem1.presentChain.contains(giftToLookFor);
					
					if (result)
						System.out.println("Found gift with tag:" + giftToLookFor);
					else
						System.out.println("Did not find gift with tag: " + giftToLookFor);
					
					break;
					
			}// END OF switch
		}// END OF While
		
		System.out.println("Done!");
	}
	
}// END OF Servant



















