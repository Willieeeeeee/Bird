package edu.ucsd.cse110.team13.bof;

import static edu.ucsd.cse110.team13.bof.util.CSVUtil.*;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class CSVUtilTest {
    @Test
    public void simpleParseLine_invalidString() {
        int column = 1;
        String str = "";

        Assert.assertFalse(parseLineSimple(str,column).isPresent());
        Assert.assertFalse(parseLineSimple(null,column).isPresent());
    }

    @Test
    public void simpleParseLine_invalidColumn() {
        String str = "whatever";

        Assert.assertFalse(parseLineSimple(str,0).isPresent());
        Assert.assertFalse(parseLineSimple(str,-20).isPresent());
    }

    @Test
    public void simpleParseLine_mismatchColumn() {
        String str = "2021,FA,CSE,210\n";

        Assert.assertFalse(parse(str, 3).isPresent());
    }

    @Test
    public void simpleParseLine_simpleCSV() {
        int column = 4;
        String  str1 = "2021,FA,CSE,210",
                str2 = "https://123.xyz/o_k-,,,",
                str3 = "gOOD";

        Optional<ArrayList<String>> read1Op = parseLineSimple(str1,column),
                                    read2Op = parseLineSimple(str2,column),
                                    read3Op = parseLineSimple(str3,1);

        Assert.assertTrue(read1Op.isPresent());
        Assert.assertEquals(read1Op.get().size(), column);
        Assert.assertEquals(read1Op.get().get(0), "2021");
        Assert.assertEquals(read1Op.get().get(1), "FA");
        Assert.assertEquals(read1Op.get().get(2), "CSE");
        Assert.assertEquals(read1Op.get().get(3), "210");

        Assert.assertTrue(read2Op.isPresent());
        Assert.assertEquals(read2Op.get().size(), column);
        Assert.assertEquals(read2Op.get().get(0), "https://123.xyz/o_k-");
        Assert.assertEquals(read2Op.get().get(1), "");
        Assert.assertEquals(read2Op.get().get(2), "");
        Assert.assertEquals(read2Op.get().get(3), "");

        Assert.assertTrue(read3Op.isPresent());
        Assert.assertEquals(read3Op.get().size(), 1);
        Assert.assertEquals(read3Op.get().get(0), str3);
    }

    @Test
    public void parse_invalidString() {
        int column = 1;
        String str = "";

        Assert.assertFalse(parse(str,column).isPresent());
        Assert.assertFalse(parse(null,column).isPresent());
    }

    @Test
    public void parse_invalidColumn() {
        String str = "whatever";

        Assert.assertFalse(parse(str, 0).isPresent());
        Assert.assertFalse(parse(str, -20).isPresent());
    }

    @Test
    public void parse_mismatchColumn() {
        String str = "2021,FA,CSE,210\n" +
                     "2022,WI,CSE 110\n";

        Assert.assertFalse(parse(str, 4).isPresent());
        Assert.assertFalse(parse(str, 3).isPresent());
    }

    @Test
    public void parse_simpleCSV() {
        int column = 4;
        String str = "Bill,,,\n" +
                "https://abc.xyz/o_k-,,,\n" +
                "2021,FA,CSE,210\n" +
                "2022,WI,CSE,110\n" +
                "2022,SP,CSE,110";

        Optional<ArrayList<ArrayList<String>>> resultOp = parse(str,column);
        Assert.assertTrue(resultOp.isPresent());

        ArrayList<ArrayList<String>> result = resultOp.get();
        Assert.assertEquals(result.size(), 5);
    }
}
