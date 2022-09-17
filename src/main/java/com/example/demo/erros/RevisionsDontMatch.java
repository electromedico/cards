package com.example.demo.erros;

public class RevisionsDontMatch extends Exception {
    public RevisionsDontMatch(int revision) {
        super("the revisions don't Match" + revision);
    }
}
