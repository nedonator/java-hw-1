package Cache.CacheServices;

import Cache.Cache;

import java.io.*;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Cache.CacheType.IN_MEMORY;

public class Store {
    private Map<Method, ListObjMap> mapMemory;
    private Map<Method, File> mapFile;
    private String directory;
    private Zipper zipper;

    public Store(String directory, String archiveName){
        mapMemory = new HashMap<>();
        mapFile = new HashMap<>();
        this.directory = directory;
        zipper = new Zipper(directory, archiveName);
    }

    void put(Method method, List args, Object result, Cache annotation){
        if(annotation.cacheType() == IN_MEMORY){
            ListObjMap methodMap = mapMemory.computeIfAbsent(method, k -> new ListObjMap());
            methodMap.put(args, result);
        }
        else{
            File file = mapFile.get(method);
            if(file == null){
                String pathname = directory + File.separator + (annotation.fileNamePrefix().equals("") ? method.getName() : annotation.fileNamePrefix());
                file = new File(pathname);
                try {
                    if(!file.createNewFile()) System.err.println("file " + file + " already exists; it will be rewritten");
                } catch(IOException e){
                    System.err.println("file " + file + " creation error");
                    return;
                }
                try(ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(file))) {
                    fout.writeObject(new ListObjMap());
                } catch(IOException e){
                    System.err.println("file " + file + " initialization error");
                    return;
                }
                mapFile.put(method, file);
            }
            ListObjMap map = ListObjMap.readFromFile(file);
            map.put(args, result);
            map.writeToFile(file);
            if(annotation.zip())
                zipper.zip(file);
        }
    }
    Object get(Method method, List args, Cache annotation){
        if(annotation.cacheType() == IN_MEMORY){
            ListObjMap methodMap = mapMemory.get(method);
            return methodMap == null ? null : methodMap.get(args);
        }
        else{
            File file = mapFile.get(method);
            if(annotation.zip() && file != null && !file.exists())
                zipper.unzip(file.getName());
            return file == null ? null : ListObjMap.readFromFile(file).get(args);
        }
    }
}
