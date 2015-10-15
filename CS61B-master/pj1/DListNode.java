public class DListNode {
    
        public int runTypes;
        public int runLengths;
        public int hunger;
        public DListNode prev;
        public DListNode next;
        int previous;
        int nexter;
        
        public DListNode(){
        }
        
        public DListNode (int prev, int next, int runTypes, int runLength){
            previous = prev;
            nexter = next;
            this.runTypes = runTypes;
            this.runLengths = runLengths;
     }
}