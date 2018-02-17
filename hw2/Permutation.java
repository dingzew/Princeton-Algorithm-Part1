import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args){
        RandomizedQueue <String> d = new RandomizedQueue<>();
        int num = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            d.enqueue(value);
         }
        for(int i = 0; i < num; i++){
            String s = d.dequeue();
            System.out.println(s);
        }
        
    }
}
