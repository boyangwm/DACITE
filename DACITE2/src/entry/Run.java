package entry;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;

import experiment.ExpEnvironment;
import soot.PackManager;
import soot.Transform;


public class Run {
	public static void main(String[] args)
	{

		
		ExpEnvironment expEnv = new ExpEnvironment("Environments/brocker.txt");	//CHENGL

		try {


			File file = new File("Outputs.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			int clusters = 4;	//CHENGL
			double confidence = 0.6;	//CHENGL
				
			for(double d = 0.1; d <  0.11; d+=0.01){
				FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);	//CHENGLI
				BufferedWriter bw = new BufferedWriter(fw);	//CHENGL
				
				Dacite dc = new Dacite();
				dc.run(args, clusters, d, confidence);
				double recall = expEnv.computeRecall(dc.conflictFunctions);
				double fpratio = expEnv.computeFalsePositive(dc.conflictFunctions);
				System.out.println("recall : " + recall);
				System.out.println("fpratio : " + fpratio);
				bw.write("recall : " + recall+"\n");  //CHENGL
				bw.write("fpratio : " + fpratio+"\n");  //CHENGL
				
				BigDecimal   b   =   new   BigDecimal(recall);  
				recall    =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
				b   =   new   BigDecimal(fpratio);  
				fpratio   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();
					
				bw.write("\n"  + clusters + "\t" + d + "\t" + confidence + "\t"); //CHENGL
				bw.write(recall+ "\t"); //CHENGL
				bw.write(fpratio+ "\t"); //CHENGL
				bw.write(dc.totalTime/1000 + "s"+ "\t"); //CHENGL
				
//				bw.write("\n" + "\n"+ "\n" + "4, " + d + ", 0.6" + "\n");
//				bw.write("recall : " + recall+ "\n");
//				bw.write("fpratio : " + fpratio+ "\n");
//				bw.write("time : " + dc.totalTime + "s"+ "\n");
				bw.close();
			}

			
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
