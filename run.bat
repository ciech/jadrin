
set JADE_MAIN=jade.Boot
set JADRIN_EXECUTABLE=jadrin.jar
set JADE_BIN=jade.jar



set WAITER_AGENT=waiter:main.jadrin.waiter.WaiterAgent;
set BARTENDER_DRINKUJ_PL=drinkuj_pl:main.jadrin.bartender.BartenderAgent;
set BARTENDER_KOKTAJLBAR_PL=koktajlbar_pl:main.jadrin.bartender.BartenderAgent;
set BARTENDER_TEST_PL=test_pl:main.jadrin.bartender.BartenderAgent
set AGENTS=%WAITER_AGENT%%BARTENDER_DRINKUJ_PL%%BARTENDER_KOKTAJLBAR_PL%%BARTENDER_TEST_PL%

java -cp %JADRIN_EXECUTABLE%;%JADE_BIN% %JADE_MAIN% -agents %AGENTS%
