package $

import main.java.MyApp;

{package};

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;
import static org.junit.Assert.assertArrayEquals;

public class MyAppTest {
    private MyApp myApp;

    @Before
    public void setup() {
    }


    /**
     * TODO Dummy sample test
     */
    @Test
    public void testHello() {
        assertTrue(myApp.printHello().equals("Hello"));
    }

}