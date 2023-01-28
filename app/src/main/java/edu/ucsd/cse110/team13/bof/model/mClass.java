package edu.ucsd.cse110.team13.bof.model;

public class mClass implements IClass {
    private int         year;
    private Quarter     quarter;
    private ClassSize   size;
    private String      subject;
    private String      courseNumber;
    private boolean     favorite;

    public mClass(int year, Quarter quarter, String subject, String courseNumber) {
        this.year = year;
        this.quarter = quarter;
        this.size    = ClassSize.GIGANTIC;
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.favorite = false;
    }

    public mClass(int year, Quarter quarter, String subject, String courseNumber, boolean favorite) {
        this.year = year;
        this.quarter = quarter;
        this.size    = ClassSize.GIGANTIC;
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.favorite = favorite;
    }
    public mClass(int year, Quarter quarter, ClassSize size, String subject, String courseNumber) {
        this.year = year;
        this.quarter = quarter;
        this.size    = size;
        this.subject = subject;
        this.courseNumber = courseNumber;
        this.favorite = false;
    }

    public int     getYear()         { return year;    }
    public Quarter getQuarter()      { return quarter; }
    public ClassSize getSize()       { return size; }
    public String  getSubject()      { return subject; }
    public String  getCourseNumber() { return courseNumber; }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean status){
        this.favorite = status;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }

        // type casting is safe because the class type was checked above
        IClass a = (IClass) obj;

        // check equivalence of each attribute
        if(!(this.getYear() == (a.getYear())))
            return false;
        if(!(this.getQuarter().equals(a.getQuarter())))
            return false;
        if(!(this.getSubject().equals(a.getSubject())))
            return false;
        if(!(this.getCourseNumber().equals(a.getCourseNumber())))
            return false;

        // if nothing is wrong return true
        return true;
    }
}
