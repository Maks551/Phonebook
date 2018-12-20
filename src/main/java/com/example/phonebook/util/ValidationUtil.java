package com.example.phonebook.util;

import com.example.phonebook.HasId;
import com.example.phonebook.util.exception.IllegalRequestDataException;
import com.example.phonebook.util.exception.NotFoundException;

public class ValidationUtil {
    // Only English characters
    public static final String LOGIN_PATTERN = "([A-Za-z0-9]+)?";
    //+380(98)99-33-109     +3809899-33-109     809899-33-109   098-99-33-109
    public static final String MOBILE_PHONE_NUMBER_PATTERN =
            "^((([+][3]|[3])?[8])?([0])([ -(])?([0-9]){2}([ -)])?([0-9]){2}([ -])?" +
            "([0-9]){2}([ -])?([0-9]){3})?$";
    //035 99 109 11     035-99-109-11   99-109-11
    public static final String HOME_PHONE_NUMBER_PATTERN =
            "^((([0])?([0-9]){2,4}([ -])?)?([0-9]){2}([ -])?([0-9]){3}([ -])?([0-9]){2})?$";

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException(msg);
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }
}
