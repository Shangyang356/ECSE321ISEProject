/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.homeAudioSystem.model;
import java.util.*;

// line 26 "../../../../../domainModelJava.ump"
// line 31 "../../../../../domainModel.ump"
// line 80 "../../../../../domainModel.ump"
public class Artist extends ListOfSong
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artist(String aName)
  {
    super(aName);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }

  // line 30 "../../../../../domainModelJava.ump"
   public String toString(){
    return this.getName();
  }

}