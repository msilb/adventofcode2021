package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day4 {

    record Board(int[][] fields) {
    }

    public int part1(Board[] boards, int[] numbersDrawn) {
        boolean[][][] marked = new boolean[boards.length][5][5];
        int cnt = 0;
        while (cnt < numbersDrawn.length) {
            int n = numbersDrawn[cnt++];
            for (int i = 0; i < boards.length; i++) {
                Board board = boards[i];
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (board.fields[j][k] == n) {
                            marked[i][j][k] = true;
                        }
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (marked[i][j][0] && marked[i][j][1] && marked[i][j][2] && marked[i][j][3] && marked[i][j][4]) {
                        int sum = getSum(board, marked[i]);
                        return sum * n;
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (marked[i][0][j] && marked[i][1][j] && marked[i][2][j] && marked[i][3][j] && marked[i][4][j]) {
                        int sum = getSum(board, marked[i]);
                        return sum * n;
                    }
                }
            }
        }
        return -1;
    }

    public int part2(Board[] boards, int[] numbersDrawn) {
        boolean[][][] marked = new boolean[boards.length][5][5];
        int cnt = 0;
        Set<Board> alreadyWonBoards = new HashSet<>();
        while (cnt < numbersDrawn.length) {
            int n = numbersDrawn[cnt++];
            for (int i = 0; i < boards.length; i++) {
                Board board = boards[i];
                if (alreadyWonBoards.contains(board)) {
                    continue;
                }
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        if (board.fields[j][k] == n) {
                            marked[i][j][k] = true;
                        }
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (marked[i][j][0] && marked[i][j][1] && marked[i][j][2] && marked[i][j][3] && marked[i][j][4]) {
                        alreadyWonBoards.add(board);
                        if (alreadyWonBoards.size() == boards.length) {
                            int sum = getSum(boards[i], marked[i]);
                            return sum * n;
                        }
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (marked[i][0][j] && marked[i][1][j] && marked[i][2][j] && marked[i][3][j] && marked[i][4][j]) {
                        alreadyWonBoards.add(board);
                        if (alreadyWonBoards.size() == boards.length) {
                            int sum = getSum(boards[i], marked[i]);
                            return sum * n;
                        }
                    }
                }
            }
        }
        return -1;
    }

    private int getSum(Board board, boolean[][] marked) {
        int sum = 0;
        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {
                if (!marked[k][l]) {
                    sum += board.fields[k][l];
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) throws URISyntaxException, IOException {
        Day4 day4 = new Day4();

        Path path = Paths.get(Objects.requireNonNull(Day4.class.getClassLoader().getResource("day4.txt")).toURI());
        List<String> lines = Files.readAllLines(path);

        int[] numbersDrawn = Arrays.stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        ListIterator<String> it = lines.listIterator(1);
        List<Board> boards = new ArrayList<>();
        while (it.hasNext()) {
            it.next();
            Board b = parseBoard(it);
            boards.add(b);
        }

        int resultPart1 = day4.part1(boards.toArray(new Board[0]), numbersDrawn);
        System.out.println(resultPart1);
        int resultPart2 = day4.part2(boards.toArray(new Board[0]), numbersDrawn);
        System.out.println(resultPart2);
    }

    private static Board parseBoard(ListIterator<String> it) {
        int[][] fields = new int[5][5];
        for (int i = 0; i < 5; i++) {
            String[] strings = it.next().trim().split("\s+");
            fields[i] = Arrays.stream(strings).mapToInt(Integer::parseInt).toArray();
        }
        return new Board(fields);
    }
}
