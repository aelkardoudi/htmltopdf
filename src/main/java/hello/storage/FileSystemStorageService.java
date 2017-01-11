package hello.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private SecureRandom random = new SecureRandom();

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
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
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public byte[] loadAsResource(String filename) {
        try {
            Path file = load(filename);
            byte[] resource = Files.readAllBytes(file);
            if(resource.length!=0 ) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);
            }
        } catch (IOException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    
    /**
     * Creates a PDF with the words "Hello World"
     * @param file
     * @throws IOException
     * @throws DocumentException
     */
    public void createPdf(String file) throws IOException, DocumentException {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("./upload-dir/"+file+".pdf"));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream("./upload-dir/"+file));
        // step 5
        document.close();
    }
    
    public String nextSessionId() {
        return new BigInteger(130, random).toString(32);
      }
}
