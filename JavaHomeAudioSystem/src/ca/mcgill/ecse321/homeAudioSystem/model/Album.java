/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.homeAudioSystem.model;
import java.sql.Date;
import java.util.*;

// line 3 "../../../../../domainModelJava.ump"
// line 24 "../../../../../domainModel.ump"
// line 85 "../../../../../domainModel.ump"
public class Album extends ListOfSong
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Album Attributes
  private Date releaseDate;

  //Album Associations
  private List<Artist> artists;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Album(String aName, Date aReleaseDate)
  {
    super(aName);
    releaseDate = aReleaseDate;
    artists = new ArrayList<Artist>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setReleaseDate(Date aReleaseDate)
  {
    boolean wasSet = false;
    releaseDate = aReleaseDate;
    wasSet = true;
    return wasSet;
  }

  public Date getReleaseDate()
  {
    return releaseDate;
  }

  public Artist getArtist(int index)
  {
    Artist aArtist = artists.get(index);
    return aArtist;
  }

  public List<Artist> getArtists()
  {
    List<Artist> newArtists = Collections.unmodifiableList(artists);
    return newArtists;
  }

  public int numberOfArtists()
  {
    int number = artists.size();
    return number;
  }

  public boolean hasArtists()
  {
    boolean has = artists.size() > 0;
    return has;
  }

  public int indexOfArtist(Artist aArtist)
  {
    int index = artists.indexOf(aArtist);
    return index;
  }

  public static int minimumNumberOfArtists()
  {
    return 0;
  }

  public boolean addArtist(Artist aArtist)
  {
    boolean wasAdded = false;
    if (artists.contains(aArtist)) { return false; }
    artists.add(aArtist);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtist(Artist aArtist)
  {
    boolean wasRemoved = false;
    if (artists.contains(aArtist))
    {
      artists.remove(aArtist);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addArtistAt(Artist aArtist, int index)
  {  
    boolean wasAdded = false;
    if(addArtist(aArtist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtists()) { index = numberOfArtists() - 1; }
      artists.remove(aArtist);
      artists.add(index, aArtist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtistAt(Artist aArtist, int index)
  {
    boolean wasAdded = false;
    if(artists.contains(aArtist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtists()) { index = numberOfArtists() - 1; }
      artists.remove(aArtist);
      artists.add(index, aArtist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtistAt(aArtist, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    artists.clear();
    super.delete();
  }

  // line 12 "../../../../../domainModelJava.ump"
   public void setGenre(Genre aGenre){
    genre = aGenre;
  }

  // line 17 "../../../../../domainModelJava.ump"
   public Genre getGenre(){
    return genre;
  }

  // line 22 "../../../../../domainModelJava.ump"
   public String toString(){
    return this.getName();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 5 ../../../../../domainModelJava.ump
  public enum Genre {Pop, EDM, Hiphop, Country, Rock, Indie,
	 RnB, Jazz, Soul, Classical, Kpop, Metal, Punk, Reggae, Blues, Funk};
// line 8 ../../../../../domainModelJava.ump
  private Genre genre ;

  
}