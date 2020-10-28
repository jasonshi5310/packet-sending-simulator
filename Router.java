package HW4;

/**
* This <code>Router</code> class represents a router in the network, which
* is ultimately a queue.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/
import java.util.*;

public class Router extends LinkedList<Packet> 
  //implements Deque<>, List<>
{
    private LinkedList<Packet> buffer; // The buffer to store Packet.

    /**
    * Returns an instance of Router object.
    **/
    public Router()
    {
        this.buffer = new LinkedList<>();
    }

    /**
    * Adds a new Packet to the end of the router buffer.
    *
    * @param p
    *    The new Packet added.
    *
    **/
    public void enqueue(Packet p)
    {
        buffer.add(p);
    }
        

    /**
    * Removes the first Packet in the router buffer.
    *
    * @return
    *   The Packet removed from the buffer. 
    *
    * @exception NoSuchElementException
    *    If the buffer is empty
    **/
    public Packet dequeue() throws NoSuchElementException
    {
        return buffer.remove();
    }

    /**
    * Returns, but does not remove the first Packet in the router buffer.
    *
    * @return
    *    The first Packet in the buffer without removing it.
    **/
    public Packet peek()
    {
        return buffer.peek();
    }

    /**
    * Returns the number of Packets that are in the router buffer.
    *
    * @return
    *    the number of Packets in the buffer.
    **/
    public int size()
    {
        return buffer.size();
    }

    /**
    * Returns whether the router buffer is empty or not.
    *
    * @return
    *    True if the buffer is empty; otherwise, false.
    **/
    public boolean isEmpty()
    {
        return buffer.size() <= 0;
    }

    /**
    * Returns a String representation of the router buffer in the following
    * format: {[packet1], [packet2], ... , [packetN]}.
    * 
    * @return 
    *    A string representation.
    **/
    @Override
    public String toString()
    {
        String temp = "";
        for (int i = 0; i < buffer.size();i++)
        {
            if (i < 3)
            {
                if (i == buffer.size()-1)
                    temp += buffer.get(i);
                else
                    temp += buffer.get(i) + ", ";
            }
            else if (i == 3)
            {
                temp += " ... ... ..." ;
                break;
            }
        }
        temp = "{" + temp + "}";
        return temp;
    }

    /**
    * This method should loop through the list Intermediate routers. 
    * Find the router with the most free buffer space (contains least 
    * Packets), and return the index of the router. If there are multiple 
    * routers, any corresponding indices will be acceptable. If all router
    * buffers are full, throw an exception. 
    * You must handle this in your code.
    *
    * @param routers
    *    The list of all the intermediate routers.
    *
    * @return
    *    The index of the router.
    * 
    * @exception FullRoutersException
    *    If all the intermediate routers are full
    **/
    public static int sendPacketTo(LinkedList routers) 
      throws FullRoutersException
    {
        int count = 1;
        int index = -1;
        for (int i = 1; i < routers.size(); i++)
        {
            Router temp = (Router) routers.get(i);
            if(Simulator.maxBuff - temp.size() > count)
            {
                index = i;
                count = Simulator.maxBuff - temp.size();
            }
        }
        if (index == -1)
        {
            throw new FullRoutersException();
        }
        return index;
    }
}

// This is the exception throwed If all router buffers are full.
class FullRoutersException extends Exception
{
	public FullRoutersException()
	{

	}
}