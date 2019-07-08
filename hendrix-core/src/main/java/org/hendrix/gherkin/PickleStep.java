package org.hendrix.gherkin;

public class PickleStep {
    private final String keyword;
    private final String text;
    private final PickleLocation pickleLocation;

    public PickleStep(String keyword, String text, PickleLocation pickleLocation) {
        this.keyword = keyword;
        this.text = text;
        this.pickleLocation = pickleLocation;
    }

    public String getText() {
        return text;
    }

    public String getKeyword() {
        return keyword;
    }

    public PickleLocation getPickleLocation() {
        return pickleLocation;
    }
}
