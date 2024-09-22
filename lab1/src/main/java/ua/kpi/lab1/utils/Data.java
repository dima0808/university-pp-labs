package ua.kpi.lab1.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

/**
 * Клас Data містить методи для введення, генерації та обробки векторів і матриць.
 * Він забезпечує введення даних з консолі, файлів або генерацію випадкових даних,
 * а також виконання різних математичних операцій над векторами та матрицями.
 */
public class Data {
  public static int N = 3;
  private static final Object lock = new Object();

  /**
   * Вводить вектор з однаковими значеннями для кожного елемента.
   *
   * @param value значення для кожного елемента вектора
   * @param vectorName ім'я вектора
   * @return вектор з однаковими значеннями
   */
  public static int[] inputVector(int value, String vectorName) {
    int[] vector = new int[N];
    for (int i = 0; i < N; i++) {
      vector[i] = value;
    }
    System.out.printf("%s = %s%n", vectorName, printVector(vector));
    return vector;
  }

  /**
   * Вводить матрицю з однаковими значеннями для кожного елемента.
   *
   * @param value значення для кожного елемента матриці
   * @param matrixName ім'я матриці
   * @return матриця з однаковими значеннями
   */
  public static int[][] inputMatrix(int value, String matrixName) {
    int[][] matrix = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        matrix[i][j] = value;
      }
    }
    System.out.printf("%s = %n%s%n", matrixName, printMatrix(matrix));
    return matrix;
  }

  /**
   * Вводить вектор з консолі.
   *
   * @param taskName ім'я задачі
   * @param vectorName ім'я вектора
   * @return вектор, введений з консолі
   */
  public static int[] inputVectorFromConsole(String taskName, String vectorName) {
    synchronized (lock) {
      Scanner scanner = new Scanner(System.in);
      int[] vector = new int[N];
      System.out.printf("[%s] Введіть %d елементів для вектора '%s':%n",
          taskName, N, vectorName);
      for (int i = 0; i < N; i++) {
        vector[i] = scanner.nextInt();
      }
      System.out.printf("%s = %s%n", vectorName, printVector(vector));
      return vector;
    }
  }

  /**
   * Вводить матрицю з консолі.
   *
   * @param taskName ім'я задачі
   * @param matrixName ім'я матриці
   * @return матриця, введена з консолі
   */
  public static int[][] inputMatrixFromConsole(String taskName, String matrixName) {
    synchronized (lock) {
      Scanner scanner = new Scanner(System.in);
      int[][] matrix = new int[N][N];
      System.out.printf("[%s] Введіть елементи для %dx%d матриці '%s':%n",
          taskName, N, N, matrixName);
      for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
          matrix[i][j] = scanner.nextInt();
        }
      }
      System.out.printf("%s = %n%s%n", matrixName, printMatrix(matrix));
      return matrix;
    }
  }

  /**
   * Генерує випадковий вектор.
   *
   * @param vectorName ім'я вектора
   * @return випадковий вектор
   */
  public static int[] randomVector(String vectorName) {
    Random rand = new Random();
    int[] vector = new int[N];
    for (int i = 0; i < N; i++) {
      vector[i] = rand.nextInt(100);
    }
    System.out.printf("%s = %s%n", vectorName, printVector(vector));
    return vector;
  }

  /**
   * Генерує випадкову матрицю.
   *
   * @param matrixName ім'я матриці
   * @return випадкова матриця
   */
  public static int[][] randomMatrix(String matrixName) {
    Random rand = new Random();
    int[][] matrix = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        matrix[i][j] = rand.nextInt(100);
      }
    }
    System.out.printf("%s = %n%s%n", matrixName, printMatrix(matrix));
    return matrix;
  }

  /**
   * Зчитує вектор з файлу.
   *
   * @param filename ім'я файлу
   * @param vectorName ім'я вектора
   * @return вектор, зчитаний з файлу
   */
  public static int[] readVectorFromFile(String filename, String vectorName) {
    int[] vector = new int[N];
    InputStream inputStream = Data.class.getClassLoader().getResourceAsStream(filename);
    if (inputStream == null) {
      throw new IllegalArgumentException("Input file not found: " + filename);
    }
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      for (int i = 0; i < N; i++) {
        vector[i] = Integer.parseInt(br.readLine().trim());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.printf("%s = %s%n", vectorName, printVector(vector));
    return vector;
  }

  /**
   * Зчитує матрицю з файлу.
   *
   * @param filename ім'я файлу
   * @param matrixName ім'я матриці
   * @return матриця, зчитана з файлу
   */
  public static int[][] readMatrixFromFile(String filename, String matrixName) {
    int[][] matrix = new int[N][N];
    InputStream inputStream = Data.class.getClassLoader().getResourceAsStream(filename);
    if (inputStream == null) {
      throw new IllegalArgumentException("Input file not found: " + filename);
    }
    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
      for (int i = 0; i < N; i++) {
        String[] numbers = br.readLine().trim().split("\\s+");
        for (int j = 0; j < N; j++) {
          matrix[i][j] = Integer.parseInt(numbers[j]);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    System.out.printf("%s = %n%s%n", matrixName, printMatrix(matrix));
    return matrix;
  }

  /**
   * Обчислює скалярний добуток двох векторів.
   *
   * @param A вектор A
   * @param B вектор B
   * @return скалярний добуток векторів
   */
  public static int dotProduct(int[] A, int[] B) {
    int result = 0;
    for (int i = 0; i < N; i++) {
      result += A[i] * B[i];
    }
    return result;
  }

  /**
   * Множить вектор на матрицю.
   *
   * @param vector вектор
   * @param matrix матриця
   * @return результат множення вектора на матрицю
   */
  public static int[] multiplyVectorMatrix(int[] vector, int[][] matrix) {
    int[] result = new int[N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        result[i] += vector[j] * matrix[i][j];
      }
    }
    return result;
  }

  /**
   * Сумує два вектори.
   *
   * @param A вектор A
   * @param B вектор B
   * @return результат суми векторів
   */
  public static int[] sumVectors(int[] A, int[] B) {
    int[] result = new int[N];
    for (int i = 0; i < N; i++) {
      result[i] = A[i] + B[i];
    }
    return result;
  }

  /**
   * Множить дві матриці.
   *
   * @param A матриця A
   * @param B матриця B
   * @return результат множення матриць
   */
  public static int[][] multiplyMatrices(int[][] A, int[][] B) {
    int[][] result = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        for (int k = 0; k < N; k++) {
          result[i][j] += A[i][k] * B[k][j];
        }
      }
    }
    return result;
  }

  /**
   * Сумує дві матриці.
   *
   * @param A матриця A
   * @param B матриця B
   * @return результат суми матриць
   */
  public static int[][] sumMatrices(int[][] A, int[][] B) {
    int[][] result = new int[N][N];
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        result[i][j] = A[i][j] + B[i][j];
      }
    }
    return result;
  }

  /**
   * Знаходить максимальний елемент матриці.
   *
   * @param matrix матриця
   * @return максимальний елемент матриці
   */
  public static int maxMatrixElement(int[][] matrix) {
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        if (matrix[i][j] > max) {
          max = matrix[i][j];
        }
      }
    }
    return max;
  }

  /**
   * Повертає рядкове представлення вектора.
   *
   * @param vector вектор
   * @param result чи є вивід вектора результатом
   * @return рядкове представлення вектора. Якщо вивід вектора не є результатом, виводиться
   * скорочений вектор в разі перевищення довжини 50 символів. Інакше виводиться повний вектор.
   */
  public static String printVector(int[] vector, boolean result) {
    StringBuilder sb = new StringBuilder();
    sb.append("(");
    for (int i = 0; i < N; i++) {
      sb.append(vector[i]);
      if (i < N - 1) {
        sb.append(", ");
      }
    }
    sb.append(")");
    if (result) {
      return sb.toString();
    } else {
      return sb.length() < 50 ? sb.toString() : sb.substring(0, 49) + "...";
    }
  }

  /**
   * Повертає рядкове представлення матриці.
   *
   * @param matrix матриця
   * @param result чи є вивід матриці результатом
   * @return рядкове представлення матриці. Якщо вивід матриці не є результатом, виводиться
   * скорочена матриця в разі перевищення довжини 70 символів. Інакше виводиться повна матриця.
   */
  public static String printMatrix(int[][] matrix, boolean result) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        sb.append(matrix[i][j]).append("\t");
      }
      sb.append("\n");
    }
    if (result) {
      return sb.toString();
    } else {
      return sb.length() < 70 ? sb.toString() : sb.substring(0, 69) + "...";
    }
  }

  /**
   * Повертає рядкове представлення вектора.
   * Обгортка методу {@link #printVector(int[], boolean) printVector(vector, false)}.
   *
   * @param vector вектор
   * @return рядкове представлення вектора
   */
  public static String printVector(int[] vector) {
    return printVector(vector, false);
  }

  /**
   * Повертає рядкове представлення матриці.
   * Обгортка методу {@link #printMatrix(int[][], boolean) printMatrix(matrix, false)}.
   *
   * @param matrix матриця
   * @return рядкове представлення матриці
   */
  public static String printMatrix(int[][] matrix) {
    return printMatrix(matrix, false);
  }
}