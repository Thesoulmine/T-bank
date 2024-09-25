package ru.tbank.restful.repository;

import ru.tbank.restful.annotation.Id;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

public abstract class RepositoryWithId<T> implements Repository<T>  {

    protected final AtomicLong idCounter = new AtomicLong(1);
    protected Field idField;

    protected synchronized void initializeIdField(T entity) {
        if (idField == null) {
            int idFieldsCount = 0;

            for (Field field : entity.getClass().getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    idFieldsCount++;
                    idField = field;
                }
            }

            if (idFieldsCount != 1) {
                throw new RuntimeException("The entity class must contain only one Long field with @Id annotation");
            }
        }
    }

    protected T setIdFieldValueInEntity(T entity, Long id) {
        try {
            Field entityIdField = entity.getClass().getDeclaredField(idField.getName());
            entityIdField.setAccessible(true);
            entityIdField.set(entity, id);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        return entity;
    }
}
