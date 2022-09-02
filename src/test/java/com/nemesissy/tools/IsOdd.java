package com.nemesissy.tools;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
//import org.hamcrest.TypeSafeMatcher;

public class IsOdd extends TypeSafeDiagnosingMatcher<Integer> {

    @Override
    public void describeTo(final Description description) {
        description.appendText("an odd number");
    }

    @Override
    public boolean matchesSafely(Integer val, Description description) {
        description.appendText("was ").appendValue(val).appendText(", which is an Even number");
        return val % 2 != 0;
    }

    public static IsOdd isOdd() {
        //return new IsEven(val);
        return new IsOdd();
    }

}
