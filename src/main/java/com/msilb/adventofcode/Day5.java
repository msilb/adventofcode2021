package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.max;
import static java.lang.Integer.min;

public class Day5 {

    record Point(int x, int y) {
    }

    sealed interface Line permits HorizontalLine, VerticalLine, DiagonalLine {
    }

    record HorizontalLine(Point a, Point b) implements Line {
    }

    record VerticalLine(Point a, Point b) implements Line {
    }

    record DiagonalLine(Point a, Point b) implements Line {
    }

    public int part1(List<? extends Line> lines) {
        int[][] field = new int[1000][1000];
        for (Line l : lines) {
            if (l instanceof HorizontalLine hl) {
                processHorizontalLine(hl, field);
            } else if (l instanceof VerticalLine vl) {
                processVerticalLine(vl, field);
            } else {
                throw new RuntimeException("Line must be either horizontal or vertical!");
            }
        }
        return countDangerousAreas(field);
    }

    public int part2(List<? extends Line> lines) {
        int[][] field = new int[1000][1000];
        for (Line l : lines) {
            if (l instanceof HorizontalLine hl) {
                processHorizontalLine(hl, field);
            } else if (l instanceof VerticalLine vl) {
                processVerticalLine(vl, field);
            } else if (l instanceof DiagonalLine dl) {
                processDiagonalLine(dl, field);
            } else {
                throw new RuntimeException("Line must be horizontal, vertical or diagonal!");
            }
        }
        return countDangerousAreas(field);
    }

    private void processHorizontalLine(HorizontalLine hl, int[][] field) {
        for (int i = min(hl.a.y, hl.b.y); i <= max(hl.a.y, hl.b.y); i++) {
            field[hl.a.x][i]++;
        }
    }

    private void processVerticalLine(VerticalLine vl, int[][] field) {
        for (int i = min(vl.a.x, vl.b.x); i <= max(vl.a.x, vl.b.x); i++) {
            field[i][vl.a.y]++;
        }
    }

    private void processDiagonalLine(DiagonalLine dl, int[][] field) {
        if (dl.a.x < dl.b.x && dl.a.y < dl.b.y) {
            for (int i = dl.a.x; i <= dl.b.x; i++) {
                field[i][dl.a.y + i - dl.a.x]++;
            }
        }
        if (dl.a.x < dl.b.x && dl.a.y > dl.b.y) {
            for (int i = dl.a.x; i <= dl.b.x; i++) {
                field[i][dl.a.y - i + dl.a.x]++;
            }
        }
        if (dl.a.x > dl.b.x && dl.a.y < dl.b.y) {
            for (int i = dl.b.x; i <= dl.a.x; i++) {
                field[i][dl.b.y - i + dl.b.x]++;
            }
        }
        if (dl.a.x > dl.b.x && dl.a.y > dl.b.y) {
            for (int i = dl.b.x; i <= dl.a.x; i++) {
                field[i][dl.b.y + i - dl.b.x]++;
            }
        }
    }

    private int countDangerousAreas(int[][] field) {
        int cnt = 0;
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 1000; j++) {
                if (field[i][j] > 1) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day5 day5 = new Day5();

        Path path = Paths.get(Objects.requireNonNull(Day5.class.getClassLoader().getResource("day5.txt")).toURI());
        List<String> textLines = Files.readAllLines(path);

        List<? extends Line> lines = parseLines(textLines);

        int resultPart1 = day5.part1(lines.stream().filter(l -> l instanceof HorizontalLine || l instanceof VerticalLine).toList());
        System.out.println(resultPart1);
        int resultPart2 = day5.part2(lines);
        System.out.println(resultPart2);
    }

    private static List<? extends Line> parseLines(List<String> textLines) {
        return textLines.stream().map(s -> {
            String[] stringPoints = s.split(" -> ");
            int[] coordinates1 = Arrays.stream(stringPoints[0].split(",")).mapToInt(Integer::parseInt).toArray();
            int[] coordinates2 = Arrays.stream(stringPoints[1].split(",")).mapToInt(Integer::parseInt).toArray();
            Point a = new Point(coordinates1[0], coordinates1[1]);
            Point b = new Point(coordinates2[0], coordinates2[1]);
            if (a.x == b.x) {
                return new HorizontalLine(a, b);
            } else if (a.y == b.y) {
                return new VerticalLine(a, b);
            } else {
                return new DiagonalLine(a, b);
            }
        }).toList();
    }
}
