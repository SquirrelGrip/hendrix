package org.hendrix.gherkin;

public class PickleTag {
    private final String name;
    private final PickleLocation pickleLocation;

    public PickleTag(String name, PickleLocation pickleLocation) {
        this.name = name;
        this.pickleLocation = pickleLocation;
    }

    public PickleLocation getPickleLocation() {
        return pickleLocation;
    }

    public String getName() {
        return name;
    }
}
