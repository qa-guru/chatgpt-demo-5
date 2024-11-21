import com.codeborne.selenide.Configuration;
import io.qameta.allure.Attachment;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;

import java.nio.file.Paths;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class ExtendedRegistrationFormTest {

    @BeforeAll
    public static void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = Paths.get("src/test/resources/pages/registration.html").toUri().toString();
    }

    @AfterEach
    public void tearDown() {
        attachScreenshot(); // Вложение скриншота в отчет
        closeWebDriver();
    }

    @Test
    @Description("Проверка успешной регистрации в форме")
    public void testExtendedRegistrationForm() {
        open("");
        fillRegistrationForm();
        submitForm();
        checkSuccessMessage();
    }

    @Test
    @Description("Проверка валидации формы при отсутствии имени пользователя")
    public void testValidationError() {
        open("");
        submitForm();
        checkValidationError();
    }

    @Step("Заполняем форму регистрации")
    private void fillRegistrationForm() {
        $("#username").setValue("testuser");
        $("#email").setValue("testuser@example.com");
        $("#password").setValue("password123");
        $("#confirmPassword").setValue("password123");
        $("#fullName").setValue("John Doe");
        $("#dob").setValue("1990-01-01");
        $("#gender").selectOption("Male");
    }

    @Step("Отправляем форму")
    private void submitForm() {
        $("button[type='submit']").click();
    }

    @Step("Проверяем сообщение об успешной регистрации")
    private void checkSuccessMessage() {
        $("#successMessage").shouldHave(text("Registration successful"));
    }

    @Step("Проверяем ошибку валидации для поля 'Username'")
    private void checkValidationError() {
        $("#username").shouldHave(cssClass("error"));
    }

    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] attachScreenshot() {
        return screenshot(OutputType.BYTES);
    }
}