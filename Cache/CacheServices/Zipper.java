package Cache.CacheServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {
    private File archive;
    private String directory;
    public Zipper(String directory, String archiveName){
        this.directory = directory;
        archive = new File(directory + File.separator + archiveName);
        try {
            if(!archive.createNewFile()) System.err.println("archive " + archive + " already exists");
        } catch(IOException e){
            System.err.println("failed to create an archive");
        }
    }
    public void zip(File file){
        try(ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(archive)); FileInputStream fis= new FileInputStream(file)){
            zout.putNextEntry(new ZipEntry(file.getName()));
            int c;
            while((c = fis.read()) != -1){
                zout.write(c);
            }
            zout.closeEntry();
        } catch(IOException e){
            System.err.println("archive writing error");
        }
        if(!file.delete()) System.err.println("file deleting error");
    }
    public File unzip(String fileName) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(archive))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName(); // получим название файла
                if(name.equals(fileName)) {
                    File file = new File(directory + File.separator + name);
                    if(!file.createNewFile()) System.err.println("file " + file + " already exists");
                    FileOutputStream fout = new FileOutputStream(file);
                    for (int c = zin.read(); c != -1; c = zin.read()) {
                        fout.write(c);
                    }
                    fout.flush();
                    zin.closeEntry();
                    fout.close();
                    return file;
                }
            }
        } catch (IOException e) {
            System.err.println("archive reading error");
        }
        return null;
    }
}
