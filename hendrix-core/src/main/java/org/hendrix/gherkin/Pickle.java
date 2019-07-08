package org.hendrix.gherkin;

import java.util.ArrayList;
import java.util.List;

public class Pickle {
    private final String name;
    private final List<PickleStep> pickleSteps;
    private final List<PickleTag> tags;
    private final PickleLocation pickleLocation;

    public Pickle(String name, List<PickleStep> pickleSteps, List<PickleTag> tags, PickleLocation pickleLocation) {

        this.name = name;
        this.pickleSteps = pickleSteps;
        this.tags = tags;
        this.pickleLocation = pickleLocation;
    }

    public Iterable<? extends PickleStep> getSteps() {
        return pickleSteps;
    }

    public Iterable<? extends PickleTag> getTags() {
        return tags;
    }

    public String getName() {
        return name;
    }

    public PickleLocation getPickleLocation() {
        return pickleLocation;
    }
}
