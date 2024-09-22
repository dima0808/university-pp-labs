package ua.kpi.lab1.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import ua.kpi.lab1.utils.Data;
import ua.kpi.lab1.InputType;

/**
 * Клас, що представляє потік T3 для обчислення формули F3: O = (P + R) * (MS * MT).
 */
public class T3 extends Thread {

  private final InputType inputType;
  private final CyclicBarrier barrier;

  int[] P = new int[0];
  int[] R = new int[0];
  int[][] MS = new int[0][];
  int[][] MT = new int[0][];

  /**
   * Конструктор для створення потоку T3.
   *
   * @param name      ім'я потоку
   * @param priority  пріоритет потоку
   * @param stackSize розмір стека
   * @param inputType тип введення даних (CONSOLE, RANDOM, FILE, DEFAULT)
   * @param barrier   об'єкт для синхронізації потоків
   */
  public T3(String name, int priority, long stackSize, InputType inputType, CyclicBarrier barrier) {
    super(null, null, name, stackSize);
    this.setPriority(priority);
    this.inputType = inputType;
    this.barrier = barrier;
  }

  /**
   * Метод, який виконується при запуску потоку T3.
   * Зчитує дані, чекає на інші потоки та обчислює результат формули F3.
   */
  @Override
  public void run() {
    initializeTensors();

    waitBarrier();

    long startTime = System.currentTimeMillis();
    int[] result = function3(P, R, MS, MT);
    long endTime = System.currentTimeMillis();

    System.out.println("Результат '" + getName() + "': " + Data.printVector(result, true));
    System.out.println("Час виконання обчислень '" + getName() + "': " +
        (endTime - startTime) + " мс");
  }

  /**
   * Ініціалізує тензори (вектори та матриці) для потоку T3 на основі типу введення даних.
   * Зчитує дані з консолі, файлу або генерує випадкові дані.
   */
  private void initializeTensors() {
    String taskName = getName();
    switch (inputType) {
      case CONSOLE:
        P = Data.inputVectorFromConsole(taskName, "P");
        R = Data.inputVectorFromConsole(taskName, "R");
        MS = Data.inputMatrixFromConsole(taskName, "MS");
        MT = Data.inputMatrixFromConsole(taskName, "MT");
        break;
      case RANDOM:
        P = Data.randomVector("P");
        R = Data.randomVector("R");
        MS = Data.randomMatrix("MS");
        MT = Data.randomMatrix("MT");
        break;
      case FILE:
        P = Data.readVectorFromFile("P.txt", "P");
        R = Data.readVectorFromFile("R.txt", "R");
        MS = Data.readMatrixFromFile("MS.txt", "MS");
        MT = Data.readMatrixFromFile("MT.txt", "MT");
        break;
      default:
        P = Data.inputVector(3, "P");
        R = Data.inputVector(3, "R");
        MS = Data.inputMatrix(3, "MS");
        MT = Data.inputMatrix(3, "MT");
        break;
    }
  }

  /**
   * Чекає на інші потоки, використовуючи CyclicBarrier для синхронізації.
   * Виводить повідомлення про очікування інших потоків.
   */
  private void waitBarrier() {
    try {
      System.out.println("'" + getName() + "' має всі дані та чекає на інші потоки.");
      barrier.await();
    } catch (InterruptedException | BrokenBarrierException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Обчислює результат формули F3: O = (P + R) * (MS * MT).
   *
   * @param P вектор P
   * @param R вектор R
   * @param MS матриця MS
   * @param MT матриця MT
   * @return результат обчислення формули F3 у вигляді вектора
   */
  private int[] function3(int[] P, int[] R, int[][] MS, int[][] MT) {
    return Data.multiplyVectorMatrix(
        Data.sumVectors(P, R),
        Data.multiplyMatrices(MS, MT));
  }
}
