package Cache.CacheServices;

import Cache.Cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class ArgFilter {
    static List extract(Class[] target, Object[] args){
        if(target.length == 1 && target[0] == Cache.class)
            return Arrays.asList(args);
        List<Object> list = new ArrayList<>();
        int it = 0;
        for(Object o : args){
            if(it == target.length)
                break;
            if(o == null || o.getClass().equals(target[it])){
                list.add(o);
                it++;
            }
        }
        return list;
    }
}
