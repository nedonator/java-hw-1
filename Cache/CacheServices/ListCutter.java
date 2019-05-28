package Cache.CacheServices;

import java.util.List;

class ListCutter {
    static Object cut(Object o, int max_size){
        if(o instanceof List){
            List l = (List)o;
            while(l.size() > max_size)
                l.remove(l.size() - 1);
        }
        return o;
    }
}
