/**
 * 
 */
package com.adservio.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Adservio
 *
 */
public interface UploadFileService {

	String store(MultipartFile file);
}
