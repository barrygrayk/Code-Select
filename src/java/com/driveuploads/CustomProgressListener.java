package com.driveuploads;

import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CustomProgressListener implements MediaHttpUploaderProgressListener {

    public void progressChanged(MediaHttpUploader uploader) throws IOException {
        switch (uploader.getUploadState()) {
            case INITIATION_STARTED:
                System.out.println("Initiation has started!");
                break;
            case INITIATION_COMPLETE:
                System.out.println("Initiation is complete!");
                break;
            case MEDIA_IN_PROGRESS:
                System.out.println(uploader.getProgress());
                break;
            case MEDIA_COMPLETE:
                System.out.println("Upload is complete!");
        }
    }

    public void tst() throws FileNotFoundException {
        File mediaFile = new File("/tmp/driveFile.jpg");
        InputStreamContent mediaContent
                = new InputStreamContent("image/jpeg",
                        new BufferedInputStream(new FileInputStream(mediaFile)));
        mediaContent.setLength(mediaFile.length());

        Drive.Files.Insert request = drive.files().insert(fileMetadata, mediaContent);
        request.getMediaHttpUploader().setProgressListener(new CustomProgressListener());
        request.execute();
    }

}
