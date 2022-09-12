package com.aravinth.pdfsplitter.pdfsplitdatadapi.utils;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DateFormat {
    public String dateFormat(Date date) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(pattern);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }
}
