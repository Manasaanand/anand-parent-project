package net.orfdev.integration.test.util;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

    static String CurrentReport;
    static Date date = new Date();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    static String subjectToEmailBody;

    public static void main(String[] args) throws Exception {

        CurrentReport = "test report.txt";
        // zipFolder("C:\\Users\\Anand Jois\\Documents\\test report",
        // "C:\\Users\\Anand Jois\\Documents\\test report.zip");
        String reportLink = null;
        execute("Test report", reportLink);
        // sendMail();
    }

    static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        // addFolderToZip("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

//    static private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
//
//        File folder = new File(srcFile);
//        if (folder.isDirectory()) {
//            // addFolderToZip(path, srcFile, zip);
//        } else {
//            byte[] buf = new byte[1024];
//            int len;
//            FileInputStream in = new FileInputStream(srcFile);
//            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
//            while ((len = in.read(buf)) > 0) {
//                zip.write(buf, 0, len);
//            }
//            in.close();
//        }
//        
//    }

//    static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
//        File folder = new File(srcFolder);
//
//        for (String fileName : folder.list()) {
//            if (path.equals("")) {
//                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
//            } else {
//                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
//            }
//        }
//    }

    public static void execute(String subJect, String reportLink) {
        subjectToEmailBody = subJect;
        String[] to = {
                "anand.jois@1cover.com.au"/*, "tony.goyarzu@1cover.com.au"/*, "minmin.soe@1cover.com.au", "tony.goyarzu@1cover.com.au", "sagar.nannapaneni@1cover.com.au"/*
                                             * ,
											 * "sagar.nannapaneni@1cover.com.au",
											 * "kai.pancratz@1cover.com.au",
											 * "minmin.soe@1cover.com.au",
											 * "shweta.desai@1cover.com.au",
											 * "tony.goyarzu@1cover.com.au",
											 * "vaani.sukhija@1cover.com.au"/* ,
											 * "luke.pryor@orpheussoftware.co.uk"
											 * ,"konstantin.kastanov@orpheussoftware.co.uk"
											 * ,"lee.stevens@1cover.com.au",
											 * "leon.borlinghaus@1cover.com.au"
											 * ,"pete.storey@1cover.com.au"
											 */
        };
        String[] cc = {};
        String[] bcc = {"anand.jois@1cover.com.au"};
        
            sendMail("anand.jois@1cover.com.au", "anandjn", "172.16.70.252", "", "true", "true", true,
                    "javax.net.ssl.SSLSocketFactory", "true", to, cc, bcc, subJect + " Public Website dated " + dateFormat.format(date),
                    "Hi, Could you Please find the attached report for Today?   Kind Regards   Anand Jois", reportLink);// ,
            // att,
            // reportFileName);

        
    }

    public static boolean sendMail(String userName, String passWord, String host, String port, String starttls,
                                   String auth, boolean debug, String socketFactoryClass, String fallback, String[] to, String[] cc,
                                   String[] bcc, String subject, String text, String reportLink) {// ,
        // File
        // attachmentPath,
        // String
        // attachmentName) {

        // Object Instantiation of a properties file.
        Properties props = new Properties();

        props.put("mail.smtp.user", userName);

        props.put("mail.smtp.host", host);

        if (!"".equals(port)) {
            props.put("mail.smtp.port", port);
        }

        if (!"".equals(starttls)) {
            props.put("mail.smtp.starttls.enable", starttls);
            props.put("mail.smtp.auth", auth);
        }

        if (debug) {

            props.put("mail.smtp.debug", "true");

        } else {

            props.put("mail.smtp.debug", "false");

        }

        if (!"".equals(port)) {
            props.put("mail.smtp.socketFactory.port", port);
        }
        if (!"".equals(socketFactoryClass)) {
            props.put("mail.smtp.socketFactory.class", socketFactoryClass);
        }
        if (!"".equals(fallback)) {
            props.put("mail.smtp.socketFactory.fallback", fallback);
        }

        try {

            Session session = Session.getDefaultInstance(props, null);

            session.setDebug(debug);

            MimeMessage msg = new MimeMessage(session);

            msg.setText(text);

            msg.setSubject(subject);

            Multipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            // DataSource source = new FileDataSource(attachmentPath);
            // messageBodyPart.setDataHandler(new DataHandler(source));
            // messageBodyPart.setFileName(attachmentName);
            multipart.addBodyPart(messageBodyPart);
            // messageBodyPart.attachFile(attachmentPath);
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart.setText("Hi 1Cover IT Team," + "\n\n" + "Please find automation test results" + "\n\n"
                    + "http://engage-staging-test-results.s3-website-ap-southeast-2.amazonaws.com/" + reportLink
                    + "/cucumber-html-reports/" + "\n\n" + "Note: We do not send Email Notifications if " + subjectToEmailBody + " public website is working fine." + "\n\n" + "Regards" + "\n" + "Testing Team");

            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
            msg.setFrom(new InternetAddress(userName));

            for (int i = 0; i < to.length; i++) {
                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
            }

            for (int i = 0; i < cc.length; i++) {
                msg.addRecipient(Message.RecipientType.CC, new InternetAddress(cc[i]));
            }

            for (int i = 0; i < bcc.length; i++) {
                msg.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc[i]));
            }

            msg.saveChanges();

            Transport transport = session.getTransport("smtp");

            transport.connect(host, userName, passWord);

            transport.sendMessage(msg, msg.getAllRecipients());

            transport.close();

            return true;

        } catch (Exception mex) {
            mex.printStackTrace();
            return false;
        }
    }

}
