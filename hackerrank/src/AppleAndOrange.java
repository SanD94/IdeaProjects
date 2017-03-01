import java.util.Scanner;

/**
 * Created by sand94 on 29.10.2016.
 */
public class AppleAndOrange {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int s = in.nextInt(), t = in.nextInt();
        int a = in.nextInt(), b = in.nextInt();
        int m = in.nextInt(), n = in.nextInt();
        int count = 0;
        for (int i = 0; i < m; i++) {
            int x = in.nextInt() + a;
            if (s <= x && x <= t) count++;
        }
        System.out.println(count);
        count = 0;
        for (int i = 0; i < n; i++) {
            int x = in.nextInt() + b;
            if (s <= x && x <= t) count++;
        }
        System.out.println(count);
    }
}
