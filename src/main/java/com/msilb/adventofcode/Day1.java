package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Day1 {

    public int part1() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("day1.txt")).toURI());
        final List<String> lines = Files.readAllLines(path);
        int cnt = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            final int curDepth = Integer.parseInt(lines.get(i));
            final int nextDepth = Integer.parseInt(lines.get(i + 1));
            if (nextDepth > curDepth) {
                cnt++;
            }
        }
        return cnt;
    }

    public int part2() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("day1.txt")).toURI());
        final List<String> lines = Files.readAllLines(path);
        int cnt = 0;
        for (int i = 0; i < lines.size() - 3; i++) {
            int curDepth = Integer.parseInt(lines.get(i));
            int nextDepth = Integer.parseInt(lines.get(i + 1));
            int nextNextDepth = Integer.parseInt(lines.get(i + 2));
            int nextNextNextDepth = Integer.parseInt(lines.get(i + 3));
            int curSum = curDepth + nextDepth + nextNextDepth;
            int nextSum = nextDepth + nextNextDepth + nextNextNextDepth;
            if (nextSum > curSum) {
                cnt++;
            }
        }
        return cnt;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day1 day1 = new Day1();
        int resultPart1 = day1.part1();
        System.out.println(resultPart1);
        int resultPart2 = day1.part2();
        System.out.println(resultPart2);
    }
}
