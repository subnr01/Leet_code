class ReverseList {
	//let's test our code using a simple example 1>2>3>4
	public static void main(String[] args) {
		List myList = new List(1);
		myList.next = new List(2);
		myList.next.next = new List(3);
		myList.next.next.next = new List(4);
		System.out.println("Original List: "+myList.tostring());
		//now reverse it
		myList = Reverse(myList);
		System.out.println("Reversed List: "+myList.tostring());
	}

	//now let's define the reverse method based on our proposed method
	static List Reverse(List L) {
		//firstly check if L is empty or only has one element then return
		if(L==null || L.next==null)
			return L;
		//otherwise, we can use our recursive method
		List remainingReverse = Reverse(L.next);
		//next we have two step steps, firstly we need update the tail of remaining reverse as our head L
		L.next.next = L;//this (L.next) is the key to get te tail in constant time!
		//Very important, we need set L.next to NULL after that! Otherwise it's causing cycles in list
		L.next = null;
		//finally we return the reverse List
		return remainingReverse;
	}
}
//we firstly define our List class
class List {
	int value;
	List next;
	//constructor
	public List(int k) {
		value = k;
	}
	//we also define a tostring method to easy output
	public String tostring() {
		List current = this;
		String output = "";
		while(current!=null) {
			output += current.value+"->";
			current = current.next;//do not forget to increment the pointer index current
		}
		return output+"NULL";
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
