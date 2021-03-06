import java.util.Scanner;
/*
This is a simulation of the Acey Ducey card game.
In the game, the dealer (the computer) deals two cards face up.
You have an option to bet or not to bet depending on whether or not
you feel the next card dealt will have a value between the first two.

Your initial money is set to 100; alter accordingly.
The game keeps going on until you lose all your money or interrupt the program.
The original program author (in BASIC) was Bill Palmby of Prairie View, Illinois.
This is the Java conversion of that code.
 */

public class AceyDucey {

    private static String[] cardValue = {"0", "0", "2", "3", "4", "5", "6", "7", "8", "9",
                                         "10", "Jack (11)", "Queen (12)", "King (13)", "Ace (14)"};

    public static void ace() {
        Scanner keyboard = new Scanner(System.in);


        int[] moneyBet = {100, 0}; //index 0 total money, index 1 bet
        int bet = 0;

        boolean hasMoney = true;
        boolean yourTurn;
        boolean roundWin = true;

        System.out.println("Acey Duecy is played in the following manner");
        System.out.println("The dealer (computer) deals two cards face up");
        System.out.println("You have an option to bet or not bet depending");
        System.out.println("on whether or not you feel the card will have");
        System.out.println("a value between the first two. \n");
        System.out.println("If you do not want to bet, input a 0 (zero)");
        System.out.println("If you want to return to main menu, input -999");
        System.out.println();

        System.out.printf("You have %d dollars \n", moneyBet[0]);

        do {

            yourTurn = false;
            hasMoney = checkMoney(hasMoney, moneyBet);

            if(!hasMoney) {
                System.out.println("You ran out of money! Game Over!");
            }
            else {
                System.out.println();
                System.out.println("Here are your next two cards ");

                int cardOne = rollCard();
                int cardTwo = rollCard();

                while(cardTwo == 2) {
                    cardTwo = rollCard();
                }

                while(cardOne >= cardTwo) {
                    cardOne = rollCard();
                }

                printCard(cardOne);
                printCard(cardTwo);

                System.out.println();

                do {

                    System.out.println("What is your bet? ");
                    bet = keyboard.nextInt();
                    keyboard.nextLine();
                    if(bet == -999) {
                        System.out.println("Returning to main menu");
                        hasMoney = false;
                    }
                    else if(bet < 0 || bet > moneyBet[0]) {
                        System.out.println("Enter a valid bet \n");
                    }
                    else {
                        moneyBet[1] = bet;
                        if(bet == 0) {
                            System.out.println("Chicken!!");
                        }
                        else if(bet <= moneyBet[0]) {
                            int cardThree = rollCard();
                            printCard(cardThree);
                            roundWin = inBetween(cardOne, cardTwo, cardThree);
                        }
                    }

                    if(bet != -999) {
                        moneyBet = calcMoney(roundWin, moneyBet);
                    }
                    yourTurn = true;

                } while(!yourTurn);
            }
        } while(hasMoney);
    }

    private static boolean inBetween(int cardOne, int cardTwo, int cardThree) {
        boolean roundWin;
        if(cardThree > cardOne && cardThree < cardTwo) {
            roundWin = true;
        }
        else {
            roundWin = false;
            System.out.println("Sorry, you lose.");
        }
        return roundWin;
    }

    private static boolean checkMoney(boolean hasMoney, int[] moneyBet) {
        int money = moneyBet[0];
        if(money <= 0) {
            hasMoney = false;
        }
        return hasMoney;
    }

    private static int[] calcMoney(boolean roundWin, int[] moneyBet) {
        if(roundWin) {
            moneyBet[0] = moneyBet[0] + moneyBet[1];
        }
        else {
            moneyBet[0] = moneyBet[0] - moneyBet[1];
        }
        System.out.printf("You now have %d dollars \n", moneyBet[0]);
        return moneyBet;
    }

    private static int rollCard() {
        int cardNum;
        do {
            cardNum = (int)((Math.random() * 14) + 2);
        } while(cardNum < 2 || cardNum > 14);
        return cardNum;
    }

    private static void printCard(int card) {
        System.out.println(" " + cardValue[card]);
    }
}