package xyz.ceberus.celiac;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DecisionStump {

	public static final char OP_LESSER_THAN = '<';
	public static final char OP_LESSER_THAN_OR_EQUAL = '(';
	public static final char OP_GREATER_THAN = '>';
	public static final char OP_GREATER_THAN_OR_EQUAL = ')';

	public static final char[] OP_ARRAY_WITH_EQUAL = { OP_LESSER_THAN_OR_EQUAL,
			OP_GREATER_THAN_OR_EQUAL };
	public static final char[] OP_ARRAY = { OP_LESSER_THAN, OP_GREATER_THAN };
	private BigDecimal threshold;
	private char operation;
	private int columnIndex;
	private BigDecimal weightedError;
	private BigDecimal alpha;
	private int[] classEst;

	private DecisionStump(BigDecimal threshold, char operation,
						  int columnIndex, int samplesCount) {
		this.threshold = threshold;
		this.operation = operation;
		this.columnIndex = columnIndex;
		this.classEst = new int[samplesCount];
	}


	public int classify(BigDecimal columnValue) {

		switch (operation) {
		case OP_LESSER_THAN:
			return columnValue.compareTo(threshold) == -1 ? 1 : -1;

		case OP_LESSER_THAN_OR_EQUAL:
			return columnValue.compareTo(threshold) <= 0 ? 1 : -1;

		case OP_GREATER_THAN:
			return columnValue.compareTo(threshold) == 1 ? 1 : -1;

		case OP_GREATER_THAN_OR_EQUAL:
			return columnValue.compareTo(threshold) >= 0 ? 1 : -1;

		default:
			throw new IllegalArgumentException("Illegal Operation Character");
		}

	}

	public int classify(double[] object) {

		return classify(BigDecimal.valueOf(object[columnIndex]));

	}

	public int classify(String[] object) {
		return classify(BigDecimal.valueOf(Double
				.parseDouble(object[columnIndex])));

	}

	public double calculateWeightedError(double[] object, int label,
			double weight) {

		return classify(object) == label ? 0 : weight;

	}

	private double calculateWeightedError(double columnValue, int label,
			double weight, int index) {

		int stumpLabel = classify(BigDecimal.valueOf(columnValue));
		classEst[index] = stumpLabel;
		return stumpLabel == label ? 0 : weight;

	}

	/**
	 * Calculate total error for current feature ( Column )
	 */
	private BigDecimal calculateColumnWeightedError(ColumnData columnData,
			double[] weights, int[] labels) {
		BigDecimal totalError = BigDecimal.valueOf(0.0);
		for (int i = 0; i < columnData.getData().size(); i++) {
			Double data = columnData.getData().get(i);

			totalError = totalError.add(BigDecimal
					.valueOf(calculateWeightedError(data, labels[i],
							weights[i], i)));

		}
		weightedError = totalError;

		return totalError;
	}

	/**
	 * Find Best Split Feature and return it as stump
	 */
	public static DecisionStump bestStump(String strTrainData, int[] labels,
										  int numberofSteps, double[] weights) throws IOException {

		int columnsCount = getColumnCount(strTrainData);
		DecisionStump minErrorDecisionStump = null;
		BigDecimal minError = BigDecimal.valueOf(Double.MAX_VALUE);
		for (int i = 0; i < columnsCount; i++) {
			ColumnData columnData = getColumnData(strTrainData, i);
			BigDecimal stepSize = BigDecimal.valueOf(columnData.max)
					.subtract(BigDecimal.valueOf(columnData.min))
					.divide(BigDecimal.valueOf(numberofSteps));
		
			for (BigDecimal threshold = (BigDecimal.valueOf(columnData.min)
					.subtract(stepSize)); threshold.compareTo(BigDecimal
					.valueOf(columnData.max).add(stepSize)) < 0; threshold = threshold
					.add(stepSize)) {

				for (int j = 0; j < OP_ARRAY.length; j++) {

					DecisionStump stump = new DecisionStump(threshold,
							OP_ARRAY[j], i, labels.length);

					BigDecimal error = stump.calculateColumnWeightedError(
							getColumnData(strTrainData, i), weights, labels);
	
					if (minError.compareTo(error) == 1) {
						minError = error;
						minErrorDecisionStump = stump;
					}

				}

			}
		}
		return minErrorDecisionStump;

	}

	/**
	 * Get Feature Information at @param index
	 */
	public static ColumnData getColumnData(String strTrainData, int index)
			throws IOException {
		List<Double> column = new ArrayList<Double>();
		
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		String arrStrRow[] = strTrainData.split("\n");
		for (String strRow: arrStrRow) {
			String[] cols = strRow.split("\\|");
			double columnData = Double.parseDouble(cols[index]);
			min = columnData < min ? columnData : min;
			max = columnData > max ? columnData : max;
			column.add(columnData);
		}
		ColumnData columnData = new ColumnData(column, min, max);
		return columnData;
	}

	/**
	 * Get Column Count without calculate last column because
	 */
	private static int getColumnCount(String strTrainData) throws IOException {
		String arrStrRow[] = strTrainData.split("\n");
		String[] cols = arrStrRow[0].split("\\|");
		return cols.length - 1;
	}

	public char getOperation() {
		return operation;
	}
	
	public BigDecimal getThreshold() {
		return threshold;
	}

	public BigDecimal getWeightedError() {
		return weightedError;
	}

	public void setAlpha(BigDecimal alpha) {
		this.alpha = alpha;
	}

	public BigDecimal getAlpha() {
		return alpha;
	}

	public int[] getClassEst() {
		return classEst;
	}

	public int getColumnIndex() {
		return columnIndex;
	}


	static class ColumnData {

		List<Double> data;
		double min;
		double max;

		public ColumnData(List<Double> data, double min, double max) {
			super();
			this.data = data;
			this.min = min;
			this.max = max;
		}

		public ColumnData() {
			// TODO Auto-generated constructor stub
		}

		public List<Double> getData() {
			return data;
		}

		public void setData(List<Double> data) {
			this.data = data;
		}

		public double getMin() {
			return min;
		}

		public void setMin(double min) {
			this.min = min;
		}

		public double getMax() {
			return max;
		}

		public void setMax(double max) {
			this.max = max;
		}

	}

}