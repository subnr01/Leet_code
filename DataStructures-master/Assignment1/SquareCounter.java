/*Jose Nino - jnino1@jhu.edu
Data Structures - Assignment 1
*/

public class SquareCounter implements ResetableCounter {

	//Data Members
	private int val;

	//Constructor
	public SquareCounter (){
		val = 2;
	}

	//Functions
	public int value() {
		return val;
	}

	public void up() {
		//Need to typecast
		val = (int)(Math.pow(val,2));
	}

	public void down() {
		//Math.ceil will always round up
		val = (int)(Math.ceil(Math.sqrt(val)));
	}

	public void reset() {
		val = 2;
	}

	//Used for unit testing SquareCounter
	public static void main (String [] args) {

		SquareCounter c = new SquareCounter();

		//When created, a SquareCounter has the value = 2
		assert c.value() == 2;

		//Test that after up, SquareCounter is increased by squaring it
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.up();
			assert c.value() == (int)Math.pow(tempval,2);
		}

		//Test that after reset the value is == 2
		c.reset();
		assert c.value() == 2;

		//Test that after down, SquareCounter is decreased by 1
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.down();
			assert c.value() == (int)(Math.ceil(Math.sqrt(tempval)));
		}

		//Test that when decreasing a number that is not a perfect square, the number is rounded up
		c.reset();
		c.down();
		assert c.value() == 2;

	}
}