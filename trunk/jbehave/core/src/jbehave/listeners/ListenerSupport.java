/*
 * Created on 05-Aug-2004
 * 
 * (c) 2003-2004 ThoughtWorks Ltd
 *
 * See license.txt for license details
 */
package jbehave.listeners;

import jbehave.framework.Listener;
import jbehave.framework.ResponsibilityVerification;
import jbehave.framework.ResponsibilityVerifier;

/**
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North </a>
 */
public class ListenerSupport implements Listener {

    public void behaviourClassVerificationStarting(Class behaviourClass) {
    }

    public void responsibilityVerificationStarting(
            ResponsibilityVerifier verifier, Object behaviourClassInstance) {
    }

    public ResponsibilityVerification responsibilityVerificationEnding(
            ResponsibilityVerification verification,
            Object behaviourClassInstance) {
        return verification;
    }

    public void behaviourClassVerificationEnding(Class behaviourClass) {
    }

}