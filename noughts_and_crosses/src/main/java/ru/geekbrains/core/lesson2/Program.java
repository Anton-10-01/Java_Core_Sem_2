package ru.geekbrains.core.lesson2;

import java.util.Random;
import java.util.Scanner;

public class Program {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final int WIN_COUNT = 4;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;


    public static void main(String[] args) {
        while (true) {
            initialize();
            printFild();
            while (true) {
                humanTurn();
                printFild();
                if (gameChecks(DOT_HUMAN, WIN_COUNT, "Human win")) break;
                aiTurn();
                printFild();
                if (gameChecks(DOT_AI, WIN_COUNT, "Computer win")) break;
            }
            System.out.println("Желаете сыграть снова? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y")) break;
        }
    }

    /*
    Инициализация объектов игры
     */
    static void initialize() {
        fieldSizeX = 5;
        fieldSizeY = 5;
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /*
    Печать текущего состояние игрововго поля
     */
    static void printFild() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("-" + (x + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /*
    Ход игрока (человека)
     */
    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите мне координаты хода X и Y\n(от 1 до 3) через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка волидности координат хода
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }


    /*
    Ход игрока (компьютера)
     */
    private static void aiTurn() {

        // Побеждает ли компьютер в текущем ходе (при выигрышной комбинации WIN_COUNT)?
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_AI;
                    if (checkWin(DOT_AI, WIN_COUNT))
                        return;
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Побеждает ли игрок на текущий момент при выигрышной комбинации WIN_COUNT - 1?
        boolean f = checkWin(DOT_HUMAN, WIN_COUNT - 1);
        // Теперь, снова пройдем по всем свободным ячейкам игрового поля, если игрок уже побеждает при
        // выигрышной комбинации WIN_COUNT - 1, компьютер попытается закрыть последнюю выигрышную ячейку.
        // Если игрок НЕ побеждает при выигрышной комбинации WIN_COUNT - 1, компьютер будет действовать
        // на опережение, попытается заранее "подпортить" человеку выигрышную комбинацию.
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == DOT_EMPTY){
                    field[y][x] = DOT_HUMAN;
                    if (checkWin(DOT_HUMAN, WIN_COUNT - (f ? 0 : 1))) {
                        field[y][x] = DOT_AI;
                        return;
                    }
                    else
                        field[y][x] = DOT_EMPTY;
                }
            }
        }

        // Ни человек, ни компьютер не выигрывают, значит, компьютер ставит фишку случайным образом
        int x, y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (!isCellEmpty(x, y));
        field[y][x] = DOT_AI;
    }
    static void aiTurn1() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка на ничью
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверкаи победы
     *
     * @param dot фишка игрока
     * @return
     */
    /*
    static boolean checkWin(char dot){
        // провекра победы по горизонтали
        if(field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if(field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if(field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;

        // провекра победы по вертикали
        if(field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if(field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if(field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;

        // провекра победы по диагонали
        if(field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if(field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;

        return false;
    }
    static boolean checkWinV2(char dot, int win) {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (check1(x, y, dot, win)) return true;
            }
        }
        return false;
    }
*/
    /**
     * Проверка по горизонтали и вертикали и диагонали
     *
     * @param x
     * @param y
     * @param dot
     * @param win
     * @return
     */
    static boolean check1(int x, int y, char dot, int win) {
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        for (int i = 0; i < win; i++) {
            if (y + i < fieldSizeY && field[x][y + i] == dot) count1++;
            if (x + i < fieldSizeX && field[x + i][y] == dot) count2++;
            if (x + i < fieldSizeX && y + i < fieldSizeY && field[x + i][y + i] == dot) count3++;
            if (x + i < fieldSizeX && y - i > 0 && field[x + i][y - i] == dot) count4++;
        }
        if (count1 == win || count2 == win || count3 == win || count4 == win) return true;
        return false;
    }

    /**
     * Проверка состояния игры
     *
     * @param dot фишка игрока
     * @param s   победный слоган
     * @return
     */
    /*
    static boolean checkState(char dot, String s) {
        if (checkWinV2(dot, WIN_COUNT)) {
            System.out.println(s);
            return true;
        } else if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }
    */
//region Универсальная проверка победы игрока (задача 3*)

    /**
     * Проверка выигрыша игрока (человек или компьютер) горизонтали + вправо/вертикали + вниз
     *
     * @param x   начальная координата фишки
     * @param y   начальная координата фишки
     * @param dir направление проверки (-1 => горизонтали + вправо/ 1 => вертикали + вниз)
     * @param win выигрышная комбинация
     * @return результат проверки
     */
    static boolean checkXY(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 2,3) по горизонтали вправо и по вертикали вниз
        // (в зависимости от значения параметра dir)
            /*  +-1-2-3-4-5-
                1|.|.|.|.|.|
                2|.|.|.|.|.|
                3|.|X|X|X|X|
                4|.|X|.|.|.|
                5|.|X|.|.|.|
                ------------
            */
        for (int i = 1; i < win; i++)
            if (dir > 0 && (!isCellValid(x + i, y) || c != field[x + i][y])) return false;
            else if (dir < 0 && (!isCellValid(x, y + i) || c != field[x][y + i])) return false;
        return true;
    }

    /**
     * Проверка выигрыша игрока (человек или компьютер) по диагонали вверх + вправо/вниз + вправо
     *
     * @param x   начальная координата фишки
     * @param y   начальная координата фишки
     * @param dir направление проверки (-1 => вверх + вправо/ 1 => вниз + вправо)
     * @param win кол-во фишек для победы
     * @return результат проверки
     */
    static boolean checkDiagonal(int x, int y, int dir, int win) {
        char c = field[x][y]; // получим текущую фишку (игрок или компьютер)
        // Пройдем по всем ячейкам от начальной координаты (например 3,3) по диагонали вверх и по диагонали вниз
        // (в зависимости от значения параметра dir)
            /*  +-1-2-3-4-5-
                1|.|.|.|.|X|
                2|.|.|.|X|.|
                3|.|.|X|.|.|
                4|.|.|.|X|.|
                5|.|.|.|.|X|
                ------------
            */
        for (int i = 1; i < win; i++)
            if (!isCellValid(x + i, y + i * dir) || c != field[x + i][y + i * dir]) return false;
        return true;
    }

    /**
     * Проверка победы игрока
     *
     * @param dot      фишка игрока (человек или компьютер)
     * @param winCount кол-во фишек для победы
     * @return
     */
    static boolean checkWin(char dot, int winCount) {
        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == dot)
                    if (checkXY(y, x, 1, winCount) ||
                            checkXY(y, x, -1, winCount) ||
                            checkDiagonal(y, x, -1, winCount) ||
                            checkDiagonal(y, x, 1, winCount))
                        return true;
            }
        }
        return false;
    }

    /**
     * Метод проверки состояния игры
     *
     * @param dot фишка игрока (человек/компьютер)
     * @param win выигрышная комбинация
     * @param s   победное сообщение
     * @return результат проверки
     */
    private static boolean gameChecks(char dot, int win, String s) {
        if (checkWin(dot, win)) {
            System.out.println(s);
            return true;
        }
        if (checkDraw()) {
            System.out.println("draw!");
            return true;
        }
        return false;
    }
}