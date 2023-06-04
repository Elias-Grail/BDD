package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;





public class TransferTest {

    DashboardPage dashboardPage;
    LoginPage loginPage;


    @BeforeEach

    void setup(){
        loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = authInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = verificationCode();
        dashboardPage = verificationPage.validVerify(verificationCode);

    }

    @Test

    void valideTransferTest(){
        var firstCardInfo = firstCardInfo();
        var secondCardInfo = secondCardInfo();
        var firstCardBalance = dashboardPage.cardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.cardBalance(secondCardInfo);
        var sum = generateValidSum(firstCardBalance);
        var expectedBalanceFirstCard = firstCardBalance - sum;
        var expectedBalanceSecondCart = secondCardBalance + sum;
        var transferPage = dashboardPage.cardToTransfer (secondCardInfo);
        dashboardPage = transferPage.validTransfer(String.valueOf(sum), firstCardInfo);
        var actualBalanceFirstCard = dashboardPage.cardBalance(firstCardInfo);
        var actualBalanceSecondCard = dashboardPage.cardBalance(secondCardInfo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCart, actualBalanceSecondCard);

    }
}
