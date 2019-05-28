import Cache.CacheProxy;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception{
        Service service = new SimpleService();
        CacheProxy proxy = new CacheProxy("C:\\магия", "winrar.rar");
        Service newService = proxy.cache(service);
        for (int i = 0; i < 30; i++) {
            List result = newService.run("london", i, new Date());
            System.out.println(result);
        }
    }
}
