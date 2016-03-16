package de.jpwinkler.daf.dataprocessing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import de.jpwinkler.daf.dafcore.csv.ObjectCSVWriter;
import de.jpwinkler.daf.dafcore.model.csv.DoorsObject;
import de.jpwinkler.daf.dataprocessing.streaming.SimpleFolderCSVSpliterator;

public class TaskGenerationApp {

    private static Iterator<DoorsObject> getIterator() {
        return StreamSupport.stream(new SimpleFolderCSVSpliterator(new File("C:\\WORK\\DOORS\\export\\body\\comp\\de"), true), false).iterator();
    }

    public static void main(final String[] args) throws IOException {
        final Pattern objectTypePattern = Pattern.compile("[a-z]+");

        final Random r = new Random();

        final Map<String, Integer> attributes = new HashMap<>();

        int totalObjects = 0;

        Iterator<DoorsObject> iterator = getIterator();
        while (iterator.hasNext()) {
            final DoorsObject next = iterator.next();
            for (final String attributeName : next.getAttributes().keySet()) {
                if (attributes.containsKey(attributeName)) {
                    attributes.put(attributeName, attributes.get(attributeName) + 1);
                } else {
                    attributes.put(attributeName, 1);
                }
            }
            totalObjects++;
        }

        final int totalObjectsFinal = totalObjects;

        final String[] header = attributes.entrySet().stream().filter(e -> (double) e.getValue() / totalObjectsFinal > 0.25).map(e -> e.getKey()).toArray(i -> new String[i]);

        final Map<String, Set<Integer>> writtenObjects = new HashMap<>();

        final ObjectCSVWriter csvWriter = new ObjectCSVWriter(header, new FileOutputStream("temp/task.csv"));

        iterator = getIterator();
        while (iterator.hasNext()) {
            final DoorsObject next = iterator.next();
            next.setObjectLevel(1);
            final String ot = next.getAttributes().get("Object Type");
            if (ot != null && objectTypePattern.matcher(ot).matches() && next.getText() != null && !next.getText().trim().isEmpty()) {

                if (!writtenObjects.containsKey(ot)) {
                    writtenObjects.put(ot, new HashSet<>());
                }

                final String text = next.getText();

                if (!text.trim().isEmpty() && !writtenObjects.get(ot).contains(text.hashCode())) {

                    if (ot.equals("information") || ot.equals("requirement")) {
                        csvWriter.writeObject(next);
                        writtenObjects.get(ot).add(text.hashCode());
                    }


                }
            }
        }

        csvWriter.close();

        for (final Entry<String, Set<Integer>> e : writtenObjects.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue().size());
        }
    }

}