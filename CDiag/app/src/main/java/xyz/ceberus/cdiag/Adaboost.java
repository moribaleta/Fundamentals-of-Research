package xyz.ceberus.cdiag;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Adaboost {

	private List<DecisionStump> model;

	private Adaboost() {

	}

	private Adaboost(List<DecisionStump> model) {
		this.model = model;
	}

	/**
	 * Train adaboost algorithm with trainFile in PSV format and return Adaboost Object 
	 * @param trainFile
	 * @param numberofSteps
	 * @param maxIteration
	 * @param targetError
	 * @return
	 * @throws IOException
	 */
	public static Adaboost train(String strTrainData, int numberofSteps,
								 int maxIteration, double targetError) throws IOException {

		int samplesCount = getTotalSamplesNumber(strTrainData);
		
		double[] weights = new double[samplesCount];
		double[] aggClassEst = new double[samplesCount];
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = ((double) 1 / samplesCount);
			aggClassEst[i] = 0;
		}

		int[] labels = getlabels(strTrainData, samplesCount);
		List<DecisionStump> model = new ArrayList<>();
		
		for (int i = 0; i < maxIteration; i++) {
			DecisionStump stump = DecisionStump.bestStump(strTrainData, labels,
					numberofSteps, weights);
			BigDecimal log = BigDecimal.ONE
					.subtract((stump.getWeightedError())).divide(
							stump.getWeightedError(), 6, RoundingMode.HALF_UP);

			BigDecimal alpha = BigDecimal.valueOf(0.5)
					.multiply(BigDecimal.valueOf(Math.log(log.doubleValue())))
					.setScale(12, BigDecimal.ROUND_HALF_UP);

			stump.setAlpha(alpha);
			model.add(stump);

			weights = updateWeights(strTrainData, labels, stump, weights,
					aggClassEst, alpha.doubleValue());
			double error = calculateError(aggClassEst, labels);

			if (error <= targetError)
				break;
		}

		return new Adaboost(model);
	}

	/**
	 * Used to predict new Enities
	 * @param Observation
	 * @return
	 */
	public int classify(String[] Observation) {

		BigDecimal sum = BigDecimal.ZERO;
		for (DecisionStump classifier : model) {

			sum = sum.add(BigDecimal.valueOf(classifier.classify(Observation))
					.multiply(classifier.getAlpha()));
		}

		if (sum.compareTo(BigDecimal.ZERO) == 1)
			return 1;

		return -1;

	}

	public int classify(double[] Observation) {

		BigDecimal sum = BigDecimal.ZERO;
		for (DecisionStump classifier : model) {

			sum = sum.add(BigDecimal.valueOf(classifier.classify(Observation))
					.multiply(classifier.getAlpha()));
		}

		if (sum.compareTo(BigDecimal.ZERO) == 1)
			return 1;

		return -1;

	}

	/**
	 * Calculate Error of current adaboost based on previous learning
	 * @param aggClassEst
	 * @param labels
	 * @return
	 */
	private static double calculateError(double[] aggClassEst, int[] labels) {

		int errorsCount = 0;
		for (int i = 0; i < aggClassEst.length; i++) {
			if ((aggClassEst[i] > 0 ? 1 : -1) != labels[i])
				errorsCount++;

		}
		return ((double) errorsCount / aggClassEst.length);
	}

	/**
	 * Update Weights based on errors of previous learners
	 * @param trainFile
	 * @param labels
	 * @param stump
	 * @param weights
	 * @param aggClassEst
	 * @param alpha
	 * @return
	 * @throws IOException
	 */
	private static double[] updateWeights(String strTrainData, int[] labels,
										  DecisionStump stump, double[] weights, double[] aggClassEst,
										  double alpha) throws IOException {

		for (int i = 0; i < weights.length; i++) {

			aggClassEst[i] += BigDecimal.valueOf(stump.getClassEst()[i])
					.multiply(stump.getAlpha())
					.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
			if (stump.getClassEst()[i] == labels[i]) {
				weights[i] = BigDecimal
						.valueOf(weights[i])
						.multiply(
								BigDecimal.valueOf(Math.pow(Math.E, (-alpha))))
						.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

			} else {

				weights[i] = BigDecimal
						.valueOf(weights[i])
						.multiply(BigDecimal.valueOf(Math.pow(Math.E, (alpha))))
						.setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();

			}
		}

		return normalizeWeights(weights);

	}

	public List<DecisionStump> getModel() {
		return model;
	}

	/**
	 * Normalize Weights By divide everyone by total sum
	 * @param inputWeights
	 * @return
	 */
	private static double[] normalizeWeights(double[] inputWeights) {

		double[] weights = new double[inputWeights.length];
		BigDecimal total = BigDecimal.ZERO;
		for (int i = 0; i < inputWeights.length; i++)
			total = total.add(BigDecimal.valueOf(inputWeights[i]));
		for (int i = 0; i < inputWeights.length; i++) {

			weights[i] = BigDecimal.valueOf(inputWeights[i])
					.divide(total, 8, RoundingMode.HALF_UP).doubleValue();
		}
		return weights;

	}

	/**
	 * Get Number of Lines in Training File
	 * @param trainFile
	 * @return
	 * @throws IOException
	 */
	private static int getTotalSamplesNumber(String strTrainData) throws IOException {

		/*LineNumberReader lineNumberReader = new LineNumberReader(
				new FileReader(trainFile));
		lineNumberReader.skip(Long.MAX_VALUE);
		lineNumberReader.close();
		return lineNumberReader.getLineNumber() + 1;*/
		return strTrainData.split("\n").length+1;

	}

	
	/**
	 * Cache Label/Target Column of training File
	 * @param trainFile
	 * @param samplesCount
	 * @return
	 * @throws IOException
	 */
	public static int[] getlabels(String strTrainData, int samplesCount)
			throws IOException {
		int[] labels = new int[samplesCount];
		/*FileReader fileReader = new FileReader(trainFile);
		BufferedReader breader = new BufferedReader(fileReader);
		String line;*/
		String arrStrRow[] = strTrainData.split("\n");
		int i = 0;
		for(String strRow:arrStrRow) {
			String[] cols = strRow.split("\\|");
			Integer label = (int) Double.parseDouble(cols[cols.length - 1]
					.trim());
			labels[i] = label;
			i++;
		}

		//breader.close();
		return labels;
	}



}