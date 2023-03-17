package cwtech.telemetry;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CSVLogger {
    private final LinkedList<String> m_headers = new LinkedList<>();
    private LinkedList<String> m_values = new LinkedList<>();

    private boolean m_writtenHeaders = false;

    private final String m_filePath;
    private final File m_file;
    private final PrintWriter m_writer;

    public CSVLogger(String filePath) throws IOException {
        m_filePath = filePath;
        m_file = new File(m_filePath);
        m_writer = new PrintWriter(m_file);

    }

    public void log(String key, String value) {
        m_values.push(value);
        if(!m_headers.contains(key)) {
            m_headers.push(key);
        }
    }

    public void finish() {
        if (!m_writtenHeaders) {
            m_writtenHeaders = true;
            m_writer.println(m_headers.stream().map(this::escapeSpecialCharacters).collect(Collectors.joining(",")));
        }
        m_writer.println(m_values.stream().map(this::escapeSpecialCharacters).collect(Collectors.joining(",")));
        m_values.clear();
    }

    String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
