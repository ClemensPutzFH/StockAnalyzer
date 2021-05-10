package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import stockanalyzer.ctrl.Controller;
import stockanalyzer.downloader.ParallelDownloader;
import stockanalyzer.downloader.SequentialDownloader;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		try {
			ctrl.process("REGI");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl2(){
		try {
			ctrl.process("SGRE.MC");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl3(){
		try {
			ctrl.process("AAPL");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void getDataFromCtrl4(){
		try {
			ctrl.getMax("REGI");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getDataForCustomInput() {
		try {
			ctrl.getMin("REGI");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrlSequentialDownloader(){
		long time1, time2;
		var list = Arrays.asList("OMV.VI",
				"EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");
		SequentialDownloader sq = new SequentialDownloader();
		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list,sq);
		time2 =System.currentTimeMillis();
		System.out.println("Time for Parallel Download: "+ (time2-time1) + "ms");
	}
	public void getDataFromCtrlParallelDownloader(){
		long time1, time2;
		var list = Arrays.asList("OMV.VI",
				"EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");
		ParallelDownloader pq = new ParallelDownloader();
		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list,pq);
		time2 =System.currentTimeMillis();
		System.out.println("Time for Parallel Download: "+ (time2-time1)+ "ms");
	}
	public void getDataFromCtrlParallelSequentialDownloader(){
		long time1, time2;
		var list = Arrays.asList("OMV.VI",
				"EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");
		SequentialDownloader sq = new SequentialDownloader();
		ParallelDownloader pq = new ParallelDownloader();
		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list,sq);
		time2 =System.currentTimeMillis();
		System.out.println("Time for Sequential Download: "+ (time2-time1)+ "ms");
		long sequTime= time2-time1;
		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list,pq);
		time2 = System.currentTimeMillis();
		System.out.println("Time for Parallel Download: "+ (time2-time1)+ "ms");
		long parrTime=time2-time1;
		System.out.println("Time Difference: "+ (sequTime-parrTime)+ "ms");
	}

	public static void print(String s){
		System.out.println(s);
	}

	public static void printError(String s){
		System.out.println(s);
	}

	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Renewable Energy Group, Inc. (REGI)", this::getDataFromCtrl1);
		menu.insert("b", "Siemens Gamesa Renewable Energy, S.A. (SGRE.MC)", this::getDataFromCtrl2);
		menu.insert("c", "Apple Inc. (AAPL)", this::getDataFromCtrl3);
		menu.insert("d", "MIN: Renewable Energy Group, Inc. (REGI)",this::getDataForCustomInput);
		menu.insert("e", "MAX: Renewable Energy Group, Inc. (REGI)",this::getDataFromCtrl4);
		menu.insert("f", "Download 1",this::getDataFromCtrlSequentialDownloader);
		menu.insert("g", "Download 2",this::getDataFromCtrlParallelDownloader);
		menu.insert("h", "Download 2",this::getDataFromCtrlParallelSequentialDownloader);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
