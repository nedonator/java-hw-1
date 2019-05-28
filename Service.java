import Cache.Cache;

import java.util.Date;
import java.util.List;
import static Cache.CacheType.FILE;
import static Cache.CacheType.IN_MEMORY;

public interface Service {
    @Cache(cacheType = FILE, fileNamePrefix = "data", zip = true, identityBy
            = {String.class}, listList = 1)

    List<String> run(String item, double value, Date date);
    @Cache(cacheType = IN_MEMORY, listList = 100_000)
    List<String> work(String item);
}
