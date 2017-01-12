/**
 * 
 */
package com.adservio.storage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.adservio.util.UtilTool;
import com.itextpdf.text.DocumentException;


/**
 * @author Adservio
 *
 */
@Service
public class FileStorageImpl implements FileStorage {
	
	private final Path rootLocation;
    
    public FileStorageImpl() {
        this.rootLocation = Paths.get(UtilTool.getLocation());
    }


	@Override
	public String store(MultipartFile file) throws DocumentException {
		try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            String fileName=UtilTool.nextSessionId()+".html";
            String pdfFileName=UtilTool.nextSessionId()+".pdf";
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
            UtilTool.createPdf( new FileInputStream(UtilTool.getBasePath()+fileName),UtilTool.getBasePath()+pdfFileName);
            return pdfFileName;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        } 
	}


	@Override
	public void init() {
		try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
	}


	@Override
	public void deleteAll() {
		 FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

}
