package advent_of_code_2020;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class Application {

    public static void main(String[] argv) {
        try {
            new Application().go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void go() throws IOException {
        Stream<String> stream = Files.lines(Paths.get("./input"));
        int[] seats = stream
                .mapToInt(this::getSeatID)
                .sorted()
                .toArray();

        int min = seats[0];
        int max = seats[seats.length - 1];

        int available = IntStream.range(min, max + 1)
                .filter(i -> seats[i - min] != i)
                .findAny()
                .orElseThrow();

        System.out.println("Min Seat ID = " + min);
        System.out.println("Max Seat ID = " + max);
        System.out.println("My Seat ID = " + available);
    }

    int getSeatID(String input) {
        int row = find(input.substring(0, 7), 'B');
        int col = find(input.substring(7, 10), 'R');

        return row * 8 + col;
    }

    int find(String input, char upper) {
        Range range = new Range(0, (1 << input.length()) - 1);
        for (char ch : input.toCharArray()) {
            range = range.divide(ch == upper);
        }

        return range.min;
    }

    static final class Range {
        final int min;
        final int max;

        Range(int min, int max) {
            this.min = min;
            this.max = max;
        }

        Range divide(boolean upper) {
            return upper ? upperHalf() : lowerHalf();
        }

        Range upperHalf() {
            return new Range((min + max + 1) / 2, max);
        }

        Range lowerHalf() {
            return new Range(min, (min + max + 1) / 2 - 1);
        }
    }

}
