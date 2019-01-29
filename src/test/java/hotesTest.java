import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.Select;

import utils.ConfProp;

import static org.hamcrest.MatcherAssert.assertThat;

public class hotesTest {
    private static WebDriver driver;

    public void Setup() throws InterruptedException {       //Настройки
        String chrome = ConfProp.getTestProperty("chromedriver");
        System.setProperty("webdriver.chrome.driver", chrome);
        driver = new ChromeDriver();
        //System.setProperty("webdriver.gecko.driver", "C:\\drivers\\geckodriver-v0.23.0-win64\\geckodriver.exe");      //при необходимости раскоментить для прогона тестов в Firefox
        //WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);  //доп. настройки, при надобности раскоментить

        String url = "https://www.google.com.ua/";
        driver.get(url);                   //открываем страницу
        System.out.println("Page opens!");
        Thread.sleep(5000);
    }

    @Test
    public void setParameters() throws InterruptedException {
        Setup(); //вызов метода настроек
        WebElement search = driver.findElement(By.cssSelector("[maxlength]"));  // Находим пполе поиска и
        search.sendKeys("automation"); //  пишем необходимое слово
        System.out.println("Search input 'automation' is done");    //вывод на экран отчет, что все прошло ок
        search.sendKeys(Keys.ENTER); //жмем Enter
        System.out.println("The button 'Search' is pressed");
        System.out.println();

        //ниже проверяем количество результатов поиска на первой странице
        List<WebElement> findElements = driver.findElements(By.xpath("//*[@id=\"rso\"]//a/div/cite"));   //создаем лист элементов, ищем по хПассу
        System.out.println("Number of search results on the first page = " + findElements.size());       //находим кол-во эдементов и выводим на экран
        System.out.println("The list of the search output elements:");
        for (WebElement webElement : findElements) {
            System.out.println(webElement.getText());   //выводим на экран список результатов поиска
        }
        System.out.println();
        // проверяем, что на первой странице в результатах поиска есть ссылка «https://en.wikipedia.org/wiki/Automation"
        if(driver.getPageSource().contains("https://en.wikipedia.org/wiki/Automation")){
            System.out.println("In the search output on the first page the link 'https://en.wikipedia.org/wiki/Automation' is present");
        }
        System.out.println();
        // проверяем,что всего результатов поиска больше 100 000 000
        WebElement element = driver.findElement(By.xpath("//*[@id=\"resultStats\"]"));
        String str = element.getText().replaceAll("\\D+","");   // оставляем только цифры в найденной строке результата
        System.out.println("Total number of search results = " + str.substring(0, str.length() - 3));   //убираем три последние цифры, которые являются показателем времени и не относятся к кол-ву результатов
        Integer res = Integer.parseInt(str.substring(0, str.length() - 3));     //приводим стринговую строку в интовую
        if (res >= 100000000){
            System.out.println("The total number of search result is more than 100 000 000");
        }
        else{
            System.out.println("The total number of search result is less than 100 000 000");
        }
        System.out.println();
        System.out.println("Test task complete! Best regards from Ievgen Obodianskyi!");
        driver.quit();
    }
}