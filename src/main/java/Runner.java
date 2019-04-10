import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        new ICcrawler().crawl(args[0]).stream().forEach(System.out::println);
    }
}