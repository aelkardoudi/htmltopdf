/**
 * 
 */
package com.adservio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adservio.storage.FileStorage;
import com.itextpdf.text.DocumentException;

/**
 * @author Adservio
 *
 */
@Service
public class UploadFileServiceImpl implements UploadFileService {
	
	@Autowired
	FileStorage fileStorage;

	@Override
	public String store(MultipartFile file) {
		try {
			String fileName=fileStorage.store(file);
            return fileName;
        } catch (DocumentException e) {
			e.printStackTrace();
			 return "";
		}
	}

}
