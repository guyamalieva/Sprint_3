
import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

@RunWith(Parameterized.class)

public class OrderCreationTest {

    private final List<String> color;

    public OrderCreationTest (List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColorType () {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK","GREY")},
                {List.of()}
        };
    }

    @Test
    @Story("Проверка создания заказа")
    @DisplayName("Проверка, что заказ может быть создан")
    public void orderCreatedTest () {

        Order order = Order.setColor(color);
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.create(order);

        int statusCode = response.extract().statusCode();
        int trackId = response.extract().path("track");
        System.out.println(trackId);
        assertThat ("Некорректный код статуса", statusCode, equalTo(201));
        assertThat("Отсутствует номер трэка", trackId, is(not(0)));
    }
}