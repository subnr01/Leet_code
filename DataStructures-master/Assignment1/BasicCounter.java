/*Jose Nino - jnino1@jhu.edu
Data Structures - Assignment 1
*/

public class BasicCounter implements ResetableCounter {

	//Data Members
	private int val;

	//Constructor
	public BasicCounter (){
		val = 0;
	}

	//Functions
	public int value() {
		return val;
	}

	public void up() {
		val++;
	}

	public void down() {
		val--;
	}

	public void reset() {
		val = 0;
	}

	//Used for unit testing BasicCounter
	public static void main (String [] args) {

		BasicCounter c = new BasicCounter();

		//When created, a BasicCounter have the value = 0
		assert c.value() == 0;

		//Test that after up, BasicCounter is increased by 1
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.up();
			assert c.value() == tempval + 1;
		}

		//Test that after reset the value is == 0
		c.reset();
		assert c.value() == 0;

		//Test that after down, BasicCounter is decreased by 1
		for(int i = 0; i < 50; i++) {
			int tempval = c.value();
			c.down();
			assert c.value() == tempval - 1;
		}

	}
}