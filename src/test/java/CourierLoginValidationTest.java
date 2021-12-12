import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierLoginValidationTest {

    private static CourierClient courierClient = new CourierClient();
    private static Courier courier = Courier.getRandom();
    private int courierId;
    private int expectedStatus;
    private String expectedMessage;
    private CourierCredentials courierCredentials;

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    public CourierLoginValidationTest(CourierCredentials courierCredentials, int expectedStatus, String expectedMessage) {
        this.courierCredentials = courierCredentials;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                {CourierCredentials.getCredentialsWithPasswordOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.getCredentialsWithLoginOnly(courier), 400, "Недостаточно данных для входа"},
                {CourierCredentials.getCredentialsWithRandomLogin (courier), 404 , "Учетная запись не найдена"},
                {CourierCredentials.getCredentialsWithRandomPassword (courier), 404 , "Учетная запись не найдена"},
        };
    }
    @Test
    @Story("Проверка авторизации")
    @DisplayName("Для авторизации нужно передать все обязательные поля")
    public void courierLoginValidationTest() {
        courierClient.create(courier);
        ValidatableResponse response = courierClient.login(CourierCredentials.from(courier));
        courierId = response.extract().path("id");
        ValidatableResponse errorResponse = new CourierClient().login(courierCredentials);
        int statusCode = errorResponse.extract().statusCode();
        assertEquals ("Некорректный код статуса", expectedStatus, statusCode);
        String errorMessage = errorResponse.extract().path ("message");
        assertEquals ("Некорректное сообщение об ошибке", expectedMessage, errorMessage);
    }
}