package ca.mcgill.ecse321.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.homeAudioSystem.controller.HASController;
import ca.mcgill.ecse321.homeAudioSystem.model.Album;
import ca.mcgill.ecse321.homeAudioSystem.model.Album.Genre;
import ca.mcgill.ecse321.homeAudioSystem.model.Artist;
import ca.mcgill.ecse321.homeAudioSystem.model.GroupLocation;
import ca.mcgill.ecse321.homeAudioSystem.model.Location;
import ca.mcgill.ecse321.homeAudioSystem.model.Manager;
import ca.mcgill.ecse321.homeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.homeAudioSystem.model.Song;
import ca.mcgill.ecse321.homeAudioSystem.persistence.PersistenceXstream;

public class HASControllerTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		PersistenceXstream.setFilename("test"+File.separator+"ca"+File.separator+"mcgill"+File.separator+"ecse321"+File.separator+"controllerTest"
				+File.separator+"data.xml");

		PersistenceXstream.setAlias("Album", Album.class);
		PersistenceXstream.setAlias("Playlist", Playlist.class);
		PersistenceXstream.setAlias("Artist", Artist.class);
		PersistenceXstream.setAlias("Song", Song.class);
		PersistenceXstream.setAlias("Location", Location.class);
		PersistenceXstream.setAlias("GroupLocation", GroupLocation.class);
		PersistenceXstream.setAlias("Manager", Manager.class);
	}

	@After
	public void tearDown() throws Exception
	{
		Manager rm = Manager.getInstance();
		rm.delete();
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createSongNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getSongs().size());

		String songName = null;
		Time songDuration = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addSong(songName, songDuration);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Song name is missing! Song duration is missing! ", error);
		assertEquals(0, m.getSongs().size());
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void createSongEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getSongs().size());

		String name = "";
		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		Time duration = new Time(date.getTimeInMillis());
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addSong(name, duration);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Song name is missing! Song needs a duration greater than zero! ", error);
		assertEquals(0, m.getSongs().size());
	}

	/**
	 * Tests the successful creation of a single song
	 */
	@Test
	public void createSong() 
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getSongs().size());

		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		
		HASController hasC = new HASController();
		
		try {
			hasC.addSong(songName, songDuration);
		} catch (Exception e) {
			fail("Song should have been created!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(1, m2.getSongs().size());
		assertEquals(songName, m2.getSong(0).getName());
		assertEquals(songDuration, m2.getSong(0).getDuration());		
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createArtistNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getPlaylists().size());

		String artistName = null;
		List<Song> songs = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addArtist(artistName, songs);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Artist name is missing! Artist songs are missing! ", error);
		assertEquals(0, m.getArtists().size());
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void createArtistEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getPlaylists().size());

		String artistName = "";
		List<Song> songs = new ArrayList<Song>();
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addArtist(artistName, songs);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Artist name is missing! Artist songs are missing! ", error);
		assertEquals(0, m.getArtists().size());
	}

	/**
	 * Tests the successful creation of an artist containing one song
	 */
	@Test
	public void createArtist()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getArtists().size());

		String artistName = "The Beatles";
		
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		HASController hasC = new HASController();

		try {
			hasC.addArtist(artistName, songs);
		} catch (Exception e) {
			fail("Artist should have been created!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		assertEquals(1, m2.getArtists().size());
		assertEquals(artistName, m2.getArtist(0).getName());
		assertEquals(songName, m2.getArtist(0).getSong(0).getName());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createAlbumNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getAlbums().size());

		String name = null;
		Date releaseDate = null;
		List<Song> songs = null;

		String error = null;
		HASController hasC = new HASController();
		
		try {
			hasC.addAlbum(name, songs, null, releaseDate);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Album name is missing! Album songs are missing! Album artists are missing! Album genre is missing! Album release date is missing! ",error);
		assertEquals(m.getAlbums().size(), 0);
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void createAlbumEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getAlbums().size());
		
		List<Song> songs = new ArrayList<Song>();
		String name = "";
		Date releaseDate = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addAlbum(name, songs, null , releaseDate);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Album name is missing! Album songs are missing! Album artists are missing! Album genre is missing! Album release date is missing! ",error);
		assertEquals(m.getAlbums().size(), 0);
	}

	/**
	 * Tests the successful creation of an album containing one song and one artist
	 */
	@Test
	public void createAlbum()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getAlbums().size());

		String albumName = "Yellow Submarine";
		
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		Artist artist = new Artist("The Beatles");
		artist.addSong(song);
		List<Artist> artists = new ArrayList<Artist>();
		artists.add(artist);
		m.addArtist(artist);
		
		Genre albumGenre = Genre.Rock;
		
		Calendar c = Calendar.getInstance();
		Date releaseDate = new Date(c.getTimeInMillis());
		
		HASController hasC = new HASController();

		try {
			hasC.addAlbum(albumName, songs, albumGenre, releaseDate);
		} catch (Exception e) {
			fail("Album should have been created!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(1, m2.getAlbums().size());
		assertEquals("Yellow Submarine", m2.getAlbum(0).getName());
		assertEquals(albumGenre, (m2.getAlbum(0).getGenre()));
		assertEquals(releaseDate.toString(), m2.getAlbum(0).getReleaseDate().toString());
		assertEquals("The Beatles", m2.getAlbum(0).getArtist(0).getName());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createPlaylistNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getPlaylists().size());

		String name = null;
		List<Song> songs = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addPlaylist(name, songs);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Playlist name is missing! Playlist songs are missing! ", error);
		assertEquals(0, m.getPlaylists().size());
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void createPlaylistEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getPlaylists().size());

		String name = "";
		List<Song> songs = new ArrayList<Song>();
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addPlaylist(name, songs);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Playlist name is missing! Playlist songs are missing! ", error);
		assertEquals(0, m.getPlaylists().size());
	}

	/**
	 * Tests the successful creation of an playlist containing one song
	 */
	@Test
	public void createPlaylist()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getPlaylists().size());

		String playlistName = "myplaylist";
		
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		HASController hasC = new HASController();

		try {
			hasC.addPlaylist(playlistName, songs);
		} catch (Exception e) {
			fail("Playlist should have been created!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(1, m2.getPlaylists().size());
		assertEquals(playlistName, m2.getPlaylist(0).getName());
		assertEquals(songName, m2.getPlaylist(0).getSong(0).getName());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createLocationNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getLocations().size());

		String locationName = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addLocation(locationName);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Location name is missing! ", error);
		assertEquals(0, m.getLocations().size());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createLocationEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getLocations().size());

		String locationName = "";
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.addLocation(locationName);
		} catch (Exception e) {
			error = e.getMessage();
		}

		assertEquals("Location name is missing! ", error);
		assertEquals(0, m.getLocations().size());
	}

	/**
	 * Tests the successful creation of a location
	 */
	@Test
	public void createLocation()
	{
		Manager m = Manager.getInstance();
		assertEquals(0, m.getLocations().size());

		String locationName = "Bedroom";

		HASController hasC = new HASController();

		try {
			hasC.addLocation(locationName);
		} catch (Exception e) {
			fail("Location should have been created!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(1, m2.getLocations().size());
		assertEquals(locationName, m2.getLocation(0).getName());
		assertEquals(100, m2.getLocation(0).getVolume());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void createGroupLocationNull()
	{
		Manager m = Manager.getInstance();
		assertEquals(0,m.getGroupLocations().size());

		String groupName = null;
		List<Location> locations = null;
		
		String error = null;		
		HASController hasC = new HASController();
		
		try {
			hasC.addGroupLocation(groupName, locations);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Group location name is missing! Locations are missing! ", error);
		assertEquals(0, m.getGroupLocations().size());
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void createGroupLocationEmpty()
	{
		Manager m = Manager.getInstance();
		assertEquals(0,m.getGroupLocations().size());

		String groupName = "";
		List<Location> locations = new ArrayList<Location>();
		
		String error = null;
		HASController hasC = new HASController();
		
		try {
			hasC.addGroupLocation(groupName, locations);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Group location name is missing! Locations are missing! ", error);
		assertEquals(0, m.getGroupLocations().size());
	}

	/**
	 * Tests the successful creation of a group location containing one location
	 */
	@Test
	public void createGroupLocation()
	{
		Manager m = Manager.getInstance();
		assertEquals(0,m.getGroupLocations().size());
		
		String groupName = "GroupLocation";

		String locationName = "Bedroom";
		int volume = 100;
		Location location = new Location(locationName, volume);
		List<Location> locations = new ArrayList<Location>();
		locations.add(location);
		
		HASController hasC = new HASController();
		
		try {
			hasC.addGroupLocation(groupName, locations);
		} catch (Exception e) {
			fail("GroupLocation should have been created!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(1, m2.getGroupLocations().size());
		assertEquals(groupName, m2.getGroupLocation(0).getName());
		assertEquals(locationName, m2.getGroupLocation(0).getLocation(0).getName());
		assertEquals(volume, m2.getGroupLocation(0).getLocation(0).getVolume());
	}
	
	/**
	 * Tests removal of a song
	 */
	@Test
	public void deleteSong()
	{
		Manager m = Manager.getInstance();

		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		m.addSong(song);

		String artistName = "The Beatles";
		Artist artist = new Artist(artistName);
		artist.addSong(song);
		m.addArtist(artist);
		
		String albumName = "Yellow Submarine";
		Calendar c = Calendar.getInstance();
		Date releaseDate = new Date(c.getTimeInMillis());
		Album album = new Album(albumName, releaseDate);
		album.addSong(song);
		m.addAlbum(album);
		
		String playlistName = "Best Songs";
		Playlist playlist = new Playlist(playlistName);
		playlist.addSong(song);
		m.addPlaylist(playlist);
		
		assertEquals(1, m.getSongs().size());
		assertEquals(1, m.getArtists().size());
		assertEquals(1, m.getAlbums().size());
		assertEquals(1, m.getPlaylists().size());
		
		HASController hasC = new HASController();

		hasC.deleteSong(song);

		assertEquals(0, m.getSongs().size());
		assertEquals(0, m.getArtists().size());
		assertEquals(0, m.getAlbums().size());
		assertEquals(0, m.getPlaylists().size());
	}
	
	/**
	 * Tests removal of an artist
	 */
	@Test
	public void deleteArtist()
	{
		Manager m = Manager.getInstance();

		String artistName = "The Beatles";
		Artist artist = new Artist(artistName);
		m.addArtist(artist);
		
		String albumName = "Yellow Submarine";
		Calendar c = Calendar.getInstance();
		Date releaseDate = new Date(c.getTimeInMillis());
		Album album = new Album(albumName, releaseDate);
		album.addArtist(artist);
		m.addAlbum(album);
		
		assertEquals(1, m.getArtists().size());
		assertEquals(1, m.getAlbums().size());
		
		HASController hasC = new HASController();

		hasC.deleteArtist(artist);

		assertEquals(0, m.getArtists().size());
		assertEquals(0, m.getAlbums().size());
	}
	
	/**
	 * Tests removal of an album
	 */
	@Test
	public void deleteAlbum()
	{
		Manager m = Manager.getInstance();

		String albumName = "Yellow Submarine";
		Calendar c = Calendar.getInstance();
		Date releaseDate = new Date(c.getTimeInMillis());
		Album album = new Album(albumName, releaseDate);
		m.addAlbum(album);
		
		assertEquals(1, m.getAlbums().size());
		
		HASController hasC = new HASController();

		hasC.deleteAlbum(album);

		assertEquals(0, m.getAlbums().size());
	}
	
	/**
	 * Tests removal of a playlist
	 */
	@Test
	public void deletePlaylist()
	{
		Manager m = Manager.getInstance();

		String playlistName = "Best Songs";
		Playlist playlist = new Playlist(playlistName);
		m.addPlaylist(playlist);
		
		assertEquals(1, m.getPlaylists().size());
		
		HASController hasC = new HASController();

		hasC.deletePlaylist(playlist);

		assertEquals(0, m.getPlaylists().size());
	}
	
	/**
	 * Tests removal of a location
	 */
	@Test
	public void deleteLocation()
	{
		Manager m = Manager.getInstance();

		String locationName = "Bedroom";
		int locationVolume = 100;
		Location location = new Location(locationName, locationVolume);
		m.addLocation(location);

		String groupName = "GroupLocation";
		GroupLocation groupLocation = new GroupLocation(groupName);
		groupLocation.addLocation(location);
		m.addGroupLocation(groupLocation);
		
		assertEquals(1, m.getLocations().size());
		assertEquals(1, m.getGroupLocations().size());
		
		HASController hasC = new HASController();

		hasC.deleteLocation(location);

		assertEquals(0, m.getLocations().size());
		assertEquals(0, m.getGroupLocations().size());
	}
	
	/**
	 * Tests removal of a group location
	 */
	@Test
	public void deleteGroupLocation()
	{
		Manager m = Manager.getInstance();

		String groupName = "GroupLocation";
		GroupLocation groupLocation = new GroupLocation(groupName);
		m.addGroupLocation(groupLocation);
		
		assertEquals(1, m.getGroupLocations().size());
		
		HASController hasC = new HASController();

		hasC.deleteGroupLocation(groupLocation);
		
		assertEquals(0, m.getGroupLocations().size());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void assignMusicToGroupsNull()
	{
		List<Song> songs = null;
		List<GroupLocation> groups = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToGroups(groups, songs);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Group Locations are missing! Songs are missing! ", error);
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void assignMusicToGroupsEmpty()
	{
		List<Song> songs = new ArrayList<Song>();
		List<GroupLocation> groups = new ArrayList<GroupLocation>();
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToGroups(groups, songs);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Group Locations are missing! Songs are missing! ", error);
	}

	/**
	 * Tests adding a couple songs to a ListOfSong to a couple group locations
	 */
	@Test
	public void assignMusicToGroups()
	{
		Manager m = Manager.getInstance();
		
		String song1Name = "A Cool Song";
		Time song1Duration =  java.sql.Time.valueOf("00:03:00");
		Song song1 = new Song(song1Name, song1Duration);
		m.addSong(song1);
		
		String song2Name = "Another Song";
		Time song2Duration =  java.sql.Time.valueOf("00:02:00");
		Song song2 = new Song(song2Name, song2Duration);
		m.addSong(song2);

		String playlistName = "Best Songs";
		Playlist playlist = new Playlist(playlistName);
		playlist.addSong(song1);
		playlist.addSong(song2);
		m.addPlaylist(playlist);

		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		m.addLocation(location1);

		String location2Name = "Kitchen";
		Location location2 = new Location(location2Name, 100);
		m.addLocation(location2);

		String group1Name = "GroupLocation1";
		GroupLocation groupLocation1 = new GroupLocation(group1Name);
		groupLocation1.addLocation(location1);
		m.addGroupLocation(groupLocation1);

		String group2Name = "GroupLocation2";
		GroupLocation groupLocation2 = new GroupLocation(group2Name);
		groupLocation2.addLocation(location2);
		m.addGroupLocation(groupLocation2);
		
		List<GroupLocation> groups = new ArrayList<GroupLocation>();
		groups.add(groupLocation1);
		groups.add(groupLocation2);

		assertEquals(0, m.getGroupLocation(0).getLocation(0).numberOfSongs());
		assertEquals(0, m.getGroupLocation(1).getLocation(0).numberOfSongs());
		
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToGroups(groups, playlist.getSongs());
		} catch(Exception e) {
			fail("Music should have been added to the group!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(2, m2.getGroupLocation(0).getLocation(0).numberOfSongs());
		assertEquals(2, m2.getGroupLocation(1).getLocation(0).numberOfSongs());
		assertEquals(song1Name, m2.getGroupLocation(0).getLocation(0).getSong(0).getName());
		assertEquals(song1Name, m2.getGroupLocation(1).getLocation(0).getSong(0).getName());
		assertEquals(song2Name, m2.getGroupLocation(0).getLocation(0).getSong(1).getName());
		assertEquals(song2Name, m2.getGroupLocation(1).getLocation(0).getSong(1).getName());
	}

	/**
	 * Tests all inputs null
	 */
	@Test
	public void assignMusicToLocationsNull()
	{
		List<Song> songs = null;
		List<Location> locations = null;
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToLocations(locations, songs);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Locations are missing! Songs are missing! ", error);
	}

	/**
	 * Tests all inputs as empty values as opposed to null, where possible
	 */
	@Test
	public void assignMusicToLocationsEmpty()
	{
		List<Song> songs = new ArrayList<Song>();
		List<Location> locations = new ArrayList<Location>();
		
		String error = null;
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToLocations(locations, songs);
		} catch(Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Locations are missing! Songs are missing! ", error);
	}

	/**
	 * Tests adding a couple songs to a couple locations
	 */
	@Test
	public void assignMusicToLocations()
	{
		Manager m = Manager.getInstance();
		
		String song1Name = "A Cool Song";
		Time song1Duration =  java.sql.Time.valueOf("00:03:00");
		Song song1 = new Song(song1Name, song1Duration);
		m.addSong(song1);
		
		String song2Name = "Another Song";
		Time song2Duration =  java.sql.Time.valueOf("00:02:00");
		Song song2 = new Song(song2Name, song2Duration);
		m.addSong(song2);

		String playlistName = "Best Songs";
		Playlist playlist = new Playlist(playlistName);
		playlist.addSong(song1);
		playlist.addSong(song2);
		m.addPlaylist(playlist);

		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		m.addLocation(location1);

		String location2Name = "Kitchen";
		Location location2 = new Location(location2Name, 100);
		m.addLocation(location2);
		
		List<Location> locations = new ArrayList<Location>();
		locations.add(location1);
		locations.add(location2);

		assertEquals(0, m.getLocation(0).numberOfSongs());
		assertEquals(0, m.getLocation(1).numberOfSongs());
		
		HASController hasC = new HASController();

		try {
			hasC.assignMusicToLocations(locations, playlist.getSongs());
		} catch(Exception e) {
			fail("Music should have been added to the locations!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(2, m2.getLocation(0).numberOfSongs());
		assertEquals(2, m2.getLocation(1).numberOfSongs());
		assertEquals(song1Name, m2.getLocation(0).getSong(0).getName());
		assertEquals(song1Name, m2.getLocation(1).getSong(0).getName());
		assertEquals(song2Name, m2.getLocation(0).getSong(1).getName());
		assertEquals(song2Name, m2.getLocation(1).getSong(1).getName());
	}

	/**
	 * Tests removing a null song from all locations
	 */
	@Test
	public void removeMusicFromAllLocationsNull()
	{
		Manager m = Manager.getInstance();
		
		String song1Name = "A Cool Song";
		Time song1Duration =  java.sql.Time.valueOf("00:03:00");
		Song song1 = new Song(song1Name, song1Duration);
		m.addSong(song1);
		
		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		location1.addSong(song1);
		m.addLocation(location1);

		String location2Name = "Kitchen";
		Location location2 = new Location(location2Name, 100);
		location2.addSong(song1);
		m.addLocation(location2);
		
		List<Location> locations = new ArrayList<Location>();
		locations.add(location1);
		locations.add(location2);

		assertEquals(1, m.getLocation(0).numberOfSongs());
		assertEquals(1, m.getLocation(1).numberOfSongs());
		
		HASController hasC = new HASController();

		try {
			hasC.removeMusicFromAllLocations(null);
		} catch(Exception e) {
			fail("Should not throw an exception!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(1, m2.getLocation(0).numberOfSongs());
		assertEquals(1, m2.getLocation(1).numberOfSongs());
	}

	/**
	 * Tests removing songs from all locations
	 */
	@Test
	public void removeMusicFromAllLocations()
	{
		Manager m = Manager.getInstance();
		
		String song1Name = "A Cool Song";
		Time song1Duration =  java.sql.Time.valueOf("00:03:00");
		Song song1 = new Song(song1Name, song1Duration);
		m.addSong(song1);
		
		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		location1.addSong(song1);
		m.addLocation(location1);

		String location2Name = "Kitchen";
		Location location2 = new Location(location2Name, 100);
		location2.addSong(song1);
		m.addLocation(location2);
		
		List<Location> locations = new ArrayList<Location>();
		locations.add(location1);
		locations.add(location2);

		assertEquals(1, m.getLocation(0).numberOfSongs());
		assertEquals(1, m.getLocation(1).numberOfSongs());
		
		HASController hasC = new HASController();

		try {
			hasC.removeMusicFromAllLocations(song1);
		} catch(Exception e) {
			fail("Music should have been removed from all locations!");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		assertEquals(0, m2.getLocation(0).numberOfSongs());
		assertEquals(0, m2.getLocation(1).numberOfSongs());
	}

	/**
	 * Tests setting the volume at a null group location
	 */
	@Test
	public void setVolumeAtGroupLocationNull()
	{
		GroupLocation groupLocation = null;
		
		String error = null;
		HASController hasC = new HASController();
		
		try {
			hasC.setVolume(50, groupLocation);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Group location is missing! ", error);
	}

	/**
	 * Tests setting the volume at a location
	 */
	@Test
	public void setVolumeAtGroupLocation()
	{
		Manager m = Manager.getInstance();
		
		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		m.addLocation(location1);

		String location2Name = "Kitchen";
		Location location2 = new Location(location2Name, 100);
		m.addLocation(location2);

		String groupName = "Rooms";
		GroupLocation groupLocation = new GroupLocation(groupName);
		groupLocation.addLocation(location1);
		groupLocation.addLocation(location2);
		m.addGroupLocation(groupLocation);

		HASController hasC = new HASController();

		assertEquals(100, m.getGroupLocation(0).getLocation(0).getVolume());
		assertEquals(100, m.getGroupLocation(0).getLocation(1).getVolume());
		
		try {
			hasC.setVolume(200, groupLocation);
			assertEquals(100, location1.getVolume());
			assertEquals(100, location1.getVolume());
			hasC.setVolume(-45, groupLocation);
			assertEquals(0, location1.getVolume());
			assertEquals(0, location1.getVolume());
			hasC.setVolume(35, groupLocation);
			assertEquals(35, location1.getVolume());
			assertEquals(35, location1.getVolume());
		} catch (Exception e) {
			fail("Should not throw exception!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(35, m2.getGroupLocation(0).getLocation(0).getVolume());
		assertEquals(35, m2.getGroupLocation(0).getLocation(1).getVolume());
	}

	/**
	 * Tests setting the volume at a null location
	 */
	@Test
	public void setVolumeAtLocationNull()
	{
		Location location1 = null;
		
		String error = null;
		HASController hasC = new HASController();
		
		try {
			hasC.setVolume(50, location1);
		} catch (Exception e) {
			error = e.getMessage();
		}
		
		assertEquals("Location is missing! ", error);
	}

	/**
	 * Tests setting the volume at a location
	 */
	@Test
	public void setVolumeAtLocation()
	{
		Manager m = Manager.getInstance();
		
		String location1Name = "Bedroom";
		Location location1 = new Location(location1Name, 100);
		m.addLocation(location1);

		HASController hasC = new HASController();
		
		try {
			hasC.setVolume(200, location1);
			assertEquals(100, location1.getVolume());
			hasC.setVolume(-30 , location1);
			assertEquals(0, location1.getVolume());
			hasC.setVolume(45 , location1);
			assertEquals(45, location1.getVolume());
		} catch (Exception e) {
			fail("Should not throw exception!");
		}

		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals(45, m2.getLocation(0).getVolume());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortSongsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortSongsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of songs by name
	 */
	@Test
	public void sortSongsByName()
	{
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		
		HASController hasC = new HASController();
		
		try {
			hasC.addSong("Bbb", songDuration);
			hasC.addSong("cba", songDuration);
			hasC.addSong("cDe", songDuration);
			hasC.addSong("ccc", songDuration);
			hasC.addSong("cab", songDuration);
			hasC.addSong("Cbb", songDuration);
			hasC.addSong("aaa", songDuration);
			hasC.sortSongsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getSong(0).getName());
		assertEquals("Bbb", m2.getSong(1).getName());
		assertEquals("cab", m2.getSong(2).getName());
		assertEquals("cba", m2.getSong(3).getName());
		assertEquals("Cbb", m2.getSong(4).getName());
		assertEquals("ccc", m2.getSong(5).getName());
		assertEquals("cDe", m2.getSong(6).getName());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortArtistsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortArtistsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of artists by name
	 */
	@Test
	public void sortArtistsByName()
	{
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		HASController hasC = new HASController();
		
		try {
			hasC.addArtist("Bbb", songs);
			hasC.addArtist("cba", songs);
			hasC.addArtist("cDe", songs);
			hasC.addArtist("ccc", songs);
			hasC.addArtist("cab", songs);
			hasC.addArtist("Cbb", songs);
			hasC.addArtist("aaa", songs);
			hasC.sortArtistsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getArtist(0).getName());
		assertEquals("Bbb", m2.getArtist(1).getName());
		assertEquals("cab", m2.getArtist(2).getName());
		assertEquals("cba", m2.getArtist(3).getName());
		assertEquals("Cbb", m2.getArtist(4).getName());
		assertEquals("ccc", m2.getArtist(5).getName());
		assertEquals("cDe", m2.getArtist(6).getName());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortAlbumsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortAlbumsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of albums by name
	 */
	@Test
	public void sortAlbumsByName()
	{
		Manager m = Manager.getInstance();
		
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		Artist artist = new Artist("The Beatles");
		artist.addSong(song);
		List<Artist> artists = new ArrayList<Artist>();
		artists.add(artist);
		m.addArtist(artist);
		
		Genre albumGenre = Genre.Rock;
		
		Calendar c = Calendar.getInstance();
		Date releaseDate = new Date(c.getTimeInMillis());
		
		HASController hasC = new HASController();
		
		try {
			hasC.addAlbum("Bbb", songs, albumGenre, releaseDate);
			hasC.addAlbum("cba", songs, albumGenre, releaseDate);
			hasC.addAlbum("cDe", songs, albumGenre, releaseDate);
			hasC.addAlbum("ccc", songs, albumGenre, releaseDate);
			hasC.addAlbum("cab", songs, albumGenre, releaseDate);
			hasC.addAlbum("Cbb", songs, albumGenre, releaseDate);
			hasC.addAlbum("aaa", songs, albumGenre, releaseDate);
			hasC.sortAlbumsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getAlbum(0).getName());
		assertEquals("Bbb", m2.getAlbum(1).getName());
		assertEquals("cab", m2.getAlbum(2).getName());
		assertEquals("cba", m2.getAlbum(3).getName());
		assertEquals("Cbb", m2.getAlbum(4).getName());
		assertEquals("ccc", m2.getAlbum(5).getName());
		assertEquals("cDe", m2.getAlbum(6).getName());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortPlaylistsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortPlaylistsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of playlists by name
	 */
	@Test
	public void sortPlaylistsByName()
	{
		String songName = "A Cool Song";
		Time songDuration =  java.sql.Time.valueOf("00:03:00");
		Song song = new Song(songName, songDuration);
		List<Song> songs = new ArrayList<Song>();
		songs.add(song);
		
		HASController hasC = new HASController();
		
		try {
			hasC.addPlaylist("Bbb", songs);
			hasC.addPlaylist("cba", songs);
			hasC.addPlaylist("cDe", songs);
			hasC.addPlaylist("ccc", songs);
			hasC.addPlaylist("cab", songs);
			hasC.addPlaylist("Cbb", songs);
			hasC.addPlaylist("aaa", songs);
			hasC.sortPlaylistsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getPlaylist(0).getName());
		assertEquals("Bbb", m2.getPlaylist(1).getName());
		assertEquals("cab", m2.getPlaylist(2).getName());
		assertEquals("cba", m2.getPlaylist(3).getName());
		assertEquals("Cbb", m2.getPlaylist(4).getName());
		assertEquals("ccc", m2.getPlaylist(5).getName());
		assertEquals("cDe", m2.getPlaylist(6).getName());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortLocationsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortLocationsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of locations by name
	 */
	@Test
	public void sortLocationsyName()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.addLocation("Bbb");
			hasC.addLocation("cba");
			hasC.addLocation("cDe");
			hasC.addLocation("ccc");
			hasC.addLocation("cab");
			hasC.addLocation("Cbb");
			hasC.addLocation("aaa");
			hasC.sortLocationsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getLocation(0).getName());
		assertEquals("Bbb", m2.getLocation(1).getName());
		assertEquals("cab", m2.getLocation(2).getName());
		assertEquals("cba", m2.getLocation(3).getName());
		assertEquals("Cbb", m2.getLocation(4).getName());
		assertEquals("ccc", m2.getLocation(5).getName());
		assertEquals("cDe", m2.getLocation(6).getName());
	}

	/**
	 * Tests sorting when there is nothing to sort
	 */
	@Test
	public void sortGroupLocationsEmpty()
	{
		HASController hasC = new HASController();
		
		try {
			hasC.sortGroupLocationsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
	}

	/**
	 * Tests sorting a bunch of group location  by name
	 */
	@Test
	public void sortGroupLocationsByName()
	{
		String locationName = "Bedroom";
		Location locaiton = new Location(locationName, 100);
		List<Location> locaitons = new ArrayList<Location>();
		locaitons.add(locaiton);
		
		HASController hasC = new HASController();
		
		try {
			hasC.addGroupLocation("Bbb", locaitons);
			hasC.addGroupLocation("cba", locaitons);
			hasC.addGroupLocation("cDe", locaitons);
			hasC.addGroupLocation("ccc", locaitons);
			hasC.addGroupLocation("cab", locaitons);
			hasC.addGroupLocation("Cbb", locaitons);
			hasC.addGroupLocation("aaa", locaitons);
			hasC.sortGroupLocationsByName();
		} catch(Exception e) {
			fail("The sort should not thow an exception");
		}
		
		Manager m2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();
		
		assertEquals("aaa", m2.getGroupLocation(0).getName());
		assertEquals("Bbb", m2.getGroupLocation(1).getName());
		assertEquals("cab", m2.getGroupLocation(2).getName());
		assertEquals("cba", m2.getGroupLocation(3).getName());
		assertEquals("Cbb", m2.getGroupLocation(4).getName());
		assertEquals("ccc", m2.getGroupLocation(5).getName());
		assertEquals("cDe", m2.getGroupLocation(6).getName());
	}
}