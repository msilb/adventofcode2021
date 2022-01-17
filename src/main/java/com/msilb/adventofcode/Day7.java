package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day7 {

    public int part1(List<Integer> crabs) {
        List<Integer> sorted = crabs.stream().sorted().toList();
        int median = sorted.get(sorted.size() / 2);
        int minFuel = 0;
        for (int crab : crabs) {
            minFuel += Math.abs(crab - median);
        }
        return minFuel;
    }

    public long part2(List<Integer> crabs) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int crab : crabs) {
            if (crab < min) {
                min = crab;
            }
            if (crab > max) {
                max = crab;
            }
        }
        int minFuel = Integer.MAX_VALUE;
        for (int i = min; i <= max; i++) {
            int sum = 0;
            for (int crab : crabs) {
                int distance = Math.abs(crab - i);
                sum += distance * (distance + 1) / 2;
            }
            if (sum < minFuel) {
                minFuel = sum;
            }
        }
        return minFuel;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day7 day7 = new Day7();

        Path path = Paths.get(Objects.requireNonNull(Day7.class.getClassLoader().getResource("day7.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        List<Integer> crabs = lines.stream().flatMap(e -> Arrays.stream(e.split(","))).map(Integer::parseInt).toList();

        int resultPart1 = day7.part1(crabs);
        System.out.println(resultPart1);
        long resultPart2 = day7.part2(crabs);
        System.out.println(resultPart2);
    }
}
