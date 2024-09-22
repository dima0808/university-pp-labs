package ua.kpi.lab1;

import java.util.Scanner;
import java.util.concurrent.CyclicBarrier;
import ua.kpi.lab1.thread.T1;
import ua.kpi.lab1.thread.T2;
import ua.kpi.lab1.thread.T3;
import ua.kpi.lab1.utils.Data;

/**
 * Паралельне програмування
 * Лабораторна робота 1.3 "Потоки в мові Java"
 * Варіант 18
 * <ul>
 *   <li>1.11 F1: c = MAX(MA * MB) * (A * B)</li>
 *   <li>2.29 F2: MF = (MG + MH) * (MK * ML) * (MG + ML)</li>
 *   <li>3.7 F3: O = (P + R) * (MS * MT)</li>
 * </ul>
 * Мамченко Д.О. ІО-25
 * Дата 22.09.2024
 */
public class Lab1 {
  private static final CyclicBarrier barrier = new CyclicBarrier(3);
  private static InputType inputType;

  /**
   * Головний метод програми, який запускає три потоки для обчислення формул F1, F2 та F3.
   *
   * @param args аргументи командного рядка
   */
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    initializeData(scanner);

    TaskDetails t1Details = getTaskDetails(scanner, "T1");
    TaskDetails t2Details = getTaskDetails(scanner, "T2");
    TaskDetails t3Details = getTaskDetails(scanner, "T3");

    T1 t1 = new T1(t1Details.getName(), t1Details.getPriority(), t1Details.getStackSize(),
        inputType, barrier);
    T2 t2 = new T2(t2Details.getName(), t2Details.getPriority(), t2Details.getStackSize(),
        inputType, barrier);
    T3 t3 = new T3(t3Details.getName(), t3Details.getPriority(), t3Details.getStackSize(),
        inputType, barrier);

    t1.start();
    t2.start();
    t3.start();

    try {
      t1.join();
      t2.join();
      t3.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Ініціалізує дані для обчислень, включаючи розмірність N та тип введення даних.
   *
   * @param scanner об'єкт Scanner для зчитування введення користувача
   */
  private static void initializeData(Scanner scanner) {
    System.out.print("Введіть розмірність N: ");
    Data.N = scanner.nextInt();

    System.out.print("Виберіть тип задання елементів (CONSOLE, DEFAULT, RANDOM, FILE): ");
    inputType = InputType.valueOf(scanner.next().toUpperCase());

    if (inputType == InputType.RANDOM) {
      System.out.println("Рандомні значення будуть згенеровані в діапазоні [0, 100)");
    }

    System.out.println();
  }

  /**
   * Збирає деталі задачі для потоку, включаючи ім'я, пріоритет та розмір стека.
   *
   * @param scanner об'єкт Scanner для зчитування введення користувача
   * @param taskLabel мітка задачі (наприклад, "T1", "T2", "T3")
   * @return об'єкт TaskDetails, що містить ім'я, пріоритет та розмір стека задачі
   */
  private static TaskDetails getTaskDetails(Scanner scanner, String taskLabel) {
    System.out.print("Вкажіть ім'я задачі " + taskLabel + ": ");
    String name = scanner.next();
    System.out.print("Встановіть пріоритет задачі " + name + " (1-10): ");
    int priority = scanner.nextInt();
    System.out.print("Задайте розмір стека задачі " + name + ": ");
    long stackSize = scanner.nextLong();
    System.out.println();
    return new TaskDetails(name, priority, stackSize);
  }
}