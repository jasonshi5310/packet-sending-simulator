package HW4;

/**
* This <code>Packet</code> class represents a packet that will be 
* sent through the network.  
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/

public class Packet
{
    // This value is used to assign an id to a newly created packet. 
    // It will start with the value 0, and every time a new packet object
    // is created, increment this counter and assign the value as the id 
    // of the Packet.
    private static int packetCount = 0;
    // A unique identifier for the packet. This will be systematically 
    // determined by using packetCount.
    private int id;
    // The size of the packet being sent. This value is randomly determined
    // by the simulator by using the Math.random() method. 
    private int packetSize;
    // The time this Packet is created.
    private int timeArrive;
    // This variable contains the number of simulation units that it takes 
    // for a packet to arrive at the destination router. The value will start 
    // at one hundredth of the packet size, that is: packetSize/100. At every
    // simulation time unit, this counter will decrease. Once it reaches 0, 
    // we can assume that the packet has arrived at the destination. 
    private int timeToDest; 

    /**
    * Returns an instance of Packet.
    **/
    public Packet()
    {
    	this.id = ++Packet.packetCount;
    }

    /**
    * Returns the packetCount of this Packet object.
    *
    * @return
    *    The packetCount of this Packet object.
    **/
    public static int getPacketCount()
    {
    	return packetCount;
    }

    /**
    * Returns the id of this Packet object.
    *
    * @return
    *    The id of this Packet object.
    **/
    public int getId()
    {
    	return id;
    }
    
    /**
    * Returns the packet size of this Packet object. 
    *
    * @return
    *    The packetSize of this Packet object.
    **/
    public int getPacketSize()
    {
    	return packetSize;
    }
    
    /**
    * Returns the timeArrive of this Packet object.
    *
    * @return
    *    The timeArrive of this Packet object.
    **/
    public int getTimeArrive()
    {
    	return timeArrive;
    }

    /**
    * Returns the time to destination of this Packet object.
    *
    * @return
    *    The timeToDest of this Packet object.
    **/
    public int getTimeToDest()
    {
    	return timeToDest;
    }

    /**
    * Sets a new packetCount for this Packet object.
    *
    * @param newPacketCount
    *    The new packetCount of this Packet object.
    **/
    public static void setPacketCount(int newPacketCount)
    {
    	packetCount = newPacketCount;
    }

    /**
    * Sets a new id for this Packet object.
    *
    * @param newId
    *    The new id of this Packet object.
    **/
    public void setId(int newId)
    {
    	this.id = newId;
    }

    /**
    * Sets a new packetSize for this Packet object.
    *
    * @param newPacketSize
    *    The new packetSize of this Packet object.
    **/
    public void setPacketSize(int newPacketSize)
    {
    	this.packetSize = newPacketSize;
    }

    /**
    * Sets a new timeArrive for this Packet object.
    *
    * @param newTimeArrive
    *    The new time Arrive of this Packet object.
    **/
    public void setTimeArrive(int newTimeArrive)
    {
    	this.timeArrive = newTimeArrive;
    }

    /**
    * Sets a new time to destination for this Packet object.
    *
    * @param newTimeToDest
    *    The new timeToDest of this Packet object.
    **/
    public void setTimeToDest(int newTimeToDest)
    {
    	this.timeToDest = newTimeToDest;
    }

    /**
    * Returns a String that represent the Packet object in the format:
    * [id, timeArrive, timeToDest]
    *
    * @return
    *    A neatly formatted string containing [id, timeArrive, timeToDest].
    **/
    @Override
    public String toString()
    {
    	return String.format("[%d, %d, %d]",id, timeArrive, timeToDest);
    }
}