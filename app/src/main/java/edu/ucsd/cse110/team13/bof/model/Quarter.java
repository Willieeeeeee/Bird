package edu.ucsd.cse110.team13.bof.model;

public enum Quarter {
    FA  ("Fall",                   "FA" ),
    WI  ("Winter",                 "WI" ),
    SP  ("Spring",                 "SP" ),
    SS1 ("Summer Session I",       "SS1"),
    SS2 ("Summer Session II",      "SS2"),
    SSS ("Special Summer Session", "SSS");

    private final String quarterName, quarterCode;

    Quarter(String quarterName, String quarterCode) {
        this.quarterName = quarterName;
        this.quarterCode = quarterCode;
    }

    public String getQuarterName() { return quarterName; }
    public String getQuarterCode() { return quarterCode; }

    @Override
    public String toString() { return getQuarterName(); }
}
