package com.praktikum;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

public class CreateUserTest {

    private User user;
    private UserClient userClient;

    // Создание рандомного пользователя
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }
    //это есть в параметризованном тесте
    @Test
    @DisplayName("Проверка создания пользователя с валидными данными")
    public void checkUserCreationPassedTest() {
        // Создание пользователя
       // ValidatableResponse response = new UserClient().create(user);
        ValidatableResponse response = userClient.create(user);
        // Получение статус кода с тела создания пользователя
        int statusCode = response.extract().statusCode();
        // Получение ответа
        boolean isUserCreated = response.extract().path("success");
        // Получение токена пользователя
        String accessToken = response.extract().path("accessToken");
        // Проверка что пользлователь создался
        assertTrue("Пользователь не создан", isUserCreated);
        // Проверка что статус код соответствует ожиданиям
        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        // Проверка что токен пользователя есть
        assertTrue("Некорректный accessToken", accessToken.startsWith("Bearer"));
    }

    //посмотреть еще
    @Test
    @DisplayName("Проверка невозможности создать двух одинаковых пользователя")
    public void checkCannotCreateIdenticalUsersTest() {
        userClient.create(user);
        ValidatableResponse response = userClient.create(user);
        int statusCode = response.extract().statusCode();
        //переименовать
        boolean isIdenticalUserNotCreated = response.extract().path("message").equals("User already exists");
        assertThat("Некорректный код статуса", statusCode, equalTo(403));
        assertTrue("Пользователь уже существует", isIdenticalUserNotCreated);
    }
   // @Test
  //  @Description("Проверка что нельзя зарегистрировать 2х одинаковых пользователей")
  //  public void userCanBeCreatedTest (){

        // Создание клиента
     //   userClient.create(user);
        // Попытка создания пользователя с теми же данными
     //   ValidatableResponse response = userClient.create(user);
        // Получение статус кода с тела создания одинакового пользователя
     //   int statusCode = response.extract().statusCode();
        // Получение тела ответа при создании одинакового пользователя
     //   boolean isUserNotCreated = response.extract().path("success");
        // Получение тела сообщения
    //    String errorMessage = response.extract().path("message");

        // Проверка что статус код соответсвует ожидаемому
     //   assertThat ("Status code is not correct", statusCode, equalTo(403));
        // Проверка что одинаковый пользователь не создался
      //  assertFalse ("The user has been created", isUserNotCreated);
        // Проверка что сообщение об ошибке соответсвует ожидаемому
     //   assertEquals("The error massage is not correct", "User already exists", errorMessage);
    //}
}

