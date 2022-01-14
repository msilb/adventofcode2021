package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class Day6 {

    public int part1(List<Integer> fishes) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(fishes);
        for (int i = 1; i <= 80; i++) {
            queue = queue.stream().map(k -> k - 1).collect(Collectors.toCollection(PriorityQueue::new));
            while (queue.size() > 0 && queue.peek() == -1) {
                queue.poll();
                queue.add(6);
                queue.add(8);
            }
        }
        return queue.size();
    }

    public long part2(List<Integer> fishes) {
        long[] cnt = new long[9];
        for (int f : fishes) {
            cnt[f]++;
        }
        for (int day = 0; day < 256; day++) {
            long[] arr = new long[9];
            for (int i = 0; i < 9; i++) {
                arr[(i + 8) % 9] += cnt[i];
            }
            arr[6] += cnt[0];
            cnt = arr;
        }

        return Arrays.stream(cnt).sum();
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day6 day6 = new Day6();

        Path path = Paths.get(Objects.requireNonNull(Day6.class.getClassLoader().getResource("day6.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        List<Integer> fishes = lines.stream().flatMap(e -> Arrays.stream(e.split(","))).map(Integer::parseInt).toList();

        int resultPart1 = day6.part1(fishes);
        System.out.println(resultPart1);
        long resultPart2 = day6.part2(fishes);
        System.out.println(resultPart2);
    }
}
