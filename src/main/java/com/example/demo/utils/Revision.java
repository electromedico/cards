package com.example.demo.utils;

import com.example.demo.errors.RevisionsDontMatchException;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Revision {

    private int value;

    public Revision matchAndBump(Revision revision) throws RevisionsDontMatchException {
        match(revision);
        this.setValue(++value);
        return this;
    }

    public void match(Revision revision) throws RevisionsDontMatchException {
        if (this.value != revision.value) throw new RevisionsDontMatchException(revision.value);
    }

    public String toString(){
        return String.valueOf(this.getValue());
    }
}
