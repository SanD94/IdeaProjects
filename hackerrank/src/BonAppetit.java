import java.util.Scanner;

/**
 * Created by sand94 on 24.10.2016.
 */
public class BonAppetit {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int k = in.nextInt();
        int sum = 0;
        for (int i=0; i<n; i++) {
            int next = in.nextInt();
            if (i == k) continue;
            sum += next;
        }
        int bCharged = in.nextInt();
        int diff = bCharged - sum/2;
        String res = "Bon Appetit";
        if (diff > 0) res = ""+diff;
        System.out.println(res);
    }
}
