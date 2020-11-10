//-----------------------------------------------------
// Title: Tester for AlgExpressionTree
// Author: Umut Zeren
// ID: 19952605782
// Section: 1
// Assignment: 3
// Description: This tests created  algExpressionTree 
// with giving different inputs.
//------------------------------------------
public class TesterTree {

	public static void main(String[] args) {
	
		//for testing display tree with 2subtrees 
		//entering complex expressions
		//both side have subtrees 
		
		//first two tree has more then 10 char so we are testing 
		//if dynamic array in my Stack class works
		//-+78+6+*3x5
		AlgExpressionTree T1 = new AlgExpressionTree("/++9185");
		System.out.println("result is +"+T1.evaluate());
		AlgExpressionTree T2 = new AlgExpressionTree("+**354+1++y6/+z85");
		//just gets deeper in  right side 
		AlgExpressionTree oneSide=new AlgExpressionTree("+6-9+5+xy");
		T1.displayTree();
		oneSide.displayTree();
		// Evaluate the expression for x = 0 (default value):
		System.out.println("Result for x = 0 : " + T1.evaluate());
		// Evaluate the expression for x = 8, but do not change the tree:
		T1.setVariable('x', 8);
		T1.displayTree();
		System.out.println("Result for x = 8 : " + T1.evaluate());
		// Evaluate the expression for x = 6, but do not change the tree:
		T1.setVariable('x', 6);
		System.out.println("Result for x = 6 : " + T1.evaluate());
		// The setVariable function does not do anything
		// since y does not exist in the expression and
		// the evaluate function uses the most recent value of x
		T1.setVariable('y', 10);
		System.out.println("Result for y = 10 : " + T1.evaluate());
		// Print the tree in postfix form
		System.out.println("Postfix form: ");
		T1.displayPostfix();
		System.out.println();
		// Evaluate the expression for z = 10
		// Note: uses the default value for y, which is 0
		T2.setVariable('z',9);
		T2.displayTree();
		//örnek pdf de T2.evaluate olmalý
		System.out.println("Result for z = 10  and : " + T2.evaluate());
		// Evaluates the expression for z = 10 and y = 3
		T2.setVariable('y', 3);
		T2.displayTree();
		System.out.println("Result for y = 3 : " + T2.evaluate());
		//testing one sided
		oneSide.setVariable('x',-5);
		oneSide.displayTree();
		System.out.println("for x=5 "+oneSide.evaluate());
		
		//not using double values since data is in 1-9 numbers as mentioned in hw description
		//testing to changing already assigned variables value.
		oneSide.setVariable('x',100);
		System.out.println("for x=100 "+oneSide.evaluate());
		//testing to assign value at different variables
		oneSide.setVariable('y',100);
		System.out.println("for x=100 and y=100 "+oneSide.evaluate());
	}

}
