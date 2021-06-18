import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */
public class RandomWord {
    public static void main(String[] args) {
        int counter = 0;
        double p = 0.0;
        String champion = "";

        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            counter += 1;
            p = (double) 1 / counter;
            if (StdRandom.bernoulli(p)) {
                champion = s;
            }
        }
        System.out.println(champion);
    }
}
