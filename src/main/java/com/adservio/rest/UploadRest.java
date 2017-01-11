/**
 * 
 */
package com.adservio.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Adservio
 *
 */
@RestController
@RequestMapping("/api")
public class UploadRest {

	@PostMapping("/uploadfile")
	 public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        String linkFile=uploadFileService.store(file);

        return "redirect:"+linkFile;
    }
}
