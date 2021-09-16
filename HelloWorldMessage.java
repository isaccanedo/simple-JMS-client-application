 
/*
 * @(#)HelloWorldMessage.java  1.8 05/03/09
 * 
 * Copyright (c) 2000-2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */


================================================================================
@(#)README  1.7 03/22/05
================================================================================

HelloWorldMessage example

Description
-----------
This example is a simple JMS client application. It is the companion
example to the "Quick Start Tutorial" in the Sun Java(tm) System Message 
Queue Developer's Guide. This application sends and receives a "Hello World" 
message via a Queue destination. This example does not use JNDI to
lookup administered objects.

Files
-----
HelloWorldMessage.java  Source file for this example.
*.class      Prebuilt Java class files for this example.
README      This file.

Configuring the environment
---------------------------
To recompile or run this example, you need to set CLASSPATH
to include at least:
    jms.jar
    imq.jar
    directory containing this example

A detailed guideline on setting CLASSPATH is found in the README
file in the jms demo subdirectory as well as in the "Quick Start
Tutorial" in the Sun Java(tm) System Message Queue Developer's Guide.

The following are examples for setting CLASSPATH on the different
platforms. These commands are run from the directory containing
this example.

On Solaris:
    setenv CLASSPATH /usr/share/lib/jms.jar:/usr/share/lib/imq.jar:.

On Windows:
    set CLASSPATH=%IMQ_HOME%\lib\jms.jar;%IMQ_HOME%\lib\imq.jar;.

On Linux:
    setenv CLASSPATH /opt/sun/mq/share/lib/jms.jar:
  /opt/sun/mq/share/lib/imq.jar:.

#####hpux-dev#####
On HP-UX:
   export CLASSPATH=/opt/sun/mq/share/lib/jms.jar:
                /opt/sun/mq/share/lib/imq.jar:.
Note that it is assumed that the above export command is run on 
BASH shell


Building the example
--------------------
Run the following:

    javac HelloWorldMessage.java

Running the example
-------------------
Run the following:

    java HelloWorldMessage

You should see the following output:

    Sending Message: Hello World
    Read Message: Hello World



/*
 * @(#)HelloWorldMessage.java  1.8 05/03/09
 * 
 * Copyright (c) 2000-2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Sun grants you ("Licensee") a non-exclusive, royalty free, license to use,
 * modify and redistribute this software in source and binary code form,
 * provided that i) this copyright notice and license appear on all copies of
 * the software; and ii) Licensee does not utilize the software in a manner
 * which is disparaging to Sun.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING ANY
 * IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 * NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE
 * LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THE SOFTWARE OR ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS
 * LICENSORS BE LIABLE FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT,
 * INDIRECT, SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF
 * OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * This software is not designed or intended for use in on-line control of
 * aircraft, air traffic, aircraft navigation or aircraft communications; or in
 * the design, construction, operation or maintenance of any nuclear
 * facility. Licensee represents and warrants that it will not use or
 * redistribute the Software for such purposes.
 */


/**
 * The HelloWorldMessage class consists only of a main method, which sends 
 * a message to a queue and then receives the message from the queue.
 * <p>
 * This example is used in the "Quick Start Tutorial" of the Sun Java(tm) 
 * System Message Queue Developer's Guide to illustrate a very simple JMS 
 * client.
 * The line comments associate the lines of code with the steps in the tutorial.
 *
 * @version 1.8 05/03/09
 */

//Step 1:
//Import the JMS API classes.
import javax.jms.ConnectionFactory;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageProducer;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
//Import the classes to use JNDI.
import javax.naming.*;
import java.util.*;

public class HelloWorldMessage {

    /**
     * Main method.
     *
     * @param args  not used
     *
     */
    public static void main(String[] args) {

        try {

            ConnectionFactory myConnFactory;
            Queue myQueue;

            /*
             * The following code uses the JNDI File System Service Provider
             * to lookup() Administered Objects that were stored in the
             * Administration Console Tutorial in the Administrator's Guide
             *
             * The following code (in this comment block replaces the
             * statements in Steps 2 and 5 of this example.
             *
             ****
                String MYCF_LOOKUP_NAME = "MyConnectionFactory";
                String MYQUEUE_LOOKUP_NAME = "MyQueue";

                Hashtable env;
                Context ctx = null;

                env = new Hashtable();

                // Store the environment variable that tell JNDI which initial context
                // to use and where to find the provider.

                // For use with the File System JNDI Service Provider
                env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
                // On Unix, use file:///tmp instead of file:///C:/Temp
                env.put(Context.PROVIDER_URL, "file:///C:/Temp");
 
                // Create the initial context.
                ctx = new InitialContext(env);

                // Lookup my connection factory from the admin object store.
                // The name used here here must match the lookup name
                // used when the admin object was stored.
                myConnFactory = (javax.jms.ConnectionFactory) ctx.lookup(MYCF_LOOKUP_NAME);
      
                // Lookup my queue from the admin object store.
                // The name I search for here must match the lookup name used when
                // the admin object was stored.
                myQueue = (javax.jms.Queue)ctx.lookup(MYQUEUE_LOOKUP_NAME);
            ****
            *
            */

            //Step 2:
            //Instantiate a Sun Java(tm) System Message Queue ConnectionFactory 
      //administered object.
            //This statement can be eliminated if the JNDI code above is used.
            myConnFactory = new com.sun.messaging.ConnectionFactory();


            //Step 3:
            //Create a connection to the Sun Java(tm) System Message Queue Message 
      //Service.
            Connection myConn = myConnFactory.createConnection();


            //Step 4:
            //Create a session within the connection.
            Session mySess = myConn.createSession(false, Session.AUTO_ACKNOWLEDGE);


            //Step 5:
            //Instantiate a Sun Java(tm) System Message Queue Destination 
      //administered object.
            //This statement can be eliminated if the JNDI code above is used.
            myQueue = new com.sun.messaging.Queue("world");


            //Step 6:
            //Create a message producer.
            MessageProducer myMsgProducer = mySess.createProducer(myQueue);


            //Step 7:
            //Create and send a message to the queue.
            TextMessage myTextMsg = mySess.createTextMessage();
            myTextMsg.setText("Hello World");
            System.out.println("Sending Message: " + myTextMsg.getText());
            myMsgProducer.send(myTextMsg);


            //Step 8:
            //Create a message consumer.
            MessageConsumer myMsgConsumer = mySess.createConsumer(myQueue);


            //Step 9:
            //Start the Connection created in step 3.
            myConn.start();


            //Step 10:
            //Receive a message from the queue.
            Message msg = myMsgConsumer.receive();


            //Step 11:
            //Retreive the contents of the message.
            if (msg instanceof TextMessage) {
                TextMessage txtMsg = (TextMessage) msg;
                System.out.println("Read Message: " + txtMsg.getText());
            }

     
            //Step 12:
            //Close the session and connection resources.
            mySess.close();
            myConn.close();

        } catch (Exception jmse) {
            System.out.println("Exception occurred : " + jmse.toString());
            jmse.printStackTrace();
        }
    }
}
