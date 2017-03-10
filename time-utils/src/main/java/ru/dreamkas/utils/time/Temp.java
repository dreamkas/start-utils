package ru.dreamkas.utils.time;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Temp {
    public static void main(String[] args) {
        System.out.println(execute("D:\\Program Files (x86)\\Git\\bin\\git.exe"));
    }

    private static List<String> execute(String bin) {
        try {
            ProcessBuilder builder = new ProcessBuilder(bin, "describe", "--first-parent");
            builder.redirectErrorStream(true);
            Process process = builder.start();
            List<String> lines = new ArrayList<>();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                String line;
                while ((line = input.readLine()) != null) {
                    System.out.println(line);
                    lines.add(line);
                }
            } finally {
                input.close();
            }
            return lines;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
