package ca.mcgill.ecse321.homeAudioSystem.persistence;

import java.util.Iterator;

import ca.mcgill.ecse321.homeAudioSystem.model.Album;
import ca.mcgill.ecse321.homeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.homeAudioSystem.model.Song;
import ca.mcgill.ecse321.homeAudioSystem.model.Artist;
import ca.mcgill.ecse321.homeAudioSystem.model.GroupLocation;
import ca.mcgill.ecse321.homeAudioSystem.model.Location;
import ca.mcgill.ecse321.homeAudioSystem.model.Manager;

/**
 * Responsible for managing the Saving and Loading
 */
public class PersistenceHAS
{
	/**
	 * Initializes Xstream
	 */
	private static void initializeXStream()
	{
		PersistenceXstream.setFilename("HASsetting.xml");
		PersistenceXstream.setAlias("Album", Album.class);
		PersistenceXstream.setAlias("Playlist", Playlist.class);
		PersistenceXstream.setAlias("Artist", Artist.class);
		PersistenceXstream.setAlias("Song", Song.class);
		PersistenceXstream.setAlias("Location", Location.class);
		PersistenceXstream.setAlias("GroupLocation", GroupLocation.class);
		PersistenceXstream.setAlias("Manager", Manager.class);
	}

	/**
	 * Replaces the current model data with one saved to storage if available
	 */
	public static void loadHASModel()
	{
		Manager rm = Manager.getInstance();
		PersistenceHAS.initializeXStream();
		Manager rm2 = (Manager) PersistenceXstream.loadFromXMLwithXStream();

		if (rm2 != null) {
			Iterator<Song> songIt = rm2.getSongs().iterator();
			while (songIt.hasNext())
				rm.addSong(songIt.next());

			Iterator<Album> albumIt = rm2.getAlbums().iterator();
			while (albumIt.hasNext())
				rm.addAlbum(albumIt.next());

			Iterator<Artist> artistIt = rm2.getArtists().iterator();
			while (artistIt.hasNext())
				rm.addArtist(artistIt.next());

			Iterator<Playlist> playlistIt = rm2.getPlaylists().iterator();
			while (playlistIt.hasNext())
				rm.addPlaylist(playlistIt.next());

			Iterator<Location> locationIt = rm2.getLocations().iterator();
			while (locationIt.hasNext())
				rm.addLocation(locationIt.next());

			Iterator<GroupLocation> groupLocationIt = rm2.getGroupLocations().iterator();
			while (groupLocationIt.hasNext())
				rm.addGroupLocation(groupLocationIt.next());
		}
	}
}
