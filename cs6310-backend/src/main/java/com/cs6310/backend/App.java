package com.cs6310.backend;

import org.apache.log4j.Logger;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Logger LOGGER = Logger.getLogger(App.class);
    	LOGGER.info("This is an info log");
        System.out.println( "Hello World!" );
    }
}
