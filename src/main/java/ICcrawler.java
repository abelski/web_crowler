import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ICcrawler {


    public static final String SEPARATOR = "&q%5";
    public static final String CSV_SEPARATOR = ",";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    public static final String INTERCITY_DOMAIN = "https://intercity.by";


    public List<String> crawl(final String city) throws IOException {
        final Date now = new Date();
        final String str_from = DATE_FORMAT.format(now);
        final Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MONTH, 6);

        final String str_to = DATE_FORMAT.format(instance.getTime());
        final Document document = Jsoup.connect(buildUrl(city, str_from, str_to)).get();

        final Integer founded_count = new Integer(document.select(".offers_result_h1,count")
                .text().replaceAll("[^1234567890]", ""));

        final List<String> result = document.select(".offers_el").stream().map(this::getCsvString).collect
                (Collectors.toList());

        //by default 10 per page
        if(founded_count>10){
            //TODO hiden results should be included
        }

        return result;
    }

    private String buildUrl(final String city, final String str_from, final String str_to) {
        return INTERCITY_DOMAIN + "/tours/list/country/" + city + "/?" + SEPARATOR +
                "Badults%5D=2&q" +
                "%5Bchild%5D=0" + SEPARATOR +
                "Bchild_ages%5D=" + SEPARATOR +
                "Bratecode%5D=BYR" + SEPARATOR +
                "Bfrom%5D=448&q%5" +
                "Bcity%5D%5B%5D=-1" + SEPARATOR +
                "Bhotel%5D%5B%5D=-1" + SEPARATOR +
                "Bd_from%5D=" + str_from + SEPARATOR +
                "Bd_to%5D=" + str_to +
                "&adults=2" +
                "&children=0\"";
    }

    private String getCsvString(final Element element) {
        final String offers_price = element.select(".offers_price").text();
        final String offers_detail = element.select(".offers_detail").text();
        final String offers_rating = element.select(".offers_rating").text();
        final String offers_date = element.select(".offers_date").text();
        final String hotel_name = element.select(".offers_name_link").text();
        final String offers_name_link = INTERCITY_DOMAIN + element.select(".offers_name_link").attr("href");

        return
                offers_price + CSV_SEPARATOR +
                        offers_detail + CSV_SEPARATOR +
                        offers_rating + CSV_SEPARATOR +
                        offers_date + CSV_SEPARATOR +
                        hotel_name + CSV_SEPARATOR +
                        offers_name_link + CSV_SEPARATOR
                ;
    }
}
