package inf226.database;

import inf226.Maybe;
import inf226.Storage.Stored;

import java.io.IOException;

public class UserBase extends DataBaseUserStorage {
    @Override
    public Maybe<Stored> lookup(Object key) {
        return super.lookup(key);
    }

    @Override
    public Stored save(Object value) throws IOException {
        return super.save(value);
    }

    @Override
    public Stored refresh(Stored old) throws ObjectDeletedException, IOException {
        return super.refresh(old);
    }

    @Override
    public Stored update(Stored old, Object newValue) throws ObjectModifiedException, ObjectDeletedException, IOException {
        return super.update(old, newValue);
    }

    @Override
    public void delete(Stored old) throws ObjectModifiedException, ObjectDeletedException, IOException {
        super.delete(old);
    }
}
