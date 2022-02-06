package com.praktikum;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreatUserTestParam {

    private UserClient userClient;
    private final User user;
    private final int statusCode;
    private final String message;

    //@Before
   // public void setUp() {
        //userClient = new UserClient();
  //  }

// Метод для параметризации

    public CreatUserTestParam (User user,int statusCode, String message) {
        this.user = user;
        this.statusCode = statusCode;
        this.message = message;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {User.getRandom(), 200, null},
                {User.getUserWithoutEmail(),403,"Email, password and name are required fields"},
                {User.getUserWithoutPassword(),403,"Email, password and name are required fields"},
                {User.getUserWithoutName(),403,"Email, password and name are required fields"}
        };
    }
    @Test
    public void courierNotCreatedWithoutNecessaryField () {

        // Создание курьера
        //ValidatableResponse response = userClient.create(user); В БЕФОР ЕСТЬ ЮЗЕР
        ValidatableResponse response = new UserClient().create(user);
        // Получение статус кода запроса
        int statusCode = response.extract().statusCode();
        // Получение тела ответа при создании клиента
        boolean isUserNotCreated = response.extract().path("success");
        // Получение сообщения об ошибке
        String errorMessage = response.extract().path("message");

        // Проверка что статус код соответствует ожиданиям
        assertEquals("Status code is not correct", statusCode, statusCode);
        // Проверка что ссобщение об ошибке соответвует ожиданимя
        assertEquals("The error message is not correct", message, errorMessage);
    }
}

