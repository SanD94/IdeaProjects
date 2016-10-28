import java.util.Scanner;

/**
 * Created by sand94 on 24.10.2016.
 */
public class SaveThePrisoner {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int T = in.nextInt();
        while (T-- != 0) {
            int N = in.nextInt();
            int M = in.nextInt();
            int S = in.nextInt();
            int poisoned = (S - 1 + M - 1) % N + 1;
            System.out.println(poisoned);
        }
    }
}
