package ca.mcgill.ecse321.homeAudioSystem.controller;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import ca.mcgill.ecse321.homeAudioSystem.model.Album;
import ca.mcgill.ecse321.homeAudioSystem.model.Album.Genre;
import ca.mcgill.ecse321.homeAudioSystem.model.Artist;
import ca.mcgill.ecse321.homeAudioSystem.model.GroupLocation;
import ca.mcgill.ecse321.homeAudioSystem.model.Location;
import ca.mcgill.ecse321.homeAudioSystem.model.Manager;
import ca.mcgill.ecse321.homeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.homeAudioSystem.model.Song;
import ca.mcgill.ecse321.homeAudioSystem.persistence.PersistenceXstream;

/**
 * Handles the modification of data stored in the model.
 */
public class HASController
{
	/**
	 * Adds a new song to the model.
	 * 
	 * @param name Name of Song.
	 * @param duration Duration of Song.
	 * @return The newly created song.
	 * @throws InvalidInputException when the parameters are invalid information for a song.
	 */
	public Song addSong(String name, Time duration) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Song name is missing! ";
		}

		Calendar date = Calendar.getInstance();
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		if (duration == null || duration.toString().trim().length() == 0) {
			error = error + "Song duration is missing! ";
		}
		else if (duration.toString().equals(new Time(date.getTimeInMillis()).toString())) {
			error = error + "Song needs a duration greater than zero! ";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
			
			Song song = new Song(name, duration);

			manager.addSong(song);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return song;
		}
	}

	/**
	 * Adds a new artist to the model.
	 * 
	 * @param name The name of the artist
	 * @param songs The songs that the artist made.
	 * @return The newly created artist.
	 * @throws InvalidInputException when the parameters are invalid information for an artist.
	 */
	public Artist addArtist(String name, List<Song> songs) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Artist name is missing! ";
		}
		
		if (songs == null || songs.size() == 0) {
			error = error + "Artist songs are missing! ";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
			
			Artist artist = new Artist(name);
			for (Song song : songs) {
				artist.addSong(song);
			}
			
			manager.addArtist(artist);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return artist;
		}
	}
	
	/**
	 * Adds a new album to the model.
	 * 
	 * @param name The name of the album.
	 * @param songs The songs that are in the album.
	 * @param genre The genre of the music.
	 * @param releaseDate The release date of the album.
	 * @return The newly created album.
	 * @throws InvalidInputException when the parameters are invalid information for an album.
	 */
	public Album addAlbum(String name, List<Song> songs, Genre genre, Date releaseDate) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Album name is missing! ";
		}
		
		if (songs == null || songs.size() == 0) {
			error = error + "Album songs are missing! ";
		}

		// Searches through the artists to see which artists  
		Manager manager = Manager.getInstance();
		List<Artist> artists = new ArrayList<Artist>();
		for (Artist artist : manager.getArtists()) {
			for (Song song : artist.getSongs()) {
				if (songs.contains(song)) {
					artists.add(artist);
				}
			}
		}
		if (artists.size() == 0) {
			error = error + "Album artists are missing! ";
		}

		if (genre == null) {
			error = error + "Album genre is missing! ";
		}

		if (releaseDate == null || releaseDate.toString().length() == 0) {
			error = error + "Album release date is missing! ";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Album album = new Album(name, releaseDate);
			album.setGenre(genre);
			for (Song song : songs) {
				album.addSong(song);
			}
			for (Artist artist : artists) {
				album.addArtist(artist);
			}

			manager.addAlbum(album);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return album;
		}
	}

	/**
	 * Adds a new playlist to the model.
	 * 
	 * @param name The name of the playlist.
	 * @param songs The songs to be in the playlist.
	 * @return The newly created playlist.
	 * @throws InvalidInputException when the parameters are invalid information for a playlist.
	 */
	public Playlist addPlaylist(String name, List<Song> songs) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Playlist name is missing! ";
		}

		if (songs == null || songs.size() == 0) {
			error = error + "Playlist songs are missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
			
			Playlist playlist = new Playlist(name);
			for (Song song : songs) {
				playlist.addSong(song);
			}
			
			manager.addPlaylist(playlist);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return playlist;
		}
	}

	/**
	 * Adds a new home location to the model.
	 * 
	 * @param name The name of the location.
	 * @return The newly created location.
	 * @throws InvalidInputException when the parameters are invalid information for a location.
	 */
	public Location addLocation(String name) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Location name is missing! ";
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();

			int Volume = 100;
			Location location = new Location(name, Volume);

			manager.addLocation(location);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return location;
		}
	}

	/**
	 * Adds a new group location to the model.
	 * 
	 * @param name The name of the group location.
	 * @return The newly created group.
	 * @throws InvalidInputException when the parameters are invalid information for a group location.
	 */
	public GroupLocation addGroupLocation(String name, List<Location> locations) throws InvalidInputException
	{
		String error = "";

		if (name == null || name.trim().length() == 0) {
			error = error + "Group location name is missing! ";
		}
		
		if (locations == null || locations.size() == 0) {
			error = error + "Locations are missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
			
			GroupLocation grouploc = new GroupLocation(name);
			for (Location location : locations) {
				grouploc.addLocation(location);
			}

			manager.addGroupLocation(grouploc);
			PersistenceXstream.saveToXMLwithXStream(manager);
			
			return grouploc;
		}
	}

	/**
	 * Delete one song from the model and its references in any playlist, album, artist, or location.
	 * If a playlist, album or artist becomes empty it is removed.
	 * 
	 * @param song The song to removed.
	 */
	public void deleteSong(Song song)
	{
		Manager m = Manager.getInstance();
		for (Album album : m.getAlbums()) {
			album.removeSong(song);
		}
		for (Artist artist : m.getArtists()) {
			artist.removeSong(song);
		}
		for (Playlist playlist : m.getPlaylists()) {
			playlist.removeSong(song);
		}
		removeMusicFromAllLocations(song);
		m.removeSong(song);
		removeIncomplete();
		PersistenceXstream.saveToXMLwithXStream(m);
	}

	/**
	 * Delete one artist from the model and its reference in playlists.
	 * 
	 * @param artist
	 */
	public void deleteArtist(Artist artist)
	{
		Manager m = Manager.getInstance();
		for (Album album : m.getAlbums()) {
			album.removeArtist(artist);
		}
		m.removeArtist(artist);
		removeIncomplete();
		PersistenceXstream.saveToXMLwithXStream(m);
	}

	/**
	 * Delete one album from the model.
	 * 
	 * @param album
	 */
	public void deleteAlbum(Album album)
	{
		Manager m = Manager.getInstance();
		m.removeAlbum(album);
		PersistenceXstream.saveToXMLwithXStream(m);
	}

	/**
	 * Delete one playlist from the model.
	 * 
	 * @param playlist
	 */
	public void deletePlaylist(Playlist playlist)
	{
		Manager m = Manager.getInstance();
		m.removePlaylist(playlist);
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Delete one location from the manager and any location groups it is a part of.
	 * If a location group becomes empty it is removed.
	 * 
	 * @param location
	 */
	public void deleteLocation(Location location)
	{
		Manager m = Manager.getInstance();
		for (GroupLocation locGroup : m.getGroupLocations()) {
			locGroup.removeLocation(location);
		}
		m.removeLocation(location);
		removeIncomplete();
		PersistenceXstream.saveToXMLwithXStream(m);
	}

	/**
	 * Delete one group location from the model.
	 * 
	 * @param locGroup
	 */
	public void deleteGroupLocation(GroupLocation locGroup)
	{
		Manager m = Manager.getInstance();
		m.removeGroupLocation(locGroup);
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Removes data structures in the model that are incomplete as data they depend on was deleted.
	 */
	private void removeIncomplete()
	{
		Manager m = Manager.getInstance();
		for (Album album : new ArrayList<Album>(m.getAlbums())) {
			if (album.numberOfSongs() == 0 || album.numberOfArtists() == 0) {
				deleteAlbum(album);
			}
		}
		for (Artist artist :  new ArrayList<Artist>(m.getArtists())) {
			if (artist.numberOfSongs() == 0) {
				deleteArtist(artist);
			}
		}
		for (Playlist playlist :  new ArrayList<Playlist>(m.getPlaylists())) {
			if (playlist.numberOfSongs() == 0) {
				deletePlaylist(playlist);
			}
		}
		for (GroupLocation locGroup :  new ArrayList<GroupLocation>(m.getGroupLocations())) {
			if (locGroup.numberOfLocations() == 0) {
				deleteGroupLocation(locGroup);
			}
		}
	}

	/**
	 * Adds songs to play at the locations in every group 
	 * 
	 * @param locGroups Location groups containing the locations where the music will be played.
	 * @param songs The songs to be played.
	 * @throws InvalidInputException When the parameters are invalid lists of locations or songs.
	 */
	public void assignMusicToGroups(List<GroupLocation> locGroups, List<Song> songs) throws InvalidInputException
	{
		String error = "";

		if (locGroups == null || locGroups.size() == 0) {
			error = error + "Group Locations are missing! ";
		}
		
		if (songs == null || songs.size() == 0) {
			error = error + "Songs are missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
			
			for (GroupLocation groupLoc : locGroups) {
				for (Location loc : groupLoc.getLocations()) {
					for (Song song : songs) {
						loc.addSong(song);
					}
				}
			}
			
			PersistenceXstream.saveToXMLwithXStream(manager);
		}
	}

	/**
	 * Adds songs to play at the locations
	 * 
	 * @param locations Locations where the music will be played.
	 * @param songs The songs to be played.
	 * @throws InvalidInputException When the parameters are invalid lists of locations or songs.
	 */
	public void assignMusicToLocations(List<Location> locations, List<Song> songs) throws InvalidInputException
	{
		String error = "";

		if (locations == null || locations.size() == 0) {
			error = error + "Locations are missing! ";
		}
		
		if (songs == null || songs.size() == 0) {
			error = error + "Songs are missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager manager = Manager.getInstance();
		
			for (Location loc : locations) {
				for (Song song : songs) {
					loc.addSong(song);
				}
			}
			
			PersistenceXstream.saveToXMLwithXStream(manager);
		}
	}
	
	/**
	 * Removes a song from any location groups that use it.
	 * 
	 * @param song The song to be removed.
	 */
	public void removeMusicFromAllLocations(Song song)
	{
		Manager m = Manager.getInstance();
		
		for (Location loc : m.getLocations()) {
			loc.removeSong(song);
		}
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}

	/**
	 * Set the Volume of the locations in a location group. If the volume is
	 * higher than 100 brings it down to 100. If lower than 0 brings it to 0.
	 * 
	 * @param Volume The volume to be assigned to the group.
	 * @param groupLoc The group location to modify the volume of.
	 * @throws InvalidInputException When groupLoc is null.
	 */
	public void setVolume(int volume, GroupLocation groupLoc) throws InvalidInputException
	{
		String error = "";

		if (groupLoc == null) {
			error = error + "Group location is missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager m = Manager.getInstance();
			
			for (Location loc : groupLoc.getLocations()) {
				loc.setVolume(Math.max(Math.min(volume, 100), 0));
			}
		
			PersistenceXstream.saveToXMLwithXStream(m);
		}
	}

	/**
	 * Set the Volume of a location. If the volume is higher than 100 brings it
	 * down to 100. If lower than 0 brings it to 0.
	 * 
	 * @param Volume The volume to be assigned to the group.
	 * @param location The location to modify the volume of.
	 * @throws InvalidInputException When location is null.
	 */
	public void setVolume(int volume, Location location) throws InvalidInputException
	{
		String error = "";

		if (location == null) {
			error = error + "Location is missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager m = Manager.getInstance();
			location.setVolume(Math.max(Math.min(volume, 100), 0));
			PersistenceXstream.saveToXMLwithXStream(m);
		}
	}

	/**
	 * Changes the muting of a single location
	 * 
	 * @param location The location where the muting is being set.
	 * @param mute Whether or not to play music at this location.
	 * @throws InvalidInputException When location is null.
	 */
	public void setLocationMute(Location location, boolean mute) throws InvalidInputException
	{
		String error = "";

		if (location == null) {
			error = error + "Location is missing! ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		} else {
			Manager m = Manager.getInstance();
			location.setIsMuted(mute);
			PersistenceXstream.saveToXMLwithXStream(m);
		}
	}

	/**
	 * Sorts the songs in the model comparing by name.
	 */
	public void sortSongsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortSongs(new Comparator<Song>() {
			@Override
			public int compare(final Song song1, final Song song2)
			{
				return song1.getName().compareToIgnoreCase(song2.getName());
			}
		});
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Sorts the artists in the model comparing by name.
	 */
	public void sortArtistsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortArtists(new Comparator<Artist>() {
			@Override
			public int compare(final Artist a1, final Artist a2)
			{
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Sorts the albums in the model comparing by name.
	 */
	public void sortAlbumsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortAlbums(new Comparator<Album>() {
			@Override
			public int compare(final Album a1, final Album a2)
			{
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Sorts the playlists in the model comparing by name.
	 */
	public void sortPlaylistsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortPlaylists(new Comparator<Playlist>() {
			@Override
			public int compare(final Playlist a1, final Playlist a2)
			{
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Sorts the locations in the model comparing by name.
	 */
	public void sortLocationsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortLocations(new Comparator<Location>() {
			@Override
			public int compare(final Location a1, final Location a2)
			{
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});
		
		PersistenceXstream.saveToXMLwithXStream(m);
	}
	
	/**
	 * Sorts the group locations in the model comparing by name.
	 */
	public void sortGroupLocationsByName()
	{
		Manager m = Manager.getInstance();
		
		m.sortGroupLocations(new Comparator<GroupLocation>() {
			@Override
			public int compare(final GroupLocation a1, final GroupLocation a2)
			{
				return a1.getName().compareToIgnoreCase(a2.getName());
			}
		});
		PersistenceXstream.saveToXMLwithXStream(m);
	}
}