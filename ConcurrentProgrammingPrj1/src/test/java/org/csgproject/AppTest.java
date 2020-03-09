package org.csgproject;

import org.csgproject.tcpClient.TcpClient;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

//    @Test
//    public void shouldAnswerWithHelloWorld() {
//        TcpClient tcpClient = new TcpClient();
//        tcpClient.establishSocketConnection("127.0.0.1", 8080);
//        String answer = tcpClient.sendInfo("helloWorld");
//        assertEquals("Hi There", answer);
//    }

    @Test
    public void clientOneRequest() throws IOException {
        TcpClient cl1 = new TcpClient();
        cl1.establishSocketConnection("127.0.0.1", 8080);
        String payload1 = cl1.beginCommunication("Good morning");

//        String payload2 = cl1.beginCommunication("How are you?");
//        String payload3 = cl1.beginCommunication("end");

        System.out.println(payload1);
//        System.out.println(payload2);
//        System.out.println(payload3);

//        assertEquals("Good morning", payload1);
//        assertEquals("How are you?", payload2);
//        assertEquals("Goodbye", payload3);
    }

    @Test
    public void clientTwoRequest() throws IOException {
        TcpClient cl2 = new TcpClient();
        cl2.establishSocketConnection("127.0.0.1", 8080);
        String payload1 = cl2.beginCommunication("Good morning");
        String payload2 = cl2.beginCommunication("How are you?");
        String payload3 = cl2.beginCommunication("end");

        assertEquals("Good morning", payload1);
        assertEquals("How are you?", payload2);
        assertEquals("Goodbye", payload3);
    }
}
