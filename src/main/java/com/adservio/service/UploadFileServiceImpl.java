/**
 * 
 */
package com.adservio.service;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;

import hello.storage.StorageException;

/**
 * @author Adservio
 *
 */
public class UploadFileServiceImpl implements UploadFileService {

	@Override
	public String store(MultipartFile file) {
		try {
			// test de surface.
            if (file.isEmpty()) {
            	return "";
            }
            String fileName=nextSessionId()+".html";
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            createPdf(fileName);
            return fileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        } catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 return "";
		}
		return null;
	}

}
