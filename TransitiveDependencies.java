package com.coding.dependencies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;

public class TransitiveDependencies {  
    Map<String, HashSet<String>> inputDependencies = new HashMap<String,HashSet<String>>();

    public static void main(String args[]) {
    	TransitiveDependencies codeKata = new TransitiveDependencies();
        codeKata.addDirectDependency("A B C");
        codeKata.addDirectDependency("B C E");
        codeKata.addDirectDependency("C G");
        codeKata.addDirectDependency("D A F");
        codeKata.addDirectDependency("E F");
        codeKata.addDirectDependency("F H");
        System.out.println(codeKata.getDependencies());
    }

    public boolean addDirectDependency(String rawDependency) {
        String[] rawDepenenciesBreak = rawDependency.split(" ");
        String key = rawDepenenciesBreak[0];
        HashSet<String> dependencies = new HashSet<String>();
        for (int i = 1; i < rawDepenenciesBreak.length; i++) {
            dependencies.add(rawDepenenciesBreak[i]);
        }
        return inputDependencies.put(key, dependencies) != null;
    }

    public Map<String, HashSet<String>> getDependencies() {
        Map<String, HashSet<String>> transitiveDependencies = new HashMap<String, HashSet<String>>();
        // Iterate though the provided input dependencies..
        for (Entry<String, HashSet<String>> entry : inputDependencies.entrySet()) {
            String key = entry.getKey();
            Set<String> dependenciesForKey = entry.getValue();
            Queue<String> dependeciesQueue = new LinkedList<String>();
            if (dependenciesForKey != null) {
                for (String s : dependenciesForKey) {
                    //System.out.println("Dependency queue: "+s);
                    dependeciesQueue.add(s);
                }
            }
            // Used to store all the dependencies till now..
            HashSet<String> allDependencies = new HashSet<String>();
            // As concurrent modifications is not allowed, a temp queue is
            // maintained
            Queue<String> tempDependeciesQueue = new LinkedList<String>();
            // If dependenciesQues is not empty, add all the values in the
            // queues to allDependencies
            // and add all the dependencies of these to tempDependeciesQueue
            while (!dependeciesQueue.isEmpty()) {
                for (String d : dependeciesQueue) {
                    allDependencies.add(d);
                    if (inputDependencies.get(d) != null) {
                        for (String s : inputDependencies.get(d)) {
                            tempDependeciesQueue.add(s);
                        }
                    }
                }
                // Replace dependeciesQueue with tempDependeciesQueue and Make
                // tempDependeciesQueue new list.
                dependeciesQueue = tempDependeciesQueue;
                tempDependeciesQueue = new LinkedList<String>();
            }
            // Put the dependencies obtained in the final list.
            transitiveDependencies.put(key, allDependencies);
        }
        return transitiveDependencies;
    }
}
