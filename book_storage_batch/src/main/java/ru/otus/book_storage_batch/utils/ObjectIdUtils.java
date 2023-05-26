package ru.otus.book_storage_batch.utils;

import org.bson.types.ObjectId;

import java.util.Date;

public final class ObjectIdUtils {

    public static String getObjectId() {
        Date now = new Date();
        ObjectId objectId = new ObjectId(now);
        return objectId.toString();
    }
}
