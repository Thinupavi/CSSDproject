/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Linus
 */
public class SetOfBooksTest {
    private static Book[] books;
    
    private SetOfBooks instance;
    
    public SetOfBooksTest() {
    }
    
    @BeforeClass
    public static void setUpBeforeClass() {
        // We include this initialisation as we expect the book array not
        // to be changed as part of any of the tests - if we intended to modify
        // it as part of the test cases then it belongs in setUp()
        books = new Book[3];
       
        books[0] = new Book(1,"Fahrenheit 451", "Ray Bradbury", 10, null);
        books[1] = new Book(2,"The Da Vinci Code", "Dan Brown", 11, null);
        books[2] = new Book(3,"Predictably Irrational", "Dan Ariely", 12, null);
    }

    @Before
    public void setUp() {
        instance = new SetOfBooks();
        for(int i = 0; i < books.length; ++i) {
            instance.addBook(books[i]);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void testFindBookByAuthor() {
         // For those interested: This test can be made more concise by using a so-called
         // "parameterised test class"
         SetOfBooks result;
         
         // Exact match
         result = instance.findBookByAuthor("Ray Bradbury");
         assertEquals(1, result.size());
         assertEquals(books[0], result.get(0));
         
         // Partial match
         result = instance.findBookByAuthor("Bradbury");
         assertEquals(1, result.size());
         assertEquals(books[0], result.get(0));

         // Mixed case
         result = instance.findBookByAuthor("BRAD");
         assertEquals(1, result.size());
         assertEquals(books[0], result.get(0));
                 
         // Multiple matches
         result = instance.findBookByAuthor("Dan");
         assertEquals(2, result.size());
         
         // Unknown author
         result = instance.findBookByAuthor("Linus");
         assertEquals(0, result.size());
        
    }
    
    @Test
    public void testFindBookByAuthor_EmptyString() {
        SetOfBooks result;
        
        // Passing an empty string - is this the correct behaviour?
        // Once you've decided the correct behaviour update the test class
        result = instance.findBookByAuthor("");
        assertEquals(0, result.size());
    }
    
    // What do you think is this the right outcome? Should it 
    // throw NullPointerException or should there be some other error
    // handling?
    @Test(expected=NullPointerException.class)
    public void testFindBookByAuthor_Null() {
        SetOfBooks result;
        result = instance.findBookByAuthor(null);
        // OR: should it rather be the case that we return an empty set?
        assertEquals(0, result.size()); // this won't be reached due to exception
    }   
   
}
