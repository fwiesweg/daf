package de.jpwinkler.daf.fap5gui.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnalysisResults {

    private final List<Version> versions = new ArrayList<>();

    public List<Version> getVersions() {
        return versions;
    }

    public List<String> getVersionNumbers() {
        return versions.stream().map(v -> v.getVersionString()).collect(Collectors.toList());
    }

}
