import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.FileHandler;

public class generateDataset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileReader fileReader;
		BufferedReader buffReader;
		try {
			fileReader = new FileReader("resources/final_dataset.psv");
			buffReader = new BufferedReader(fileReader);
			String strline;
			int intLoop = 1;
			while ((strline = buffReader.readLine()) != null) {
				//System.out.println(strline);
				String arrSplit[] = strline.split("\\|");
				String strBuilder = strline;
				String strResult = arrSplit[arrSplit.length-1];
				strBuilder= strBuilder.substring(0,strBuilder.length()-strResult.length());
				
				/*for (String strItem : arrSplit) {
					//System.out.println(strItem);
					if (strItem.equals("yes") || strItem.equals("never")) {
						strBuilder += "1|";
					}
					if (strItem.equals("sometimes") || strItem.equals("no")) {
						strBuilder += "2|";
					}
					if (strItem.equals("often")) {
						strBuilder += "3|";
					}
					if (strItem.equals("always")) {
						strBuilder += "4|";
					}
					if (strItem.equals("TRUE")) {
						strResult="1";
					}
					if (strItem.equals("FALSE")) {
						strResult="-1";
					}
				}*/
				strBuilder = strBuilder.substring(0,strBuilder.length()-1);
				String strSQL = "INSERT INTO TRAINING_TBL ('DATASET','RESULT') values ('"+strBuilder+"','"+strResult+"');";
				//String strPSV = strBuilder+"|"+strResult+"\n";
				System.out.println(strSQL);
				//System.out.print(strPSV);
				intLoop++;
			}

		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
