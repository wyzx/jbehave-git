/*
 * Created on 05-Jul-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.framework.exception;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class PendingException extends VerificationException {

    public PendingException(String message) {
        super(message);
    }
    public PendingException(String message, Object expected, Object actual) {
        super(message, expected, actual);
    }
}