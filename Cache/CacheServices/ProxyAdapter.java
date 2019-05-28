package Cache.CacheServices;

import Cache.Cache;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyAdapter<T> {
    private final T proxy;

    @SuppressWarnings("unchecked")
    public ProxyAdapter(Object obj, Store store) {
        proxy = (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), (proxy, method, args) -> {
            Cache methodCache = method.getAnnotation(Cache.class);
            Object result = null;
            List filteredArgs = null;
            if(methodCache != null) {
                filteredArgs = ArgFilter.extract(methodCache.identityBy(), args);
                result = store.get(method, filteredArgs, methodCache);
                if (result != null)
                    return result;
            }
            //Invoke the method found and return the result
            try {
                result = method.invoke(obj, args);
                if(methodCache != null) {
                    result = ListCutter.cut(result, methodCache.listList());
                    store.put(method, filteredArgs, result, methodCache);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                System.out.println("method running error: "+e.getMessage());
            }
            return result;
        });
    }

    /**
     * @return proxy instance implementing T.
     */
    public T getProxy() {
        return proxy;
    }
}
