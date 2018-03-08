import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class main {
	//static String strListData = "1|1|2|2|1|1|1|2|1|2|4|2|4|4|2|1__1|2|2|1|1|1|2|3|2|3|3|1|1|2|3|2__1|1|2|1|1|1|3|2|1|4|3|1|2|2|2|3__1|1|2|1|2|2|4|2|2|2|4|2|1|2|1|4__1|2|2|1|1|1|2|3|1|2|4|1|2|3|2|2__2|1|2|2|2|1|2|4|1|3|3|1|2|2|2|2__2|1|2|2|1|1|3|4|2|4|4|2|2|1|2|3__2|1|2|2|1|2|4|2|2|4|3|2|3|1|3|1__2|1|2|2|2|2|4|4|2|2|3|2|4|4|2|3__2|1|2|2|2|1|3|2|1|2|4|1|2|2|4|2__2|2|1|2|2|1|2|2|2|2|1|2|2|1|2|2__2|2|1|2|1|2|3|2|1|2|2|1|2|2|3|3__2|2|1|2|2|1|2|2|1|3|3|1|3|2|4|4__2|2|1|1|2|1|3|3|1|4|4|1|4|3|4|2__2|2|1|2|2|2|4|4|2|2|4|2|1|4|1|1__1|1|1|2|1|2|1|2|2|2|2|2|2|2|2|1__1|1|1|1|2|2|1|1|2|1|1|2|1|1|1|1__1|1|2|1|2|2|1|1|2|1|1|2|1|1|1|1__1|2|1|1|2|2|1|1|2|1|1|2|1|1|1|1__1|1|2|1|2|2|1|1|2|1|1|2|1|1|1|1__1|2|1|1|2|2|1|1|2|1|1|2|1|1|1|1__1|1|1|1|2|2|1|1|2|1|1|2|1|1|1|1__1|1|2|2|1|2|1|2|2|3|4|2|2|1|2|1__1|2|1|2|2|2|1|1|2|2|4|2|2|1|2|1__1|1|2|2|1|2|1|2|2|3|3|2|2|2|1|2__2|2|1|2|2|1|2|2|2|2|1|2|1|1|1|3__2|2|1|2|1|2|2|4|2|3|4|1|4|1|2|1__2|2|1|1|2|2|3|2|2|3|2|2|2|4|1|3__2|2|1|2|2|2|4|4|1|2|2|2|4|2|2|2__2|2|1|2|2|2|3|2|2|3|4|2|2|4|1|2__1|1|2|1|2|1|4|3|2|2|4|2|4|2|2|2__1|1|2|1|2|2|3|4|2|4|2|2|2|2|1|3__1|1|2|1|1|2|4|3|2|3|1|1|2|4|2|2__1|1|2|2|2|2|4|3|2|4|4|2|4|2|1|2__1|1|2|1|2|2|3|3|1|4|1|2|2|2|2|4__1|2|1|2|2|2|1|1|2|4|4|2|1|1|1|1__1|2|1|2|1|2|4|4|2|1|3|2|4|1|4|3__1|2|1|2|1|1|1|2|2|4|4|2|2|2|2|3__1|2|1|1|1|2|4|4|2|2|2|2|4|4|1|2__1|2|1|2|2|2|2|2|2|3|4|2|3|1|1|3__2|1|1|2|2|2|2|2|1|2|2|2|2|3|3|1__2|1|1|2|2|2|1|2|2|2|3|2|2|2|2|1__2|2|1|2|1|1|3|3|1|3|2|2|3|3|3|1__1|2|2|2|2|2|2|4|1|3|2|2|3|3|3|2__1|2|2|2|2|2|3|4|2|3|2|2|4|2|3|1__1|1|2|2|2|1|3|3|2|3|2|2|3|3|3|2__2|2|1|2|1|2|4|3|2|3|2|2|3|2|4|1__2|1|2|2|1|1|2|4|1|3|2|1|3|3|3|2__2|1|2|2|1|1|3|3|1|2|2|1|3|3|3|1__1|1|2|2|1|1|3|3|1|2|2|2|3|3|3|1__2|1|2|2|2|2|1|1|2|1|2|2|1|2|1|2__2|1|2|2|2|2|1|2|1|1|2|1|1|2|1|2__2|1|2|2|1|1|1|2|2|2|2|2|1|2|1|2__2|1|2|2|2|2|2|2|1|2|1|1|1|1|2|1__2|1|2|2|2|1|2|2|1|2|2|2|1|1|2|1__2|1|2|2|2|1|2|1|2|1|2|1|1|2|1|1__2|1|2|1|2|1|1|2|2|1|2|2|1|2|2|2__2|1|2|1|2|2|1|2|1|1|1|2|1|2|1|2__2|1|2|1|2|2|1|1|1|1|1|2|1|1|1|2__2|1|2|1|1|1|1|1|2|1|1|1|2|1|1|1";
	static String strListData = "2|2|1|2|1|2|2|2|2|2|2|2|2|2|2|2__1|1|2|2|2|2|2|2|1|3|3|2|2|2|2|1__1|1|1|2|1|2|2|2|1|4|3|2|2|3|2|3__1|2|1|2|2|1|3|3|1|4|3|2|4|2|2|2__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__2|2|1|2|1|1|1|1|2|3|2|2|2|1|3|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__1|2|2|2|1|2|2|2|2|2|3|2|2|3|3|2__2|2|1|2|2|2|2|2|2|3|2|2|2|2|4|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__2|2|1|2|2|2|2|2|2|4|2|2|2|3|3|2__2|1|1|2|2|2|2|2|1|2|2|2|2|3|3|1__2|1|2|2|2|2|2|2|2|2|3|2|2|2|3|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__2|1|2|2|1|2|1|2|2|3|3|2|4|4|1|1__1|1|1|2|2|2|2|2|2|3|2|2|2|3|3|2__2|1|2|2|2|2|2|2|2|3|3|2|2|2|2|1__1|2|1|2|1|2|2|2|2|3|2|2|2|2|2|1__2|1|2|2|1|2|2|2|1|4|2|2|2|3|2|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__1|2|1|2|1|2|1|1|2|2|1|2|1|1|2|1__2|1|1|2|1|2|1|1|2|2|1|2|1|2|2|3__1|1|2|2|2|2|3|1|2|2|2|2|1|2|3|1__2|1|2|2|2|2|1|1|2|2|2|2|2|2|2|1__2|1|1|2|2|2|1|2|2|2|3|2|2|2|2|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1__2|1|2|2|2|2|2|2|2|2|2|2|3|2|3|1__2|1|2|2|2|2|1|2|1|2|2|2|2|3|3|1__1|1|1|2|1|2|1|2|2|2|2|2|2|2|2|1__2|2|2|2|2|2|1|1|2|1|1|2|1|1|1|1";
	//static String sample = "2|2|2|1|2|1|1|2|1|2|2|1|2|2|2|1";
	//static String strResult = "1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__1__-1__-1__-1__-1__-1__-1__-1__-1__-1__-1";
	static String strResult = "-1__1__1__1__-1__-1__-1__1__-1__-1__-1__1__-1__-1__1__1__-1__-1__-1__-1__-1__-1__1__-1__1__-1__-1__-1__1__-1";
	public static void main(String args[]){
		
		//openTest();
		//openTestAtype();
		
		
		File file = new File("resources/final_dataset.psv");
		Adaboost boosting;
		try {
			boosting = Adaboost.train(file, 10, 50, 0);
			String arrStrSet[] = strListData.split("__");
			String arrResult[] = strResult.split("__");
			int intCountPos = 0;
			int intCountNeg = 0;
			int error = 0;
			//String strBuilder = "";
			CeliacType ctype = new CeliacType();
			//System.out.println("label: "+boosting.classify(sample.split("\\|")));
			ArrayList<String>arrListBuilder = new ArrayList<>();
			for(int i = 0; i<arrStrSet.length; i++){
				//String strSample = "1|1|1|1|1|1|2|2|2|1|2|1|1|3|4|4";
				String strSample = arrStrSet[i];
				System.out.println("test: "+(i+1));
				int label = boosting.classify(strSample.split("\\|"));
				//System.out.println("Data Label"+strSample+" = "+label);
				if(label>0){
					intCountPos++;
				}else{
					intCountNeg++;
				}
				String result = " ";
				if(label>0){
					result += "positive - "+ctype.TestResult(strSample);
					
				}else{
					result += "negative";
				}
				
				boolean check = true;
				if(label!=Integer.parseInt(arrResult[i])){
					error++;
					check = false;
				}
				arrListBuilder.add((i+1)+". "+result+" match: "+check) ;
			}	
			for(String strOut: arrListBuilder){
				System.out.println(strOut);
			}
			
			double accuracy = ((double)arrStrSet.length - Double.parseDouble(error+""))/(double)arrStrSet.length;
			System.out.println("accuracy: "+accuracy);
			
			System.out.println("Pos: "+intCountPos+" Neg "+intCountNeg+" error: "+error);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		openTestTyp();
		
		
		
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
	
	private static void openTestTyp() {
		// TODO Auto-generated method stub
		String sample = "2|2|2|1|2|1|1|2|1|2|2|1|2|2|2|1";
		File file = new File("resources/training_new.psv");
		Adaboost boosting;
		int intIteration = 10;
		int label = 0;
		try {
			while(label<1&&intIteration<=100){
				boosting = Adaboost.train(file, 10, intIteration, 0);	
				label = boosting.classify(sample.split("\\|"));
				System.out.println(intIteration +"output: " + label);
				intIteration += 10;
			}
			//System.out.println(intIteration-10 + label);
			//String arrResult[] = strResult.split("__");
			
		}catch(Exception e){
			
		}
	}

	/*private static void openTestAtype() {
		// TODO Auto-generated method stub
		System.out.println("atype");
		File file = new File("resources/training_atyp.psv");
		Adaboost boosting;
		int intIteration = 10;
		int label = 0;
		try {
			while(label<1&&intIteration<=100){
				boosting = Adaboost.train(file, 10, intIteration, 0);	
				label = boosting.classify(sample.split("\\|"));
				System.out.println(intIteration +"output: " + label);
				intIteration += 10;
			}
			//System.out.println(intIteration-10 + label);
			//String arrResult[] = strResult.split("__");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}*/

	/*public static void openTest(){
		
		File file = new File("resources/training_data.psv");
		Adaboost boosting;
		int intIteration = 10;
		int label = 0;
		try {
			while(label<1&&intIteration<=100){
				boosting = Adaboost.train(file, 10, intIteration, 0);	
				label = boosting.classify(sample.split("\\|"));
				System.out.println(intIteration +"output: " + label);
				intIteration += 10;
			}
			//System.out.println(intIteration-10 + label);
			//String arrResult[] = strResult.split("__");
			
		}catch(Exception e){
			
		}
		
	}*/
	
}
