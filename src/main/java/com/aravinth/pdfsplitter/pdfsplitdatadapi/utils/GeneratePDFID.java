package com.aravinth.pdfsplitter.pdfsplitdatadapi.utils;


import com.fasterxml.uuid.Generators;

import java.util.UUID;

public interface GeneratePDFID {
    static String GeneratePDFGUIDId(){
        UUID uuid = Generators.timeBasedGenerator().generate();
        return uuid.toString().toUpperCase();
    }
}
