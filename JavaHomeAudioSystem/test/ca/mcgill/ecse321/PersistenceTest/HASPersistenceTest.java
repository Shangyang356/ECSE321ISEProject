package ca.mcgill.ecse321.PersistenceTest;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.mcgill.ecse321.homeAudioSystem.model.Album;
import ca.mcgill.ecse321.homeAudioSystem.model.Artist;
import ca.mcgill.ecse321.homeAudioSystem.model.GroupLocation;
import ca.mcgill.ecse321.homeAudioSystem.model.Location;
import ca.mcgill.ecse321.homeAudioSystem.model.Manager;
import ca.mcgill.ecse321.homeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.homeAudioSystem.model.Song;
import ca.mcgill.ecse321.homeAudioSystem.persistence.PersistenceXstream;

public class HASPersistenceTest
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		PersistenceXstream.setFilename("test" + File.separator + "ca" + File.separator + "mcgill" + File.separator + "ecse321" + File.separator + "PersistenceTest" + File.separator + "HASsetting.xml");

		Manager m = Manager.getInstance();

		// save a test case
		String albumname = "yellow sub";
		String albumname2 = "yellow sub2";
		String playlistname = "myplaylist1";
		String playlistname2 = "myplaylist2";
		String artistname = "aaa";
		String artistname2 = "bbb";
		Calendar c = Calendar.getInstance();
		c.set(2015,Calendar.SEPTEMBER,15,8,30,0);
		Date releasedate = new Date(c.getTimeInMillis());
		String songname = "songtest";
		Time duration =  java.sql.Time.valueOf("00:03:00");
		String Locationname = "bedroom";
		String GroupLocation1name = "GroupLocation0";
		String GroupLocation2name = "";
		
		Album album = new Album(albumname, releasedate);
		Album album2 = new Album(albumname2,releasedate);
		Playlist playlist = new Playlist(playlistname);
		Playlist playlist2 = new Playlist(playlistname2);
		Artist artist = new Artist(artistname);
		Artist artist2 = new Artist(artistname2);
		Location bedroom = new Location(Locationname,50);
		Song song = new Song(songname,duration);
		GroupLocation group1 = new GroupLocation(GroupLocation1name);
		GroupLocation group2 = new GroupLocation(GroupLocation2name);
		album.setGenre(Album.Genre.Rock);
		album2.setGenre(Album.Genre.Rock);
			
		m.addAlbum(album);
		m.addAlbum(album2);
		m.addArtist(artist);
		m.addArtist(artist2);
		m.addPlaylist(playlist);
		m.addPlaylist(playlist2);
		m.addSong(song);
		m.addLocation(bedroom);
		m.addGroupLocation(group1);
		m.addGroupLocation(group2);
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	@After
	public void tearDown() throws Exception
	{
		Manager m = Manager.getInstance();
		m.delete();
	}

	@Test
	public void test()
	{
		PersistenceXstream.setFilename("test" + File.separator + "ca" + File.separator + "mcgill" + File.separator + "ecse321" + File.separator + "PersistenceTest" + File.separator + "HASsetting.xml");

		Manager m = Manager.getInstance();
		
		//clear the model in memory
		m.delete();
		
		// load the test file and check if all the contained values are as expected
		m = (Manager)PersistenceXstream.loadFromXMLwithXStream();
		
		if (m == null)
		{
			fail("Could not load file.");
		}
		
		assertEquals(2,m.getAlbums().size());
		assertEquals("yellow sub",m.getAlbum(0).getName());
		assertEquals("yellow sub2",m.getAlbum(1).getName());
		Calendar c = Calendar.getInstance();
		c.set(2015, Calendar.SEPTEMBER,15,8,30,0);
		Date releasedate = new Date(c.getTimeInMillis());
		
		assertEquals(releasedate.toString(),m.getAlbum(0).getReleaseDate().toString());
		assertEquals(releasedate.toString(),m.getAlbum(1).getReleaseDate().toString());
		
		assertEquals(2,m.getArtists().size());
		assertEquals("myplaylist1",m.getPlaylist(0).getName());
		assertEquals("myplaylist2",m.getPlaylist(1).getName());
		
		assertEquals(2,m.getPlaylists().size());
		assertEquals("aaa",m.getArtist(0).getName());
		assertEquals("bbb",m.getArtist(1).getName());
		
		assertEquals(1,m.getSongs().size());
		assertEquals("songtest",m.getSong(0).getName());
		assertEquals(java.sql.Time.valueOf("00:03:00"),m.getSong(0).getDuration());
		
		assertEquals(1,m.getLocations().size());
		assertEquals("bedroom",m.getLocation(0).getName());
		assertEquals(50,m.getLocation(0).getVolume());
		
		assertEquals(2,m.getGroupLocations().size());
		assertEquals("GroupLocation0",m.getGroupLocation(0).getName());
		assertEquals("",m.getGroupLocation(1).getName());	
	}
}
