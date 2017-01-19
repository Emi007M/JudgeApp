/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serializable.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import judgeApp.model.Dictionary;

/**
 *
 * @author Emilia
 */
public class Chart implements Serializable {

    private static final long serialVersionUID = 2989628431129900660L;

    Node winner;
    LinkedList<Node> matches;

    //TODO
    int matchesTotal;
    int matchesPlayed = 0;

    public Chart(ArrayList<Person> _a, boolean isTwoThirdPlaces) {
        //seed preranked players and put at front
        ArrayList<Person> preranked = new ArrayList<>();
        ArrayList<Person> a = new ArrayList<>(_a);

        System.out.println(a.size());

        for (Iterator<Person> it = a.iterator(); it.hasNext();) {
            Person p = it.next();
            if (p.getRank() != 0) {
                preranked.add(p);
                it.remove();
            }
        }
        //set preranked in order
        Collections.sort(preranked, (o1, o2) -> {
            return o1.getRank().compareTo(o2.getRank());
        });

        ArrayList<Person> all = new ArrayList<>(preranked);
        all.addAll(a);

        System.out.println(a.size());
        System.out.println(all.size());

        //create nodes with athletes as a primary structure for matches
        ArrayList<Node> athletes = new ArrayList<>();

        int amount = all.size();
        for (int i = 0; i < amount; i++) {
            athletes.add(new Node(all.get(i)));
        }

        //mix list      
        randomizeList(athletes, preranked.size());

        //list should be already mixed with priviliged athletes on top positions
        //now generate matches
        if (preranked.size() >= 4 && isTwoThirdPlaces) {
            InitializeMatches(athletes, preranked.size(), true);
        } else {
            InitializeMatches(athletes, preranked.size(), false);
        }
    }

    public void addPlayerToLockedChart(Person p) {
        //TODO
        //only do if not started
    }

    public void removePlayerFromLockedChart(Person p) {
        //TODO
        //only do if not started
    }

    public int getTotalMatchesAmount() {
        return matchesTotal;
    }

    public int getPlayedMatchesAmount() {
        return matchesPlayed;
    }

