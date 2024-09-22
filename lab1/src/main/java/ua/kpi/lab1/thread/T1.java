package ua.kpi.lab1.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import ua.kpi.lab1.utils.Data;
import ua.kpi.lab1.InputType;

/**
 * Клас, що представляє потік T1 для обчислення формули F1: c = MAX(MA * MB) * (A * B).
 */
public class T1 extends Thread {
  private final InputType inputType;
  private final CyclicBarrier barrier;

  int[] A = new int[0];
  int[] B = new int[0];
  int[][] MA = new int[0][];
  int[][] MB = new int[0][];

  /**
   * Конструктор для створення потоку T1.
   *
   * @param name      ім'я потоку
   * @param priority  пріоритет потоку
   * @param stackSize розмір стека
   * @param inputType тип введення даних (CONSOLE, RANDOM, FILE, DEFAULT)
   * @param barrier   об'єкт для синхронізації потоків
   */
  public T1(String name, int priority, long stackSize, InputType inputType, CyclicBarrier barrier) {
    super(null, null, name, stackSize);
    this.setPriority(priority);
    this.inputType = inputType;
    this.barrier = barrier;
  }

  /**
   * Метод, який виконується при запуску потоку T1.
   * Зчитує дані, чекає на інші потоки та обчислює результат формули F1.
   */
  @Override
  public void run() {
    initializeTensors();

    waitBarrier();

    long startTime = System.currentTimeMillis();
    int result = function1(MA, MB, A, B);
    long endTime = System.currentTimeMillis();

    System.out.println("Результат '" + getName() + "': " + result);
    System.out.println("Час виконання обчислень '" + getName() + "': " +
        (endTime - startTime) + " мс");
  }

  /**
   * Ініціалізує тензори (вектори та матриці) для потоку T1 на основі типу введення даних.
   * Зчитує дані з консолі, файлу або генерує випадкові дані.
   */
  private void initializeTensors() {
    String taskName = getName();
    switch (inputType) {
      case CONSOLE:
        A = Data.inputVectorFromConsole(taskName, "A");
        B = Data.inputVectorFromConsole(taskName, "B");
        MA = Data.inputMatrixFromConsole(taskName, "MA");
        MB = Data.inputMatrixFromConsole(taskName, "MB");
        break;
      case RANDOM:
        A = Data.randomVector("A");
        B = Data.randomVector("B");
        MA = Data.randomMatrix("MA");
        MB = Data.randomMatrix("MB");
        break;
      case FILE:
        A = Data.readVectorFromFile("A.txt", "A");
        B = Data.readVectorFromFile("B.txt", "B");
        MA = Data.readMatrixFromFile("MA.txt", "MA");
        MB = Data.readMatrixFromFile("MB.txt", "MB");
        break;
      default:
        A = Data.inputVector(1, "A");
        B = Data.inputVector(1, "B");
        MA = Data.inputMatrix(1, "MA");
        MB = Data.inputMatrix(1, "MB");
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
   * Обчислює результат формули F1: c = MAX(MA * MB) * (A * B).
   *
   * @param MA матриця MA
   * @param MB матриця MB
   * @param A вектор A
   * @param B вектор B
   * @return результат обчислення формули F1 у вигляді цілого числа
   */
  private int function1(int[][] MA, int[][] MB, int[] A, int[] B) {
    return Data.maxMatrixElement(Data.multiplyMatrices(MA, MB)) * Data.dotProduct(A, B);
  }
}
