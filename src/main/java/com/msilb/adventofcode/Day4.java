package com.msilb.adventofcode;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day4 {

    record Board(int[][] fields, boolean[][] markings) {
    }

    public int part1(Board[] boards, int[] allDrawnNumbers) {
        for (int drawnNumber : allDrawnNumbers) {
            for (Board board : boards) {
                markBoard(board, drawnNumber);
                for (int j = 0; j < 5; j++) {
                    if (board.markings[j][0] && board.markings[j][1] && board.markings[j][2] && board.markings[j][3] && board.markings[j][4]) {
                        return calculateFinalResult(board, drawnNumber);
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (board.markings[0][j] && board.markings[1][j] && board.markings[2][j] && board.markings[3][j] && board.markings[4][j]) {
                        return calculateFinalResult(board, drawnNumber);
                    }
                }
            }
        }
        return -1;
    }

    public int part2(Board[] boards, int[] allDrawnNumbers) {
        Set<Board> alreadyWonBoards = new HashSet<>();
        for (int drawnNumber : allDrawnNumbers) {
            for (Board board : boards) {
                if (alreadyWonBoards.contains(board)) {
                    continue;
                }
                markBoard(board, drawnNumber);
                for (int j = 0; j < 5; j++) {
                    if (board.markings[j][0] && board.markings[j][1] && board.markings[j][2] && board.markings[j][3] && board.markings[j][4]) {
                        alreadyWonBoards.add(board);
                        if (alreadyWonBoards.size() == boards.length) {
                            return calculateFinalResult(board, drawnNumber);
                        }
                    }
                }
                for (int j = 0; j < 5; j++) {
                    if (board.markings[0][j] && board.markings[1][j] && board.markings[2][j] && board.markings[3][j] && board.markings[4][j]) {
                        alreadyWonBoards.add(board);
                        if (alreadyWonBoards.size() == boards.length) {
                            return calculateFinalResult(board, drawnNumber);
                        }
                    }
                }
            }
        }
        return -1;
    }

    private void markBoard(Board board, int drawnNumber) {
        for (int j = 0; j < 5; j++) {
            for (int k = 0; k < 5; k++) {
                if (board.fields[j][k] == drawnNumber) {
                    board.markings[j][k] = true;
                }
            }
        }
    }

    private int getSum(Board board) {
        int sum = 0;
        for (int k = 0; k < 5; k++) {
            for (int l = 0; l < 5; l++) {
                if (!board.markings[k][l]) {
                    sum += board.fields[k][l];
                }
            }
        }
        return sum;
    }

    private int calculateFinalResult(Board board, int drawnNumber) {
        int sum = getSum(board);
        return sum * drawnNumber;
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
        return new Board(fields, new boolean[5][5]);
    }
}
