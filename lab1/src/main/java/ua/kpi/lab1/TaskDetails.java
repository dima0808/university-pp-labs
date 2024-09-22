package ua.kpi.lab1;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Клас, що представляє деталі задачі (потоку).
 * Містить інформацію про назву, пріоритет та розмір стека.
 */
@AllArgsConstructor
@Data
public class TaskDetails {

  /**
   * Назва задачі.
   */
  private String name;

  /**
   * Пріоритет задачі.
   */
  private int priority;

  /**
   * Розмір стека задачі (в байтах).
   */
  private long stackSize;
}
