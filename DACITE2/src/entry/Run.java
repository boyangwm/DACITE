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

		ExpEnvironment expEnv = new ExpEnvironment("Environments/1.txt");

		try {


			File file = new File("Outputs.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}


			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);


			for(double d = 0.20; d < 0.21; d+=0.01){
				Dacite dc = new Dacite();
				dc.run(args, 4, d, 0.6);
				double recall = expEnv.computeRecall(dc.conflictFunctions);
				double fpratio = expEnv.computeFalsePositive(dc.conflictFunctions);
				System.out.println("recall : " + recall);
				System.out.println("fpratio : " + fpratio);

				BigDecimal   b   =   new   BigDecimal(recall);  
				recall   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
				b   =   new   BigDecimal(fpratio);  
				fpratio   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  

				bw.write("\n" + "4\t" + d + "\t 0.6\t");
				bw.write(recall+ "\t");
				bw.write(fpratio+ "\t");
				bw.write(dc.totalTime/1000 + "s"+ "\t");
				
//				bw.write("\n" + "\n"+ "\n" + "4, " + d + ", 0.6" + "\n");
//				bw.write("recall : " + recall+ "\n");
//				bw.write("fpratio : " + fpratio+ "\n");
//				bw.write("time : " + dc.totalTime + "s"+ "\n");

			}

			bw.close();
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
