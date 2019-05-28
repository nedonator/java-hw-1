package Cache;
import Cache.CacheServices.ProxyAdapter;
import Cache.CacheServices.Store;

public class CacheProxy {
    private Store store;

    public CacheProxy(String directory, String archiveName){
        store = new Store(directory, archiveName);
    }

    public<T> T cache(T obj){
        return new ProxyAdapter<T>(obj, store).getProxy();
    }
}
