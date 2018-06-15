package net.orfdev.integration.test.exception;

import java.io.IOException;

/**
 * Created by cai on 1/27/17.
 */
public class CoverException extends RuntimeException {

    public CoverException(String s, IOException ex) {
    }

    public CoverException(String s) {
        super(s);
    }
}
