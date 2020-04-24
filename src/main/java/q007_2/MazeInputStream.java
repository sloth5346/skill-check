package q007_2;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Random;

/**
 * 迷路用の InputStream
 */
public class MazeInputStream extends ByteArrayInputStream {
    /**
     * 迷路文字列を作成する
     * @return
     */
    private static String makeMaze() {
        Random random = new Random();
        int width = random.nextInt(5) + 5;
        int height = random.nextInt(5) + 5;
        char[][] data = new char[height * 2 + 3][width * 2 + 3];
        Arrays.fill(data[0], 'X');
        Arrays.fill(data[data.length - 1], 'X');
        for (int y = 1; y < data.length - 1; y++) {
            data[y][0] = 'X';
            data[y][data[y].length - 1] = 'X';
            Arrays.fill(data[y], 1, data[y].length - 1, ' ');
        }
        int startX = random.nextInt(width + 1) * 2 + 1;
        int startY = random.nextInt(height + 1) * 2 + 1;
        data[startY][startX] = 'S';
        while (true) {
            int endX = random.nextInt(width + 1) * 2 + 1;
            int endY = random.nextInt(height + 1) * 2 + 1;
            if (Math.abs(startX - endX) + Math.abs(startY - endY) > 10) {
                data[endY][endX] = 'E';
                break;
            }
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int xx = x * 2 + 2;
                int yy = y * 2 + 2;
                data[yy][xx] = 'X';
                switch (random.nextInt(4)) {
                    case 0:
                        data[yy - 1][xx] = 'X';
                        break;
                    case 1:
                        data[yy + 1][xx] = 'X';
                        break;
                    case 2:
                        data[yy][xx - 1] = 'X';
                        break;
                    case 3:
                        data[yy][xx + 1] = 'X';
                        break;
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (char[] strs: data) {
            result.append(new String(strs)).append(System.lineSeparator());
        }
        result.append(System.lineSeparator());
        return result.toString();
    }

    public MazeInputStream() {
        super(makeMaze().getBytes());
    }
}