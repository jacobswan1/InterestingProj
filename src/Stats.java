import java.util.*;
import java.io.*;


public class Stats {
    private int lines_processed;
    private int words_processed;
    private int empty_lines;
    private double avg_words_per_line;
    private double avg_line_length;

    public void update(int line_cnt, int word_cnt, int empty_line, int total_length) {
        this.lines_processed = line_cnt;
        this.words_processed = word_cnt;
        this.empty_lines = empty_line;
        this.avg_words_per_line = (double)word_cnt / line_cnt;
        this.avg_line_length = (double)total_length / line_cnt;
    }

    public int lines_processed() {
        return this.lines_processed;
    }

    public int words_processed() {
        return this.words_processed;
    }

    public int empty_liens() {
        return this.empty_lines;
    }

    public double avg_words_per_line() {
        return this.avg_words_per_line;
    }

    public double avg_line_length() {
        return this.avg_line_length;
    }
}
