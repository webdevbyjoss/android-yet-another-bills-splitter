package com.zali.yetanotherbillsplitter.tests;

import com.zali.yetanotherbillsplitter.payments.Entry;
import com.zali.yetanotherbillsplitter.payments.Log;
import com.zali.yetanotherbillsplitter.payments.SharesCalculator;

import junit.framework.TestCase;
import java.util.Map;

public class CombinedLogTest extends TestCase {

    public void testLogSplitting() {
        Log log = new Log();

        // Group 1
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

        // Group 2
        Entry p21 = new Entry();
        p21.put("Mark", 20);
        p21.put("John", 50);
        p21.put("Ann", 0);
        log.add(p21);

        // Group 3
        Entry p31 = new Entry();
        p31.put("John", 30);
        p31.put("Ann", 0);
        p31.put("Mark", 0);
        log.add(p31);


        Map<Integer, Log> dividedLogs = SharesCalculator.splitLogs(log);
        assertEquals(dividedLogs.size(), 2);

        SharesCalculator.Result res = SharesCalculator.processCombined(log);
        Entry result = res.entry;
        assertEquals((int)result.get("Ann"), -265);
        assertEquals((int)result.get("John"), 424);
        assertEquals((int)result.get("Mark"), -285);
        assertEquals((int)result.get("Alex"), 128);
        assertEquals(res.getTotal(), 1290);
    }

}
