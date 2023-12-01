import algorithm.Algorithm;
import algorithm.TwoPhaseLocking;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Algorithm two = new TwoPhaseLocking("R1(A) R2(A) W2(A) C1 R3(A) C2 W3(A) W3(B) C3");
        two.execute();
    }
}