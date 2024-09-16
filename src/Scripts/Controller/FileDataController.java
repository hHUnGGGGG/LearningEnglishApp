package Scripts.Controller;

import java.io.*;
import java.util.ArrayList;

public class FileDataController {
    private String filePath = "src/Data/JsonWord.txt";

    public void readFile(ArrayList<String> Line) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Line.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(ArrayList<String> Line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for(String word : Line) {
                writer.write(word);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
