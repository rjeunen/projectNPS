package org.example.projectnps;

import java.util.List;

public interface DataImporter {
    List<DataRecord> readFromFile (String filePath);
}
