package net.ml.datatools.utils;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import lombok.extern.slf4j.Slf4j;
import net.ml.data.utils.ISBNUtil;


@Slf4j
public class ISBNUtilTest {
    
    @Test
    public void testGetISBNAsLong1() {
        String isbn1 = "0771074670";
        long exp1 = 7710746700l;
        long l1 = ISBNUtil.getISBNAsLong(isbn1);
        assertEquals(exp1, l1);
    }
    
    @Test
    public void testGetISBNAsLong2() {
        String isbn1 = "077107467X";
        long exp1 = 7710746710l;
        long l1 = ISBNUtil.getISBNAsLong(isbn1);
        assertEquals(exp1, l1);
    }

    @Test
    public void testGetISBN() {
        String isbn1 = "077107467X";
        long exp1 = 7710746710l;
        String s1 = ISBNUtil.getISBN(exp1);
        assertEquals(isbn1, s1);
    }

    
}
