// William Thing, CSE 373, Winter 2015, 
// Programming Assignment #3, 1/30/2015
//
// This class keeps track of nodes to create a binary search tree. It has the ability 
// to update records and insert keywords. Find a specific keyword and/or the record that 
// is associated with a given key. Can also delete and print elements in the tree.

class bst{
    
    Node root;

    private class Node{
    	
        String keyword;
        Record record;
        int size;
        Node l;
        Node r;
        
        //	post:	constructs a Node with given String as a keyword.
        private Node(String k){
        	keyword = k;
        }
        
        //	post:	stores the given record; updating it to be the first found in the
        //			Node.
        private void update(Record r){
        	Record temp = record;
        	record = r;
        	record.next = temp;
        	size++;
        }
    
    }

    //	post:	constructs an empty bst.
    public bst(){
        this.root = null;
    }
    
    //	pre:	takes in keyword and FileData.
    //	post:	adds a new record from given file data to the specified Node with 
    //			given keyword, if keyword not found, a new Node is constructed 
    //			and the record is added.
    public void insert(String keyword, FileData fd){
        Record recordToAdd = new Record(fd.id,fd.title, fd.author, null); // retrieves new record
        root = insert(root, keyword, recordToAdd);						 // adds new record
    }
    
    //	pre:	takes in root, keyword, and record.
    //	post:	adds a new record to specific root with same keyword.
    private Node insert(Node root, String keyword, Record recordToAdd) {
    	if (root == null) {
    		root = new Node(keyword);
    		root.record = recordToAdd;
    		root.size++;
    	} else if (keyword.equals(root.keyword)) {
    		root.update(recordToAdd);
    	} else if (keyword.compareTo(root.keyword) < 0) {
    		root.l = insert(root.l, keyword, recordToAdd);
    	} else {		//	last case will search the right side of the tree
    		root.r = insert(root.r, keyword, recordToAdd);
    	}
    	return root;
    }
    
    //	post:	returns true if given keyword is found, otherwise false.
    public boolean contains(String keyword){
    	return contains(root, keyword);
    }
    
    //	post:	takes in given root to return true if given keyword is found, otherwise 
    //			returns false.
    private boolean contains(Node root, String keyword) {
    	if (root != null) {
    		if (keyword.equals(root.keyword)) {
    			return true;
    		} else if (keyword.compareTo(root.keyword) < 0) {
    			return contains(root.l, keyword);
    		//	last case will search the right side of the tree if keyword is found or not	
    		} else {
    			return contains(root.r, keyword);
    		}
    	}
    	return false;
    }
    
    //	post:	takes in keyword to return the record associated if found in bst, otherwise 
    //			if not found will return null.
    public Record get_records(String keyword){
    	return getRecords(root, keyword);
    }
    
    //	post:	takes in given root to find specific given keyword to return
    //			the last record added along with previous ones if they exist,
    //			if no record found returns null.
    private Record getRecords(Node root, String keyword) {
    	if (root != null) {
    		if (keyword.equals(root.keyword)) {
    			return root.record;
    		} else if (keyword.compareTo(root.keyword) < 0) {
    			return getRecords(root.l, keyword);
    		//	last case will search the right side for the record matching the keyword
    		} else {	
    			return getRecords(root.r, keyword);
    		}
    	}
    	return null;
    }
    
    //	post:	deletes the Node with the specified given keyword, if the keyword
    //			is not found will not delete anything.
    public void delete(String keyword){
    	root = delete(root, keyword);
    }
    
    //	post:	deletes the Node with the specified given keyword, if the keyword
    //			is not found will not delete anything.
    private Node delete(Node root, String keyword) {
    	if (root != null) {
    		if (keyword.equals(root.keyword)) {
    			if (root.l != null && root.r != null) {	// check double children case
    				Node minRoot = findMin(root.r);
    				minRoot.r = delete(root.r, minRoot.keyword);
    				minRoot.l = root.l;
    				root = minRoot;
    			} else if (root.l != null && root.r == null) {	// only left of root exist since failed first test
    				root = root.l;
    			} else if (root.r != null && root.l == null) {	// left of root is null and right of root exist
    				root = root.r;
    			} else {
    				root = null;
    			}
    		} else if (keyword.compareTo(root.keyword) < 0) {
    			root.l = delete(root.l, keyword);
    		} else {
    			root.r = delete(root.r, keyword);
    		}
    	}
    	return root;
    }
    
    //	post:	recursively searches left of given root to return the root with 
    //			smallest minimum keyword
    private Node findMin(Node root) {
    	if (root.l != null) {
    		return findMin(root.l);
    	}
    	return root;
    }

    //	post:	prints bst, includes: every keyword and all records.
    public void print(){
        print(root);
    }

    //	post:	prints every node, includes: every keyword and all records.
    //			in-order traversal.
    private void print(Node t){
        if (t!=null){
            print(t.l);
            System.out.println(t.keyword);
            Record r = t.record;
            while(r != null){
                System.out.printf("\t%s\n",r.title);
                r = r.next;
            }
            print(t.r);
        } 
    }
    
}
