/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

package ca.mcgill.ecse321.homeAudioSystem.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 66 "../../../../../domainModelJava.ump"
// line 5 "../../../../../domainModel.ump"
// line 70 "../../../../../domainModel.ump"
public class Manager
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Manager theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations
  private List<Album> albums;
  private List<Artist> artists;
  private List<Playlist> playlists;
  private List<Location> locations;
  private List<Song> songs;
  private List<GroupLocation> groupLocations;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private Manager()
  {
    albums = new ArrayList<Album>();
    artists = new ArrayList<Artist>();
    playlists = new ArrayList<Playlist>();
    locations = new ArrayList<Location>();
    songs = new ArrayList<Song>();
    groupLocations = new ArrayList<GroupLocation>();
  }

  public static Manager getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new Manager();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public Album getAlbum(int index)
  {
    Album aAlbum = albums.get(index);
    return aAlbum;
  }

  public List<Album> getAlbums()
  {
    List<Album> newAlbums = Collections.unmodifiableList(albums);
    return newAlbums;
  }

  public int numberOfAlbums()
  {
    int number = albums.size();
    return number;
  }

  public boolean hasAlbums()
  {
    boolean has = albums.size() > 0;
    return has;
  }

  public int indexOfAlbum(Album aAlbum)
  {
    int index = albums.indexOf(aAlbum);
    return index;
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

  public Playlist getPlaylist(int index)
  {
    Playlist aPlaylist = playlists.get(index);
    return aPlaylist;
  }

  public List<Playlist> getPlaylists()
  {
    List<Playlist> newPlaylists = Collections.unmodifiableList(playlists);
    return newPlaylists;
  }

  public int numberOfPlaylists()
  {
    int number = playlists.size();
    return number;
  }

  public boolean hasPlaylists()
  {
    boolean has = playlists.size() > 0;
    return has;
  }

  public int indexOfPlaylist(Playlist aPlaylist)
  {
    int index = playlists.indexOf(aPlaylist);
    return index;
  }

  public Location getLocation(int index)
  {
    Location aLocation = locations.get(index);
    return aLocation;
  }

  public List<Location> getLocations()
  {
    List<Location> newLocations = Collections.unmodifiableList(locations);
    return newLocations;
  }

  public int numberOfLocations()
  {
    int number = locations.size();
    return number;
  }

  public boolean hasLocations()
  {
    boolean has = locations.size() > 0;
    return has;
  }

  public int indexOfLocation(Location aLocation)
  {
    int index = locations.indexOf(aLocation);
    return index;
  }

  public Song getSong(int index)
  {
    Song aSong = songs.get(index);
    return aSong;
  }

  public List<Song> getSongs()
  {
    List<Song> newSongs = Collections.unmodifiableList(songs);
    return newSongs;
  }

  public int numberOfSongs()
  {
    int number = songs.size();
    return number;
  }

  public boolean hasSongs()
  {
    boolean has = songs.size() > 0;
    return has;
  }

  public int indexOfSong(Song aSong)
  {
    int index = songs.indexOf(aSong);
    return index;
  }

  public GroupLocation getGroupLocation(int index)
  {
    GroupLocation aGroupLocation = groupLocations.get(index);
    return aGroupLocation;
  }

  public List<GroupLocation> getGroupLocations()
  {
    List<GroupLocation> newGroupLocations = Collections.unmodifiableList(groupLocations);
    return newGroupLocations;
  }

  public int numberOfGroupLocations()
  {
    int number = groupLocations.size();
    return number;
  }

  public boolean hasGroupLocations()
  {
    boolean has = groupLocations.size() > 0;
    return has;
  }

  public int indexOfGroupLocation(GroupLocation aGroupLocation)
  {
    int index = groupLocations.indexOf(aGroupLocation);
    return index;
  }

  public static int minimumNumberOfAlbums()
  {
    return 0;
  }

  public boolean addAlbum(Album aAlbum)
  {
    boolean wasAdded = false;
    if (albums.contains(aAlbum)) { return false; }
    albums.add(aAlbum);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAlbum(Album aAlbum)
  {
    boolean wasRemoved = false;
    if (albums.contains(aAlbum))
    {
      albums.remove(aAlbum);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addAlbumAt(Album aAlbum, int index)
  {  
    boolean wasAdded = false;
    if(addAlbum(aAlbum))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlbums()) { index = numberOfAlbums() - 1; }
      albums.remove(aAlbum);
      albums.add(index, aAlbum);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAlbumAt(Album aAlbum, int index)
  {
    boolean wasAdded = false;
    if(albums.contains(aAlbum))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAlbums()) { index = numberOfAlbums() - 1; }
      albums.remove(aAlbum);
      albums.add(index, aAlbum);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAlbumAt(aAlbum, index);
    }
    return wasAdded;
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

  public static int minimumNumberOfPlaylists()
  {
    return 0;
  }

  public boolean addPlaylist(Playlist aPlaylist)
  {
    boolean wasAdded = false;
    if (playlists.contains(aPlaylist)) { return false; }
    playlists.add(aPlaylist);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlaylist(Playlist aPlaylist)
  {
    boolean wasRemoved = false;
    if (playlists.contains(aPlaylist))
    {
      playlists.remove(aPlaylist);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addPlaylistAt(Playlist aPlaylist, int index)
  {  
    boolean wasAdded = false;
    if(addPlaylist(aPlaylist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlaylists()) { index = numberOfPlaylists() - 1; }
      playlists.remove(aPlaylist);
      playlists.add(index, aPlaylist);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlaylistAt(Playlist aPlaylist, int index)
  {
    boolean wasAdded = false;
    if(playlists.contains(aPlaylist))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlaylists()) { index = numberOfPlaylists() - 1; }
      playlists.remove(aPlaylist);
      playlists.add(index, aPlaylist);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlaylistAt(aPlaylist, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfLocations()
  {
    return 0;
  }

  public boolean addLocation(Location aLocation)
  {
    boolean wasAdded = false;
    if (locations.contains(aLocation)) { return false; }
    locations.add(aLocation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLocation(Location aLocation)
  {
    boolean wasRemoved = false;
    if (locations.contains(aLocation))
    {
      locations.remove(aLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addLocationAt(Location aLocation, int index)
  {  
    boolean wasAdded = false;
    if(addLocation(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLocationAt(Location aLocation, int index)
  {
    boolean wasAdded = false;
    if(locations.contains(aLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLocations()) { index = numberOfLocations() - 1; }
      locations.remove(aLocation);
      locations.add(index, aLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLocationAt(aLocation, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfSongs()
  {
    return 0;
  }

  public boolean addSong(Song aSong)
  {
    boolean wasAdded = false;
    if (songs.contains(aSong)) { return false; }
    songs.add(aSong);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSong(Song aSong)
  {
    boolean wasRemoved = false;
    if (songs.contains(aSong))
    {
      songs.remove(aSong);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addSongAt(Song aSong, int index)
  {  
    boolean wasAdded = false;
    if(addSong(aSong))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSongs()) { index = numberOfSongs() - 1; }
      songs.remove(aSong);
      songs.add(index, aSong);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSongAt(Song aSong, int index)
  {
    boolean wasAdded = false;
    if(songs.contains(aSong))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSongs()) { index = numberOfSongs() - 1; }
      songs.remove(aSong);
      songs.add(index, aSong);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSongAt(aSong, index);
    }
    return wasAdded;
  }

  public static int minimumNumberOfGroupLocations()
  {
    return 0;
  }

  public boolean addGroupLocation(GroupLocation aGroupLocation)
  {
    boolean wasAdded = false;
    if (groupLocations.contains(aGroupLocation)) { return false; }
    groupLocations.add(aGroupLocation);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGroupLocation(GroupLocation aGroupLocation)
  {
    boolean wasRemoved = false;
    if (groupLocations.contains(aGroupLocation))
    {
      groupLocations.remove(aGroupLocation);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addGroupLocationAt(GroupLocation aGroupLocation, int index)
  {  
    boolean wasAdded = false;
    if(addGroupLocation(aGroupLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGroupLocations()) { index = numberOfGroupLocations() - 1; }
      groupLocations.remove(aGroupLocation);
      groupLocations.add(index, aGroupLocation);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGroupLocationAt(GroupLocation aGroupLocation, int index)
  {
    boolean wasAdded = false;
    if(groupLocations.contains(aGroupLocation))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGroupLocations()) { index = numberOfGroupLocations() - 1; }
      groupLocations.remove(aGroupLocation);
      groupLocations.add(index, aGroupLocation);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGroupLocationAt(aGroupLocation, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    albums.clear();
    artists.clear();
    playlists.clear();
    locations.clear();
    songs.clear();
    groupLocations.clear();
  }

  // line 70 "../../../../../domainModelJava.ump"
   public void sortSongs(Comparator<Song> comparator){
    Collections.sort(songs, comparator);
  }

  // line 75 "../../../../../domainModelJava.ump"
   public void sortArtists(Comparator<Artist> comparator){
    Collections.sort(artists, comparator);
  }

  // line 80 "../../../../../domainModelJava.ump"
   public void sortAlbums(Comparator<Album> comparator){
    Collections.sort(albums, comparator);
  }

  // line 85 "../../../../../domainModelJava.ump"
   public void sortPlaylists(Comparator<Playlist> comparator){
    Collections.sort(playlists, comparator);
  }

  // line 90 "../../../../../domainModelJava.ump"
   public void sortLocations(Comparator<Location> comparator){
    Collections.sort(locations, comparator);
  }

  // line 95 "../../../../../domainModelJava.ump"
   public void sortGroupLocations(Comparator<GroupLocation> comparator){
    Collections.sort(groupLocations, comparator);
  }

}