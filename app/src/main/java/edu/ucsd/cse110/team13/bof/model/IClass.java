package edu.ucsd.cse110.team13.bof.model;

public interface IClass {
    int       getYear();
    Quarter   getQuarter();
    ClassSize getSize();
    String    getSubject();
    String    getCourseNumber();

    @Override
    boolean equals(Object obj);
}
