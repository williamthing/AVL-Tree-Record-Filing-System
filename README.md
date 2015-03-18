# Files Given Records by Bst/Avl tree

Implemented a binary search tree and avl tree used to store and retrieve articles.
The tree will be sorted by keyword, and each node will contain an unordered linked
list of Record objects which contain information about each article that corresponds
to that keyword.

Record.java: The “Record” class are the objects stored in the value for
each keyword in the tree. This class also has a ”next” pointer which provides
the structure for the linked list.

Test.java: This code performs reading from the data file as well as allowing
test operations of given search tree. Performing changes to this file can
be done to test particular cases.
The code provided will print the contents of the tree in inorder,
which is alphabetical order. At each node of the tree, it will print the key word
and then the titles of all the records in the list. The test code also performs a 
few deletions and checks the result to ensure that delete() works correctly.

