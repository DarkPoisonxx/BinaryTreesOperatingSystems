
public class Node {
    String value;
        Node left, right;

        Node(String value){
            this.value = value;
            left = right = null;
        }

        public String getValue(){
            return value;
        }
}
