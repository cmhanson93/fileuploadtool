
package com.cmh.uploadfiletool.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author chanson
 */
public class FileSystemHelper {
    
    private final String path = "C:/Documents";
    
    public boolean uploadFileToSystem(UploadedFile file) {
        boolean fileUploaded = false;
        
        OutputStream output = null;
        InputStream input = null;
        
        try {
            output = new FileOutputStream(new File(path + File.separator + file.getFileName()));
            input = file.getInputstream();
            
            byte[] buffer = new byte[100000];
            int bulk;
            
            bulk = input.read(buffer);
            
            while (bulk > 0) {
                output.write(buffer, 0, bulk);
                output.flush();
            }
            
            fileUploaded = true;
            
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return fileUploaded;
    }
}
