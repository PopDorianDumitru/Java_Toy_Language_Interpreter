package model.types;

import model.values.Value;

public interface Type {
    Type deepCopy();
    Value defaultValue();
}
