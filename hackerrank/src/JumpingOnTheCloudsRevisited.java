import java.util.Scanner;

/**
 * Created by sand94 on 24.10.2016.
 */
public class JumpingOnTheCloudsRevisited {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int E = 100;
        for (int i = 0; i < n; i++) {
            int c = in.nextInt();
            if (i % k == 0)
                E -= 1 + 2*c;
        }
        System.out.println(E);
    }
}
