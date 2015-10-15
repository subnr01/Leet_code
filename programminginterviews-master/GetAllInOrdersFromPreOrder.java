import java.util.List;
import java.util.ArrayList;
public class GetAllInOrdersFromPreOrder {
	public static void main(String[] args) {
		//now we test our code, we know from our slides that {1,2,3} preset
		//has in total 5 different BTs thus have 5 different in-order ways!
		int[] testPreOrder = {1,2,3};
		List<BinaryTree> testResults =
				GetAllTrees(testPreOrder, 0, testPreOrder.length-1);
		for(BinaryTree eachFormedBt: testResults)
			eachFormedBt.PrintInOrder();
	}
	//now we come to think about the key method
	//basically we want choose first value as root and split remaining element into two
	//however, even those splited elements can form MULTIPLE sub-trees, therefore we
	//need a data structure to store all the possible BTs
	//we choose a LIST as it is easy to append/remove/iterate
	//start and end index are used to know the focus window in the preorder array!
	static List<BinaryTree> GetAllTrees(int[] preorder, int start, int end) {
		//we firstly define a return data structure
		List<BinaryTree> returnTrees = new ArrayList<BinaryTree>();
		//as a recusrive method, I'd prefer to define the stopping cases!
		if(start>end || start<0 || end>=preorder.length) {
			//there can be no trees, return null
			returnTrees.add(null);
			return returnTrees;
		}
		if(start==end) {//only one element
			returnTrees.add(new BinaryTree(preorder[start]));
			return returnTrees;
		}
		//otherwise,it's the key part we discussed in our slides, split!
		//notice i starts from -1?! So that means left child can be NULL, a trick I tested ^_^
		for(int i=-1; i<end-start; i++) {
			//call the recursive part
			//notice startindex is incremented by 1, and end-index for left child is controlled by i
			List<BinaryTree> leftChildren = GetAllTrees(preorder, start+1, start+1+i);
			List<BinaryTree> rightChildren = GetAllTrees(preorder, start+1+i+1, end);//right child is the remaining part!
			//now we have to go through a nested loop to assign each left/right to root!
			for(BinaryTree eachLeft: leftChildren) {
				for(BinaryTree eachRight: rightChildren) {
					BinaryTree tempRoot = new BinaryTree(preorder[start]);//everytime we make a copy of root
					tempRoot.left = eachLeft;
					tempRoot.right = eachRight;
					//do not forget to add to our return LIST!
					returnTrees.add(tempRoot);
				}
			}
		}
		//and finally do not forget to return the returnTrees list
		return returnTrees;
	}
}
//firstly we define our simple BinaryTree class
class BinaryTree {
	int value;
	BinaryTree left;
	BinaryTree right;
	//contructor
	public BinaryTree(int k) {
		value = k;
	}
	//we also define an in-order print method
	public void PrintInOrder() {
		inOrderTraversal(this);
		System.out.println();//add a new line for formatting
	}
	private void inOrderTraversal(BinaryTree root) {
		if(root==null) return;
		inOrderTraversal(root.left);
		System.out.print(root.value);
		inOrderTraversal(root.right);
	}
}

/**
* Please watch at http://www.youtube.com/user/ProgrammingInterview
* Contact: haimenboy@gmail.com
*
* Step by step to crack programming interview questions.
* 1. All questions were searched publicly from Google, Glassdoor, Careercup and StackOverflow.
* 2. All codes were written from scratch and links to download the source files are provided in each video's description. All examples were written in java, and tools I have used include Editplus, Eclipse and IntelliJ.
* 3. All videos were made without using any non-authorized material. All videos are silent sorry. Text comment is provided during coding as additional explanations.
* Thank you very much. 
*/
