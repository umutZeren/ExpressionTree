//-----------------------------------------------------

// Description: This class defines a BST and additional methods to 
//use bst as as a expression tree.
//-----------------------------------------------------
public class AlgExpressionTree {
//to hold expressions in object such as ++x9y
	private String exp;
	///Holding the root of the treee
	private Node root;
	//a Stack class that ý implemented.
	private myStack mySt=new myStack();
	//2d array to print
	private char [][]table;
	// to hold current max depth of the tree
	int currentDepth=0;
	//arrays to hold variables values with 1-1 mapping for instance
	// varA [val1,val2] vars[x,y..]
	//so x=value1 and y =value2 right now 
	int tupleIndex=0;
	//to hold array of index numbers to be used in setVariable
	private int []variableA;
	//to hold whic variable to Set in setVariable
	private char []vars;
	//global index nomber for putting in vars[]
	int varindex=0;

	public AlgExpressionTree(String exp) 
	{
		//--------------------------------------------------------
		 // Summary: Constructor for the tree.Initalizze attrs
		 // Precondition: Constructor called from main with argument String exp
		 // Postcondition: The value of all variables setted.
		// 1d arrays for holding index and "v" value of variables initiliazed
		//create Table called so 2d Table created to print Tree
		this.exp=exp;
		this.arrangeTree();
		createTable();
		variableA=new int[this.exp.length()];
		vars=new char[this.exp.length()];
	}
	
	public void displayPostfix() 
	{
		//--------------------------------------------------------
		 // Summary: Display tree with postorder traverse
		 // Precondition: called with no arguments
		 // Postcondition: displayPostfixRec called.Treeprinted when displayePostfixRec ended.
		displayPostfixRec(this.root); 
	}
	public void displayPostfixRec(Node troot) 
	{	//--------------------------------------------------------
		 // Summary: Display tree with getting the root node
		 // Precondition: called with root node to use recursively
		 // Postcondition: Three printed and returned to displayPostfix method.
		if(troot==null)
			 return;
		displayPostfixRec(troot.left);
		displayPostfixRec(troot.right);
		System.out.print(troot.dat);
	}



	void setVariable(char varName, int varValue) 
	{
		//--------------------------------------------------------
		 // Summary: sets a variable to temporary value
		 // Precondition: varName is a char and varValue is an
		 // integer
		 // Postcondition: The value of the variable is set.
		for(int i =0;i<this.exp.length();i++)
		{
			//if char in vars match the varName it means
			//its a char that its Value setted already 
			//just change it 
			if(vars[i]==varName) 
			{
				variableA[i]=varValue;
			}
			//else its not seetted yet set variable 
			//same indexvalue in variableA
			else if(this.exp.charAt(i)==varName)
			{
				//value holded in variable
						variableA[tupleIndex]=varValue;
						vars[tupleIndex++]=varName;
			}
		}
	}
	
	private boolean isNumeric(char c) 
	{
		//--------------------------------------------------------
		 // Summary: to understand is a char is numeric
		 // Precondition: a char passed to fuunction
		 // Postcondition: true returned if char is equal to onne of 1,2,3,4,5,6,7,8,9 else false returned 
		if(c=='0'||c=='1'||c=='2'||c=='3'||c=='4'||c=='5'||c=='6'||c=='7'||c=='8'||c=='9')
		{
			return true;
		}
		else return false;
	}
	double res=0;
	
