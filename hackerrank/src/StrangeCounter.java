import java.util.Scanner;

/**
 * Created by sand94 on 24.10.2016.
 */
public class StrangeCounter {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long t = in.nextLong();
        long dummy = 1;
        while (dummy*3 < t) dummy = (dummy << 1) | 1;
        long res = dummy*3 - t + 1;
        System.out.println(res);

    }
}
