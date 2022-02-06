import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class AccountTestClass {

    private final String externalName;
    private final boolean expectedResult;

    public AccountTestClass(String externalName, boolean expectedResult){
        this.externalName = externalName;
        this.expectedResult = expectedResult;
    }

    //в рамках задания не было озвучено спец требований по кириллице и латинице
    @Parameterized.Parameters
    public static Object[][] checkIsExternalNameIsCorrectToEmboss() {
        return new Object[][] {
                //проверки выхода за граничные значения
                {"КК", false},
                {"Коллаборация Коллайдеров", false},
                //проверки на пробелы
                {" Пробел Впереди", false},
                {"Пробел Позади ", false},
                {"БезПробелов", false},
                {"Много   Пробелов", false},
                //проверки на непечатаемые символы и цифры и тд
                {"!@#$% ^&*()", false},
                {"1234567 8900", false},
                {"Винсент-Ван-Гог", false},
                {"Имя Три Слова", false},
                //валидные проверки
                {"Константин Makarov", true},
                {"К К", true},
        };
    }
    @Test
    public void checkCardholdersNameParameterizedTest(){
        Account account = new Account(externalName);
        boolean actualResult = account.checkNameToEmboss();
        assertEquals(actualResult, expectedResult);
    }
}