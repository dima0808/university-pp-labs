import org.junit.jupiter.api.Test;
import ua.kpi.lab1.utils.Data;

import static org.junit.jupiter.api.Assertions.*;

public class DataTest {

  @Test
  public void testDotProduct() {
    int[] A = {1, 2, 3};
    int[] B = {4, 5, 6};
    int expected = 32;

    assertEquals(expected, Data.dotProduct(A, B));
  }

  @Test
  public void testMultiplyVectorMatrix() {
    int[] vector = {1, 2, 3};
    int[][] matrix = {
        {4, 5, 6},
        {7, 8, 9},
        {10, 11, 12}
    };
    int[] expected = {32, 50, 68};

    assertArrayEquals(expected, Data.multiplyVectorMatrix(vector, matrix));
  }

  @Test
  public void testMultiplyMatrices() {
    int[][] A = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9}
    };
    int[][] B = {
        {10, 11, 12},
        {13, 14, 15},
        {16, 17, 18}
    };
    int[][] expected = {
        {84, 90, 96},
        {201, 216, 231},
        {318, 342, 366}
    };

    assertArrayEquals(expected, Data.multiplyMatrices(A, B));
  }
}