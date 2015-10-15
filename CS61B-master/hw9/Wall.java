/* Wall.java */

import java.util.*;
import set.*;

public class Wall {
	
	public final static int HORIZONTAL = -1;
	public final static int VERTICAL = 1;
	public int wallType;
	public int[] coordinate;
        public int x;
        public int y;


    public Wall(int xcoord, int ycoord, int wallType) {
        int[] coordinate = {xcoord, ycoord};
        this.x = xcoord;
        this.y = ycoord;
	this.wallType = wallType;

	}
}