    private void InitializeMatches(ArrayList<Node> athletes, int preranked, boolean twoThirdPlaces) {
        double log = log(athletes.size(), 2);
        int height = (int) Math.ceil(log); // deepest lvl of the tree (2^height >= athletes' amount)
        System.out.println(athletes.size());
        System.out.println(log);
        System.out.println(height);

        int firstDepthMatches = (int) Math.pow(2, (height - 1)); //for 5-8 athletes there are 4 matches in a first row
        int roundedAthletes = (int) Math.pow(2, (height));    //for 9-16 athletes rounded value is 16

        //prepare seed order
        int[] bracket_order = new int[roundedAthletes];
        {
            ArrayList<Integer> bracket_list = new ArrayList<>();
            for (int i = 0; i < roundedAthletes; i++) {
                bracket_list.add(i);
            }

            for (int i = 0; i < roundedAthletes; i++) {
                System.out.print((bracket_list.get(i) + 1) + " ");
            }
            System.out.println("");

            int slice = 1;
            while (slice < bracket_list.size() / 2) {
                ArrayList tmp = new ArrayList(bracket_list);
                bracket_list.clear();

                while (tmp.size() > 0) {
                    bracket_list.addAll(tmp.subList(0, slice)); //add slice amount from the beginning
                    bracket_list.addAll(tmp.subList(tmp.size() - slice, tmp.size())); //add slice amount from the end
                    tmp.subList(0, slice).clear();
                    tmp.subList(tmp.size() - slice, tmp.size()).clear();
                }
                slice *= 2;
                for (int i = 0; i < roundedAthletes; i++) {
                    System.out.print((bracket_list.get(i) + 1) + " ");
                }
                System.out.println("");
            }
            for (int i = 0; i < roundedAthletes; i++) {
                System.out.print((bracket_list.get(i) + 1) + " ");
            }
            System.out.println("");

            for (int i = 0; i < roundedAthletes; i++) {
                bracket_order[bracket_list.get(i)] = i;
            }
            bracket_list.clear();
        }

        Node[] athletes_to_bracket = new Node[roundedAthletes];

        //if there are two players on 3rd position
        if (preranked > 3 && twoThirdPlaces) {
            if (athletes.get(0).getAthlete().getClub()
                    .equals(athletes.get(3).getAthlete().getClub())
                    && !athletes.get(2).getAthlete().getClub()
                            .equals(athletes.get(3).getAthlete().getClub())) {//and one of them is from the same club as the 1st player, separate them
                //swap 3rd and 4th players
                Node tmp = athletes.get(3);
                athletes.set(3, athletes.get(2));
                athletes.set(2, tmp);

            }
        }

        //fixing positions for pre-ranked competitors
        for (int i = 0; i < preranked; i++) {
            athletes_to_bracket[bracket_order[i]] = athletes.get(i);
        }
        //adding null competitors
        if (roundedAthletes > athletes.size()) { //add empty players to the size of the power of 2
            int emptys = roundedAthletes - athletes.size();
            for (int i = 0; i < emptys; i++) {
                athletes_to_bracket[bracket_order[roundedAthletes - i - 1]] = new Node();
            }
        }
        System.out.println(".model.Chart.InitializeMatches()");

        for (int i = 0; i < roundedAthletes; i++) {
            System.out.print(i + ": ");
            try {
                if (athletes_to_bracket[i] != null) {
                    System.out.println(athletes_to_bracket[i].getAthlete().toString());
                } else {
                    System.out.println(athletes_to_bracket[i] + "- to fulfill");
                }
            } catch (Exception e) {
                System.out.println("DUMMY " + e.toString());
            }
        }

        for (int i = 0; i < preranked; i++) {
            athletes.remove(0);
        }

        /////
        //adding all the other competitors 
        //so as to separate ones from the same club as far as possible
        Map<String, List<Node>> players_over_clubs = new LinkedHashMap<>();

        for (Node p : athletes) {
            List<Node> club = players_over_clubs.get(p.getAthlete().getClub());
            if (club == null) {
                club = new ArrayList<Node>();
                players_over_clubs.put(p.getAthlete().getClub(), club);
            }
            club.add(p);
        }

        //divide into clubs, ordered according to the decreasing amount of players in each
        ArrayList<List<Node>> ordered_in_clubs = new ArrayList<>(players_over_clubs.values());
        ordered_in_clubs.sort((a, b) -> b.size() - a.size());
        ordered_in_clubs.forEach(node -> System.out.println("N: " + node.get(0).getAthlete().getClub() + " " + node.size()));

        //for each club place players separately
        for (List<Node> club : ordered_in_clubs) {
            int split = 1;
            int shift = roundedAthletes / split;
            ArrayList<Integer> parts = new ArrayList<>();

            String club_name = club.get(0).getAthlete().getClub();

            while (!club.isEmpty()) {
                //if step has ended, proceed on step further
                if (parts.isEmpty()) {
                    split *= 2;
                    shift = roundedAthletes / split;
                    for (int i = 0; i < split; i++) {
                        parts.add(i);
                    }
                    Collections.shuffle(parts);
                }

                int part = parts.remove(0);

                //check if there is no such club in this part already
                boolean no_such_club = true;
                for (int i = part * shift; i < part * shift + shift; i++) {
                    try {
                        if (athletes_to_bracket[i] != null && athletes_to_bracket[i].getAthlete().getClub().equals(club_name)) {
                            no_such_club = false;
                            break;
                        }
                    } catch (Exception e) {
                        //position fixed for being empty
                    }

                }

                //if there is none, add on first available position
                if (no_such_club) {
                    for (int i = part * shift; i < part * shift + shift; i++) {
                        try {
                            if (athletes_to_bracket[i] == null) {
                                athletes_to_bracket[i] = club.remove(0);
                                break;
                            }
                        } catch (Exception e) {
                            //position fixed for being empty
                        }
                    }

                }
            }

        }

        for (int i = 0; i < roundedAthletes; i++) {
            System.out.print(i + ": ");
            try {
                if (athletes_to_bracket[i] != null) {
                    System.out.println(athletes_to_bracket[i].getAthlete().toString());
                } else {
                    System.out.println(athletes_to_bracket[i] + "- to fulfill");
                }
            } catch (Exception e) {
                System.out.println("DUMMY " + e.toString());
            }
        }

        //create blank first matches
        ArrayList<Node> firstMatches = new ArrayList<>();

        for (int i = 0; i < roundedAthletes; i += 2) {
            Node match = new Node();
            match.aka = athletes_to_bracket[i];
            match.shiro = athletes_to_bracket[i + 1];
            match.chartLvl = height - 1;
            firstMatches.add(match);
        }

        //   System.exit(0);
//        for(int i =0;i<firstDepthMatches;i++){
//            Node match = new Node();
//            match.aka = athletes.get(i);
//            match.shiro = athletes.get(roundedAthletes-i-1);
//            match.chartLvl = height-1;
//            firstMatches.add(match);    
//        }
//        
//        //order matches
//        if(firstMatches.size()>1) firstMatches = orderMatches(firstMatches);
        this.matches = new LinkedList<>();
        this.matches.addAll(firstMatches);

        //create the rest of a chart
        if (firstMatches.size() > 1) {
            this.matches.addAll(generateWholeChart(firstMatches));
        }
        if (this.matches.size() > 0) {
            this.winner = this.matches.getLast();
        }

        //fulfill empty matches
        winEmptyMatches();

        //TODO nie prawda
        this.matchesTotal = this.matches.size();

        System.out.println(this.toString());

    }

