package xyz.ceberus.celiac;

import android.util.Log;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Adaboost {

	private List<DecisionStump> listDecisionStumpModel;

	private Adaboost() {

	}

	private Adaboost(List<DecisionStump> listDecisionStumpModel) {
		this.listDecisionStumpModel = listDecisionStumpModel;
	}

	public static Adaboost train(String strTrainData, int intNumberofSteps,
								 int intMaxIteration, double dbTargetError) throws IOException {

		int intSampleCount = getTotalSamplesNumber(strTrainData);
		
		double[] dbWeights = new double[intSampleCount];
		double[] dbAggClassEst = new double[intSampleCount];
		
		for (int i = 0; i < dbWeights.length; i++) {
			dbWeights[i] = ((double) 1 / intSampleCount);
			dbAggClassEst[i] = 0;
		}

		int[] arrIntLabels = getlabels(strTrainData, intSampleCount);
		List<DecisionStump> listDecisionStumpModels = new ArrayList<>();
		
		for (int i = 0; i < intMaxIteration; i++) {
			DecisionStump stump = DecisionStump.bestStump(strTrainData, arrIntLabels,
					intNumberofSteps, dbWeights);

			BigDecimal bdLog = BigDecimal.ONE
					.subtract((stump.getWeightedError())).divide(
							stump.getWeightedError(), 6, RoundingMode.HALF_UP);

			BigDecimal bgDecimal = BigDecimal.valueOf(0.5)
					.multiply(BigDecimal.valueOf(Math.log(bdLog.doubleValue())))
					.setScale(12, BigDecimal.ROUND_HALF_UP);

			stump.setAlpha(bgDecimal);
			listDecisionStumpModels.add(stump);

			dbWeights = updateWeights(arrIntLabels, stump, dbWeights,
					dbAggClassEst, bgDecimal.doubleValue());
			double dbError = calculateError(dbAggClassEst, arrIntLabels);

			if (dbError <= dbTargetError)
				break;
		}

		return new Adaboost(listDecisionStumpModels);
	}

	/*public int classify(String[] Observation) {

		BigDecimal sum = BigDecimal.ZERO;
		int intStumpIndex = 0;
		for (DecisionStump classifier : model) {
			BigDecimal output = BigDecimal.valueOf(classifier.classify(Observation));
			sum = sum.add(output.multiply(classifier.getAlpha())
			);
			Log.e("ADABOOST","index stump: "+intStumpIndex+" output: "+output+" sum: "+sum+" alpha: "+classifier.getAlpha());
			intStumpIndex++;
		}

		if (sum.compareTo(BigDecimal.ZERO) == 1)
			return 1;

		return -1;

	}*/

	public AdaboostData classifyData(String[] arrStrObservation) {
        AdaboostData adbdataObject = new AdaboostData();
		BigDecimal bgSum = BigDecimal.ZERO;
		int intStumpIndex = 0;
        ArrayList<String>arrayList = new ArrayList<>();
		for (DecisionStump decisionStumpOut : listDecisionStumpModel) {
			BigDecimal bgOutput = BigDecimal.valueOf(decisionStumpOut.classify(arrStrObservation));
			bgSum = bgSum.add(bgOutput.multiply(decisionStumpOut.getAlpha())
			);
			Log.e("ADABOOST","index stump: "+intStumpIndex+" output: "+bgOutput+" sum: "+bgSum+" alpha: "+decisionStumpOut.getAlpha());
			arrayList.add("index stump: "+intStumpIndex+"\n output: "+bgOutput+"\n sum: "+bgSum+"\n alpha: "+decisionStumpOut.getAlpha()+"\n");
            intStumpIndex++;
		}
        adbdataObject.setArrayListLog(arrayList);

		if (bgSum.compareTo(BigDecimal.ZERO) == 1)
			 adbdataObject.setResult(1);
        else
            adbdataObject.setResult(-1);
		return adbdataObject;
	}

	/*public int classify(double[] Observation) {

		BigDecimal sum = BigDecimal.ZERO;
		for (DecisionStump classifier : model) {

			sum = sum.add(BigDecimal.valueOf(classifier.classify(Observation))
					.multiply(classifier.getAlpha()));
		}

		if (sum.compareTo(BigDecimal.ZERO) == 1)
			return 1;

		return -1;

	}*/


	private static double calculateError(double[] arrDbAggClassEst, int[] arrIntLabels) {
		int intErrorCount = 0;
		for (int i = 0; i < arrDbAggClassEst.length; i++) {
			if ((arrDbAggClassEst[i] > 0 ? 1 : -1) != arrIntLabels[i])
				intErrorCount++;
		}
		return ((double) intErrorCount / arrDbAggClassEst.length);
	}

	private static double[] updateWeights(int[] labels,
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
		return listDecisionStumpModel;
	}

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


	private static int getTotalSamplesNumber(String strTrainData) throws IOException {
		return strTrainData.split("\n").length+1;
	}

	

	public static int[] getlabels(String strTrainData, int intSamplesCount)
			throws IOException {
		int[] arrIntLabels = new int[intSamplesCount];
		String arrStrRow[] = strTrainData.split("\n");
		int i = 0;
		for(String strRow:arrStrRow) {
			String[] arrStrCol = strRow.split("\\|");
			Integer intLabel = (int) Double.parseDouble(arrStrCol[arrStrCol.length - 1]
					.trim());
			arrIntLabels[i] = intLabel;
			i++;
		}
		return arrIntLabels;
	}



}