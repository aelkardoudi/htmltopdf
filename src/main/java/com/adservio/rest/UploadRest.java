/**
 * 
 */
package com.adservio.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adservio.service.UploadFileService;

/**
 * @author Adservio
 *
 */
@RestController
@RequestMapping("/api")
public class UploadRest {
	
	@Autowired
	UploadFileService uploadFileService;

	@PostMapping("/uploadfile")
	 public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        String linkFile=uploadFileService.store(file);
        return linkFile;
    }
}
