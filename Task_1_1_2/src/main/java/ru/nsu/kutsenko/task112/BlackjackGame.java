package ru.nsu.kutsenko.task112;

import java.util.Scanner;

public class BlackjackGame {
    private Deck deck;
    public Player player;
    public Dealer dealer;
    private int playerWins;
    private int dealerWins;
    private int roundNumber;
    private Scanner scanner;


    public BlackjackGame(int numberOfDecks) {
        this.deck = new Deck(numberOfDecks);
        this.player = new Player();
        this.dealer = new Dealer();
        this.playerWins = 0;
        this.dealerWins = 0;
        this.roundNumber = 0;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        System.out.println("Добро пожаловать в Блэкджек!\n");

        while (true) {
            roundNumber++;
            playRound();

            System.out.println("\nСчет: Игрок " + playerWins + ":" + dealerWins + " Дилер");

            if (playerWins > dealerWins) {
                System.out.println("Ведет игрок!");
            } else if (dealerWins > playerWins) {
                System.out.println("Ведет дилер!");
            } else {
                System.out.println("Ничья в счете!");
            }

            System.out.print("Хотите сыграть еще раунд? (1 - да, 0 - нет): ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            }
            System.out.println();
        }

        System.out.println("\nИгра завершена. Счет: Игрок " + playerWins + ":" + dealerWins
                + " Дилер");

        if (playerWins > dealerWins) {
            System.out.println("Победил игрок!");
        } else if (dealerWins > playerWins) {
            System.out.println("Победил дилер!");
        } else {
            System.out.println("Ничья!");
        }

        scanner.close();
    }

    public void playRound() {
        System.out.println("Раунд " + roundNumber);

        player.clearHand();
        dealer.clearHand();
        if (deck.remainingCards() < 10) {
            deck.shuffle();
            System.out.println("Колода перетасована");
        }

        System.out.println("Дилер раздал карты");
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());

        displayGameState(false);

        if (checkBlackjack()) {
            return;
        }

        playerTurn();

        if (player.getHandValue() <= 21) {
            dealerTurn();
        }

        determineWinner();
    }

    private boolean checkBlackjack() {
        boolean playerBj = player.hasBlackjack();
        boolean dealerBj = dealer.hasBlackjack();

        if (playerBj && dealerBj) {
            System.out.println("У обоих блэкджек! Ничья.");
            return true;
        } else if (playerBj) {
            System.out.println("У вас блэкджек! Вы выиграли раунд!");
            playerWins++;
            return true;
        } else if (dealerBj) {
            dealer.revealAllCards();
            displayGameState(true);
            System.out.println("У дилера блэкджек! Дилер выиграл раунд.");
            dealerWins++;
            return true;
        }
        return false;
    }

    private void playerTurn() {
        System.out.println("\nВаш ход");
        System.out.println("-------");

        while (player.getHandValue() < 21) {
            System.out.print("Введите \"1\", чтобы взять карту, "
                    + "\"0\", чтобы остановиться: ");
            int choice = scanner.nextInt();

            if (choice == 0) {
                break;
            }

            Card newCard = deck.drawCard();
            player.addCard(newCard);
            System.out.println("Вы открыли карту " + newCard.textCard());
            displayGameState(false);
        }

        if (player.getHandValue() > 21) {
            System.out.println("Перебор! Сумма ваших карт: " + player.getHandValue());
        }
    }

    private void dealerTurn() {
        System.out.println("\nХод дилера");
        System.out.println("-------");

        dealer.revealAllCards();
        displayGameState(true);

        while (dealer.getHandValue() < 17) {
            Card newCard = deck.drawCard();
            dealer.addCard(newCard);
            System.out.println("Дилер открывает карту " + newCard.textCard());
            displayGameState(true);

            if (dealer.getHandValue() > 21) {
                System.out.println("Дилер перебрал! Сумма карт дилера: " + dealer.getHandValue());
                break;
            }
        }
    }

    private void determineWinner() {
        int playerValue = player.getHandValue();
        int dealerValue = dealer.getHandValue();

        System.out.println("\nИтог раунда:");
        System.out.println("Ваши карты: " + player.getHandString() + " > " + playerValue);
        System.out.println("Карты дилера: " + dealer.getHandString(true) + " > " + dealerValue);

        if (playerValue > 21) {
            System.out.println("Вы проиграли раунд!");
            dealerWins++;
        } else if (dealerValue > 21) {
            System.out.println("Вы выиграли раунд!");
            playerWins++;
        } else if (playerValue > dealerValue) {
            System.out.println("Вы выиграли раунд!");
            playerWins++;
        } else if (dealerValue > playerValue) {
            System.out.println("Дилер выиграл раунд!");
            dealerWins++;
        } else {
            System.out.println("Ничья!");
        }
    }

    private void displayGameState(boolean dealerRevealed) {
        System.out.println("Ваши карты: " + player.getHandString() + " > " + player.getHandValue());
        System.out.println("Карты дилера: " + dealer.getHandString(dealerRevealed));
    }

    public static void main(String[] args) {
        System.out.print("Введите количество колод (1-8): ");
        Scanner initScanner = new Scanner(System.in);
        int decks = initScanner.nextInt();

        if (decks < 1) {
            decks = 1;
        }
        if (decks > 8) {
            decks = 8;
        }

        BlackjackGame game = new BlackjackGame(decks);
        game.startGame();
    }

    public int getPlayerWins() {
        return playerWins;
    }

    public int getDealerWins() {
        return dealerWins;
    }
}