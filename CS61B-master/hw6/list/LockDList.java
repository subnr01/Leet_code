package list;
public class LockDList extends DList {
	
    public LockDList() {
	super();
    }

    public void lockNode(DListNode node){
	((LockDListNode)node).locked = true;
        }
        
    protected LockDListNode newNode(Object item, DListNode prev, DListNode next) {
        return new LockDListNode(item, prev, next);
    }
    public void remove(DListNode node) {
        if (((LockDListNode) node).locked == false){
            super.remove(node);
        }
    }
    public static void main (String[] args){
        LockDList lst = new LockDList();
        lst.insertFront(1);
        System.out.println(lst + " Length: " + lst.length() + " " + lst.head.next);
        lst.insertBack(2);
        System.out.println(lst + " Length: " + lst.length());
        lst.insertAfter(3, lst.back());
        System.out.println(lst + " Length: " + lst.length());
        lst.insertBefore(0, lst.front());
        System.out.println(lst + " Length: " + lst.length());
        lst.insertBack(8);
        System.out.println(lst + " Length: " + lst.length() + " " + lst.head.next.next.next.next);
        lst.lockNode(lst.back());
        lst.remove(lst.back());
        System.out.println(lst + " Length: " + lst.length() + " Last Node is locked: " + ((LockDListNode) lst.back()).locked);
        lst.insertBefore(10, lst.head.next.next);
        System.out.println(lst + " Length: " + lst.length() + " 2nd Node is locked: " + ((LockDListNode) lst.head.next.next).locked);
        lst.remove(lst.head.next.next);
        System.out.println(lst + " Length: " + lst.length() + " Called remove on 2nd node");
    }
}