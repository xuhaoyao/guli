package com.scnu;

import org.joda.time.DateTime;
import org.junit.Test;

import javax.validation.constraints.Negative;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Test01 {

    @Test
    public void test() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(new Date());
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(format);
        System.out.println(uuid);
    }

    @Test
    public void test2() {
        String fileName = "2.jpg";

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String format = sdf.format(new Date());
        fileName = format + "/" + uuid + fileName;
        System.out.println(fileName);
    }

    @Test
    public void test3() {
        String format1 = new DateTime().toString("yyyy/MM/dd");
        System.out.println(format1);
    }
}
