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
    private static int filedSizeX;
    private static int filedSizeY;
    private static char[][] filed;


    public static void main(String[] args) {
        while (true){
            initialize();
            printFild();
            while (true){
                humanTurn();
                printFild();
                if (checkState(DOT_HUMAN, "Вы победили!")) break;
                aiTurn();
                printFild();
                if (checkState(DOT_AI, "Победил компьютер!")) break;
            }
            System.out.println("Желаете сыграть снова? (Y - да): ");
            if(!scanner.next().equalsIgnoreCase("Y")) break;
        }
    }
    /*
    Инициализация объектов игры
     */
    static void initialize(){
        filedSizeX = 5;
        filedSizeY = 5;
        filed = new char[filedSizeX][filedSizeY];
        for (int x = 0; x < filedSizeX; x++) {
            for (int y = 0; y < filedSizeY; y++) {
                filed[x][y] = DOT_EMPTY;
            }
        }
    }

    /*
    Печать текущего состояние игрововго поля
     */
    static void printFild(){
        System.out.print("+");
        for (int x = 0; x < filedSizeX; x++) {
            System.out.print("-" + (x + 1));
        }
        System.out.println("-");

        for (int x = 0; x < filedSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < filedSizeY; y++) {
                System.out.print(filed[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < filedSizeX * 2 + 2; x++) {
            System.out.print("-");
        }
        System.out.println();
    }

    /*
    Ход игрока (человека)
     */
    static void humanTurn(){
        int x;
        int y;
        do {
            System.out.println("Введите мне координаты хода X и Y\n(от 1 до 3) через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        filed[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y){
        return filed[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка волидности координат хода
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y){
        return x >= 0 && x < filedSizeX && y >= 0 && y < filedSizeY;
    }


    /*
    Ход игрока (компьютера)
     */
    static void aiTurn(){
        int x;
        int y;
            do {
                x = random.nextInt(filedSizeX);
                y = random.nextInt(filedSizeY);
            }
            while(!isCellEmpty(x, y));
            filed[x][y] = DOT_AI;
    }

    /**
     * Проверка на ничью
     * @return
     */
    static boolean checkDraw(){
        for (int x = 0; x < filedSizeX; x++) {
            for (int y = 0; y < filedSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверкаи победы
     * @param dot фишка игрока
     * @return
     */
    /*
    static boolean checkWin(char dot){
        // провекра победы по горизонтали
        if(filed[0][0] == dot && filed[0][1] == dot && filed[0][2] == dot) return true;
        if(filed[1][0] == dot && filed[1][1] == dot && filed[1][2] == dot) return true;
        if(filed[2][0] == dot && filed[2][1] == dot && filed[2][2] == dot) return true;

        // провекра победы по вертикали
        if(filed[0][0] == dot && filed[1][0] == dot && filed[2][0] == dot) return true;
        if(filed[0][1] == dot && filed[1][1] == dot && filed[2][1] == dot) return true;
        if(filed[0][2] == dot && filed[1][2] == dot && filed[2][2] == dot) return true;

        // провекра победы по диагонали
        if(filed[0][0] == dot && filed[1][1] == dot && filed[2][2] == dot) return true;
        if(filed[0][2] == dot && filed[1][1] == dot && filed[2][0] == dot) return true;

        return false;
    }*/

    static boolean checkWinV2(char dot, int win){
        for (int x = 0; x < filedSizeX; x++) {
            for (int y = 0; y < filedSizeY; y++) {
                if (check1(x, y, dot, win)) return true;
            }
        }
        return false;
    }

    /**
     * Проверка по горизонтали и вертикали и диагонали
     * @param x
     * @param y
     * @param dot
     * @param win
     * @return
     */
    static boolean check1(int x, int y, char dot, int win){
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        for (int i = 0; i < win; i++) {
            if(y + i < filedSizeY && filed[x][y + i] == dot) count1++;
            if(x + i < filedSizeX && filed[x + i][y] == dot) count2++;
            if(x + i < filedSizeX && y + i < filedSizeY && filed[x + i][y + i] == dot) count3++;
            if(x + i < filedSizeX && y - i > 0 && filed[x + i][y - i] == dot) count4++;
        }
        if(count1 == win || count2 == win || count3 == win || count4 == win) return true;
        return false;
    }

    /**
     * Проверка состояния игры
     * @param dot фишка игрока
     * @param s победный слоган
     * @return
     */
    static boolean checkState(char dot, String s){
        if(checkWinV2(dot, WIN_COUNT)){
            System.out.println(s);
            return true;
        }
        else if(checkDraw()){
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается
    }
}