	double calculate(Node n) 
	{	//--------------------------------------------------------
		 // Summary:calculates equality in expression using recursion
		 // Precondition: Root passed to this method
		 // Postcondition: Returned double value according to the expression and variable values.
		//if node is empty end this method.
		if(n==null)
			return 0;
		//n.left and .nright  null means we are in leaf node.and node itself not null since we are still in method
		//if we are in leaf node
		if(n.left==null && n.right ==null)
		{
			if(	isNumeric(n.dat))
			{
				//if numeric get value
				return Character.getNumericValue(n.dat);
			}	
			//if not numberic and in leaf then it is a variable
			else 
			{
				//if a value found to respective char/variable in vars
				//return its value
				//else return 0
				
				//finding char representation of variable
				//in array
				boolean found=false;
				int index=0;
				for(int a=0;a<=this.vars.length-1;a++)
				{
					if(this.vars[a]==n.dat)
					{
						found=true;
						index=a;
					}
				}
				//if found return its pre setted value 
				if(found)
					return this.variableA[index];
				else
					//not found so return default value 0
					return 0;
			}
		}
		//recursive traversing tree to calculate
		//if instruction counter points here first time then we are in head node
		double leftTree =calculate(n.left);
		double rightTree =calculate(n.right);
		//so left subtree and right and right subtree calculated
		//so no numeric in this node
		//double res=0;

		//make a operation
		// wrt.  node data about operator.result  of left tree and  right tree
		//calculated in leftTree,righTree
		if(n.dat=='+')
			return leftTree+rightTree;
		else if ( n.dat=='-')
			return leftTree-rightTree;
		else if (n.dat=='*')
			return leftTree*rightTree;

		try{
				return leftTree/rightTree;
			}catch(ArithmeticException e) {
	            System.out.println (" cannot divide by zero");
	            return -10000;
			}
	}

	double evaluate()
	{
		//--------------------------------------------------------
		 // Summary:evaluates experssion 
		 // Precondition: called from main with no argument
		 // Postcondition: Returned double value according to the expression and variable values.
		//if node is empty end this method.
		//called calculate
		double result=calculate(this.root);
		return result;
	}
	private void arrangeTree()
	{//--------------------------------------------------------
		 // Summary:creates tree according to given expression
		 // Precondition:expression is not null
		 // Postcondition: With using linled list data structure
		//head nodes-next assigned to left*right subbtrees 
		//stack data structure used to read from right to left
	
		//read chars in a loop
		for(int i=this.exp.length()-1;i>=0;i--)
		{
			//isOp returns if char is one of the + - / * 
			//if not push stack
			if(!this.isOp(this.exp.charAt(i)))
			{
				Node temp=new Node(this.exp.charAt(i));
				mySt.push(temp);
			}
			//if operator them pop last 2 node and assing to operator nodes right-left tree
			else 
			{
				Node temp,operand1,operand2;
				char t;
				// (/) changed to (:) for printing purposes 
				if(this.exp.charAt(i)=='/')
					t='%';	
				else t=this.exp.charAt(i);
				temp=new Node(t);
				//get lass nodes pushed 
				//assign to  as subtrees
				operand1=mySt.pop();
				operand2=mySt.pop();

				temp.left=operand1;
				temp.right=operand2;
//push new node that is formed with eft right subtree
				mySt.push(temp);
			}
		}//end of for
		//trees root is last node in the stack
		this.root=mySt.pop();
	}
	private int findDepthRec(Node n){
		//--------------------------------------------------------
		 // Summary:calculates equality in expression using recursion
		 // Precondition: Root passed to this method
		 // Postcondition: Returned int value depth of treee
		if(n==null)
		{
			return 0;
		}
		else 
		{
			int leftD=findDepthRec(n.left);
			int rightD=findDepthRec(n.right);
			//using ternarary to write max depth to int depth variable
			int depth=leftD>rightD? leftD:rightD;
			return(1+depth);
		}
	}
	private void createTable() {//--------------------------------------------------------
		 // Summary:creates 2d table
		 // Precondition: called withr no arguments referenceto current depth declared
		 // Postcondition: 2d table created with empty spaces inside
		
		currentDepth=findDepthRec(this.root);
		//initialize table
		this.table=new char[currentDepth*10][10*currentDepth];
		for(int i=0;i<currentDepth*4;i++) 
		{
			//fill table  with defauly " " 
			for(int j=0;j<currentDepth*9;j++)
				this.table[i][j]=' ';
		}
	}

