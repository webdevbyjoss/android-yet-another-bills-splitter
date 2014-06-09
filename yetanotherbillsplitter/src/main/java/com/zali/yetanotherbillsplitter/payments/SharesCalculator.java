package com.zali.yetanotherbillsplitter.payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * SharesCalculator - calculates payment transactions shares for team members
 *
 * @author Joseph Chereshnovsky <joseph.chereshnovsky@gmail.com>
 * @version 1.0 9/06/14
 */
public class SharesCalculator {

    /**
     * Result that holds spending map by member and total amount of money spend by the team
     */
    static public class Result {
        public Entry entry;
        public Integer total;

        public Result(Entry entry, Integer total) {
            this.entry = entry;
            this.total = total;
        }

        public int getTotal() {
            return total;
        }
    }

    /**
     * Processed combined payment log, where we have payments with different users participated
     * So first we filter given log into separate flat logs grouped by unique users combinations,
     * produce the payment tables for each log and calculate totals based on logs summary
     *
     * @param log
     * @return
     */
    static public Result processCombined(Log log) {

        // filter unique members groups into separate logs
        Map<Integer, Log> dividedLogs = SharesCalculator.splitLogs(log);

        // run through each log and calculate the total spendings map for each log
        Map<Integer, Result> results = new HashMap<>();
        for (Map.Entry<Integer, Log> entry : dividedLogs.entrySet()) {
            results.put(entry.getKey(), SharesCalculator.processFlat(entry.getValue()));
        }

        // calculate total for each member by adding results from each log
        Entry totalsByMember = new Entry();
        int total = 0;
        for (Result res: results.values()) {
            for (Map.Entry<String, Integer> entry : res.entry.entrySet()) {
                // add new member or increment its value with appropriate amount (including negative)
                if (totalsByMember.containsKey(entry.getKey())) {
                    totalsByMember.put(
                            entry.getKey(),
                            totalsByMember.get(entry.getKey()) + entry.getValue());
                } else {
                    totalsByMember.put(entry.getKey(), entry.getValue());
                }
            }
            total += res.total;
        }

        return new Result(totalsByMember, total);
    }

    /**
     * Process flat payments log, where all members participated in every payment
     *
     * @param log
     * @return
     */
    static public Result processFlat(Log log) {

        // map members to spendings
        Entry totalByMember = new Entry();

        // total amount of money spend by whole team
        int totalForTeam = 0;

        // run through the transactions log
        // and extract the required totals for team
        for(Entry spendings : log) {

            // run through each member in spending entry
            for (String member : spendings.keySet()) {

                // get the number of money spend by each team member
                if (totalByMember.get(member) == null) {
                    totalByMember.put(member, spendings.get(member));
                } else {
                    totalByMember.put(member,
                            totalByMember.get(member) + spendings.get(member));
                }

                // we need the total number of money that was spend by whole team
                totalForTeam += spendings.get(member);
            }
        }

        // calculate team member's share
        Entry restForMember = new Entry();

        Set<String> members = totalByMember.keySet();
        Integer share = totalForTeam / members.size();

        for (String member : members) {
            restForMember.put(member, totalByMember.get(member) - share);
        }

        // resulting object will contain the money each team member should put/get from the table
        // in order to complete the deal. For example {John: 20, Anny: -10, Mark: -10} means that
        // Anny and Makr should put $10 on the table and John should take $20
        return new Result(restForMember, totalForTeam);
    }

    /**
     * Splits the list of transactions into separate lists based on users who participated in
     * those transactions allowing us to work with separate flat logs where each list
     * has all users who participated in particular payments
     *
     * @param log
     * @return
     */
    static public Map<Integer, Log> splitLogs(Log log) {

        // place log entries with unique members combination into separate lists
        Map<Integer, Log> logs = new HashMap<>();

        // the List of sets where each set identified by id and
        // set of members that correspond to appropriate log record from logs array
        List<Set<String>> uniqMembersGroups = new ArrayList<>();

        // runs through all log entires and splitting them into couple log channels
        for (Entry spendings : log) {
            Set<String> currentMembers = spendings.keySet();

            Integer logIndex = getMembersGroupIndex(uniqMembersGroups, currentMembers);
            if (logIndex != null) {
                logs.get(logIndex).add(spendings);
            } else {
                uniqMembersGroups.add(currentMembers);
                logIndex = uniqMembersGroups.indexOf(currentMembers);
                if (logs.get(logIndex) != null) {
                    logs.get(logIndex).add(spendings);
                } else {
                    Log newLog = new Log();
                    newLog.add(spendings);
                    logs.put(logIndex, newLog);
                }
            }
        }

        return logs;
    }

    /**
     * Returns the index of the unique group of members
     *
     * @param uniqueMembersGroups collection of groups
     * @param membersGroup given group
     * @return
     */
    static private Integer getMembersGroupIndex(List<Set<String>> uniqueMembersGroups, Set membersGroup) {
        for (Set<String> currentMembers : uniqueMembersGroups) {
            if (currentMembers.equals(membersGroup)) {
                return uniqueMembersGroups.indexOf(currentMembers);
            }
        }
        return null;
    }
}