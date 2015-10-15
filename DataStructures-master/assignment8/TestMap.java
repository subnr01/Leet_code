/*Emily Wagner ewagne14@jhu.edu
Jose Nino Rivera jnino1@jhu.edu
600.226 Data Structures
Assignment 8: Hashing Out Spells
4/11/14*/

import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;

import java.util.Iterator;

import org.junit.rules.ExpectedException;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
 
@RunWith(Theories.class)
public class TestMap {

    private static final int TEST_SIZE1 = 10;
    private static final int TEST_SIZE2 = 5;
    private static final String TEST_VAL1 = "Peter";
    private static final String TEST_VAL2 = "Paul";

    private interface Fixture {
        Map<Integer,String> init();
    }
    

    @DataPoint
    public static final Fixture OpenAddressingHashMap = new Fixture() {
        public OpenAddressingHashMap<Integer,String> init() {
            return new OpenAddressingHashMap<>();
        }
    }; 

    @DataPoint
    public static final Fixture ChainingHashMap = new Fixture() {
        public ChainingHashMap<Integer,String> init() {
            return new ChainingHashMap<>();
        }
    };

    @Theory
    public void getOfInsertIsInsertedValue(Fixture fix) {
        Map<Integer,String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
            assertEquals(map.get(i), TEST_VAL1);
        }
    }

    @Theory
    public void testGetOfPut(Fixture fix) {
        Map<Integer,String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
        }
        for (int i = 0; i < TEST_SIZE1; i++) {
            if (i % 2 == 1) {
                map.put(i, TEST_VAL2);
                assertEquals(map.get(i), TEST_VAL2);
            }
            else {
                assertEquals(map.get(i), TEST_VAL1);
            }
        }
    }

    @Theory
    public void testGetOfInsert(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i ++) {
             if (i % 2 == 1) {
                map.insert(i, TEST_VAL2);
                assertEquals(map.get(i), TEST_VAL2);
            }
            else {
                map.insert(i, TEST_VAL1);
                assertEquals(map.get(i), TEST_VAL1);
            }
        }
    }
    
    @Theory
    public void testHasOfInsert(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
            assertTrue(map.has(i));
        }
        for (int i = TEST_SIZE1; i < 2*TEST_SIZE1; i++) {
            assertFalse(map.has(i));
        }
        for (int i = -1; i > -TEST_SIZE1; i--) {
            assertFalse(map.has(i));
        }
    }

    @Theory
    public void testHasOfRemoveIsFalse(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            assertFalse(map.has(i));
            map.insert(i, TEST_VAL1);
            assertTrue(map.has(i));
            map.remove(i);
            assertFalse(map.has(i));
        }
    }

    @Theory
    public void testSizeOfInsert(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
        }
        assertEquals(map.size(), TEST_SIZE1);
    }

    @Theory
    public void testSizeOfInsertRemove(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
        }
        for (int i = 0; i < TEST_SIZE2; i++) {
            map.remove(i);
        }
        assertEquals(map.size(), TEST_SIZE1 - TEST_SIZE2);
    }

    @Theory
    public void testSizeUnchangedByHasAndPut(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
            map.has(i);
            map.put(i, TEST_VAL2);
        }

        assertEquals(map.size(), TEST_SIZE1);
    }

    @Theory
    public void testIterator(Fixture fix) {
        Map<Integer, String> map = fix.init();
        int j = 0;
        int k;
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i,TEST_VAL1);
        }
        Iterator<Integer> iter = map.iterator();
        while (iter.hasNext()) {
            k = iter.next();
            assertEquals(k, j);
            j++;
        }
    }

    // Exceptions
    @Theory @Test(expected = UnsupportedOperationException.class)
    public void IteratorRemoveOperationNotSupported(Fixture fix) {
        Map<Integer, String> map = fix.init();
        for (int i = 0; i < TEST_SIZE1; i++) {
            map.insert(i, TEST_VAL1);
        }
        Iterator<Integer> iter = map.iterator();
        iter.remove();
    }

    @Theory @Test(expected = IllegalArgumentException.class)
	public void putTriggersExceptions(Fixture fix) {
        Map<Integer,String> map = fix.init();
        map.put(null, "String");
	}
    @Theory @Test(expected = IllegalArgumentException.class)
	public void putTriggersExceptions2(Fixture fix) {
        Map<Integer,String> map = fix.init();
        map.put(4, "String");
	}
    @Theory @Test(expected = IllegalArgumentException.class)
	public void putTriggersExceptions3(Fixture fix) {
        Map<Integer,String> map = fix.init();
        for (int i = 0; i < 6; i++) {
            map.insert(i, TEST_VAL1);
        }
        map.put(9, "String");
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void getTriggersExceptions(Fixture fix) {
        Map<Integer,String> map = fix.init();
        map.get(null);
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void getTriggersExceptions2(Fixture fix) {
        Map<Integer,String> map = fix.init();
        map.get(4);
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void getTriggersExceptions3(Fixture fix) {
        Map<Integer,String> map = fix.init();
        for (int i = 0; i < 6; i++) {
            map.insert(i, TEST_VAL1);
        }
        map.put(9, "String");
    }

    @Theory @Test(expected = IllegalArgumentException.class)
	public void removeTriggersExceptions(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.remove(null);
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void removeTriggersExceptions2(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.remove(4);
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
    public void removeTriggersExceptions3(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.insert(4, "String");
        map.insert(5, "String");
        map.insert(7, "String");
        map.insert(2, "String");
        map.insert(1, "String");
        map.insert(3, "String");
        map.insert(6, "String");
        
        map.remove(9);
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)   
	public void insertTriggersExceptions(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.insert(null, "String");
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void insertTriggersExceptions2(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.insert(4, "String");
        map.insert(4, "String2");
	}
    
    @Theory @Test(expected = IllegalArgumentException.class)
	public void insertTriggersExceptions3(Fixture fix) {
        Map<Integer,String> map = fix.init();
        
        map.insert(5, "String");
        map.insert(7, "String");
        map.insert(2, "String");
        map.insert(1, "String");
        map.insert(3, "String");
        map.insert(6, "String");
        map.insert(5, "String2");
	} 
}

