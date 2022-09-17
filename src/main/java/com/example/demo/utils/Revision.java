package com.example.demo.utils;

import com.example.demo.erros.RevisionsDontMatch;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Revision {

    private int value;

    public Revision matchAndBump(Revision revision) throws RevisionsDontMatch {
        match(revision);
        this.setValue(++value);
        return this;
    }

    public void match(Revision revision) throws RevisionsDontMatch{
        if (this.value != revision.value) throw new RevisionsDontMatch(revision.value);
    }

    public String toString(){
        return String.valueOf(this.getValue());
    }
}
