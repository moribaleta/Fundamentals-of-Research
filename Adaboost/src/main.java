import java.io.File;
import java.io.IOException;

public class main {
	
	public static void main(String args[]){
		File file = new File("resources/train2.psv");
		Adaboost boosting;
		try {
			boosting = Adaboost.train(file, 10, 20, 0);
			String strSample = "1|1|2|2|2|2|2|2|2|1|1|1|1|1|1|2";
			int label = boosting.classify(strSample.split("\\|"));
			System.out.println("Data Label"+strSample+" = "+label);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Adaboost2 boosting2;
		try {
			String strTrainData = 
					"1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1\n"
					+ "2|2|4|2|2|4|4|4|2|4|4|2|4|4|4|2|-1\n"
					+ "1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|-1\n"
					+ "2|2|2|2|2|2|2|2|2|1|1|1|1|1|1|2|1\n"
					+ "2|2|1|2|2|1|1|1|2|1|1|2|1|1|1|2|-1\n";
			boosting2 = Adaboost2.train(strTrainData, 10, 10, 0);
			String strSample = "1|1|1|1|1|1|1|1|1|1|1|1|1|1|1|1";
			int label = boosting2.classify(strSample.split("\\|"));
			System.out.println("v2 Data Label"+strSample+" = "+label);
			SimpleClassifier sc = new SimpleClassifier();
			int intType = sc.ClassifyType(strSample);
			System.out.println("type: "+intType);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
}
