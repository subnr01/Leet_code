/*Jose Nino - jnino1@jhu.edu
Data Structures - Assignment 1
*/

public class FlexibleCounter implements ResetableCounter {

	//Data Members
	private int val;
	private int init_val; //Used for reset
	private int step;

	//Constructor
	public FlexibleCounter (int val, int step){
		this.val = val;
		this.init_val = val;
		this.step = step;
	}

	//Functions
	public int value() {
		return val;
	}

	public void up() {
		val += step;
	}

	public void down() {
		val -= step;
	}

	public void reset() {
		//Reset to the initial value 
		val = this.init_val;
	}

	//Used for unit testing FlexibleCounter
	public static void main (String [] args) {

		//Also wanted to test a negative step
		FlexibleCounter c = new FlexibleCounter(5,-10);

		//When created, a FlexibleCounter has the value inputted
		assert c.value() == c.init_val;

		//Test that after up, FlexibleCounter adds a step to value
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.up();
			assert c.value() == tempval + c.step;
		}

		//Test that after reset the value is the original input value
		c.reset();
		assert c.value() == c.init_val;

		//Test that after down, FlexibleCounter subtracts a step from value
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.down();
			assert c.value() == tempval - c.step;
		}

	}
}