package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Day1 {

    public int part1(List<String> lines) {
        int cnt = 0;
        for (int i = 0; i < lines.size() - 1; i++) {
            int curDepth = Integer.parseInt(lines.get(i));
            int nextDepth = Integer.parseInt(lines.get(i + 1));
            if (nextDepth > curDepth) {
                cnt++;
            }
        }
        return cnt;
    }

    public int part2(List<String> lines) {
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

        Path path = Paths.get(Objects.requireNonNull(Day1.class.getClassLoader().getResource("day1.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        int resultPart1 = day1.part1(lines);
        System.out.println(resultPart1);
        int resultPart2 = day1.part2(lines);
        System.out.println(resultPart2);
    }
}
