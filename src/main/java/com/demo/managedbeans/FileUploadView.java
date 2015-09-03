package com.demo.managedbeans;

import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import com.demo.tika.utils.TikaUtils;

@SessionScoped
@ManagedBean
public class FileUploadView implements Serializable {

	private static final long serialVersionUID = 6521166288783551734L;
	
	private UploadedFile file;
	private String fileType;
	private String fileContents;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload() {
		if (file != null) {
			FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext ec = context.getExternalContext();

			try (InputStream input = file.getInputstream()) {
				java.util.Date date = new java.util.Date();
				String fileExtName = new Timestamp(date.getTime()).toString();
				fileExtName = fileExtName.replaceAll(":", "");
				fileExtName = fileExtName.replaceAll(".", "");
				fileExtName = fileExtName.replaceAll("-", "");

				Path newFilePath = Paths.get(fileExtName + file.getFileName());
				Files.copy(input, newFilePath, StandardCopyOption.REPLACE_EXISTING);

				setFileType(TikaUtils.detectFileType(newFilePath.toFile()));
				System.out.println(getFileType());

				setFileContents(TikaUtils.getFileContents(fileType, newFilePath.toFile()));
				System.out.println(getFileContents());
				//context.getResponseWriter().write("FileType: " + getFileType() + "\n File Contents:\n" + getFileContents());
				 ec.redirect("fileContents.xhtml");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getFileContents() {
		return fileContents;
	}

	public void setFileContents(String fileContents) {
		this.fileContents = fileContents;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}