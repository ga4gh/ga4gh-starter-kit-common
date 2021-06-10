package org.ga4gh.starterkit.common.constant;

import java.time.format.DateTimeFormatter;

public class DateTimeConstants {

    /**
     * Date serialization format according to ISO 8601
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * Data formatter to fulfill ISO 8601 serialization
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
}
