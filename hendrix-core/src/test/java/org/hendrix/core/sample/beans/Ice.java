package org.hendrix.core.sample.beans;

import org.springframework.stereotype.Component;

@Component
public class Ice {
    private int size = 0;

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
