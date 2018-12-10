
//Robert Maule Jessica Silverstein
//4/15/2018
//program 5
import java.io.*;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.TreeMap;
public class GFG {
    public static void main (String[] args) throws FileNotFoundException {
        ArrayList<Integer> tempvalues = new ArrayList<Integer>(); //holds value temporarly
    	int MAXDEPTH = 0; //need to be read in
    	int BF = 0;  //needs to be read in
    	String temp; //holds lines
    	int values[] = null; //needs to be read in
    	File file = new File("sampleinput1.txt"); //file
		Scanner ln; //line scanner
		Scanner input = new Scanner(file); //file scanner
     for(int i=0; i<3; i++)    //read in
     {
    	 temp=input.nextLine(); 
    	 if(i==0)
    	 {
    		 temp=temp.substring(11, temp.length()-1);
    		 MAXDEPTH=Integer.parseInt(temp);
    	 }
    	 if(i==1)
    	 {
    		 temp=temp.substring(5, temp.length()-1);
    		 BF=Integer.parseInt(temp);
    	 }
    	 if(i==2)
    	 {
    		 temp=temp.substring(12, temp.length()-2);
    		 temp=temp.replace(",", " ");
    		 ln=new Scanner(temp);
    		 while(ln.hasNext())
    		 {
    		 tempvalues.add(Integer.parseInt(ln.next()));
    		 }
    		 values=new int[tempvalues.size()];
    		 for(int j=0; j<tempvalues.size(); j++)
    		 {
    			 values[j]=tempvalues.get(j);
    		 }
    	 }
    	 
     }
     
     TreeMap<String,Node> map=new TreeMap<String,Node>(); //holds the tree
     int optimal;
     optimal= minimax(0, 0, true, values, MIN, MAX,BF,MAXDEPTH,map);  //call function

    }
	static int MAX = 1000; //max
	static  int MIN = -1000; //min
	static int ct=0; //ct for nodes visited
	 static int minimax(int depth, int nodeIndex, Boolean maximizingPlayer,
	            int values[], int alpha, int beta,int BF,int maxdepth,TreeMap<String,Node> map)
	{
		 ct++;
	    // Terminating condition. i.e leaf node is reached
	    if (depth == maxdepth)
	    {
	    	
	        return values[nodeIndex]; //if at the end
	    }
	    if (maximizingPlayer)
	    {
	        int best = MIN; //best =min
	        
	         
	       
	        for (int i=0; i<BF; i++) //all children
	        {
	            int val = minimax(depth+1, nodeIndex*BF+i, false, values, alpha, beta,BF,maxdepth,map);
	            best = Math.max(best, val); //best value
	            alpha = Math.max(alpha, best); //alpha value
	           //Alpha Beta Pruning
	            map.put(depth+","+nodeIndex,new Node(depth,nodeIndex,alpha,beta,false)); //add to tree map
	            if (beta <= alpha)
	            {
	            	map.put(depth+","+nodeIndex,new Node(depth,nodeIndex,alpha,beta,true)); //adds and sets prunned to true
	                break;
	            }
	            
	        }
	            
	       if(depth==0) //output
	        {
	    	   System.out.println("Alpha Beta Values for non-Terminal Nodes");
	    	   System.out.println("-----------------------------------------");
	    	   System.out.println("Depth Index Alpha Beta");
	    	   System.out.println("-----------------------------------------");
	    	   for (Entry<String, Node> entry : map.entrySet()) {
	    		    String key = entry.getKey();
	    		    Node value = entry.getValue();

	    		    System.out.println(value);
	    		}
	    	   System.out.println("Prunned sub Trees sorted by Depth then Index");
	    	   System.out.println("-----------------------------------------");
	    	   for (Entry<String, Node> entry : map.entrySet()) {
	    		    String key = entry.getKey();
	    		    Node value = entry.getValue();
                    if(value.prunned==true)
	    		    System.out.println(value.basictoString());
	    		}
	    	   System.out.println();
	    	   System.out.println("-----------------------------------------");
	    	   System.out.println("Visited nodes/Total Nodes:"+ct+"/"+sigma(BF,maxdepth));
	       }
	        return best; //return
	        
	    }
	    else
	    {
	        int best = MAX; //best=max
	        
	        // Recur for left and right children
	        for (int i=0; i<BF; i++)
	        {
	            int val = minimax(depth+1, nodeIndex*BF+i,
	                       true, values, alpha, beta,BF,maxdepth,map);
	            //System.out.println("depth: "+(depth+1)+"     Node:"+nodeIndex+"       Value:  "+val+ "     Alpha: "+alpha+ "     Beta: "+beta);
	            best = Math.min(best, val); //best
	            beta = Math.min(beta, best); //beta
	            map.put(depth+","+nodeIndex,new Node(depth,nodeIndex,alpha,beta,false)); //add to map
	            // Alpha Beta Pruning
	            if (beta <= alpha)
	            {
	            	map.put(depth+","+nodeIndex,new Node(depth,nodeIndex,alpha,beta,true)); // add if prunned
	                break;
	            }
	        }
	        return best;
	    }
	    
	}
		public static int sigma(int a,int b) //sigma function for convience
		{
			int sum=0;
			for(int i=0; i<=b; i++)
			{
				sum+=Math.pow(a, i);
			}
			return sum;
		}
	 static class Node implements Comparable<Node> { //nodes
	 
		 public int depth;
		 public int index;
		 public int Alpha;
		 public int Beta;
		 public boolean prunned;
		 public Node(int depth, int index, int Alpha, int Beta,boolean prunned)
		 {
			 this.depth=depth;
			 this.index=index;
			 this.Alpha=Alpha;
			 this.Beta=Beta;
			 this.prunned=prunned;
		 }

		public int compareTo(Node node2) {
			if(this.depth>node2.depth)
				return 1;
			else if(this.depth<node2.depth)
				return -1;
			else {
				if(this.index>node2.index)
					return 1;
				else if(this.index<node2.index)
					return -1;
				return 0;
			}
			
		}
		public String toString() {
			return ""+(depth)+"    "+index+"    "+Alpha+"     "+Beta;
		}
		public String basictoString()
		{
		  return ""+(depth)+"    "+index;
		}
		}
	 
	 
	 
	
}