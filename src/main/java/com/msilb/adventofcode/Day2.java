package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Day2 {

    public long part1() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("day2.txt")).toURI());
        List<String> lines = Files.readAllLines(path);
        long horizontalPos = 0;
        long depth = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            String action = parts[0];
            long value = Long.parseLong(parts[1]);
            if (action.equalsIgnoreCase("forward")) {
                horizontalPos += value;
            } else if (action.equalsIgnoreCase("down")) {
                depth += value;
            } else if (action.equalsIgnoreCase("up")) {
                depth -= value;
            } else {
                throw new RuntimeException("Invalid action: " + action);
            }
        }
        return horizontalPos * depth;
    }

    public long part2() throws URISyntaxException, IOException {
        Path path = Paths.get(Objects.requireNonNull(getClass().getClassLoader().getResource("day2.txt")).toURI());
        List<String> lines = Files.readAllLines(path);
        long horizontalPos = 0;
        long depth = 0;
        long aim = 0;
        for (String line : lines) {
            String[] parts = line.split(" ");
            String action = parts[0];
            long value = Long.parseLong(parts[1]);
            if (action.equalsIgnoreCase("forward")) {
                horizontalPos += value;
                depth += aim * value;
            } else if (action.equalsIgnoreCase("down")) {
                aim += value;
            } else if (action.equalsIgnoreCase("up")) {
                aim -= value;
            } else {
                throw new RuntimeException("Invalid action: " + action);
            }
        }
        return horizontalPos * depth;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day2 day2 = new Day2();
        long resultPart1 = day2.part1();
        System.out.println(resultPart1);
        long resultPart2 = day2.part2();
        System.out.println(resultPart2);
    }
}
