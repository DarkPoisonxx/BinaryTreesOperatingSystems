import java.util.Stack;

public class BinaryTree {
    Node root;

    // the isOperator method check to find if the 
    // selected input from the array is an operator or not
    private boolean isOperator(String x) {
        return x.equals("+") || x.equals("-") || x.equals("*") || x.equals("/") || x.equals("^");
    }

    // the precedence method decides the priority of 
    // the operators withing the operators stack 
    // the higher number of priority 
    private int precedence(String x) {
        switch (x) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            case "^": return 3;
            default: return -1;
        }
    }
    // Build the binary expression tree from the equation
    public void createBinaryTree(String equation) {
        //initializes stacks for seperate numbers and operators
        Stack<Node> numbers = new Stack<>();
        Stack<String> operators = new Stack<>();
        String num = "";

        // for loop to be able to assign the numbers 
        // and operators in their respective node
        // in which goes the equation in a char array
        for (char i : equation.toCharArray()) {
            if (Character.isDigit(i)) {
                num += i; // Build multi-digit numbers
            } else {
                // if the nums is filled, or not empty
                if (!num.isEmpty()) {
                    numbers.push(new Node(num)); // Push the number to the numbers stack
                    num = "";
                }
                // if the char at i is an operator
                if (isOperator(String.valueOf(i))) {
                    //checks the precedence of the current stack in relation to that of the current operator at i
                    while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(String.valueOf(i))) {
                        processOperator(numbers, operators.pop());
                    }
                    operators.push(String.valueOf(i));
                }
            }
        }
        if (!num.isEmpty()) {
            numbers.push(new Node(num)); // Push the last number
        }
        while (!operators.isEmpty()) {
            processOperator(numbers, operators.pop());
        }
        root = numbers.pop();
    }

    // Process operators to create subtrees to eventually combine to create the main tree
    private void processOperator(Stack<Node> numbers, String operator) {
        if (numbers.size() < 2) return; // Prevent stack underflow
        Node right = numbers.pop(); // removes number from stack and sets it to the "right" node
        Node left = numbers.pop();  // removes number from stack and sets it to the "left" node
        Node newNode = new Node(operator);  // creates a new node 
        newNode.left = left;    // sets the left node to the newNode as a root
        newNode.right = right;  // sets the left node to the newNode as a root
        numbers.push(newNode);  //push the newnode into the numbers stack
    }

    // Evaluates the binary expression tree
    public int evaluate(Node node) {
        if (node == null) return 0; 
        if (!isOperator(node.value)) {
            return Integer.parseInt(node.value);
        }

        int leftVal = evaluate(node.left);
        int rightVal = evaluate(node.right);

        // returns the added value with the correct left and right values
        // this is returning the correct value of the equation
        switch (node.value) {
            case "+": return leftVal + rightVal;
            case "-": return leftVal - rightVal;
            case "*": return leftVal * rightVal;
            case "/": return leftVal / rightVal;
            case "^": return (int) Math.pow(leftVal, rightVal);
            default: return 0;
        }
    }

    // In-order traversal
    public void inOrderTraversal(Node node) {
        if (node != null) {
            inOrderTraversal(node.left); //recursively go left
            System.out.print(node.value + " "); // prints the node
            inOrderTraversal(node.right); // recursively gp right
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();

        String equation = "12+4*5-6/2"; 
        tree.createBinaryTree(equation);

        System.out.print("In-order Traversal of the Tree: ");
        tree.inOrderTraversal(tree.root);
        System.out.println();

        int result = tree.evaluate(tree.root);
        System.out.println("Evaluated Result: " + result);
    }
}