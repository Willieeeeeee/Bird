package edu.ucsd.cse110.team13.bof.model;

public enum ClassSize {
    TINY     ("Tiny"    ,"T",1.00f,"Tiny (<40)"),
    SMALL    ("Small"   ,"S",0.33f,"Small (40-75)"),
    MEDIUM   ("Medium"  ,"M",0.18f,"Medium (75-150)"),
    LARGE    ("Large"   ,"L",0.10f,"Large (150-250)"),
    HUGE     ("Huge"    ,"H",0.06f,"Huge (250-400)"),
    GIGANTIC ("Gigantic","G",0.03f,"Gigantic (400+)");

    private final float sizeWeight;
    private final String sizeName, sizeCode, sizeDescription;

    ClassSize(String sizeName, String sizeCode, float sizeWeight, String sizeDescription) {
        this.sizeName        = sizeName;
        this.sizeCode        = sizeCode;
        this.sizeWeight      = sizeWeight;
        this.sizeDescription = sizeDescription;
    }

    public String getSizeName()        { return sizeName; }
    public String getSizeCode()        { return sizeCode; }
    public float  getSizeWeight()      { return sizeWeight; }
    public String getSizeDescription() { return sizeDescription; }

    @Override
    public String toString() { return getSizeName(); }
}
