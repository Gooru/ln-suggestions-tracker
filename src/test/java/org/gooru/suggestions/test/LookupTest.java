package org.gooru.suggestions.test;

import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.util.StringColumnMapper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

/**
 * @author ashish
 */
class LookupTest {

    void test() {
        final String s = lookupPreTest(new DBITestHelper().getHandle());
        System.out.println("Pre test lookup value is " + s);
        JsonArray pre = new JsonArray(s);
        processArray(pre);
        System.out.println("Typed pre : " + pre.toString());
        final String s1 = lookupPostTest(new DBITestHelper().getHandle());
        float post = Float.valueOf(s1);
        System.out.println("Post test lookup value is " + s1);
        System.out.println("Typed post: " + post);
    }

    private String lookupPreTest(Handle handle) {
        return handle.createQuery("select value from default_lookup where key = :key")
            .bind("key", "pre-test-score-ranges").map(StringColumnMapper.INSTANCE).first();
    }

    private String lookupPostTest(Handle handle) {
        return handle.createQuery("select value from default_lookup where key = :key")
            .bind("key", "post-test-threshold-score-for-BA").map(StringColumnMapper.INSTANCE).first();
    }

    private void processArray(JsonArray pre) {
        JsonObject result = new JsonObject();
        for (Object o : pre) {
            JsonObject value = new JsonObject(String.valueOf(o));
            System.out.println(" O is : " + value.toString());
        }
    }


}
