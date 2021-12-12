import io.qameta.allure.Story;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierNegativeTest {


    private final Courier courier;
    private final int expectedStatus;
    private final String expectedMessage;

    public CreateCourierNegativeTest(Courier courier, int expectedStatus, String expectedMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedMessage = expectedMessage;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {Courier.getWithLoginOnly (), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getWithPasswordOnly(), 400, "Недостаточно данных для создания учетной записи"},
        };
    }
    @Test
    @Story("Проверка создания курьера")
    @DisplayName("Чтобы создать курьера, нужно передать в ручку все обязательные поля")
    public void courierInvalidRequestTest() {
        ValidatableResponse response = new CourierClient ().create(courier);
        String errorMessage = response.extract ().path ("message");
        int code = response.extract ().path ("code");
        assertEquals (expectedMessage, errorMessage);
        assertEquals (expectedStatus, code);
    }
}