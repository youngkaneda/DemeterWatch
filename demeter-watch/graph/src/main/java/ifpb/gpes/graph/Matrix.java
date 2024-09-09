package ifpb.gpes.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The {@code Matrix} class represents a matrix of integers with methods for
 * performing operations such as summing the elements, checking connectivity,
 * and computing metrics. The matrix can be initialized with a two-dimensional
 * array or a specified number of vertices.
 * <p>
 * It also supports adjacency matrix manipulation and provides methods for
 * accessing and updating matrix values through nested {@code Cell} objects.
 * </p>
 */
public class Matrix {

    protected final int[][] matrix;

    private final Node[] columns;

    /**
     * Constructs a {@code Matrix} with the specified two-dimensional integer array.
     *
     * @param matrix the two-dimensional array representing the matrix
     */
    public Matrix(int[][] matrix) {
        this.matrix = matrix;
        this.columns = new Node[matrix.length];
    }

    /**
     * Constructs a square {@code Matrix} with the given number of vertices.
     *
     * @param verticesNumber the number of vertices (both rows and columns) for the matrix
     */
    public Matrix(int verticesNumber) {
        this(new int[verticesNumber][verticesNumber]);
    }

    /**
     * Constructs an empty {@code Matrix} with no vertices (0x0).
     */
    public Matrix() {
        this(0);
    }

    /**
     * Returns the matrix as a two-dimensional integer array.
     *
     * @return the two-dimensional integer array representing the matrix
     */
    public int[][] toArray() {
        return this.matrix;
    }

    /**
     * Sums all the weights (elements) in the matrix.
     *
     * @return the sum of all elements in the matrix, or 0 if the matrix is null
     */
    public int sumAllWeight() {
        if (matrix == null) {
            return 0;
        }
        return Arrays.stream(matrix).flatMapToInt(Arrays::stream).sum();
    }

    /**
     * Checks whether the row specified is connected to any other row or column.
     *
     * @param row the row to check connectivity for
     * @return {@code true} if the row is connected, {@code false} otherwise
     */
    public boolean connected(int row) {
        int soma = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if ((i == row) || (row == j)) {
                    soma = soma + matrix[i][j];
                }
            }
        }
        return soma > 0;
    }

    /**
     * Returns the matrix as a formatted string where each row of the matrix is on a new line.
     *
     * @return a string representing the matrix values
     */
    public String valuesToString() {
        StringBuilder builder = new StringBuilder();
        for (int[] row : matrix) {
            for (int j = 0; j < matrix.length; j++) {
                builder.append(row[j]).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Returns the names of the columns of the matrix.
     *
     * @return an array of column names
     */
    public String[] namesColumns() {
        StringBuilder columnNames = new StringBuilder();
        for (Node column : columns) {
            columnNames.append(column.getMethodName()).append(";");
        }
        return columnNames.toString().split(";");
    }

    /**
     * Computes a list of {@code Metric} objects for the matrix using the given strategy.
     *
     * @param strategy the strategy to use for computing metrics
     * @return a list of computed metrics
     */
    public List<Metric> computeWithMetric(StrategyMetric strategy) {
        List<Metric> metrics = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int weight = matrix[i][j];
                if (notConnected(i, j)) {
                    continue;
                }
                int sum = sumCallsTo(j) + weight;
                metrics.add(new Metric(String.valueOf(i), String.valueOf(j),
                    weight, sum, strategy));
            }
        }
        return metrics;
    }

    /**
     * Computes a list of {@code Metric} objects for the matrix using the default strategy.
     *
     * @return a list of computed metrics
     */
    public List<Metric> computeMetric() {
        return computeWithMetric(new DefaultStrategyMetric());
    }

    /**
     * Constructs a new {@code Matrix} by refactoring the current matrix into an adjacency matrix.
     *
     * @return a new adjacency matrix based on the current matrix
     */
    public Matrix adjacencyMatrix() {
        List<Integer> notNullLines = rowsNotNull(matrix);
        int[][] refactoredMatrix = new int[notNullLines.size()][matrix.length];
        for (int i = 0; i < refactoredMatrix.length; i++) {
            refactoredMatrix[i] = matrix[notNullLines.get(i)];
        }
        return new Matrix(refactoredMatrix);
    }

    /**
     * Returns a {@code Cell} object representing the value at the specified row and column.
     *
     * @param row the row index of the cell
     * @param column the column index of the cell
     * @return a {@code Cell} object for the specified position
     */
    public Cell cell(int row, int column) {
        return new Cell(row, column);
    }

    /**
     * Updates the columns array with the given {@code Node} at the specified column index.
     *
     * @param column the column index to update
     * @param vertex the {@code Node} to set at the specified column
     */
    public void updateColumns(int column, Node vertex) {
        this.columns[column] = vertex;
    }

    /**
     * Returns the columns array of {@code Node} objects.
     *
     * @return an array of {@code Node} objects
     */
    public Node[] getColumns() {
        return columns;
    }

    /**
     * Find all rows in the matrix that are not null (i.e., have non-zero sums).
     *
     * @param data the two-dimensional array representing the matrix
     * @return a list of row indices with non-zero sums
     */
    private List<Integer> rowsNotNull(int[][] data) {
        List<Integer> rowsIndex = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            if (Arrays.stream(data[i]).sum() > 0) {
                rowsIndex.add(i);
            }
        }
        return rowsIndex;
    }

    /**
     * Private method to sum the values in the specified column, since cell values represent
     * connected nodes, thus meaning a method call.
     *
     * @param column the column index to sum the values for
     * @return the sum of the values in the specified column
     */
    private int sumCallsTo(int column) {
        return IntStream.of(matrix[column]).sum();
    }

    /**
     * Check if the specified row and column are not connected (i.e., have a zero value).
     *
     * @param row the row index
     * @param col the column index
     * @return {@code true} if the row and column are not connected, {@code false} otherwise
     */
    private boolean notConnected(int row, int col) {
        return this.matrix[row][col] == 0;
    }

    /**
     * The {@code Cell} class represents a single cell in the matrix.
     */
    class Cell {

        private final int row;
        private final int column;

        /**
         * Constructs a {@code Cell} at the specified row and column.
         *
         * @param row the row index of the cell
         * @param column the column index of the cell
         */
        protected Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }

        /**
         * Sets the value at the cell's position in the matrix.
         *
         * @param value the value to set in the matrix
         */
        public void set(int value) {
            matrix[row][column] = value;
        }

        /**
         * Returns the value at the cell's position in the matrix.
         *
         * @return the value at the cell's position
         */
        public int get() {
            return matrix[row][column];
        }

        /**
         * Returns a string representation of the cell in the format "(row-column) (value)".
         *
         * @return a string representation of the cell
         */
        @Override
        public String toString() {
            return "(" + row + "-" + column + ") (" + get() + ")";
        }
    }
}