package me.sourabh.lambda;

/**
 * Sample use case of lambda functions
 *
 * Created by sourabhmahajan on 01/10/16.
 */

class MyMath {

    public static void main(String[] args) {
        PerformOperation isOdd = i -> (i % 2) == 0;
        int a = 0;
        a = a + 2;
        System.out.println(a);
        checker(isOdd, 10);
        checker(is_palindrome, 100);
        checker(is_prime, 100);
        checker(is_odd(), 100);
    }

    private static boolean checker(PerformOperation p, int num) {

        return p.check(num);
    }

    /**
     * Returns a reference to a PerformOperation function
     * @return
     */
    static PerformOperation is_odd() {
        return new PerformOperation() {
            public boolean check(int a) {
                return (a % 2 == 1);
            }
        };
    }

    static PerformOperation is_prime = a -> true;

    static PerformOperation is_palindrome = a -> {
        String as = String.valueOf(a);
        for (int i = 0; i < as.length() / 2; i++) {
            if (as.charAt(i) != as.charAt(as.length() - i - 1)) {
                return false;
            }
        }
        return true;
    };

    interface PerformOperation {
        boolean check(int a);
    }

}