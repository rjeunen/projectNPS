package org.example.projectnps;

import java.util.List;

public interface IDataExporter {
    void writeToFile(String filePath, List<DataRecord> data);
}
