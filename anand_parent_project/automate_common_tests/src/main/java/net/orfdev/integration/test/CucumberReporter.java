package net.orfdev.integration.test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

public class CucumberReporter {

	private static final Logger log = LogManager.getLogger(null, null);

	private static String bucketName = "engage-staging-test-results";
	public static String reportName = null;
	public static String projectNameBeforeChange = null;

	public String createReport(String projectName, String jsonFile, String environment) throws IOException {
		projectNameBeforeChange = projectName;
		projectName = projectName.replace(" ", "");
		projectName = String.format("%s-%s", getDateMarker(), projectName);
		reportName = projectName;
		File reportOutputDirectory = new File("../cucumber/reports/" + projectName + "/");

		log.info("Use folder {}");

		List<String> jsonFiles = new ArrayList<String>();
		jsonFiles.add(jsonFile);

		String buildNumber = "1";
		boolean runWithJenkins = false;
		boolean parallelTesting = false;

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		// configuration.setParallelTesting(parallelTesting);
		// configuration.setRunWithJenkins(runWithJenkins);
		// configuration.setBuildNumber(buildNumber);
		String format = "dd-MM-yyyy";
		String dateInString = new SimpleDateFormat(format).format(new Date());
		configuration.addClassifications("Platform", "Windows");
		configuration.addClassifications("Browser", "Firefox");
		configuration.addClassifications("Branch", "release/1.0");
		configuration.addClassifications("Product", projectNameBeforeChange);
		// configuration.addClassifications("Date", dateInString);
		configuration.addClassifications("Environment", environment);
		configuration.addClassifications("Computer", TestContext.hostname);

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		Reportable reportable = reportBuilder.generateReports();
		// temp return . actual return is commented
		return dateInString;

		// return uploadReport(projectName, reportOutputDirectory);
	}

	/*
	 * public String uploadReport(String folderName, File directory) throws
	 * IOException {
	 * 
	 * 
	 * BasicAWSCredentials credentials = new
	 * BasicAWSCredentials("AKIAILJGZUSEH4PKOFVA",
	 * "v4S0RaRq69rwXr81lFRljUCQusrMH8amxSK53VAg"); // OMG //KP: YES
	 * #breakfast @Anand AmazonS3ClientBuilder amazonS3ClientBuilder =
	 * AmazonS3ClientBuilder.standard() .withRegion(Regions.AP_SOUTHEAST_2);
	 * amazonS3ClientBuilder.setCredentials(new
	 * AWSStaticCredentialsProvider(credentials)); AmazonS3 s3 =
	 * amazonS3ClientBuilder.build(); TransferManagerBuilder transferManagerBuilder
	 * = TransferManagerBuilder.standard().withS3Client(s3); TransferManager
	 * transferManager = transferManagerBuilder.build(); String resultPath =
	 * "/cucumber-html-reports/"; File htmlReport = new File(directory.getPath() +
	 * resultPath);
	 * 
	 * if (!htmlReport.isDirectory()) { throw new
	 * IOException("Upload has to be a whole directory."); }
	 * 
	 * log.
	 * info("Calling transferManager.uploadDirectory({}, {}, {}, true); {} to {}",
	 * bucketName, folderName, directory); MultipleFileUpload upload =
	 * transferManager.uploadDirectory(bucketName, folderName, directory, true);
	 * 
	 * while (!upload.isDone()) { log.debug("Transferred: " + ((long)
	 * upload.getProgress().getPercentTransferred()) + "%"); try {
	 * Thread.currentThread(); Thread.sleep(2000); } catch (InterruptedException e)
	 * { log.debug("Cannot Sleep."); } } log.debug("Transferred: " + ((long)
	 * upload.getProgress().getPercentTransferred()) + "%");
	 * 
	 * if (upload.isDone()) { log.debug("Upload {}", upload);
	 * transferManager.shutdownNow(); return folderName + resultPath; } throw new
	 * IOException("Cannot upload report to S3."); }
	 */
	private String getDateMarker() {
		LocalDateTime localDate = LocalDateTime.now();// For reference
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddms");
		return localDate.format(formatter);
	}

	private class UploadRunner implements Runnable {

		public UploadRunner() {

		}

		public void run() {

		}
	}

}
