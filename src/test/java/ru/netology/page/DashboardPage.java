package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final String initialBalance = "баланс: ";
    private final String finalBalance = " р.";
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final ElementsCollection cards = $$(".list__item div");

    public DashboardPage(){
        heading.shouldBe(visible);
    }


    public int cardBalance(DataHelper.CardInfo cardInfo){

        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))) .getText();
        return extractBalance(text);
    }

    public TransferPage cardToTransfer (DataHelper.CardInfo cardInfo){

        cards.findBy(attribute("data-test-id", cardInfo.getTestId())). $("button").click();
        return new TransferPage();
    }

    private int extractBalance(String text){
        var initial = text.indexOf(initialBalance);
        var finish = text.indexOf(finalBalance);
        var value = text.substring(initial + initialBalance.length(), finish);
        return Integer.parseInt(value);
    }

}
