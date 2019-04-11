import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        for (String arg : args) {
            new ICcrawler().crawl(arg).stream().forEach(System.out::println);
        }
    }
}