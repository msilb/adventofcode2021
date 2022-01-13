package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

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
        Map<Integer, Long> map = initEmptyMap();
        for (int fish : fishes) {
            map.put(fish, map.get(fish) + 1);
        }
        for (int day = 0; day < 256; day++) {
            Map<Integer, Long> currMap = initEmptyMap();
            for (Map.Entry<Integer, Long> e : map.entrySet()) {
                if (e.getKey() == 0) {
                    currMap.put(6, currMap.get(6) + e.getValue());
                    currMap.put(8, currMap.get(8) + e.getValue());
                } else {
                    currMap.put(e.getKey() - 1, currMap.get(e.getKey() - 1) + e.getValue());
                }
            }
            map = currMap;
        }

        return map.values().stream().mapToLong(Long::longValue).sum();
    }

    private Map<Integer, Long> initEmptyMap() {
        Map<Integer, Long> map = new HashMap<>();
        for (int i = 0; i <= 8; i++) {
            map.put(i, 0L);
        }
        return map;
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
