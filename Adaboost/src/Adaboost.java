import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
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
	public static Adaboost train(File trainFile, int numberofSteps,
			int maxIteration, double targetError) throws IOException {

		int samplesCount = getTotalSamplesNumber(trainFile);
		//System.out.println("sample count: "+samplesCount);
		double[] weights = new double[samplesCount];
		double[] aggClassEst = new double[samplesCount];
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = ((double) 1 / samplesCount);
			//System.out.println("set init weight: "+weights[i]);
			aggClassEst[i] = 0;
		}

		int[] labels = getlabels(trainFile, samplesCount);
		List<DecisionStump> model = new ArrayList<DecisionStump>();
		
		for (int i = 0; i < maxIteration; i++) {
			DecisionStump stump = DecisionStump.bestStump(trainFile, labels,
					numberofSteps, weights);
			BigDecimal log = BigDecimal.ONE
					.subtract((stump.getWeightedError())).divide(
							stump.getWeightedError(), 6, RoundingMode.HALF_UP);

			BigDecimal alpha = BigDecimal.valueOf(0.5)
					.multiply(BigDecimal.valueOf(Math.log(log.doubleValue())))
					.setScale(12, BigDecimal.ROUND_HALF_UP);
			//System.out.println("set init Alpha:"+alpha);
			stump.setAlpha(alpha);
			model.add(stump);

			weights = updateWeights(trainFile, labels, stump, weights,
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
		 
		//int arrAttribute[] = new int[2];
		HashMap<Integer,Attribute> hash = new HashMap<>();
		BigDecimal sum = BigDecimal.ZERO;
		int intStumpIndex = 0;
		//BigDecimal max = BigDecimal.ZERO;
		//int intMaxIndex = 0;
		for (DecisionStump classifier : model) {
			BigDecimal output = BigDecimal.valueOf(classifier.classify(Observation));
			sum = sum.add(output.multiply(classifier.getAlpha())
					);
			//System.out.println("index stump: "+intStumpIndex+" output: "+output+" sum: "+sum+" alpha: "+classifier.getAlpha()+ "column index: "+classifier.getColumnIndex());
			
			int index = classifier.getColumnIndex();
			
			int count = 1;
			
			//int dbAlpha = classifier.getAlpha();
			BigDecimal attri = output.multiply(classifier.getAlpha());
			if(hash.get(index)!=null){
				//count = hash.get(index)[0];
				Attribute attr = hash.get(index);
				count = attr.intCount;
				BigDecimal sumAlpha = attr.bgAlpha;
				sumAlpha = sumAlpha.add(classifier.getAlpha()) ;
				BigDecimal attrOutput = attr.bgOutput;
				//output = BigDecimal.valueOf(classifier.classify(Observation));
				attrOutput = attrOutput.add(output.multiply(classifier.getAlpha()));
				count++;
				attr = new Attribute(count,sumAlpha,attrOutput);
				hash.put(index,attr);
				
			}else{
				hash.put(index, new Attribute(count,classifier.getAlpha(),output.multiply(classifier.getAlpha())));
				
			}
			//System.out.println("key: "+index+" count: "+ hash.get(index));
			intStumpIndex++;
		}
		for(int intKey: hash.keySet()){
			System.out.println("attribute: "+intKey+" count: "+ hash.get(intKey).intCount+" alpha: "+hash.get(intKey).bgAlpha+" sum output: "+hash.get(intKey).bgOutput);
		}
		System.out.println("sum: "+sum);

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
	private static double[] updateWeights(File trainFile, int[] labels,
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
			//System.out.println("weight of each index: "+i+" weight: "+weights[i]);
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
	private static int getTotalSamplesNumber(File trainFile) throws IOException {

		LineNumberReader lineNumberReader = new LineNumberReader(
				new FileReader(trainFile));
		lineNumberReader.skip(Long.MAX_VALUE);
		lineNumberReader.close();
		return lineNumberReader.getLineNumber() + 1;

	}

	
	/**
	 * Cache Label/Target Column of training File
	 * @param trainFile
	 * @param samplesCount
	 * @return
	 * @throws IOException
	 */
	public static int[] getlabels(File trainFile, int samplesCount)
			throws IOException {
		int[] labels = new int[samplesCount];
		FileReader fileReader = new FileReader(trainFile);
		BufferedReader breader = new BufferedReader(fileReader);
		String line;
		int i = 0;
		while ((line = breader.readLine()) != null) {
			String[] cols = line.split("\\|");
			Integer label = (int) Double.parseDouble(cols[cols.length - 1]
					.trim());
			labels[i] = label;
			i++;
		}

		breader.close();
		//
		//System.out.println("no of labels: "+i);
		return labels;
	}



}