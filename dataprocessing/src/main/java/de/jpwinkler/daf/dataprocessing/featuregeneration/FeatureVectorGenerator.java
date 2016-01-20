package de.jpwinkler.daf.dataprocessing.featuregeneration;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author JONWINK
 *
 * @param <E>
 *            Type of the elements
 * @param <F>
 *            Type of the features
 */
public class FeatureVectorGenerator<E, F> {

    private int cutoff;
    private final Map<F, Integer> featureCounts = new HashMap<>();
    private final Map<F, Integer> featureIndices = new HashMap<>();
    private final List<FeatureGenerator<E, F>> featureGenerators = new ArrayList<>();

    public FeatureVectorGenerator(final int cutoff) {
        this.cutoff = cutoff;
    }

    public void addFeatureGenerator(final FeatureGenerator<E, F> featureGenerator) {
        this.featureGenerators.add(featureGenerator);
    }

    private List<F> getFeatures(final E element) {
        final List<F> features = new ArrayList<>();
        for (final FeatureGenerator<E, F> generator : featureGenerators) {
            features.addAll(generator.getFeatures(element));
        }
        return features;
    }

    public double[] getFeatureVector(final E element) {
        final double[] vector = new double[featureIndices.size()];

        for (final F feature : getFeatures(element)) {
            if (featureIndices.containsKey(feature)) {
                vector[featureIndices.get(feature)] += 1;
            }
        }

        return vector;
    }

    public int[] getFeatureIndexVector(final E element) {
        return getFeatures(element).stream().filter(f -> featureIndices.containsKey(f)).mapToInt(f -> featureIndices.get(f)).toArray();
    }

    public void addElement(final E element) {
        for (final F feature : getFeatures(element)) {
            if (!featureCounts.containsKey(feature)) {
                featureCounts.put(feature, 1);
            } else {
                featureCounts.put(feature, featureCounts.get(feature) + 1);
            }
        }
    }

    public void buildFeatureIndexMap() {
        featureIndices.clear();

        int idx = 0;

        for (final Entry<F, Integer> e : featureCounts.entrySet()) {
            if (e.getValue() >= cutoff) {
                featureIndices.put(e.getKey(), idx++);
            }
        }
    }

    public int getFeatureCount() {
        return featureIndices.size();
    }

    public List<F> getSortedFeatureList() {
        final List<F> result = new ArrayList<>(getFeatureCount());
        // TODO: this implementation is ridiculously retarded, replace this with
        // something proper! (but it works and is fast!)
        for (int i = 0; i < getFeatureCount(); i++) {
            result.add(null);
        }
        for (final Entry<F, Integer> e : featureIndices.entrySet()) {
            result.set(e.getValue(), e.getKey());
        }
        return result;
    }

    public void clear() {
        featureCounts.clear();
        featureIndices.clear();
    }

    public int getCutoff() {
        return cutoff;
    }

    public void setCutoff(final int cutoff) {
        this.cutoff = cutoff;
    }

    public void printFeatureStatistics() {
        final List<Entry<F, Integer>> featureList = new ArrayList<>(featureCounts.entrySet());

        featureList.sort(new Comparator<Entry<F, Integer>>() {
            @Override
            public int compare(final Entry<F, Integer> o1, final Entry<F, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        for (final Entry<F, Integer> e : featureList) {
            if (e.getValue() >= cutoff) {
                System.out.println(e.getValue() + " " + e.getKey());
            }
        }

    }
}