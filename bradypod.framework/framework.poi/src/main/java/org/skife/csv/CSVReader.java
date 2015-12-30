package org.skife.csv;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStream;

/**
 * Interface provided for things that like to play with interfaces, see
 *
 * @see SimpleReader
 */
public interface CSVReader
{
    char COMMA = ',';
    char TAB = '\t';
    char SINGLE_QUOTE = '\'';
    char DOUBLE_QUOTE = '"';
    char BACKSLASH = '\\';

    /**
     * Calls onRow for each row in the raw (including line breaks)
     * content of the csv, passed as a String
     */
    void parse(String raw, ReaderCallback callback);

    /**
     * Returns a List of String[] where it is passed the raw (including line breaks)
     * content of the csv
     */
    List<String[]> parse(String raw);

    /**
     * Opens, parses, and closes a file
     *
     * @throws java.io.IOException           if there is an error reading
     * @throws java.io.FileNotFoundException if the file does not exist
     */
    List<String[]> parse(File in) throws IOException;

    /**
     * Opens, parses, and closes a file
     *
     * @throws java.io.IOException           if there is an error reading
     * @throws java.io.FileNotFoundException if the file does not exist
     */
    void parse(File in, ReaderCallback callback) throws IOException;

    /**
     * Returns a List of String[]
     *
     * @param in will not be closed by the reader
     */
    List<String[]> parse(Reader in) throws IOException;

    /**
     * Returns a List of String[]
     *
     * @param in will not be closed by the reader
     */
    List<String[]> parse(InputStream in) throws IOException;

    /**
     * Invoke the callback for each row of the CSV, passing in the fields.
     *
     * @param in will not be closed by the reader
     */
    void parse(InputStream in, ReaderCallback callback) throws IOException;

    /**
     * Invoke the callback for each row of the CSV, passing in the fields.
     *
     * @param in will not be closed by the reader
     */
    void parse(Reader in, ReaderCallback callback) throws IOException;

    /**
     * Specify an escape character within a field, default is \
     */
    void setEscapeCharacter(char escape);

    /**
     * Specify the field seperator character, defaults to a comma
     */
    void setSeperator(char seperator);

    /**
     * Specify an array of chars that will be treated as quotes, ie, will be ignored and
     * everything between them is one field. Default is ' and "
     */
    void setQuoteCharacters(char[] quotes);

    /**
     * Trim whitespace around fields, defaults to false
     * @param b
     */
    void setTrim(boolean b);

    /**
     * Specify a string that is used to indicate that a line should be passed over
     * without processing. When the passed String is the very first thing on the line,
     * the whole line will be skipped.
     * <p>
     * Passing null indicates all lines should be processed
     * <p>
     * Processing all lines is the default behavior
     */
    void setLineCommentIndicator(String token);
}
