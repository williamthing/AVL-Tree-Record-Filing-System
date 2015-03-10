// William Thing, CSE 373, Winter 2015, 
// Programming Assignment #3, 1/30/2015
//
// This class keeps track of nodes to create a avl tree. It has the ability 
// to insert keywords and update records while keeping the tree balanced. 

public class avl{
	
    Node root;

    private class Node{
    	
        String keyword;
        Record record;
        int size;
        Node l;
        Node r;
        int height;
        
    	//	post:	constructs a Node with given String as a keyword.
        private Node(String k) {
        	keyword = k;
        }

        //	post:	stores the given record; updating it to be the first found in the
        //			Node.
        private void update(Record r) {
        	Record temp = record;
        	record = r;
        	record.next = temp;
        	size++;
        }
    }
	
    //	pre:	takes in keyword and FileData.
    //	post:	adds a new record from given file data to the specified Node with 
    //			given keyword, if keyword not found, a new Node is constructed 
    //			and the record is added.
	public void insert(String keyword, FileData fd) {
        Record recordToAdd = new Record(fd.id, fd.title, fd.author, null);
        root = insert(root, keyword, recordToAdd);
    }
	
	//	post:	adds a new record to the specified Node with matching 
    //			given keyword. If keyword is not matched, a new Node is constructed 
    //			and the record is added. Then checks to see if the tree is 
	//			balanced, if not balances the tree.
	private Node insert(Node root, String keyword, Record recordToAdd) {
		if (root == null) {
			root = new Node(keyword);
			root.record = recordToAdd;
			root.size++;
		} else if (keyword.equals(root.keyword)) {
			root.update(recordToAdd);
		} else if (keyword.compareTo(root.keyword) < 0) {
			root.l = insert(root.l, keyword, recordToAdd);
		} else {
			root.r = insert(root.r, keyword, recordToAdd);
		}
		return balance(root);
	}
	
	//	pre:	assume tree is balanced or one Node away from being balanced.
	//	post:	balances the tree depending on the height of Nodes and
	//			the it's Nodes left and right children(s).
	private Node balance(Node root) {
		if (root == null) {
			return root;
		}
		if (height(root.l) - height(root.r) > 1) {
			if (height(root.l.l) >= height(root.l.r)) {
				root = leftChildLeft(root);
			} else {
				root = rightChildLeft(root);
			}
		} else if (height(root.r) - height(root.l) > 1) {
			if (height(root.r.r) >= height(root.r.l)) {
				root = rightChildRight(root);
			} else {
				root = leftChildRight(root);
			}
		}
		// assign corresponding max height of a Node of the current avl tree
		root.height = Math.max(height(root.l), height(root.r)) + 1;
		return root;
	}
	
	//	post:	returns the height of the Node, if Node is null then
	//			returns -1.
	private int height(Node root) {
		if (root != null) {
			return root.height;
		}
		return -1;		// Returns a height of -1 if a empty root
	}
	
	//	post:	balances and returns a Node that is rotated version
	//			of the given root, using left child, and left child's right.
	private Node leftChildLeft(Node root) {
		Node rootLeft = root.l;
		root.l = rootLeft.r;
		rootLeft.r = root;
		root.height = Math.max(height(root.l), height(root.r)) + 1;
		rootLeft.height = Math.max(height(rootLeft.l), root.height) + 1;
		return rootLeft;
	}
	
	//	post:	balances and returns a double rotated Node. 
	private Node leftChildRight(Node root) {
		root.r = leftChildLeft(root.r);
		return rightChildRight(root);
	}
	
	//	post:	balances and returns a double rotated Node.
	private Node rightChildLeft(Node root) {
		root.l = rightChildRight(root.l);
		return leftChildLeft(root);
	}
	
	//	post:	balances and returns a Node that is rotated version
	//			of the given root, using right child, and right child's left.
	private Node rightChildRight(Node root) {
		Node rootRight = root.r;
		root.r = rootRight.l;
		rootRight.l = root;
		root.height = Math.max(height(root.l), height(root.r)) + 1;
		rootRight.height = Math.max(height(rootRight.r), root.height) + 1;
		return rootRight;
	}
	
}

