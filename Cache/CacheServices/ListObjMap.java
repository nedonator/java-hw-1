package Cache.CacheServices;

import java.io.*;
import java.util.HashMap;
import java.util.List;

class ListObjMap extends HashMap<List, Object> {
    static ListObjMap readFromFile(File file){
        try(ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file))){
            Object o = fin.readObject();
            if(!(o instanceof ListObjMap)){
                System.err.println("file "+file.getName()+" has wrong content");
                return new ListObjMap();
            }
            return (ListObjMap)o;
        } catch (IOException e){
            System.err.println("file "+file.getName()+" error");
        } catch(ClassNotFoundException e){
            System.err.println("file "+file.getName()+" has wrong content");
        }
        return new ListObjMap();
    }

    void writeToFile(File file){
        try(ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(file))){
            fout.writeObject(this);
        } catch(IOException e){
            System.err.println("file "+file.getName()+" error");
        }
    }
}
