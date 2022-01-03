package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Day3 {

    enum RatingType {
        OXYGEN_GENERATOR, CO2_SCRUBBER
    }

    public int part1(List<String> lines) {
        int inputLength = lines.size();
        int bitLength = lines.get(0).length();
        int[] ones = new int[bitLength];
        for (String line : lines) {
            for (int i = 0; i < bitLength; i++) {
                if (line.charAt(i) == '1') {
                    ones[i]++;
                }
            }
        }
        StringBuilder gammaBuilder = new StringBuilder();
        StringBuilder epsilonBuilder = new StringBuilder();
        for (int i = 0; i < bitLength; i++) {
            if (ones[i] >= inputLength / 2) {
                gammaBuilder.append("1");
                epsilonBuilder.append("0");
            } else {
                gammaBuilder.append("0");
                epsilonBuilder.append("1");
            }
        }
        int gamma = Integer.parseInt(gammaBuilder.toString(), 2);
        int epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);
        return gamma * epsilon;
    }

    public int part2(List<String> lines) {
        List<String> oxygenGeneratorRatingCandidates = new ArrayList<>(lines);
        List<String> co2ScrubberRatingCandidates = new ArrayList<>(lines);

        int oxygenGeneratorRating = calcRating(oxygenGeneratorRatingCandidates, RatingType.OXYGEN_GENERATOR);
        int co2ScrubberRating = calcRating(co2ScrubberRatingCandidates, RatingType.CO2_SCRUBBER);

        return oxygenGeneratorRating * co2ScrubberRating;
    }

    private int calcRating(List<String> candidates, RatingType ratingType) {
        int i = 0;
        while (candidates.size() != 1) {
            int ones = 0;
            int zeros = 0;
            for (String line : candidates) {
                if (line.charAt(i) == '1') {
                    ones++;
                } else {
                    zeros++;
                }
            }
            final int index = i;
            if (ones >= zeros) {
                if (ratingType == RatingType.OXYGEN_GENERATOR) {
                    candidates.removeIf(line -> line.charAt(index) != '1');
                } else {
                    candidates.removeIf(line -> line.charAt(index) != '0');
                }
            } else {
                if (ratingType == RatingType.OXYGEN_GENERATOR) {
                    candidates.removeIf(line -> line.charAt(index) != '0');
                } else {
                    candidates.removeIf(line -> line.charAt(index) != '1');
                }
            }
            i++;
        }
        return Integer.parseInt(candidates.get(0), 2);
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day3 day3 = new Day3();

        Path path = Paths.get(Objects.requireNonNull(Day3.class.getClassLoader().getResource("day3.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        int resultPart1 = day3.part1(lines);
        System.out.println(resultPart1);
        int resultPart2 = day3.part2(lines);
        System.out.println(resultPart2);
    }
}
