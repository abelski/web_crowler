import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Runner {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("output.csv");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String arg : args) {
                new ICcrawler().crawl(arg).stream().forEach(s -> {
                    try {
                        writer.write(s);
                        writer.newLine();
                    } catch (IOException e) {
                    }
                });
            }
        }
        System.out.println("DONE");
    }

}