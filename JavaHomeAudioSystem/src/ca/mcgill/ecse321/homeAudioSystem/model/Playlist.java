/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.homeAudioSystem.model;
import java.util.*;

// line 34 "../../../../../domainModelJava.ump"
// line 36 "../../../../../domainModel.ump"
public class Playlist extends ListOfSong
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Playlist(String aName)
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

  // line 38 "../../../../../domainModelJava.ump"
   public String toString(){
    return this.getName();
  }

}