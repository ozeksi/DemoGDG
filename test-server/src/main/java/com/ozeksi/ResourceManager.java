/*
 * Copyright 2016-2017 arcelik.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 *
 */

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
