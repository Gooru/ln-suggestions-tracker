package org.gooru.suggestions.test;

/**
 * @author ashish on 14/3/17.
 */
public class TestRunner {

    public static void main(String[] args) {
        new TestRunner().doMain();
    }

    private void doMain() {
        new LookupTest().test();
    }

}
