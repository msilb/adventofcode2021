package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Predicate;

public class Day8 {

    public long part1(List<String> lines) {
        return lines.stream().flatMap(l -> {
            String[] outputValues = l.split("\\|")[1].trim().split(" ");
            Predicate<String> isOneFourSevenOrEight = s -> s.length() == 2 || s.length() == 3 || s.length() == 4 || s.length() == 7;
            return Arrays.stream(outputValues).filter(isOneFourSevenOrEight);
        }).count();
    }

    private List<String> sortStringChunks(String s) {
        return Arrays.stream(s.trim().split(" "))
                .map(c -> c.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString())
                .toList();
    }

    public int part2(List<String> lines) {
        return lines.stream().map(l -> {
            Map<String, Integer> signalToDigitMap = new HashMap<>();
            String four = "";
            String seven = "";
            String[] split = l.split("\\|");
            List<String> signalValues = sortStringChunks(split[0]);
            List<String> outputValues = sortStringChunks(split[1]);
            for (String s : signalValues) {
                if (s.length() == 2) {
                    signalToDigitMap.put(s, 1);
                } else if (s.length() == 3) {
                    signalToDigitMap.put(s, 7);
                    seven = s;
                } else if (s.length() == 4) {
                    signalToDigitMap.put(s, 4);
                    four = s;
                } else if (s.length() == 7) {
                    signalToDigitMap.put(s, 8);
                }
            }
            for (String s : signalValues) {
                if (s.length() == 5) {
                    if (seven.chars().allMatch(c -> s.indexOf(c) != -1)) {
                        signalToDigitMap.put(s, 3);
                    } else {
                        int intersections = 0;
                        for (char c : four.toCharArray()) {
                            if (s.indexOf(c) != -1) {
                                intersections++;
                            }
                        }
                        if (intersections == 3) {
                            signalToDigitMap.put(s, 5);
                        } else {
                            signalToDigitMap.put(s, 2);
                        }
                    }
                } else if (s.length() == 6) {
                    if (four.chars().allMatch(c -> s.indexOf(c) != -1)) {
                        signalToDigitMap.put(s, 9);
                    } else if (seven.chars().allMatch(c -> s.indexOf(c) != -1)) {
                        signalToDigitMap.put(s, 0);
                    } else {
                        signalToDigitMap.put(s, 6);
                    }
                }
            }
            return signalToDigitMap.get(outputValues.get(0)) * 1000 +
                    signalToDigitMap.get(outputValues.get(1)) * 100 +
                    signalToDigitMap.get(outputValues.get(2)) * 10 +
                    signalToDigitMap.get(outputValues.get(3));
        }).mapToInt(Integer::intValue).sum();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day8 day8 = new Day8();

        Path path = Paths.get(Objects.requireNonNull(Day8.class.getClassLoader().getResource("day8.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        long resultPart1 = day8.part1(lines);
        System.out.println(resultPart1);
        int resultPart2 = day8.part2(lines);
        System.out.println(resultPart2);
    }
}
