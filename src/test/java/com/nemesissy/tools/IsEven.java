package com.nemesissy.tools;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
//import org.hamcrest.TypeSafeMatcher;

public class IsEven extends TypeSafeDiagnosingMatcher<Integer> {

    @Override
    public void describeTo(final Description description) {
        description.appendText("an even number");
    }

    @Override
    public boolean matchesSafely(Integer val, Description description) {
        description.appendText("was ").appendValue(val).appendText(", which is an Odd number");
        return val % 2 == 0;
    }

    public static IsEven isEven() {
        //return new IsEven(val);
        return new IsEven();
    }

}
