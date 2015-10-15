class Permutation 
{
	//now test the code
	public static void main(String[] args)
	{
		//PrintPermutation("123".toCharArray(), 0);
		//test if duplicates
		PrintPermutation("1234".toCharArray(), 0);
	}

	//define method header
	//notice currentFocus is the key to keep track of the current status
	public static void PrintPermutation(char[] inputs, int currentFocus)
	{
		//before start, check if currentFocus comes to the last char
		if(currentFocus==inputs.length-1)
		{
			System.out.println(new String(inputs));
			return;
		}

		//now firstly keep the current char order in the array and proceed to next
		PrintPermutation(inputs, currentFocus+1);

		//now need swap each next char with currentFocus
		for(int i=currentFocus+1; i<inputs.length; i++)
		{
			//swap the char pair of position (currentFocus, i)
			//in order to remove the duplicates, the key is to check if the two char unequal before swapping!
			boolean ifAppear = false;
			for(int j=0; j<i;j++)
			{
				if(inputs[j]==inputs[i])
				{
					ifAppear = true;
					break;
				}
			}

			if(ifAppear)
				continue;
			Swap(inputs,currentFocus, i);
			PrintPermutation(inputs, currentFocus+1);
			Swap(inputs,currentFocus, i);
		}
	}

	private static void Swap(char[] inputs, int a, int b)
	{
		char temp = inputs[a];
		inputs[a] = inputs[b];
		inputs[b] = temp;
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
