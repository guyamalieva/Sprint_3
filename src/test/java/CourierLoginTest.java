import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class CourierLoginTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp(){
        courier = Courier.getRandom();
        courierClient = new CourierClient();
        courierClient.create(courier);
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @Story("Проверка авторизации")
    @DisplayName("Курьер может авторизоваться")
    public void checkCourierLoginTest() {
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        int statusCode = login.extract().statusCode();
        courierId = login.extract().path("id");
        assertThat ("Некорректный код статуса", statusCode, equalTo(200));
        assertThat("Неверный ID курьера", courierId, notNullValue()); //is(not(0))
    }
}