package ua.kpi.lab1.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import ua.kpi.lab1.utils.Data;
import ua.kpi.lab1.InputType;

/**
 * Клас, що представляє потік T2 для обчислення формули F2: MF = (MG + MH) * (MK * ML) * (MG + ML).
 */
public class T2 extends Thread {

  private final InputType inputType;
  private final CyclicBarrier barrier;

  int[][] MG = new int[0][];
  int[][] MH = new int[0][];
  int[][] MK = new int[0][];
  int[][] ML = new int[0][];

  /**
   * Конструктор для створення потоку T2.
   *
   * @param name      ім'я потоку
   * @param priority  пріоритет потоку
   * @param stackSize розмір стека
   * @param inputType тип введення даних (CONSOLE, RANDOM, FILE, DEFAULT)
   * @param barrier   об'єкт для синхронізації потоків
   */
  public T2(String name, int priority, long stackSize, InputType inputType, CyclicBarrier barrier) {
    super(null, null, name, stackSize);
    this.setPriority(priority);
    this.inputType = inputType;
    this.barrier = barrier;
  }

  /**
   * Метод, який виконується при запуску потоку T2.
   * Зчитує дані, чекає на інші потоки та обчислює результат формули F2.
   */
  @Override
  public void run() {
    initializeTensors();

    waitBarrier();

    long startTime = System.currentTimeMillis();
    int[][] result = function2(MG, MH, MK, ML);
    long endTime = System.currentTimeMillis();

    System.out.println("Результат '" + getName() + "':\n" + Data.printMatrix(result, true));
    System.out.println("Час виконання обчислень '" + getName() + "': " +
        (endTime - startTime) + " мс");
  }

  /**
   * Ініціалізує тензори (матриці) для потоку T2 на основі типу введення даних.
   * Зчитує дані з консолі, файлу або генерує випадкові дані.
   */
  private void initializeTensors() {
    String taskName = getName();
    switch (inputType) {
      case CONSOLE:
        MG = Data.inputMatrixFromConsole(taskName, "MG");
        MH = Data.inputMatrixFromConsole(taskName, "MH");
        MK = Data.inputMatrixFromConsole(taskName, "MK");
        ML = Data.inputMatrixFromConsole(taskName, "ML");
        break;
      case RANDOM:
        MG = Data.randomMatrix("MG");
        MH = Data.randomMatrix("MH");
        MK = Data.randomMatrix("MK");
        ML = Data.randomMatrix("ML");
        break;
      case FILE:
        MG = Data.readMatrixFromFile("MG.txt", "MG");
        MH = Data.readMatrixFromFile("MH.txt", "MH");
        MK = Data.readMatrixFromFile("MK.txt", "MK");
        ML = Data.readMatrixFromFile("ML.txt", "ML");
        break;
      default:
        MG = Data.inputMatrix(2, "MG");
        MH = Data.inputMatrix(2, "MH");
        MK = Data.inputMatrix(2, "MK");
        ML = Data.inputMatrix(2, "ML");
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
   * Обчислює результат формули F2: MF = (MG + MH) * (MK * ML) * (MG + ML).
   *
   * @param MG матриця MG
   * @param MH матриця MH
   * @param MK матриця MK
   * @param ML матриця ML
   * @return результат обчислення формули F2 у вигляді матриці
   */
  private int[][] function2(int[][] MG, int[][] MH, int[][] MK, int[][] ML) {
    return Data.multiplyMatrices(
        Data.multiplyMatrices(
            Data.sumMatrices(MG, MH),
            Data.multiplyMatrices(MK, ML)),
        Data.sumMatrices(MG, ML));
  }
}
