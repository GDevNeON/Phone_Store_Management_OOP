package Controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Stream {
    public static File file;
    public static FileWriter myWriter;
    public static Scanner sc;

    public Stream() {
        super();
    }

    public static String[] read(String filePath) throws FileNotFoundException {
        file = new File(filePath);
        sc = new Scanner(file);
        String[] output = new String[0];
        int count = 0;
        int size = 0;
        while (sc.hasNextLine()) {
            String data = sc.nextLine();
            String[] name = output.clone();
            output = new String[++size];
            System.arraycopy(name, 0, output, 0, name.length);
            output[count++] = data;
        }
        sc.close();
        return output;
    }

    public static void addOneLine(String filePath, String text) throws IOException {
        myWriter = new FileWriter(filePath, true);
        myWriter.write(text + "\n");
        myWriter.close();
    }

    public static void addAll(String filePath, String[] data) throws IOException {
        myWriter = new FileWriter(filePath);
        for (int i = 0; i < data.length; i++) {
            myWriter.write(data[i] + "\n");
        }
        myWriter.close();
    }


}
