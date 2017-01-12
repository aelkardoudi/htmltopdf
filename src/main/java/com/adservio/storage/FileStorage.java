/**
 * 
 */
package com.adservio.storage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.DocumentException;

/**
 * @author Adservio
 *
 */
public interface FileStorage {
	
	String store(MultipartFile file) throws DocumentException;
    
    void init ();
    
    void deleteAll();

}
