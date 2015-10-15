public class DList {
	public DListNode head;
	public DListNode tail;
	public long size;

public DList() {
	head = null;
	tail = null;
	size = 0;
}

public DList (int a){
	head = new DListNode();
	tail = head;
	head.runTypes = a;
	head.runLengths = 1;
	size = 1;
}

public DList(int a, int b){
	head = new DListNode();
	head.runTypes = a;
	head.runLengths = 1;
	tail = new DListNode();
	tail.runTypes = b;
	tail.runLengths = 1;
	head.next = tail;
	tail.prev = head;
	size = 2;
}

public void addTail(int runTypes, int hunger, int runLengths) {
	DListNode end = new DListNode();
        DListNode oldend = this.tail;
	end.runTypes = runTypes;
	end.runLengths = runLengths ;
        end.hunger = hunger;
	end.prev = oldend;
        oldend.next = end;
        this.tail = end;
        size = this.size + 1;
}

}