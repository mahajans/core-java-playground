package me.sourabh.lambda;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Various ways to create lambda functions
 *
 * Created by sourabhmahajan on 07/01/18.
 */
public class Sample {

    public static void main(String[] args) {
        Sample s = new Sample();
        int[] sumIndices = s.findIndices(new int[]{1, 2, 4}, 3, s::sum);
        System.out.println(Arrays.stream(sumIndices).mapToObj(Integer::toString).collect(Collectors.toList()));
        int[] multiplyIndices = s.findIndices(new int[]{1, 2, 4}, 2, s.multiply);
        System.out.println(Arrays.stream(multiplyIndices).mapToObj(Integer::toString).reduce("", (s1, s2) -> s1 + ", " + s2));
    }

    private int[] findIndices(int[] nums, int target, BiFunction<Integer, Integer, Integer> func) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (func.apply(nums[j], nums[i]) == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    private int sum(int a, int b) {
        return a + b;
    }

    private BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b;

}
