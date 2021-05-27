package org.ejb.sample.servlet.helper;

import java.io.FileWriter;
import java.io.IOException;
import com.amazonaws.auth.*;
import com.amazonaws.services.s3.*;
import static com.amazonaws.mongodb.*;

public class FileUtility {

    void generateReportFile(String content) throws IOException {
        FileWriter fileWriter = new FileWriter("~/DailyReports/Report.csv");
        fileWriter.write(content);
        fileWriter.close();
    }

    public URL uploadS3ObjectAndGetUrl(String bucketName, String uploadContentPath, String keyName) {
        AmazonS3 s3client = getS3Client();
        s3client.putObject(new PutObjectRequest(bucketName, keyName, new File(uploadContentPath)));
        return s3client.getUrl(bucketName, keyName);
    }
}
