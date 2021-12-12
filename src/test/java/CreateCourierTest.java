import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest extends RestAssuredClient {

    private CourierClient courierClient;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @Story("Проверка создания курьера")
    @DisplayName("Курьера можно создать")
    public void courierCreationTest() {
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        courierId = login.extract().path("id");

        assertTrue(isCourierCreated);
        assertThat(statusCode, equalTo(201));
        assertThat(courierId, notNullValue()); //is (not(0)))
    }
    @Test
    @Story("Проверка создания курьера")
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void courierSameNotCreationTest() {
        courierClient.create(courier);
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        courierId = courierClient.login(CourierCredentials.from(courier)).extract().path("id");
        ValidatableResponse response = courierClient.create(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierNotCreated = response.extract().path("message").equals("Этот логин уже используется. Попробуйте другой.");
        assertThat("Некорректный статус код", statusCode, equalTo(409));
        assertTrue("Созданы одинаковые курьеры", isCourierNotCreated);
    }
}


