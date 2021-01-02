import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	static String file = "kayýtlistesi.txt"; // Kullanilacak veriler. Dosya, proje dosyasinin direk icinde olmalidir. 
	static FPGrowth fpGrowth;
	

	public static void main(String[] args) throws IOException {
		
		
		Scanner sc = new Scanner(System.in);
		
		

		
		
		System.out.println("Minimum Destek Deðerini giriniz (Örn. 8000)");
		threshold = Integer.parseInt(sc.nextLine());
		
		fpGrowth = new FPGrowth(new File(file), threshold);
		
		printFrequentPatterns();
		//printHeaderTable();
		
		
		

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