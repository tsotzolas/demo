/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package beans;

import org.junit.Test;
import utils.EmailSender;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


/**
 * <p>
 * {@link EmailBean} contains all the business logic for the application, and also serves as the controller for the JSF view.
 * </p>
 * <p>
 * It contains address, subject, and content for the <code>email</code> to be sent.
 * </p>
 * <p>
 * The {@link #send()} method provides the business logic to send the email
 * </p>
 *
 * @author Joel Tosi
 *
 */

@Named("EmailBean")
@SessionScoped
public class EmailBean implements Serializable {

    private static final long serialVersionUID = 1544680932114626710L;

    /**
     * Resource for sending the email. The mail subsystem is defined in either standalone.xml or domain.xml in your respective
     * configuration directory.
     */



    private String to;

    private String from;

    private String subject;

    private String body;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    /**
     * Method to send the email based upon values entered in the JSF view. Exception should be handled in a production usage but
     * is not handled in this example.
     *
     * @throws Exception
     */
    public void send() throws Exception {

//        String[] attachments = { "/home/tsotzo/Documents/mozilla.pdf", "/home/tsotzo/Documents/document.pdf"};
//        EmailSender.send(subject,body,to,null,null,attachments);

        EmailSender.sendmail(subject,body,to,null,null,null);

    }


    @Test
    public void testMe(){

        subject = "test";
        body = "testssssss ssss ssssss ssss";
        to= "tsotzolas@gmail.com";
        EmailSender.sendmail(subject,body,to,null,null,null);
    }
}