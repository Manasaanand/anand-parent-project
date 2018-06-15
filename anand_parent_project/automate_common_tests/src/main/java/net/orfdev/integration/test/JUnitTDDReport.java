package net.orfdev.integration.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JUnitTDDReport {
	static File reportDirectory;
	static File junitReport;
	static BufferedWriter junitWriter;
	static String junitReportFile;
	static File reportOutputDirectory;
	static String env = "";
	static String hostname;

	public static void createHTMLPage(String product) throws IOException {
		if (System.getProperty("user.dir").contains("staging")) {
			env = "staging\\";
		} else if (System.getProperty("user.dir").contains("production")) {
			env = "production\\";
		} else if (System.getProperty("user.dir").contains("testing")) {
			env = "testing\\";
		}else{
			env="";
		}

		String productFolder = product.replaceAll(" ", "");
		reportDirectory = new File("../engage-test-price-validation/TestReport");
		reportOutputDirectory = new File("../cucumber/reports/");
		junitReportFile = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\development\\" + env
				+ "engage-test\\cucumber\\reports\\PriceValidation" + "\\" + productFolder
				+ "\\cucumber-html-reports\\PriceValidationTestReport.html";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date date = new Date();
		junitReport = new File(junitReportFile);
		junitWriter = new BufferedWriter(new FileWriter(junitReport, true));
		junitWriter.write("<html><body>");
		junitWriter.append("<html>");
		junitWriter.append("<head>");
		junitWriter.append("</head>");
		junitWriter.append("<table>");
		junitWriter
				.write("<h1>Price Validation  Summary - " + product + "  , Date  " + dateFormat.format(date) + "</h1>");
		junitWriter.append("<filter");
		junitWriter.append("<description");
		junitWriter.append("</description>");
		junitWriter.append("</filter>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> TestCase");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Plan Type");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Destination");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Depart Date");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Return Date");
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Duration");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Traveler ages");
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Expected Retail Price");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Actual Retail Price");
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Expected Cruise Price");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Actual Cruise Price");
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Expected Winter Price");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Actual Winter Price");
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Result");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #333; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> Reason");
		junitWriter.append("</th>");

	}

	public static void deleteTestReport(String junitReportFile2) {
		File f = new File(junitReportFile2);
		if (f.exists() && !f.isDirectory()) {
			f.delete();
		}

	}

	public static void closeHTMLReport() throws IOException {

		junitWriter.write("</body></html>");
		junitWriter.close();
		// Desktop.getDesktop().browse(junitReport.toURI());
		copyFolder(reportDirectory, reportOutputDirectory);

	}

	public static void enterSuccessResults(int testcaseNo, String plantype, String destinationToEnter,
			String departDate, String returnDate, String duration, String travelerAge, String retailPrice,
			Double actualPlanPrice, Double expCruise, Double actCruise, Double winterExpValue, Double winterActualValue)
					throws IOException {
		// System.out.println("test for date Success"+junitReportFile);

		junitWriter.append("<tr>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> TC "
						+ testcaseNo);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ plantype);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ destinationToEnter);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ departDate);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ returnDate);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ duration);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ travelerAge);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ retailPrice);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ actualPlanPrice);
		junitWriter.append("</th>");

		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ expCruise);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ actCruise);
		junitWriter.append("</th>");

		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ winterExpValue);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ winterActualValue);
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> PASS");
		junitWriter.append("</th>");
		junitWriter.append(
				"<th style = \"background: #008000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> N/A");
		junitWriter.append("</th>");

		junitWriter.append("</tr>");

	}

	public static void enterFailureResults(int testcaseNo, String plantype, String destinationToEnter,
			String departDate, String returndate, String duration, String travelerAges, String retailPrice,
			Double actualPlanPrice, Double expCruise, Double actCruise, Double winterExpValue, Double winterActualValue,
			String failMessage) throws IOException {

		junitWriter.append("<tr>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> TC "
						+ testcaseNo);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ plantype);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ destinationToEnter);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ departDate);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ returndate);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ duration);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ travelerAges);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ retailPrice);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ actualPlanPrice);
		junitWriter.append("</th>");

		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ expCruise);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ actCruise);
		junitWriter.append("</th>");

		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ winterExpValue);
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ winterActualValue);
		junitWriter.append("</th>");

		junitWriter.append(
				"<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> FAIL");
		junitWriter.append("</th>");
		junitWriter
				.append("<th style = \"background: #FF0000; color: white; font-weight: bold; padding: 6px; border: 1px solid #ccc; text-align: left;\"> "
						+ failMessage);
		junitWriter.append("</th>");
		junitWriter.append("</tr>");

//		junitWriter.write("<br/>");

	}

	public static void copyFolder(File source, File destination) {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				destination.mkdirs();
			}

			String files[] = source.list();

			for (String file : files) {
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);

				copyFolder(srcFile, destFile);
			}
		} else {
			InputStream in = null;
			OutputStream out = null;

			try {
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
			} catch (Exception e) {
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public static String getEnv() {
		if (System.getProperty("user.dir").contains("staging")) {
			env = "staging\\";
		} else if (System.getProperty("user.dir").contains("production")) {
			env = "production\\";
		} else if (System.getProperty("user.dir").contains("testing")) {
			env = "testing\\";
		}else{
			env="";
		}
		return env;
	}

}
