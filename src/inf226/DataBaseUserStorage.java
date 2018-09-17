package inf226;

import inf226.Storage.KeyedStorage;
import inf226.Storage.Stored;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Hashtable;

public class DataBaseUserStorage<K, C> implements KeyedStorage<K, C>
{
    private HashMap<K, C> storage = new HashMap<>();

    public DataBaseUserStorage()
    {
        System.out.println("Hello");
    }

    @Override
    public Maybe<Stored<C>> lookup(K key)
    {
        return null;
    }

    @Override
    public Stored<C> save(C value) throws IOException
    {
        return null;
    }

    @Override
    public Stored<C> refresh(Stored<C> old) throws ObjectDeletedException, IOException
    {
        return null;
    }

    @Override
    public Stored<C> update(Stored<C> old, C newValue) throws ObjectModifiedException, ObjectDeletedException, IOException
    {
        return null;
    }

    @Override
    public void delete(Stored<C> old) throws ObjectModifiedException, ObjectDeletedException, IOException
    {

    }
}