    /**
     * Shuffles the list of competitors without first 'favoured' athletes which
     * were saved as winners from the previous tournament
     *
     * @param athletes
     * @param favored
     */
    private void randomizeList(ArrayList<Node> athletes, int favored) {

        List<Node> toshuffle = athletes.subList(favored, athletes.size());
        if (favored > 0) {
            athletes = new ArrayList<Node>(athletes.subList(0, favored - 1));
        } else {
            athletes = new ArrayList<Node>();
        }

        Collections.shuffle(toshuffle);
        athletes.addAll(toshuffle);

    }

    private ArrayList<Node> orderMatches(ArrayList<Node> matches) {

        if (matches.size() == 2) {
            return matches;
        }

        ArrayList<Node> a = new ArrayList<>();
        ArrayList<Node> b = new ArrayList<>();

        for (int i = 0; i < matches.size(); i += 2) {
            a.add(matches.get(i));
            b.add(matches.get(i + 1));
        }

        //java 8 stream join two subarrays into one
        return Stream.concat(orderMatches(a).stream(), orderMatches(b).stream()).collect(Collectors.toCollection(ArrayList::new));

    }

    /**
     * Recursively generates consecutive depths of a tree chart
     *
     * @param matches previous depth matches
     * @return complete higher part of a tree
     */
    private Collection<? extends Node> generateWholeChart(ArrayList<Node> matches) {
        ArrayList<Node> nextMatches = new ArrayList<>();

        for (int i = 0; i < matches.size(); i += 2) {
            Node match = new Node();
            match.aka = matches.get(i);
            match.shiro = matches.get(i + 1);
            match.chartLvl = matches.get(i).chartLvl - 1;
            nextMatches.add(match);

            matches.get(i).parent = match;
            matches.get(i + 1).parent = match;
        }
        if (nextMatches.get(0).chartLvl > 0) {
            nextMatches.addAll(generateWholeChart(nextMatches));
        }

        return nextMatches;
    }

    private void winEmptyMatches() {
        if (this.matches.isEmpty()) {
            return;
        }

        int size = (int) Math.pow(2, this.matches.get(0).chartLvl);
        for (int i = 0; i < size; i++) {
            if (this.matches.get(i).hasOneAthlete() != null) {
                this.matches.get(i).athlete = this.matches.get(i).hasOneAthlete().athlete;
            }
        }
    }

    static double log(int x, int base) {
        return (Math.log(x) / Math.log(base));
    }

    @Override
    public String toString() {
        String ret = "";
        if (matches.isEmpty()) {
            return ret;
        }

        int lvl = matches.get(0).chartLvl;

        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).chartLvl < lvl) {
                lvl--;
                ret += "---\n";
            }
            ret += matches.get(i).toString() + "\n";

        }
        return ret;
    }

    public String getLvl(int lvl) {
        String ret = "";
        for (int i = 0; i < matches.size(); i++) {
            if (matches.get(i).chartLvl == lvl) {
                ret += matches.get(i).toString() + "\n";
            } else if (matches.get(i).chartLvl < lvl) {
                return ret;
            }
        }

        return ret;
    }

    public Integer getMaxLvl() {
        if (matches.isEmpty()) {
            return null;
        }
        return matches.get(0).chartLvl;
    }

    /**
     *
     * @return first not played match from all matches
     */
    public Node getFirstMatch() {
        for (Node m : matches) {
            if (m.athlete == null) {
                return m;
            }
        }
        return null;
    }

    public Node getWaitingMatch() {
        boolean firstMatch = false;
        for (Node m : matches) {
            if (m.athlete == null && !firstMatch) {
                firstMatch = true;
            } else if (m.athlete == null && firstMatch) {
                return m;
            }
        }
        return null;
    }

    public ArrayList<Node> getMatches() {
        ArrayList<Node> list = new ArrayList<>();

        matches.stream().filter((m) -> (m.hasTwoAthletes())).forEachOrdered((m) -> {
            list.add(m);
        });

        return list;

    }

    public ArrayList<Node> getBracketMatches() {
        return new ArrayList<Node>(matches);
    }

    public static String getLvlToString(int i) {
        switch (i) {
            case 0:
                return Dictionary.getString("final");
            case 1:
                return Dictionary.getString("semi-finals");
            case 2:
                return Dictionary.getString("quarter-finals");
            case 3:
                return Dictionary.getString("eliminations") + " 16";
            case 4:
                return Dictionary.getString("eliminations") + " 32";
            default:
                return Dictionary.getString("eliminations");
        }

    }

    public Node getWinnerNode() {
        return winner;
    }

}
