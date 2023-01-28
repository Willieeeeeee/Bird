package edu.ucsd.cse110.team13.bof.model;

import java.util.List;

public interface IProfile {
    String       getFirstName();
    String       getHeadshotUrl();
    List<IClass> getClasses();
    boolean      isFavorite();

    boolean      hasCommonClasses(IProfile profile);
    List<IClass> getCommonClasses(IProfile profile);
}
