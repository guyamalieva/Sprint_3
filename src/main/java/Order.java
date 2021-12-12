import org.apache.commons.lang3.RandomStringUtils;

import java.time.Instant;
import java.util.List;

public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;
    public static Instant time = Instant.now();

    public Order(String firstName, String lastName, String address, String metroStation, String phone, int rentTime,
                 String deliveryDate, String comment, List<String> color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order setColor(List<String> setColor) {
        final String firstName = RandomStringUtils.randomAlphabetic(5);
        final String lastName = RandomStringUtils.randomAlphabetic(5);
        final String address = RandomStringUtils.randomAlphabetic(5);
        final String metroStation = RandomStringUtils.randomAlphabetic(5);
        final String phone = RandomStringUtils.randomAlphabetic(5);
        final int rentTime = (int) Math.random() * 9;
        final String deliveryDate = time.toString();
        final String comment = RandomStringUtils.randomAlphabetic(5);
        final List<String> color = setColor;
        return new Order(firstName, lastName, address, metroStation, phone, rentTime,
                deliveryDate, comment, color);
    }
}
