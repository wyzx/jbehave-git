/*
 * Created on 25-Jan-2004
 * 
 * (c) 2004 Dan North
 * 
 * See license.txt for licence details
 */
package jbehave.verify;

import java.io.OutputStreamWriter;

import jbehave.verify.listener.TextListener;
import jbehave.verify.listener.TraceListener;

/**
 * Basic command-line behaviour runner
 * 
 * @author <a href="mailto:dan.north@thoughtworks.com">Dan North</a>
 */
public class TextVerifier {

    public static void main(String[] args) {
        Verifier verifier = new Verifier();
        verifier.registerListener(new TextListener(new OutputStreamWriter(System.out)));
        verifier.registerListener(new TraceListener());
        for (int i = 0; i < args.length; i++) {
            try {
                verifier.addSpec(Class.forName(args[i]));
            }
            catch (ClassNotFoundException e) {
                System.err.println("Unknown spec class: " + args[i]);
            }
        }
        verifier.verifyCriteria();
    }
}