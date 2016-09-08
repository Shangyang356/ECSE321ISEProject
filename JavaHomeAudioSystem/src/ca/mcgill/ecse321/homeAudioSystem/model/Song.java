/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.homeAudioSystem.model;
import java.sql.Time;

// line 42 "../../../../../domainModelJava.ump"
// line 41 "../../../../../domainModel.ump"
public class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private String name;
  private Time duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Song(String aName, Time aDuration)
  {
    name = aName;
    duration = aDuration;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setDuration(Time aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Time getDuration()
  {
    return duration;
  }

  public void delete()
  {}

  // line 46 "../../../../../domainModelJava.ump"
   public String toString(){
    return this.getName();
  }

}