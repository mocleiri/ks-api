package org.kuali.student.brms.repository.rule;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * This is an <code>Item</code> test class. 
 * 
 * @author Kuali Student Team (len.kuali@googlegroups.com)
 *
 */
public class ItemTest {

    /**
     * This class implementation is used to test the 
     * <code>org.kuali.student.brms.repository.rule.AbstractItem</code> class
     * 
     * @author Kuali Student Team (len.kuali@googlegroups.com)
     *
     */
    private class ItemImpl extends AbstractItem {

        /** Class serial version uid */
        private static final long serialVersionUID = 1L;

        /**
         * Constructs a new item.
         * 
         * @param name Item name
         */
        public ItemImpl( String name ) {
            super( name );
        }

        /**
         * Constructs a new item.
         * 
         * @param uuid Item uuid
         * @param name Item name
         * @param version Item version
         */
        public ItemImpl( String uuid, String name, long version) {
            super( uuid, name, version );
        }
    }

    /**
     * Create a new Item.
     * 
     * @param name Item name
     * @return A new Item
     */
    private Item createItem( String name ) {
        return new ItemImpl( name );
    }

    /**
     * Create a new Item.
     * 
     * @param uuid Item uuid
     * @param name Item name
     * @param version Item version
     * @return A new Item
     */
    private Item createItem( String uuid, String name, long version ) {
        return new ItemImpl( uuid, name, version );
    }

    @Test
    public void testNullName() {
        try {
            createItem( null );
            fail( "Should not be able to create an item with a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuid() {
        try {
            createItem( null, "item1", -1 );
            fail( "Should not be able to create an item with a null UUID" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNullUuidAndName() {
        try {
            createItem( null, null, -1 );
            fail( "Should not be able to create an item with a null UUID and a null name" );
        }
        catch( RuntimeException e ) {
            assertTrue( true );
        }
    }

    @Test
    public void testNameEquals() {
        Item item1 = createItem( "item1" );
        Item item2 = createItem( "item1" );

        assertEquals( item1, item2 );
    }

    @Test
    public void testNameNotEquals() {
        Item item1 = createItem( "item1" );
        Item item2 = createItem( "item2" );

        assertFalse( item1.equals( item2 ) );
    }

    @Test
    public void testNameUuidEquals() {
        Item item1 = createItem( "123", "item1", 1L );
        Item item2 = createItem( "123", "item1", 1L );

        assertEquals( item1, item2 );
    }

    @Test
    public void testNameUuidNotEquals() {
        Item item1 = createItem( "123", "item1", 1L );
        Item item2 = createItem( "987", "item2", 1L );

        assertFalse( item1.equals( item2 ) );
    }

    @Test
    public void testNameUuidVersionNotEquals() {
        Item item1 = createItem( "123", "item1", 1L );
        Item item2 = createItem( "987", "item2", 2L );

        assertFalse( item1.equals( item2 ) );
    }
}
