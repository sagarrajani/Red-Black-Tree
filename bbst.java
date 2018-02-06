/* 
Java program to implement Red Black Tree
*/
import java.io.*;
import java.util.Scanner;

 /* Class RBNode */
 class RBNode
 {    
     RBNode left;
     RBNode right;
     int id;
     int color;
     int count;
 
    /* Parameterized Constructor to initialize a node*/
     public RBNode(int id,int count)
     {
         this(id,count, null, null);
     } 
     /* Parameterized Constructor to initialize a node with black color */
     public RBNode(int id1,int cnt, RBNode l, RBNode r)
     {
         count=cnt;
         left = l;
         right = r;
         id = id1;
         color = 1;
     }    
 }
 
 /* class RBTree */
 class RBTree
 {
     private RBNode node;
     private RBNode parent;
     private RBNode parent2;
     private RBNode parent3;
     private RBNode head;    
     private static RBNode nullnode;
     //Initializer for nullnode
     static 
     {
         nullnode = new RBNode(0,0);
         nullnode.left = nullnode;
         nullnode.right = nullnode;
     }
     //initializes Red - 0 / Black - 1
     static final int Black = 1;    
     static final int Red   = 0;
     // Parameterized Constructor
     public RBTree(int id,int count)
     {
         head = new RBNode(id,count);
         head.left = nullnode;
         head.right = nullnode;
     }
     /* Insert function to insert nodes in Red Black Tree */
     public void insert(int id,int count)
     {
         node = parent = parent2 = head;
         nullnode.id = id;
         nullnode.count=count;
         while (node.id != id)
         {            
             parent3 = parent2; 
             parent2 = parent; 
             parent = node;
             if(id<node.id)
             {
             node=node.left;
             }
             else
             {
             node=node.right;
             }       
             //Check if two RED Children and restructures tree  
             if (node.left.color == Red )
                if(node.right.color == Red)
                    reStructure(id);
         }
         if (node!=nullnode)
             return;
         node = new RBNode(id,count,nullnode, nullnode);
         if (id < parent.id)
             parent.left = node;
         else
             parent.right = node;        
         reStructure(id);
     }
     /* Function to reStructure the tree */
     private void reStructure(int id)
     {
        // Flip color
         node.color = Red;
         node.left.color = Black;
         node.right.color = Black;
        
         // Rotate
         if (parent.color == Red)   
         {
             parent2.color = Red;
             if (id < parent2.id != id < parent.id)
                 parent = rotate(id,parent2);
             node = rotate(id, parent3 );
             node.color = Black;
         }
         //make root black
         head.right.color = Black; 
     }      
     /* Function to rotate the tree */
     private RBNode rotate(int id, RBNode parent)
     {
         if(id < parent.id)
         {
            if(id < parent.left.id)
                return parent.left=rotateWithLeftChild(parent.left);
            else
                return parent.left=rotateWithRightChild(parent.left);
         }
         else
         {
            if(id < parent.right.id)
                return parent.right=rotateWithLeftChild(parent.right);
            else
                return parent.right=rotateWithRightChild(parent.right);
         }
     }
     /* Function to rotate with left child */
     private RBNode rotateWithLeftChild(RBNode node)
     {
         RBNode node1 = node.left;
         node.left = node1.right;
         node1.right = node;
         return node1;
     }
      /* Function to rotate with Right child */
     private RBNode rotateWithRightChild(RBNode node)
     {
         RBNode node1 = node.right;
         node.right = node1.left;
         node1.left = node;
         return node1;
     }
     
     /* Increase Function to increase count of id by count */
     public int increase(int id,int count)
     {
        RBNode node;
        node=head.right;
        boolean ifFound=false;
        while(node!=nullnode && !ifFound)
        {
            int nodeid = node.id;
             if (id < nodeid)
                 node = node.left;
             else if (id > nodeid)
                 node = node.right;
             else
             {
                 ifFound = true;
                 // increases count of id if found
                 node.count=node.count+count;
                 break;
             }
        }
        if(ifFound==false)
        {
            //inserts id if not present
            insert(id,count);
        }
        // Returns increased
        return node.count;
     }
   
   /* Function to print count of id */
    public int count(int id)
    {
         RBNode node;
         node=head.right;
         boolean found = false;
         while ((node != nullnode) && !found)
         {
             int nodeid = node.id;
             if (id < nodeid)
                 node = node.left;
             else if (id > nodeid)
                 node = node.right;
             else
             {
                found = true;
                //if found return count
                break; 
             }
         }
         if(found==false)
         {
            //prints 0 if not found
            node.count=0;
         }
         //returns count
         return node.count;
     }


     public int totalcount=0;

     /*Functon to call inrange(x,y,z)*/
     public int inrange(int id1, int id2)
     {
        return inrange(head.right,id1,id2);
     }
     /* Function to add count of id's between id1 and id2 */
     private int inrange(RBNode node,int id1,int id2)
     {

        //if node is null returns 0
        if(node == nullnode)
        return 0;
        if(node.id<=id1&&node.id>=id2)
        {}
        else
        {
            inrange(node.left, id1, id2);
        }
        if(node.id >= id1 && node.id <= id2)
        {
               // Adds count of id in range
               totalcount=totalcount+node.count;
        }
        if(node.id>=id2&&node.id<=id1)
        {}
        else
        {
            inrange(node.right,id1,id2);
        }
        //returns total count of id's in range
        return totalcount;
    }

     /* Reduce function to reduce count of id by count */
     public int reduce(int id,int count)
     {
         RBNode node;
         node=head.right; 
         boolean ifFound = false;
         while ((node != nullnode) && !ifFound)
         {
             int nodeid = node.id;
             if (id < nodeid)
                 node = node.left;
             else if (id > nodeid)
                 node = node.right;
             else
             {
                 ifFound= true;
                 // increases count of id by count
                 node.count=node.count-count;
                 //if count is less than 0 
                 if(node.count<=0)
                 {
                    // Lazy delete 
                    node=nullnode; 
                    return 0;
                 }
                 break;  
             }  
         }
         if(ifFound==false)
             {
                //if not found prints 0
                node.count=0;
             }

          // returns reduced count of id  
         return node.count;
     }





    public RBNode closest ;
    public int distance;
    public RBNode current_node;
    /* Function to find closest */
    public int findClosest(int id)
    {   
        RBNode node;
        node=head.right;
        if (node == nullnode)
            return 0;
        closest = node;
        distance = Math.abs(id - closest.id);
        current_node = node;
        while (current_node != nullnode)
        {
            if (id == current_node.id)
                return current_node.id;
            if (Math.abs(id - current_node.id) < distance)
            {
                closest = current_node;
                distance = Math.abs(id - current_node.id);
            }
            if (id < current_node.id)
                current_node = current_node.left;
            else
                current_node = current_node.right;
        }
        //returns closest's id
        return closest.id;
            
    }

    /* Find minimum in left subtree */
    public int findMinimum(RBNode node)
    {
        if (node == nullnode)
            return 0;
      
        if (node.left != nullnode)
            return findMinimum(node.left);
        //returns minimum node
        return node.id;
    }
    /* Function to find next of id */
    public int next(int id)
    {
            RBNode node;
            node=head.right;
            boolean found = false;
             while ((node != nullnode) && !found)
             {
                 int nodeid = node.id;
                 if (id < nodeid)
                     node = node.left;
                 else if (id > nodeid)
                     node = node.right;
                 else
                 {
                     found = true;
                     break;
                 }
             }
             // if element is present
            if(found == true)
            {
             if( node.right != nullnode)
                //calls to findMinimum
             return findMinimum(node.right);
     
                RBNode nxt = nullnode;
                RBNode newhead=head;
                while (newhead != nullnode)
                {
                    if (node.id < newhead.id)
                    {
                        nxt = newhead;
                        newhead = newhead.left;
                    }
                    else if (node.id > newhead.id)
                        newhead = newhead.right;
                    else
                       break;
                }
                if(nxt.right==nullnode)
                {
                    return 0;
                }
                else
                    //returns next's id
                return nxt.id;
            }
            
            else 
            {
                //finds next of closest
                 int closest  = findClosest(id);
                 if (closest < id)
                 {
                    return next(closest);
                 }
                else 
                {
                    return closest;
                }
                 
            }        
    }


    /*Find maximum in right subtree */
    public int findMaximum(RBNode node)
    {
        if (node == nullnode)
             return 0;
        if (node.right != nullnode)
            return findMaximum(node.right);

        return node.id;
    }
    /* Function to find previous of id */
    public int previous(int id)
    {
            RBNode node;
            node=head.right;
            boolean found = false;
             while ((node != nullnode) && !found)
             {
                 int nodeid = node.id;
                 if (id < nodeid)
                     node = node.left;
                 else if (id > nodeid)
                     node = node.right;
                 else
                 {
                     found = true;
                     break;
                 }
             }
             //if element is present
            if(found == true)
            {
                if( node.left != nullnode )
                    //call to findMaximum
                    return findMaximum(node.left);
      
                RBNode prev = nullnode;
                RBNode head1 = head;
              
                while (head1 != nullnode)
                {
                    if (node.id > head1.id)
                    {
                        prev = head1;
                        head1 = head1.right;
                    }
                    else if (node.id < head1.id)
                        head1 = head1.left;
                    else
                       break;
                }
                //prints previous id
                return prev.id;
            }

            else 
            {
                //finds previous of closest
                 int closest  = findClosest(id);
                 if (closest < id)
                 {
                    return closest;
                 }
                 else 
                 {
                    return previous(closest);
                 }
                 
             }
             
            
    }

    /* Search function to search for id */
     public boolean search(int id)
     {
        RBNode node;
        node=head.right;
         boolean ifFound = false;
         while ((node != nullnode) && !ifFound)
         {
             int nodeid = node.id;
             if (id < nodeid)
                 node = node.left;
             else if (id > nodeid)
                 node = node.right;
             else
             {
                 ifFound = true;
                 //Prints ID and Count of searched id
                 System.out.println(node.id+" "+node.count);
                 break;
             }
         }
         if(ifFound==false)
         {
            return ifFound;
         }

         // Returns true if found else false
         return ifFound;   
     }
 }
 
 /* Main class bbst */
 public class bbst
 {
     public static void main(String[] args) 
    {
        

         String line = null;

            try {

                // Reads test file 

                FileReader file = new FileReader(args[0]); 

                BufferedReader buffeRedReader = new BufferedReader(file);
               
               //Counting total number of nodes

                int n=Integer.parseInt(buffeRedReader.readLine()); 

                //Creates object of RBTree
                RBTree rbt=new RBTree(Integer.MIN_VALUE,Integer.MIN_VALUE); 

                    // Reads file line by line until null
                    while((line = buffeRedReader.readLine()) != null) 
                    {   
                        // Splits line using space
                        String b[]=line.split(" ");      
                        int arg=Integer.parseInt(b[0]);  
                        int arg1=Integer.parseInt(b[1]); 
                        // passes arg & arg2 into insert function
                        rbt.insert(arg,arg1);            
                        
                    }      
                     // rbt.inorder();
                buffeRedReader.close();    
                boolean bl;
                //rbt.inorder();
                Scanner input = new Scanner(System.in);
                // Reads Commands file line by line until null
                    while (input.hasNextLine())  
                    {
                            line=input.nextLine();
                            // Splits the command in function name , argument 1, argument 2
                            String a[]=line.split(" "); 
                            // Checks if first argument matches increases
                         if(a[0].matches("increase"))   
                         {
                            int arg=Integer.parseInt(a[1]);      // id = arg
                            int arg1=Integer.parseInt(a[2]);     //count = arg1
                            // Prints count of the Id passed
                            System.out.println(rbt.increase(arg,arg1));  
                         }
                         // Checks if first argument matches count
                         else if(a[0].matches("count"))  
                         {
                            int arg=Integer.parseInt(a[1]);   // id = arg
                            // Prints the count of the Id passed
                            System.out.println(rbt.count(arg)); 
                         }
                         // Checks if first argument matches reduce
                         if(a[0].matches("reduce"))  
                         {
                            int arg=Integer.parseInt(a[1]);  //id = arg
                            int arg1=Integer.parseInt(a[2]);  // count=arg1
                            // Prints the count of the id after reduce
                            System.out.println(rbt.reduce(arg,arg1)); 
                         }
                         // Checks if first argument matches inrange
                         if(a[0].matches("inrange"))   
                         {
                            int arg=Integer.parseInt(a[1]);  
                            int arg1=Integer.parseInt(a[2]); 
                            // Calculates total of counts between Id1 and Id2
                            System.out.println(rbt.inrange(arg, arg1)); 
                            rbt.totalcount=0;
                         }
                         // Checks if first argument matches next
                         if(a[0].matches("next"))  
                         {
                            int arg=Integer.parseInt(a[1]);
                            //Prints next id
                            bl=rbt.search(rbt.next(arg));   
                            // If id not present prints 0 0
                            if(bl==false)                   
                            {
                                System.out.println("0 0");  
                            }
                         }
                         // Checks if first argument matches previous
                         if(a[0].matches("previous"))   
                         {
                            int arg=Integer.parseInt(a[1]);
                            // Prints previous id
                            bl=rbt.search(rbt.previous(arg));   
                            // If id not present prints 0 0
                            if(bl==false)                       
                            {
                                System.out.println("0 0");
                            }
                         }
                         // Checks if first argument matches quit
                         if(a[0].matches("quit"))  
                         {
                             // Exit
                            System.exit(0);       
                         }



                    }
                
            }
            /* Throws Exception if unable to open file or error reading file */
            catch(Exception ex) 
            {
                System.out.println("Unable to open file OR error reading file'" + args[0] + "'");                
            }
            
   
        }

 }