	protected void displayTree()
	{//--------------------------------------------------------
		   // Summary :show tree according to chars in table
		  // Precondition: table filled
		 // Postcondition: table displayed according to tables content.

		//take depth
		int depth=this.findDepthRec(this.root);
//call fillTable from bellow 
		fillTable(this.root,0,(10*depth-1)/2,depth-1);
//print every cell with nested loop
		for(int i=0;i<currentDepth*4;i++) 
		{
			for(int j=0;j<9*currentDepth;j++)
			{	
				System.out.print(this.table[i][j]);
			}			
			System.out.println("");
		}
	}
	protected void fillTable(Node n,int rowIndex,int wNow,int depth) 
	{
		//--------------------------------------------------------
		 // Summary:Changes wanted " " with wanted values such as / * + - or numerics recursively
		 // Precondition:  Node =root ,rowIndex =0,wNow= middle char for the roor locatio,depth-1 passed
		 // Postcondition: Filled table with wanted values
		
		//if empty node end this call
		if(n==null) 
		{
			return;
		}
		else
		{	
			//fill nodes data with wright location in 2d array
			table[rowIndex++][wNow]=n.dat;

			if(n.left!=null) 
			{	//if left subtree not null
				//fill wright place whic is calculated  by 
				//variable rowIndex and wNow-depth;
				
				table[rowIndex][wNow-depth]='/';
				//pass rowIndex+1 so go 1 row downside
				//pass wnow=wnow-depth
				
				//pass currentdepth-1
				//so that wnow decrease 1 by one 
				
				//firt empyt spaces max in between root and child notes then decrease 1 by one
				fillTable(n.left,rowIndex+1,wNow-depth,depth-1);
			}
			//if left subtree not null
			//fill wright place whic is calculated  by 
			//variable rowIndex and wNow-depth;
			if(n.right!=null) 
			{
				//pass rowIndex+1 so go 1 row downside
				//pass wnow=wnow-depth
				
				//pass currentdepth-1
				//so that wnow decrease 1 by one 
				
				//firt empyt spaces max in between root and child notes then decrease 1 by one
				table[rowIndex][wNow+depth]='\\';		
				fillTable(n.right,rowIndex+1,wNow+depth,depth-1);
			}
		}
	}

	private boolean isOp(char c) { 
		//--------------------------------------------------------
		 // Summary:control if passed char is operator
		 // Precondition:  not null char passed
		 // Postcondition: returned boolean according to given char 
		if (c == '+' || c == '-'
				|| c == '*' || c == '/'
				|| c == '^')
			return true;  
		else
			return false; 
	} 	

	protected class Node{
		//-----------------------------------------------------
		// Description: This class defines node whic holds char value and have 2 vars left and right node.
		//-----------------------------------------------------
		//variables data ,left right  to hold left right subtrees
		char dat;
		Node left,right;
		//constructorNode
		public Node(char item)
		{
			///initilize vars
			dat=item;
			left=null;
			right=null;
		}
	}
	protected class  myStack  {
		//-----------------------------------------------------
		// Title: myStack
		// Author: Umut Zeren
		// ID: 19952605782
		// Section: 1
		// Assignment: 3
		// Description: This class defines stack data structure to use in reading expression and
		//to use in arrageTree method
		//-----------------------------------------------------

		//Node arraay 
		private Node[] array=new  Node [10];
		//stacks top starts from -1
		private int  top=-1;
		///push data
		protected  void  push(Node dat) {
			
			//dynamic array usage
			if(top==this.array.length-1)
			{
				//temp array 2 times larger
				Node[] temp=new Node[this.array.length*2];
				//fill temp array
				for(int  i=0;i<array.length;i++)
				{
					temp[i]=this.array[i];
				}
				//original reference points temp array now.
				this.array=temp;
			}
			//top increased since  data will be pushed
			this.top++;
			//data pushed
			this.array[top]=dat;	
		}
		protected Node pop(){
			//--------------------------------------------------------
			 // Summary: pops an item from the top position. last pushed item
			 // Precondition:  stack is not empty and instantiated
			 // Postcondition: returned top node that has been pushed newest among in stacks nodes.
			if(this.top==-1) {
				///iff stack empty print error message and return null
				System.out.println("stack is empty");
				return null;
			}
			else
				//else return element in top positon and 
				//decrease top by one ;
				return array[top--]; 	
		}
	}	
}
