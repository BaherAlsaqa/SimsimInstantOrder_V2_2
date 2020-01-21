package net.phpsm.simsim.simsiminstantorder;

import java.io.IOException;

/**
 * Created by baher on 21/01/2018.
 */

@Deprecated
public class WrappedIOException {
    public static IOException wrap(final Throwable e) {
        return wrap(e.getMessage(), e);
    }

    public static IOException wrap(final String message, final Throwable e) {
        final IOException wrappedException = new IOException(message + " [" +
                e.getMessage() + "]");
        wrappedException.setStackTrace(e.getStackTrace());
        wrappedException.initCause(e);
        return wrappedException;
    }
}
