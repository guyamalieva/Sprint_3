package com.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CreatChangeTest {

    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
        userClient.create(user);
    }

    @Test
    @DisplayName("Checking user can change credentials after authorization")
    public void checkAuthUserCredentialsChangeTest() {
        String accessToken = userClient.login(UserCredentials2.from(user)).extract().path("accessToken");
        accessToken = StringUtils.remove(accessToken, "Bearer ");
        ValidatableResponse response = userClient.userInfoChange(accessToken, UserCredentials2.getUserCredentials());
        int statusCode = response.extract().statusCode();
        // Получение тела ответа при запросе информации о пользователе
        boolean isChangesSuccess = response.extract().path("success");
        assertThat("Некорректный код статуса", statusCode, equalTo(200));
        assertThat("Информация о пользователе не изменилась", isChangesSuccess);
    }

    @Test
    @DisplayName("Checking user can't change credentials without authorization")
    public void checkNotAuthUserCanNotChangeCredentialsTest() {
        ValidatableResponse response = userClient.userInfoChange( "",UserCredentials2.getUserCredentials());
        int statusCode = response.extract().statusCode();
        boolean isNotSuccessChanges = response.extract().path("message").equals("You should be authorised");
        assertThat("Некорректный код статуса", statusCode, equalTo(401));
        assertThat("Успешный запрос на изменение данных без авторизации", isNotSuccessChanges);
    }
}