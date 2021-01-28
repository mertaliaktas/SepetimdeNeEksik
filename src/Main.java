import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Vector;


/*Girdi olarak verilecek dosya "kayitlistesi.txt" adinda olmalidir. Direk proje dosyasinin icinde olmalidir.
 *Girdi dosyasinin icindeki veriler Integer ve aralarinda bosluk olmalidir
 *Cikti olarak olusacak dosya "output.txt" adinda olusacaktir. Direk proje dosyasinin icinde bulunacaktir. 
 *Cikti dosyasinin icindeki veriler, aralarinda bosluk birakilarak ve sonunda "="(esittir) isaretinden sonra sup(destek) sayisi bulunacaktir.
 *Cikti dosyasinin en sonunda ki satirda ise toplam frequent patterns sayisi bulunmaktadir.*/

public class Main {

	static int threshold; // Min Destek sayisi
	static String file = "kayıtlistesi.txt"; // Kullanilacak veriler. Dosya, proje dosyasinin direk icinde olmalidir. 
	static String specialFile = "ozelKayitListesi.txt";
	static FPGrowth fpGrowth;
	

	public static void main(String[] args) throws IOException {
		
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Ürünler?");
		String input_ForText = sc.nextLine();
		String[] input=input_ForText.split(" ");

		System.out.println("Minimum Destek Değerini giriniz (Örn. 8000)");
		threshold = Integer.parseInt(sc.nextLine());
		
		createSpecialDatabase(file, input);
		
		fpGrowth = new FPGrowth(new File(specialFile), threshold);
		
		printFrequentPatterns();
		
		
		//ArrayList<String> inputList = new ArrayList<String>(Arrays.asList(input.split(" ")));
		
		ArrayList<String> inputList = new ArrayList<String>(Arrays.asList(input));
		
		
		printHeaderTable();
		findConfidence(inputList,input_ForText);
		
		

	}
	
	private static void createSpecialDatabase(String file2, String[] input) {
		String special_filename = "ozelKayitListesi.txt";
		String filename = "kayıtlistesi.txt";
		FileWriter specialFileWriter;
		FileReader specialFileReader;
		try {
			specialFileReader = new FileReader(filename);
			specialFileWriter = new FileWriter(special_filename);
			PrintWriter pw = new PrintWriter(specialFileWriter);
			BufferedReader br = new BufferedReader(specialFileReader);

			String line = br.readLine();

			while (line != null) {
				if (isContain(line, input)) {
					pw.append(line);
					pw.append("\n");
				}
				line=br.readLine();
			}

		} catch (IOException e) {
			System.out.println("Ozel Dosya Hatası:" + e.getMessage());
		}
		
	}
	
	private static boolean isContain(String line, String[] input)
	{
		boolean flag= true;
		for (int i = 0; i < input.length; i++) {
			if(!line.contains(input[i])) {
				flag=false;
				break;
			}
				
		}
		
		return flag;
	}

	private static void findConfidence(ArrayList<String> inputList,String input) {
		//int inputSupp = fpGrowth.frequentPatterns.get(input);
		for (Entry<String, Integer> pairs : fpGrowth.frequentPatterns.entrySet()) {
			//System.out.print(pairs.getKey());
			ArrayList<String> originalList = new ArrayList<String>(Arrays.asList( pairs.getKey().split(" ")));
			ArrayList<String> commonList = new ArrayList<String>(Arrays.asList( pairs.getKey().split(" ")));
			commonList.retainAll(inputList);
			
			if(commonList.size()==inputList.size()) {
				System.out.print("\nGüvenli Liste: ");
				for (int i = 0; i < originalList.size(); i++) {
					
					System.out.print(originalList.get(i)+" ");
				}
				/*if(inputSupp>0) {
					float conf=(float)pairs.getValue()/(float)inputSupp; 
					System.out.println("Confidence: % "+conf*100);
				}
				*/
				
				System.out.print("  Supp: "+pairs.getValue()+"\n");
				
			}

		}
	}
	
	
	private static void printHeaderTable() {
		for (int i = 0; i < fpGrowth.headerTable.size(); i++) {
			System.out.println(fpGrowth.headerTable.get(i).item+" supp:"+fpGrowth.headerTable.get(i).count);
		}
		
	}

	

	public static void printFrequentPatterns() throws IOException {
		File outputFile = new File("output.txt");// Faydali itemsetlerin bulunacagi dosya. Dosya, proje dosyasinin direk icinde olmalidir. 
		FileOutputStream fos = new FileOutputStream(outputFile);
		String newLine="\n";
		String frequentPatternsLength="The Count of Frequent Patterns: "+fpGrowth.frequentPatterns.size();
		byte[] bytes_frequentPatternLength= frequentPatternsLength.getBytes();
		
		for (Entry<String, Integer> pairs : fpGrowth.frequentPatterns.entrySet()) {
			System.out.println(pairs);
			byte[] bytes= pairs.toString().getBytes();
			byte[] bytes_newLine= newLine.getBytes();
			fos.write(bytes);
			fos.write(bytes_newLine);

		}
		fos.write(bytes_frequentPatternLength);
		fos.close();

	}

	
}
