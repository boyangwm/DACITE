package experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.config.Config;
import com.run_algorithms.DataToRules;

public class ExpEnvironment {


	Positive positive = new Positive();   //correct
	Negative negative = new Negative();   //incorrect


	public ExpEnvironment(String path){
		//import configuration
		File file = new File(path);

		String input = file.getAbsolutePath();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(input));
			String line;
			boolean bPositive = true;

			while(((line = reader.readLine())!= null)){ 
				if(line.equals("Correct")){
					bPositive = true;
					continue;
				}
				if(line.equals("Incorrect")){
					bPositive = false;
					continue;
				}
				if(line.equals("")){
					continue;
				}

				if(bPositive == true){
					positive.add(line);
				}else{
					negative.add(line);
				}

			}
			reader.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * records/positive
	 * @return
	 */
	public double computeRecall(ArrayList<String> records){
		
		Set<String> set = new HashSet<String>();
		for(String r : records){
			if(negative.contains(r)){
				if(!set.contains(r)){
					set.add(r);
				}
			}
		}
		
		double recall = (double)set.size()/(double)negative.size();
		return recall;

	}

	/**
	 * records/negative (incorrect)
	 * @return
	 */
	public double computeFalsePositive(ArrayList<String> records){
		Set<String> set = new HashSet<String>();
		for(String r : records){
			if(positive.contains(r)){
				if(!set.contains(r)){
					set.add(r);
				}
			}
		}
		
		double fp = (double)set.size()/(double)positive.size();
		return fp;

	}

}


