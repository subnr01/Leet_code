class ListIntersection 
{
	//let's create a test case
	public static void main(String[] args)
	{
		List common = new List(10);
		common.next = new List(11);//common intersection sub-list is 10>11
		List list1 = new List(1);
		list1.next = new List(2);
		list1.next.next = new List(3);//first list starts with 1>2>3
		list1.next.next.next = common;//this list is 1>2>3>10>11

		List list2 = new List(7);
		list2.next = new List(8);
		list2.next.next = common;//so the length is 7>8>10>11

		list1.Print();
		list2.Print();
		System.out.println("Intersection of two lists starts from " + Intersection(list1, list2).value);

	}
	//now we define our improved method, naive method is simple so try yourself
	//method head accept two lists and return a list
	public static List Intersection(List a, List b)
	{
		//as we discussed in slide, the first step is to find the diff of lengths of two lists
		int aLength = 0;
		int bLength = 0;
		List cur = a;//cur is the pointer used to keep track of current focus node
		while(cur!=null)
		{
			aLength++;
			cur = cur.next;
		}
		//after the previous loop, aLength records the length of list a
		cur = b;//we copy the code with slight modification to get length of list b
		while(cur!=null)
		{
			bLength++;
			cur = cur.next;
		}
		//all right we get list a and b's lengths
		//next we do some node skipping if the lengths are not equal
		if(aLength>bLength)//there are more nodes in List a
		{
			for(int i=0; i<aLength-bLength; i++)
				a = a.next;
		}//after this scope of code for cases that list a is longer than b, 
		//we skipped the necessary nodes to make the remaining a and b equal length
		else if(bLength>aLength)//do the same for list b if length b larger than a
		{
			for(int i=0; i<bLength-aLength; i++)
				b = b.next;
		}
		//now we are pretty sure the remaining a and b are equal length 
		//(check our slide example, we will skip the first node in list a
		while(a!=b && a!=null && b!=null)//make sure these two lists are not null
		{
			a = a.next;
			b = b.next;//we keep the same pace by scanning both lists to its next together
		}
		//after that loop, a=b and it is the value we are searching for!
		return a;//the point is where a=b and that's the intersection point!
	}
}
//firstly we reuse the list definition in previous examples
class List
{
	List next;
	int value;
	public List(int k)
	{
		value = k;
	}
	public void Print()
	{
		List current = this;
		while(current!=null)
		{
			System.out.print(current.value+"->");
			current = current.next;
		}
		System.out.print("NULL\n");
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
