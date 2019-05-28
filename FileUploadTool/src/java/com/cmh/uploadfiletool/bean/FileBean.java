
package com.cmh.uploadfiletool.bean;

import com.cmh.uploadfiletool.dao.FilesDbDAO;
import com.cmh.uploadfiletool.util.FileSystemHelper;
import com.cmh.uploadfiletool.vo.FileVO;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author chanson
 */
public class FileBean {
    
    private UploadedFile uploadedFile;
    private String provider;
    private FilesDbDAO dao = new FilesDbDAO();
    private FileSystemHelper fileSys = new FileSystemHelper();

    /**
     * Creates a new instance of FileBean
     */
    public FileBean() {
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        UploadedFile file = event.getFile();
        FileVO fvo = new FileVO();
        
        fvo.setName(file.getFileName());
        fvo.setSize((int) file.getSize());
        fvo.setType(file.getContentType());
        
        switch (provider) {
            case "provider1":
                // upload to database
                if (dao.insertFile(fvo)) {
                    FacesMessage message = new FacesMessage("Success!", fvo.getName() + " uploaded to Database.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    FacesMessage message = new FacesMessage("Error", "Unable to upload to database.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            case "provider2":
                // upload to file system
                if (fileSys.uploadFileToSystem(file)) {
                    FacesMessage message = new FacesMessage("Success!", fvo.getName() + " uploaded to C://Documents");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                } else {
                    FacesMessage message = new FacesMessage("Error", "Unable to upload to file system.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                }
            case "provider3":
                // show in console
                System.out.println("File Uploaded: \n"
                        + "Name: " + fvo.getName() + "\n"
                        + "Size: " + fvo.getSize() + "\n"
                        + "Type: " + fvo.getType());
            default:
                FacesMessage message = new FacesMessage("Error", "Please try again.");
                FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }
    
    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    
    
}
