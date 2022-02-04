
import java.util.Arrays;
import java.util.Scanner;

public class CinemaRoomManager {
    static int numberOfPurchasedTickets = 0;
    static int currentIncome = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();
        char[][] array = new char[rows][seats];

        for (char[] chars : array) {
            Arrays.fill(chars, 'S');
        }

        while (true) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            int input = scanner.nextInt();
            if (input == 1) {
                printSeats(array);
            } else if (input == 2) {
                boolean b = false;
                while (!b) {
                    System.out.println("Enter a row number:");
                    int row = scanner.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seat = scanner.nextInt();
                    if (row > array.length || seat > array.length) {
                        System.out.println("Wrong input!\n");
                    } else if (checkASeat(array, row, seat)) {
                        System.out.println("That ticket has already been purchased!\n");
                    } else {
                        markASeat(array, row, seat);
                        b = true;
                    }
                }
            } else if (input == 3) {
                double percentage = ((double) numberOfPurchasedTickets / (rows * seats) * 100);
                System.out.println("Number of purchased tickets: " + numberOfPurchasedTickets);
                System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
                System.out.println("Current income: $" + currentIncome);
                System.out.println("Total income: $" + calculateTotalIncome(rows, seats) + "\n");
            } else if (input == 0) {
                break;
            }
        }
    }

    public static boolean checkASeat(char[][] array, int row, int seat) {
        boolean b = false;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if ((i + 1) == row && (j + 1) == seat) {
                    b = array[i][j] != 'S';
                }
            }
        }
        return b;
    }

    public static int calculateTotalIncome(int rows, int seats) {
        if (rows * seats < 60) {
            return (rows * seats) * 10;
        } else {
            int mid = rows / 2;
            int backSeats = (rows - mid) * seats * 8;
            int frontSeats = (rows - (mid + 1)) * seats * 10;
            return backSeats + frontSeats;
        }
    }

    public static void printSeats(char[][] array) {
        System.out.print("Cinema:\n ");

        for (int i = 0; i < array[1].length; i++) {
            System.out.print(" " + (i + 1));
        }
        System.out.println();

        for (int i = 0; i < array.length; i++) {
            System.out.print(i + 1);
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(" " + array[i][j]);
            }
            System.out.println();
        }
    }

    public static void markASeat(char[][] array, int row, int seat) {
        if (row > array.length || seat > array.length) {
            System.out.println("Wrong input!\n");
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if ((i + 1) == row && (j + 1) == seat) {
                    if (array[i][j] == 'S') {
                        array[i][j] = 'B';
                        numberOfPurchasedTickets++;
                        System.out.println("Ticket price: $" + calculateTicketPrice(array, row) + "\n");
                    } else {
                        System.out.println("That ticket has already been purchased!\n");
                    }
                }
            }
        }
    }

    public static int calculateTicketPrice(char[][] array, int row) {
        int price = 0;
        if (array.length * array[1].length < 60) {
            price = 10;
        } else {
            int mid = array.length / 2;
            for (char[] chars : array) {
                for (int j = 0; j < chars.length; j++) {
                    if (row <= mid) {
                        price = 10;
                    } else {
                        price = 8;
                    }
                }
            }
        }
        currentIncome += price;
        return price;
    }
}
