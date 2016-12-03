package com.ozeksi;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

class ResourceManager {
    private static final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
    static String RESOURCE_PATH = "../test-server/src/main/resources/";//TODO: ozeksi -> fix this hack

    static Object readJsonFile(String fileName) throws IOException {
        //../test-server/src/main/resources/Users.json
        return mapper.readValue(new File(RESOURCE_PATH + fileName + ".json"), Object.class);
    }
}
