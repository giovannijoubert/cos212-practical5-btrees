@SuppressWarnings("unchecked")
class BTreeNode<T extends Comparable<T>> {
	boolean leaf;
	int keyTally;
	Comparable<T> keys[];
	BTreeNode<T> references[];
	int m;
	static int level = 0;

	// Constructor for BTreeNode class
	public BTreeNode(int order, boolean leaf1)
	{
    		// Copy the given order and leaf property
		m = order;
    		leaf = leaf1;
 
    		// Allocate memory for maximum number of possible keys
    		// and child pointers
    		keys =  new Comparable[2*m-1];
    		references = new BTreeNode[2*m];
 
    		// Initialize the number of keys as 0
    		keyTally = 0;
	}

	// Function to print all nodes in a subtree rooted with this node
	public void print()
	{
		level++;
		if (this != null) {
			System.out.print("Level " + level + " ");
			this.printKeys();
			System.out.println();

			// If this node is not a leaf, then 
        		// print all the subtrees rooted with this node.
        		if (!this.leaf)
			{	
				for (int j = 0; j < 2*m; j++)
    				{
        				if (this.references[j] != null)
						this.references[j].print();
    				}
			}
		}
		level--;
	}

	// A utility function to print all the keys in this node
	private void printKeys()
	{
		System.out.print("[");
    		for (int i = 0; i < this.keyTally; i++)
    		{
        		System.out.print(" " + this.keys[i]);
    		}
 		System.out.print("]");
	}

	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	// Function to insert the given key in tree rooted with this node
	public BTreeNode<T> insert(T key)
	{
			// Your code goes here
			// If tree is empty 
			// If tree is not empty 
			BTreeNode<T> root = this; 
				// If root is full, then tree grows in height 
				if (root.keyTally == 2*m-1) 
				{ 
					// Allocate memory for new root 
					BTreeNode<T> s = new BTreeNode<T>(m, false); 
		  
					// Make old root as child of new root 
					s.references[0] = root; 
		  
					// Split the old root and move 1 key to the new root 
					s.splitChild(0, root); 
		  
					// New root has two children now.  Decide which of the 
					// two children is going to have new key 
					int i = 0; 
					if (s.keys[0].compareTo(key) < 0) 
						i++; 
					s.references[i].insertNonFull(key); 
		  
					// Change root 
					root = s; 
				} 
				else  // If root is not full, call insertNonFull for root 
					root.insertNonFull(key); 
			 
			return root;
			
	}

	void insertNonFull(T k) 
{ 
    // Initialize index as index of rightmost element 
    int i = keyTally-1; 
  
    // If this is a leaf node 
    if (leaf == true) 
    { 
        // The following loop does two things 
        // a) Finds the location of new key to be inserted 
        // b) Moves all greater keys to one place ahead 
        while (i >= 0 && keys[i].compareTo(k) > 0) 
        { 
            keys[i+1] = keys[i]; 
            i--; 
        } 
  
        // Insert the new key at found location 
        keys[i+1] = k; 
        keyTally = keyTally+1; 
    } 
    else // If this node is not leaf 
    { 
        // Find the child which is going to have the new key 
        while (i >= 0 && keys[i].compareTo(k) > 0) 
            i--; 
  
        // See if the found child is full 
        if (references[i+1].keyTally == 2*m-1) 
        { 
            // If the child is full, then split it 
            splitChild(i+1, references[i+1]); 
  
            // After split, the middle key of C[i] goes up and 
            // C[i] is splitted into two.  See which of the two 
            // is going to have the new key 
            if (keys[i+1].compareTo(k) < 0) 
                i++; 
        } 
        references[i+1].insertNonFull(k); 
    } 
} 
  
// A utility function to split the child y of this node 
// Note that y must be full when this function is called 
void splitChild(int i, BTreeNode<T> y) 
{ 
    // Create a new node which is going to store (t-1) keys 
    // of y 
    BTreeNode<T> z = new BTreeNode(y.m, y.leaf); 
    z.keyTally = m - 1; 
  
    // Copy the last (t-1) keys of y to z 
    for (int j = 0; j < m-1; j++) 
        z.keys[j] = y.keys[j+m]; 
  
    // Copy the last t children of y to z 
    if (y.leaf == false) 
    { 
		for (int j = 0; j < m; j++) 
		{
			z.references[j] = y.references[j+m]; 
			y.references[j+m] = null;
		}
			
    } 
  
    // Reduce the number of keys in y 
    y.keyTally = m - 1; 
  
    // Since this node is going to have a new child, 
    // create space of new child 
    for (int j = keyTally ; j >= i+1; j--) 
        references[j+1] = references[j]; 
  
    // Link the new child to this node 
    references[i+1] = z; 
  
    // A key of y will move to this node. Find location of 
    // new key and move all greater keys one space ahead 
    for (int j = keyTally-1; j >= i; j--) 
        keys[j+1] = keys[j]; 
  
    // Copy the middle key of y to this node 
    keys[i] = y.keys[m-1]; 
  
    // Increment count of keys in this node 
    keyTally = keyTally + 1;
} 
  


	// Function to traverse all nodes in a subtree rooted with this node
	public void traverse()
	{
			// Your code goes here
			// There are n keys and n+1 children, travers through n keys 
    // and first n children 
    int i; 
    for (i = 0; i < keyTally; i++) 
    { 
        // If this is not leaf, then before printing key[i], 
        // traverse the subtree rooted with child C[i]. 
        if (leaf == false) 
            references[i].traverse(); 
        System.out.print(" " + keys[i]); 
    } 
  
    // Print the subtree rooted with last child 
    if (leaf == false) 
    	references[i].traverse(); 
	}
}