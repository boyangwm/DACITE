package experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class CompareFile {

	public static void main(String[] args) {

		long a = System.currentTimeMillis();

		LinkedList list = new LinkedList();
		File dir = new File("d:\\temp\\test_p");
		File file[] = dir.listFiles();
		int count = 0;
		for (int i = 0; i < file.length; i++) {
			if (file[i].isDirectory())
				list.add(file[i]);
			else
				if(file[i].getName().contains(".java")){
					System.out.println(file[i].getAbsolutePath());
					count += countLines(file[i].getAbsolutePath());
				}
		}
		File tmp;
		while (!list.isEmpty()) {
			tmp = (File) list.removeFirst();
			if (tmp.isDirectory()) {
				file = tmp.listFiles();
				if (file == null)
					continue;
				for (int i = 0; i < file.length; i++) {
					if (file[i].isDirectory())
						list.add(file[i]);
					else
						if(file[i].getName().contains(".java")){
							System.out.println(file[i].getAbsolutePath());
							count += countLines(file[i].getAbsolutePath());
						}
				}
			} else {
				System.out.println(tmp.getAbsolutePath());
			}
		}

		System.out.println("count = " + count);
	}
	
	
	public static int countLines(String path){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(path));
			String line;
			int count = 0;
			boolean bPositive = true;

			while(((line = reader.readLine())!= null)){ 
				count++;

			}
			reader.close();
			return count;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
