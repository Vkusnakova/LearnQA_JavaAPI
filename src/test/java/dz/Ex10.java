package dz;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Ex10 {

    @Test
    public void testLenghtOdString(){
        String s = "alkfjalfgkgl";

            Assertions.assertTrue(s.length()>15, "String lenght is less than 15");


    }
}
