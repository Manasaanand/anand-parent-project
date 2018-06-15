package net.orfdev.integration.test.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class ReportPreparation {
    static Properties property = new Properties();
    static String createReportWithDate;
    static String projectLists;
    static String project;
    static String latestReportsPath = "C:\\workspace\\REPORTS\\LATEST REPORT\\";
    static String historyReportsPath = "C:\\workspace\\REPORTS\\HISTORY\\";
    static String automationProjectPath = "C:\\workspace\\test-automation\\";

    public static void main(String[] args) throws IOException {

        String projectPath = System.getProperty("user.dir");
        String[] projectString = projectPath.split("\\\\");
        project = projectString[projectString.length - 1];
        System.out.println(project);
        File reportFolderPath = new File(automationProjectPath + project + "\\target\\cucumber-html-reports");
        String reportFeatureFile = automationProjectPath + project + "\\target\\cucumber-html-reports\\feature-overview.html";
        String lastModifiedDate = getLastModifiedDate(reportFeatureFile);

        createReportWithDate = "cucumber-html-reports_" + lastModifiedDate;
        File latestreportRepositoryFolders = new File(latestReportsPath + project);
        File latestreportRepository = new File(latestReportsPath + project + "\\cucumber-html-reports");
        File historyreportRepository = new File(historyReportsPath + project + "\\" + createReportWithDate);
        copyDirectory(latestreportRepository, historyreportRepository);
        FileUtils.deleteDirectory(latestreportRepositoryFolders);
        copyDirectory(reportFolderPath, latestreportRepository);


    }

    private static void createReportDirectory(String folderName) {
        File f = new File(latestReportsPath + project);
        File reportFile = new File(latestReportsPath + project + "\\cucumber-html-reports");
        String filePath = latestReportsPath + project + "\\cucumber-html-reports";
        if (f.exists()) {
            if (!reportFile.exists()) {
                boolean success = (new File(filePath)).mkdirs();
                if (!success) {
                    System.out.println("Failed to create direcotry");
                }
            }
        } else {
            boolean success = (new File(latestReportsPath + project)).mkdirs();
            if (!success) {
                System.out.println("Failed to create direcotry");
            }
            success = (new File(filePath)).mkdirs();
            if (!success) {
                System.out.println("Failed to create direcotry");
            }
        }

    }

    public static void copyDirectory(File sourceLocation, File targetLocation) throws IOException {
        createReportDirectory(createReportWithDate);
//		System.out.println(sourceLocation);
//		System.out.println(targetLocation);
        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }
            String[] children = sourceLocation.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
            }
        } else {
            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    private static String getLastModifiedDate(String reportFolder) {
        File file = new File(reportFolder);
        Date lastModified = new Date(file.lastModified());
        SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_HH_mm");
        String formattedDateString = formatter.format(lastModified);
        System.out.println(formattedDateString);
        return formattedDateString;

    }
}