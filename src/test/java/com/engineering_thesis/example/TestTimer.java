package com.engineering_thesis.example;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import com.relevantcodes.extentreports.LogStatus;

import javafx.application.Platform;

public abstract class TestTimer extends ProgressBar{
	static DecimalFormat df = new DecimalFormat("0.000");
	
	//The method that shows on the label, which part of tests is being done 
	protected  void setProgressLabel(int progress, int maxProgress) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				JavaFX.progress.setText("Progres: ("+progress+"/"+maxProgress+")");
			}
		});
	}
	//The method that shows time of all tests on the label 
	protected  void setTimer(long time) {
		final double returnTime=time/1000000000F;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				JavaFX.timer.setText("Czas wykonywania ca�ego testu = "+df.format(returnTime)+" s.");
			}
		});
	}
	//Method that saves value of test time to file CSV
	protected void saveTimeToCSV(String Web_Mobile, String continent,long elapsedTime) throws IOException
	{
		final double returnTime=elapsedTime/1000000000F;
		
		TestControl.logger.log(LogStatus.INFO, "<b>CZAS:</b> Test <b>"+Web_Mobile.toUpperCase()+"</b>, przegladarka <b>"+TestControl.returnBrowserName().toString()+"</b>, <b>"+continent+"</b>, rowny: <b>"+df.format(returnTime)+"s.</b>");
		
		 PrintWriter pw = new PrintWriter(new FileWriter("time.csv",true));
	        StringBuilder sb = new StringBuilder();
	        sb.append(Web_Mobile);
	        sb.append(';');
	        sb.append(TestControl.returnBrowserName().toString());
	        sb.append(';');
	        sb.append(continent);
	        sb.append(';');
	        sb.append(df.format(returnTime));
	        sb.append('\n');
	        
	        pw.write(sb.toString());
	        pw.close();
	        super.addToLogs();
	    	super.addToLogs("Zapisano czas dla "+continent+" "+TestControl.browser.toString(), getClass().getName().toString(),
					Thread.currentThread().getStackTrace()[1].getMethodName(), 144);
	    	super.addToLogs();
	}
}
