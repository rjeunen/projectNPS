package org.example.projectnps;

import java.util.List;

public interface IDataImporter {
    List<DataRecord> readFromFile (String filePath);
}
