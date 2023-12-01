import algorithm.TwoPhaseLocking;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        TwoPhaseLocking two = new TwoPhaseLocking("R1(A) R2(A) W2(A) R3(A) C1 C2 W3(A) W3(B) C3");
        two.execute();
    }
}