package HW4;

/**
* This <code>Simulator</code> class contains the main method 
* that tests your simulation.
*
* @author Minqi Shi
* email: minqi.shi@stonybrook.edu
* Stony Brook ID: 111548035
**/
import java.util.*;

public class Simulator extends LinkedList<Router> 
  //implements Deque<>, List<>
{
    private Router dispatcher = new Router();// Level 1 router
    private LinkedList<Router> routers = new LinkedList<>(); // Level 2 routers

    // This contains the running sum of the total time each packet is 
    // in the network. The service time per packet is simply the time
    // it has arrived to the Destination minus the time when the packet
    // was created. When a packet counter reaches 0, dequeue it from the
    // router queue and add the time to the total time. Ignore the leftover
    // Packets in the network when simulation time is up.
    private int totalServiceTime;

    // This contains the total number of packets that has been successfully
    // forwarded to the destination. When a packet counter reaches 0, dequeue
    // it from the router queue and increase this count by 1.
    private int totalPacketsArrived;
    
    // This records the number of packets that have been dropped due to a
    // congested network. Note: this can only happen when 
    // sendPacketTo(Collection routers) throws an exception.
    private int packetsDropped;
    
    // The probability of a new packet arriving at the Dispatcher.
    private double arrivalProb;
    // The number of Intermediate routers in the network.
    private int numIntRouters;
    // The maximum number of Packets a Router can accommodate for.
    private int maxBufferSize;
    
    // The maximum number of Packets a Router can accommodate for. 
    // This variable is static so it can be called in Router class.
    static int maxBuff;
    private int minPacketSize; // The minimum size of a Packet.
    private int maxPacketSize; // The maximum size of a Packet.
    
    // The maximum number of Packets the Destination router can accept 
    // at a given simulation unit.
    private int bandwidth; 
    private int duration; // The number of simulation units
    //A maximum of 3 packets can arrive per time unit.
    public static final int MAX_PACKETS = 3;

    /** 
    * This runs the simulator as described in the specs. Calculate and 
    * return the average time each packet spends within the network.
    * 
    * @return 
    *    The average time each packet spends.
    * 
    * @exception InputMismatchException
    *    If there's a wrong input.
    **/
    public double simulate() throws InputMismatchException
    {
        totalServiceTime = 0;
        totalPacketsArrived = 0;
        packetsDropped = 0;
        Packet.setPacketCount(0);
        Scanner stdin = new Scanner(System.in);
        System.out.println("Starting simulator...");
        System.out.print("Enter the number of Intermediate routers: ");
        numIntRouters = stdin.nextInt();
        routers.add(null);
        for (int i = 1; i <= numIntRouters ; i++) 
        {
            Router temp = new Router();
            routers.add(temp);
        }
        System.out.print("Enter the arrival probability of a packet: ");
        arrivalProb = stdin.nextDouble();
        System.out.print("Enter the maximum buffer size of a router: ");
        maxBufferSize = stdin.nextInt();
        System.out.print("Enter the minimum size of a packet: ");
        minPacketSize = stdin.nextInt();
        System.out.print("Enter the maximum size of a packet: ");
        maxPacketSize = stdin.nextInt();
        maxBuff = maxBufferSize;
        System.out.print("Enter the bandwidth size: ");
        bandwidth = stdin.nextInt();
        System.out.print("Enter the simulation duration: ");
        duration = stdin.nextInt();
        System.out.println();
        int time = 1;
        Router waitlist = new Router();
        while(time <= duration)
        {
            System.out.println("Time: " + time);
            for (int i = 0; i < MAX_PACKETS;i++)
            {
                if (Math.random() < arrivalProb)
                {
                    Packet temp = new Packet();
                    temp.setTimeArrive(time);
                    temp.setPacketSize(randInt(minPacketSize,maxPacketSize));
                    temp.setTimeToDest(temp.getPacketSize()/100);
                    dispatcher.enqueue(temp);
                    System.out.println("Packet " + temp.getId()
                      +" arrives at dispatcher with size " 
                      + temp.getPacketSize() + ".");
                }
            }
            if (dispatcher.isEmpty()) 
            {
                System.out.println("No packets arrived.");
            }
            while(dispatcher.size() > 0)
            {
                Packet temp = dispatcher.dequeue();
                try
                {
                    int position = Router.sendPacketTo(routers);
                    if (routers.get(position).isEmpty()) 
                    {
                        temp.setTimeToDest(temp.getTimeToDest()+1);
                    }
                    routers.get(position).enqueue(temp);
                    System.out.println("Packet "+ temp.getId() 
                      +" sent to Router "+position+".");
                }
                catch(FullRoutersException fre)
                {
                    System.out.println("Network is congested. "
                      + "Packet "+temp.getId() +" is dropped.");
                    packetsDropped++;
                }
            }
            for (int i = 1; i < routers.size() ; i++) 
            {
                Packet temp = routers.get(i).peek();
                if (temp != null && temp.getTimeToDest() != 0) 
                {
                    temp.setTimeToDest(temp.getTimeToDest()-1);
                    if (temp.getTimeToDest()==0) 
                    {
                        waitlist.enqueue(routers.get(i).peek());
                    }
                }
            }
            for (int i = 0; i < bandwidth; i++)
            {
                if(waitlist.size()>0)
                {
                    Packet temp = waitlist.dequeue();
                    for (int j = 1; j < routers.size();j++)
                    {
                        if (temp == routers.get(j).peek())
                            routers.get(j).dequeue();
                    }
                    int timeDuration = time - temp.getTimeArrive();
                    System.out.println("Packet " + temp.getId()
                      + " has successfully reached its destination: +" 
                      + timeDuration);
                    totalPacketsArrived++;
                    totalServiceTime += timeDuration;
                }
            }
            for (int i = 1;i < routers.size(); i++)
            {
                System.out.print("R"+i+": ");
                System.out.println((Router)routers.get(i));
            }
            time++;
            System.out.println();
        }
        return totalServiceTime*1.0/totalPacketsArrived;
    }

    // This will be your helper method that can generate a random number 
    // between minVal and maxVal, inclusively. Return that randomly 
    // generated number.
    private int randInt(int minVal, int maxVal)
    {
        return (int) (Math.random()*(maxVal-minVal+1) + minVal);
    }
    
    /** 
    * The main() method will prompt the user for inputs to the simulator.
    * It will then run the simulator, and outputs the result. Prompt the 
    * user whether he or she wants to run another simulation.
    **/
    public static void main(String[] args)
    {
        String conti = "y";
        Scanner stdin = new Scanner(System.in);
        while(!conti.equals("n"))
        {
            try
            {
                if (conti.equals("y")) 
                {
                    Simulator simu = new Simulator();
                    double timeAve = simu.simulate();
                    System.out.println("Simulation ending...");
                    System.out.println("Total service time: "
                      +simu.totalServiceTime);
                    System.out.println("Total packets served: "
                      +simu.totalPacketsArrived);
                    System.out.println("Average service time per packet: "
                      +String.format("%.2f", timeAve));
                    System.out.println("Total packets dropped: "
                      +simu.packetsDropped);
                    System.out.println();
                }
                System.out.print("Do you want to try another simulation? "
                  +"(y/n): ");
                conti = stdin.nextLine();
                if (conti.equals("n"))
                {
                    System.out.println("Program terminating successfully...");
                    break;
                }
                else if(conti.equals("y"));
                else
                    throw new IllegalArgumentException();
            }
            catch(InputMismatchException ime)
            {
                System.out.println("Worng type entered "
                  + "for this entry. Please enter the values again.");
                System.out.println();
            }
            catch(IllegalArgumentException ime)
            {
                System.out.println("Unrecongnized command. "
                  +"Please enter again to decide whether to"
                  + " try another simulation.");
                System.out.println();
            }
        }
    }
}