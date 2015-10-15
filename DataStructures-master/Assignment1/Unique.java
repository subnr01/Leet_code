/*Jose Nino - jnino1@jhu.edu
Data Structures - Assignment 1
*/

public class Unique {

	public static void main(String[] args) {

		//First check the command line has arguments
		if (args.length > 0) {

			//Array to hold commadline arguments
			int[] integers = new int[args.length];
			//Array to hold if the argument was parsable 
			int[] errors = new int[args.length];
			
			//Get all the command line arguments and parse them, error if they can't be parsed as integers
			for (int i = 0; i < args.length; i++) {
	    		try {
	        		integers[i] = Integer.parseInt(args[i]);
	    		} catch (NumberFormatException e) {
	        		System.err.println("Your arguments must be integers!");
	        		errors[i] = 1; //This deems this index unusable because it was not an integer
	    		}
	    	}
		
			//Now integers[] contains all the commandline arguments. Need to find the unique ones
			for(int i = 0; i < integers.length; i++) {
				
				//Only try printing arguments that were integers
				if(errors[i] == 0) {
					
					//Local variable to determine if the integer can be printed
					boolean print = true; 

					//Look up to the number, if it has appeared before make print false
					for(int j = 0; j < i; j++) {
						if (integers[i] == integers[j]) {
							print = false;
							//Break out of nested loop, already know that number is repeated and can't be printed
							break;
						}
					}

					//Print the integer, it has not been printed before
					if (print) {
						System.out.println(integers[i]);
					}
				}
			}

		} else {
			System.err.println("You did not provide any arguments in the Commandline");
		}

	}//end of main

} //end of Unique