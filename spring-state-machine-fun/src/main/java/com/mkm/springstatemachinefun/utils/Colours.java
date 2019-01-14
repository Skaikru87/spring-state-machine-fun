package com.mkm.springstatemachinefun.utils;

/**
 * https://gist.github.com/chrisopedia/8754917
 * https://www.fileformat.info/info/unicode/char/001b/index.htm
 * https://en.wikipedia.org/wiki/ANSI_escape_code
 */
public class Colours {

    public static final char UNICODE_ESCAPE = '\u001B';

    //RESET CURRENT TEXT/BACKGROUND COLOUR
    public static final String ANSI_RESET = UNICODE_ESCAPE + "[0m";

    public static final String DEFAULT = "";

    //TEXT COLOR
    public static final String ANSI_BLACK = UNICODE_ESCAPE + "[30m";
    public static final String ANSI_RED = UNICODE_ESCAPE + "[31m";
    public static final String ANSI_GREEN = UNICODE_ESCAPE + "[32m";
    public static final String ANSI_YELLOW = UNICODE_ESCAPE + "[33m";
    public static final String ANSI_BLUE = UNICODE_ESCAPE + "[34m";
    public static final String ANSI_PURPLE = UNICODE_ESCAPE + "[35m";
    public static final String ANSI_CYAN = UNICODE_ESCAPE + "[36m";
    public static final String ANSI_WHITE = UNICODE_ESCAPE + "[37m";

    //TEXT HIGH_INTENSITY COLOR
    public static final String HI_ANSI_BLACK = UNICODE_ESCAPE + "[0;90m";
    public static final String HI_ANSI_RED = UNICODE_ESCAPE + "[0;91m";
    public static final String HI_ANSI_GREEN = UNICODE_ESCAPE + "[0;92m";
    public static final String HI_ANSI_YELLOW = UNICODE_ESCAPE + "[0;93m";
    public static final String HI_ANSI_BLUE = UNICODE_ESCAPE + "[0;94m";
    public static final String HI_ANSI_PURPLE = UNICODE_ESCAPE + "[0;95m";
    public static final String HI_ANSI_CYAN = UNICODE_ESCAPE + "[0;96m";
    public static final String HI_ANSI_WHITE = UNICODE_ESCAPE + "[0;97m";

    //TEXT BOLD_HIGH_INTENSITY COLOR
    public static final String B_HI_ANSI_BLACK = UNICODE_ESCAPE + "[1;90m";
    public static final String B_HI_ANSI_RED = UNICODE_ESCAPE + "[1;91m";
    public static final String B_HI_ANSI_GREEN = UNICODE_ESCAPE + "[1;92m";
    public static final String B_HI_ANSI_YELLOW = UNICODE_ESCAPE + "[1;93m";
    public static final String B_HI_ANSI_BLUE = UNICODE_ESCAPE + "[1;94m";
    public static final String B_HI_ANSI_PURPLE = UNICODE_ESCAPE + "[1;95m";
    public static final String B_HI_ANSI_CYAN = UNICODE_ESCAPE + "[1;96m";
    public static final String B_HI_ANSI_WHITE = UNICODE_ESCAPE + "[1;97m";

    //BACKGROUND COLOR
    public static final String ANSI_BLACK_BACKGROUND = UNICODE_ESCAPE + "[40m";
    public static final String ANSI_RED_BACKGROUND = UNICODE_ESCAPE + "[41m";
    public static final String ANSI_GREEN_BACKGROUND = UNICODE_ESCAPE + "[42m";
    public static final String ANSI_YELLOW_BACKGROUND = UNICODE_ESCAPE + "[43m";
    public static final String ANSI_BLUE_BACKGROUND = UNICODE_ESCAPE + "[44m";
    public static final String ANSI_PURPLE_BACKGROUND = UNICODE_ESCAPE + "[45m";
    public static final String ANSI_CYAN_BACKGROUND = UNICODE_ESCAPE + "[46m";
    public static final String ANSI_WHITE_BACKGROUND = UNICODE_ESCAPE + "[47m";

    //BACKGROUND HIGH_INTENSITY COLOR
    public static final String HI_ANSI_BLACK_BACKGROUND = UNICODE_ESCAPE + "[0;100m";
    public static final String HI_ANSI_RED_BACKGROUND = UNICODE_ESCAPE + "[0;101m";
    public static final String HI_ANSI_GREEN_BACKGROUND = UNICODE_ESCAPE + "[0;102m";
    public static final String HI_ANSI_YELLOW_BACKGROUND = UNICODE_ESCAPE + "[0;103m";
    public static final String HI_ANSI_BLUE_BACKGROUND = UNICODE_ESCAPE + "[0;104m";
    public static final String HI_ANSI_PURPLE_BACKGROUND = UNICODE_ESCAPE + "[0;105m";
    public static final String HI_ANSI_CYAN_BACKGROUND = UNICODE_ESCAPE + "[0;106m";
    public static final String HI_ANSI_WHITE_BACKGROUND = UNICODE_ESCAPE + "[0;107m";


}
