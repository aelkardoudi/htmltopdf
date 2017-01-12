/**
 * 
 */
package com.adservio.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author Adservio
 *
 */
public class UtilTool {

	private static final String LOCATION = "target/classes/static/files";
	private static SecureRandom random = new SecureRandom();
	
	public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }
	
	public static String getBasePath() {
        return "./"+LOCATION+"/";
    }
	
	public static String getLocation() {
        return LOCATION;
    }
	
	/**
     * Creates a PDF 
     * @param htmlNameFile
     * @param pdfNameFile
     * @throws IOException
     * @throws DocumentException
     */
	public static void createPdf(InputStream htmlFile,String pdfNameFile) throws IOException, DocumentException {
		// step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfNameFile));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
               htmlFile);
        // step 5
        document.close();
	}
	
	
}
