package com.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CreatOrderTest {
    List<String> ingredients = new ArrayList<>();
    private User user;
    private UserClient userClient;
    public int orderClient;

    // Создание рандомного пользователя и бургера
    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Создание заказа. Зарегистрированный пользователь")
    public void checkAuthUserCanMakeAnOrderTest() {
        String token = userClient.create(user).extract().path("accessToken");
        ingredients = new IngredientsClient().getIngredients().extract().path("data._id");
        IngridientsData orderIngredients = new IngridientsData(ingredients.get(0));
        ValidatableResponse response = new OrderClient().createOrder(orderIngredients,token);
        //ValidatableResponse response = orderClient.createOrder(token,ingredients);
        int statusCode = response.extract().statusCode();
        boolean isOrderCreationSuccess = response.extract().path("success");
        orderClient = response.extract().path("order.number");
        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreationSuccess);
        assertThat("Отсутствует номер заказа", orderClient, is(not(0)));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void checkWithoutAuthUserCanMakeAnOrderTest() {
        ingredients = new IngredientsClient().getIngredients().extract().path("data._id");
        IngridientsData orderIngredients = new IngridientsData(ingredients.get(0));
        ValidatableResponse response = new OrderClient().createOrder(orderIngredients, "");
        int statusCode = response.extract().statusCode();
        boolean isOrderCreationSuccess = response.extract().path("success");
        int orderNumber = response.extract().path("order.number");
        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertTrue("Заказ не создан", isOrderCreationSuccess);
        assertThat("Отсутствует номер заказа", orderClient, is(not(0)));
    }

    @Test
    @DisplayName("Создание заказа без игредиентов")
    public void checkUserCanNotMakeAnOrderWithoutIngredientsTest() {
        String token = userClient.create(user).extract().path("accessToken");
        IngridientsData orderIngredients = new IngridientsData("");
        ValidatableResponse response = new OrderClient().createOrder(orderIngredients, token);
        int statusCode = response.extract().statusCode();
        boolean isOrderNotCreated = response.extract().path("message").equals("Ingredient ids must be provided");
        assertThat("Некорректный код статуса", statusCode, equalTo(400));
        assertTrue("Создан заказ с пустым списком ингредиентов", isOrderNotCreated);
    }
    @Test
    @DisplayName("Checking user can't create order with uncorrected ids of ingredients")
    public void checkUserCanNotMakeAnOrderWithUcorrectedIdsIngredientsTest() {
        String token = userClient.create(user).extract().path("accessToken");
        IngridientsData orderIngredients = new IngridientsData("test");
        ValidatableResponse response = new OrderClient().createOrder(orderIngredients, token);
        int statusCode = response.extract().statusCode();
        assertThat("Некорректный код статуса", statusCode, equalTo(500));
    }
}

