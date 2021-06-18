import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {
    public static void main(String[] args) {
        if (args.length < 1) {
            StdOut.print("Insufficent arguments");
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomqueue = new RandomizedQueue<>();

        String input;
        while (!StdIn.isEmpty()) {
            input = StdIn.readString();
            randomqueue.enqueue(input);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(randomqueue.sample());
        }
    }
}
