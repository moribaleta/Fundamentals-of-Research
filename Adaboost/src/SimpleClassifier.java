
public class SimpleClassifier {

	SimpleClassifier(){
		
	}
	
	public int ClassifyType(String strResult){
		String arrStrResult[] = strResult.split("\\|");
		return Node1(arrStrResult);
	}
	
	public int Node1(String[] arrStrResult){
		if(Integer.parseInt(arrStrResult[0])==1)
			return 1;
		return Node2(arrStrResult);
	}

	private int Node2(String[] arrStrResult) {
		if(Integer.parseInt(arrStrResult[2])>2||Integer.parseInt(arrStrResult[5])>2||Integer.parseInt(arrStrResult[10])>2){
            return 2;
        }
		return 3;
	}
	
}
