package com.zali.yetanotherbillsplitter.tests;
import com.zali.yetanotherbillsplitter.payments.Entry;
import com.zali.yetanotherbillsplitter.payments.Log;
import com.zali.yetanotherbillsplitter.payments.SharesCalculator;

import junit.framework.TestCase;

public class FlatLogTest extends TestCase {

    public void testFlatLogProcessing1() {

        Log log = new Log();

        Entry p1 = new Entry();
        p1.put("Ann", 40);
        log.add(p1);

        Entry p2 = new Entry();
        p2.put("John", 700);
        log.add(p2);

        Entry p3 = new Entry();
        p3.put("Mark", 50);
        p3.put("Ann", 50);
        log.add(p3);

        Entry p4 = new Entry();
        p4.put("Alex", 450);
        log.add(p4);

        SharesCalculator.Result res = SharesCalculator.processFlat(log);
        Entry result = res.entry;
        assertEquals((int)result.get("Ann"), -232);
        assertEquals((int)result.get("John"), 378);
        assertEquals((int)result.get("Mark"), -272);
        assertEquals((int)result.get("Alex"), 128);
        assertEquals(res.getTotal(), 1290);
    }

    public void testFlatLogProcessing2() {

        Log log = new Log();

        Entry p1 = new Entry();
        p1.put("Mark", 20);
        p1.put("John", 50);
        p1.put("Ann", 0);
        log.add(p1);

        SharesCalculator.Result res = SharesCalculator.processFlat(log);
        Entry result = res.entry;
        assertEquals((int)result.get("Ann"), -23);
        assertEquals((int)result.get("John"), 27);
        assertEquals((int)result.get("Mark"), -3);
        assertEquals(result.get("Alex"), null);
        assertEquals(res.getTotal(), 70);
    }

    public void testFlatLogProcessing3() {

        Log log = new Log();

        Entry p1 = new Entry();
        p1.put("John", 30);
        p1.put("Ann", 0);
        p1.put("Mark", 0);
        log.add(p1);

        SharesCalculator.Result res = SharesCalculator.processFlat(log);
        Entry result = res.entry;
        assertEquals((int)result.get("Ann"), -10);
        assertEquals((int)result.get("John"), 20);
        assertEquals((int)result.get("Mark"), -10);
        assertEquals(result.get("Alex"), null);
        assertEquals(res.getTotal(), 30);
    }

    public void testZeroValuesRecognition() {
        Log log = new Log();

        Entry p1 = new Entry();
        p1.put("Ann", 40);
        p1.put("John", 0);
        p1.put("Mark", 0);
        p1.put("Alex", 0);
        log.add(p1);

        Entry p2 = new Entry();
        p2.put("Ann", 0);
        p2.put("John", 700);
        p2.put("Mark", 0);
        p2.put("Alex", 0);
        log.add(p2);

        Entry p3 = new Entry();
        p3.put("Ann", 50);
        p3.put("John", 0);
        p3.put("Mark", 50);
        p3.put("Alex", 0);
        log.add(p3);

        Entry p4 = new Entry();
        p4.put("Ann", 0);
        p4.put("John", 0);
        p4.put("Mark", 0);
        p4.put("Alex", 450);
        log.add(p4);

        SharesCalculator.Result res = SharesCalculator.processFlat(log);
        Entry result = res.entry;
        assertEquals((int)result.get("Ann"), -232);
        assertEquals((int)result.get("John"), 378);
        assertEquals((int)result.get("Mark"), -272);
        assertEquals((int)result.get("Alex"), 128);
        assertEquals(res.getTotal(), 1290);
    }

}