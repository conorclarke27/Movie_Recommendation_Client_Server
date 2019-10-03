/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOs;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author conor
 * @param <K>
 * @param <V>
 */
public class LocalCache<K,V>
{
    
    private Map<K,List<V>> map = Collections.synchronizedMap(new HashMap<>());

    /**
     *
     * @param key
     * @return
     */
    public synchronized boolean checkCache(K key)
    {
        return map.containsKey(key);
    }
    
    /**
     *
     * @param key
     * @return
     */
    public synchronized List<V> getValues(K key)
    {
        return map.get(key);
    }

    /**
     *
     */
    public synchronized void clearCache()
    {
        map.clear();
    }

    /**
     *
     * @param key
     * @param values
     */
    public synchronized void putInCache(K key, List<V> values)
    {
        map.put(key, values);
    }

}
