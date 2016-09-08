<?php

require_once(__DIR__.'\InputValidator.php');
require_once(__DIR__.'\..\Model\Album.php');
require_once(__DIR__.'\..\Model\Manager.php');
require_once(__DIR__.'\..\Model\Playlist.php');
require_once(__DIR__.'\..\Model\Location.php');
require_once(__DIR__.'\..\Model\Song.php');
require_once(__DIR__.'\..\Model\Artist.php');
require_once(__DIR__.'\..\Model\ListOfSong.php');
require_once(__DIR__.'\..\Model\GroupLocation.php');
require_once(__DIR__.'\..\Persistence\PersistenceHAS.php');

class HasController {
	public static $viewflag;
	public static $viewflag2;
	public function _construct()
	{
	}
	/*
	 * Add Album to Manager
	 * Input : String $name
	 * 			int $genre
	 * 			String $releaseDate
	 * 
	*/
	public function addAlbum($name , $genre, $releaseDate){
		$name = InputValidator::validate_input($name);
		$error ="";
		
		if($name == null || strlen($name)==0){
			$error .="@1 Album name cannot be empty! ";
				
		}
		if($genre == NULL || strlen($genre)==0){
			$error .="@2 Album genre cannot be empty! ";
				
		}
		if(strlen($releaseDate)==0 || $releaseDate == NULL){
			$error.="@3 Album releaseDate cannot be empty ";
		}
		
		if(preg_match("/^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$/", $releaseDate)!=1)
		{
			$error.="@4 Album releaseDate must be in YYYY-MM-DD format ";
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
				
		}
		else{
			
			//2. load all data
			$pm = new PersistenceHAS();
			
			$ma = $pm->loadDataFromStore();
			
			//3. add Album
			$album = new Album($name, $genre, $releaseDate);
			
			$ma->addAlbum($album);
			
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
		
	}
	
	/*
	 * Add Artist to Manager
	 * Input : String $name
	 *
	 */
	
	
	public  function addArtist($name)
	{
		$name = InputValidator::validate_input($name);
		$error ="";
		
		if($name == null || strlen($name)==0){
			$error .="@1 Artist name cannot be empty!";
		
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
		}
		else{
				
			//2. load all data
			$pm = new PersistenceHAS();
			$ma = $pm->loadDataFromStore();
				
			//3. add artist
			$artist = new Artist($name);
			
			$ma->addArtist($artist);
				
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
		
	}
	
	public function addPlaylist($name)
	{
		$name = InputValidator::validate_input($name);
		$error ="";
		
		if($name == null || strlen($name)==0){
			$error .="@1 Playlist name cannot be empty! ";
		
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
		}
		else{
		
			//2. load all data
			$pm = new PersistenceHAS();
			$ma = $pm->loadDataFromStore();
		
			//3. add playlist
			$playlist = new Playlist($name);
			
			$ma->addPlaylist($playlist);
			
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
		
	}
	
	/*
	 * Add Song to Album
	 * Input : int $albumIndex
	 * 			Song $song
	 *
	 */
	
	public function addSongToListOfSong($listOfSong,$song)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$ma->addSongToListOfSong($listOfSong, $song);
		
		
		$pm->writeDataToStore($ma);

		
	}
	
	/*
	 * Add Song to Artist
	 * Input : int $albumIndex
	 * 			Song $song
	 *
	 */
	
	
	public function addSongToArtist($artistIndex,$song)
	{
		
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$artist = $ma->getArtist_index($artistIndex);
		
		$artist->addSong($song);
		
		$pm->writeDataToStore($ma);
		
	}
	
	
	
	public function addSongToPlaylist($playlistIndex,$song)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$playlist = $ma->getPlaylist_index($playlistIndex);
		
		$playlist->addSong($song);
		
		$pm->writeDataToStore($ma);
		
		
	}
	
	/* 
	 * Add a location to Manager
	 * input: String $name
	
	*/
	public function setLocation($name)
	{
		$name = InputValidator::validate_input($name);
		$error ="";
		
		if($name == null || strlen($name)==0){
			$error .="@1 name cannot be empty! ";
		
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
		}
		else{
		
			//2. load all data
			$pm = new PersistenceHAS();
			$ma = $pm->loadDataFromStore();
		
			//3. add location
			$location = new Location($name, 50);
		
			$ma->addLocation($location);
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
		
	}
	
	
	/*
	 * Add Song to Manager
	 * input: String $name
	 * 		  String $duration
	
	 */
	public function addSong($name, $duration)
	{
		$name = InputValidator::validate_input($name);
		$error ="";
		
		if($name == null || strlen($name)==0){
			$error .="@1 Song name cannot be empty! ";
		}
		
		if(strlen($duration)==0){
			$error .= "@2 Song duration cannot be empty! ";	
		}
		
		if(preg_match("/^([0-1][0-9]|2[0-4]):(0[0-9]|1[0-2]|2[0-4]):([0-6][0-9])$/", $duration)!=1 )
		{
			$error .= "@ Song duration must be specified correctly (HH:MM:SS)! ";
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
		}
		else{
		
			//2. load all data
			$pm = new PersistenceHAS();
			$ma = $pm->loadDataFromStore();
		
			//3. add location
			$song = new Song($name, $duration);
		
			$ma->addSong($song);
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
		
	}
	
	public function createGroupLocation($name)
	{
		$name = InputValidator::validate_input($name);
		$error ="";
		
		
		if($name == null || strlen($name)==0){
			$error .="@1 name cannot be empty! ";
		}
		
		if(strlen($error)!=0){
			throw new Exception(trim($error));
		}
		else{
		
			//2. load all data
			$pm = new PersistenceHAS();
			$ma = $pm->loadDataFromStore();
		
			//3. add grouplocation
			$groupLocation = new GroupLocation($name); 
		
			$ma->addGroupLocation($groupLocation);
			//4 Write all of the data
			$pm->writeDataToStore($ma);
		}
	}
	
	
	
	public function assignLocationToGroup($groupIndex,$location)
	{
		
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$grouplocation= $ma->getGroupLocation_index($groupIndex);
		
		$grouplocation->addLocation($location);
		
		$pm->writeDataToStore($ma);
		
	}
	
	public function assignListOfSongToGroup($groupIndex, $listOfSong)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$grouplocation = $ma->getGroupLocation_index($groupIndex);
		
		$grouplocation->addListOfSong($listOfSong);
		
		$pm->writeDataToStore($ma);
		
	}
	
	public function assignSongToGroup($groupIndex,$song)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$grouplocation = $ma->getGroupLocation_index($groupIndex);
		
		$playlist = new Playlist($song->getName());
		$playlist->addSong($song);
		
		$grouplocation->addListOfSong($playlist);
		
		$pm->writeDataToStore($ma);
		
	}
	
	
	public function setVolumeByGroup($groupLocation,$volume )
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$arrayListSong = $groupLocation->getListOfSongs();
		
		if($volume < 0)
		{
			$volume = 0;
		}
		if($volume> 100)
		{
				
			$volume = 100;
		}
		
		for($x = 0; $x <= count(arrayListSong); $x++)
		{
			$location = arrayListSong[$x];
			$location->setVolume($Volume);
		}
		
		$pm->writeDataToStore($ma);
	}
	
	public function setVolumeByLoc($location,$volume)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		if($volume < 0)
		{
			$volume = 0;
		}
		if($volume> 100)
		{
			
			$volume = 100;
		}
		
		
		$location->setVolume($volume);
		
		$pm->writeDataToStore($ma);
		
	}
	
	
	public function removeAlbum($album)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$ma->removeAlbum($album);
		
		$pm->writeDataToStore($ma);
	}
	
	public function removePlaylist($playlist)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
	
		$ma->removePlaylist($playlist);
	
		$pm->writeDataToStore($ma);
	}
	
	public function removeGroupLocation($GroupLocation)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$ma->removeGroupLocation($GroupLocation);
		
		$pm->writeDataToStore($ma);		
		
	}
	
	public function removeSong($song)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$playlistS = $ma->getPlaylists();
		$albumS = $ma->getAlbums();
		$artistS = $ma->getArtists();
		$grouplocS = $ma->getGroupLocations();
		
		foreach ($albumS as $album)
		{
			$album->removeSong($song);
		}
		foreach ($artistS as $artist)
		{
			$artist->removeSong($song);
		}
		foreach($playlistS as $playlist)
		{
			$playlist->removeSong($song);
		}
		
		foreach ($grouplocS as $grouploc)
		{
			$listSongS = $grouploc->getListOfSongs();
			
			foreach($listSongS as $listSong)
			{
				if($listSong->removeSong($song))
				{
					if(count($listSong->getSongs())==0)
					{
						$grouploc->removeListOfSong($listSong);
					}
					break;
				}
				
			}
			
		}
		
		$ma->removeSong($song);
		
		$pm->writeDataToStore($ma);
		
	}
	
	public function removeArtist($artist)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$albumS = $ma->getAlbums();
		
		foreach ($albumS as $album)
		{
			$album->removeArtist($artist);
		}
		
		$ma->removeArtist($artist);
		
		$pm->writeDataToStore($ma);		
	}
	
	public function removeLocation($location)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$groupLocS = $ma->getGroupLocations();
		
		foreach ($groupLocS as $grouLoc)
		{
			$grouLoc->removeLocation($location);
		}
		
		$ma->removeLocation($location);
		
		$pm->writeDataToStore($ma);
	}
	
	public function load_song()
	{
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$mysong = "";
		foreach ($rm->getSongs() as $song){
			$a=$rm->indexOfSong($song);
			$a = $a.": ".$song->getName()."  Duration:".$song->getDuration();
			$mysong.="<option>".$a."</option>";
		}
		return $mysong;
	}
	
	public function load_playlist()
	{
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$myplaylist = "";
		foreach ($rm->getPlaylists() as $playlist){
			$a = $rm->indexOfPlaylist($playlist);
			$b = $playlist->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
			if($playlist->numberOfSongs() ==0){
				$c=$c."N/A";
			}
			else{
				foreach($playlist->getSongs() as $song){
					$c=$c.",".$song.getName();
				}
			}
	
			$myplaylist.="<option>".$a."</option>";
	}
	return $myplaylist;
	}
	
	public function load_album()
	{
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$myalbum = "";
		foreach ($rm->getAlbums() as $album){
			$a = $rm->indexOfAlbum($album);
			$b = $album->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
			if($album->numberOfSongs() ==0){
				$c=$c."N/A";
			}
			else{
				foreach($album->getSongs() as $song){
					$c=$c.",".$song.getName();
				}
			}
			
			$myalbum.="<option>".$a."</option>";
		}
		return $myalbum;
	}
	
	
	
	public function load_artist()
	{
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$myartist = "";
		foreach ($rm->getArtists() as $artist){
			$a = $rm->indexOfartist($artist);
			$b = $artist->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
			if($artist->numberOfSongs() ==0){
				$c=$c."N/A";
			}
			else{
				foreach($artist->getSongs() as $song){
					$c=$c.",".$song.getName();
				}
			}
			
			$myartist.="<option>".$a."</option>";
		}
		return $myartist;
	}
	
	public function SortSong($object)
	{
		$pm = new PersistenceHAS();
		$ma = $pm->loadDataFromStore();
		
		$listSong = $object->getSongs();
		
		function cmp($a,$b)
		{
			$t = strcmp($a->getName(), $b->getName());
			return $t;
		}
		
		usort($listSong, "cmp");
		
		for ($i = 0 ;$i<count($listSong);$i++)
		{
			$song = $listSong[$i];
			$object->addOrMoveSongAt($song,$i);
		}
		
		$pm->writeDataToStore($ma);
		
	}
	
	
	public function load_grouplocationmusic(){
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$mygrouplocation = "";
		foreach ($rm->getGroupLocations() as $grouplocation){
			$a=$rm->indexOfGroupLocation($grouplocation);
			$temp1=$grouplocation->numberOfListOfSongs();$temp2 = $grouplocation->numberOfSongs();
			$temp3=$temp1+$temp2;
			$a = $a.": ".$grouplocation->getName()." numberofmusic:".$temp3;
			$mygrouplocation.="<option>".$a."</option>";
		}
		return $mygrouplocation;
	}
	
	public function load_grouplocation(){
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$mygrouplocation = "";
		foreach ($rm->getGroupLocations() as $grouplocation){
			$a=$rm->indexOfGroupLocation($grouplocation);
			$a = $a.": ".$grouplocation->getName()." numberoflocations:".$grouplocation->numberOfLocations();
			$mygrouplocation.="<option>".$a."</option>";
		}
		return $mygrouplocation;
	}
	
	
	public function load_locations(){
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$mylocation = "";
		foreach ($rm->getLocations() as $location){
			$a=$rm->indexOfLocation($location);
			$a = $a.": ".$location->getName()." volume:".$location->getvolume();
			$mylocation.="<option>".$a."</option>";
		}

		return $mylocation;
	}
	public function load_music(){
		$pm = new PersistenceHAS();
		$rm = $pm->loadDataFromStore();
		$mymusic = "";
		foreach ($rm->getArtists() as $artist){
			$a = "artist".$rm->indexOfartist($artist);
			$b = $artist->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
				
			$mymusic.="<option>".$a."</option>";
		}
		foreach ($rm->getAlbums() as $album){
			$a = "Album".$rm->indexOfAlbum($album);
			$b = $album->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
				
			$mymusic.="<option>".$a."</option>";
		}
		foreach ($rm->getPlaylists() as $playlist){
			$a = "Playlist".$rm->indexOfPlaylist($playlist);
			$b = $playlist->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
		
			$mymusic.="<option>".$a."</option>";
		}
		foreach ($rm->getSongs() as $song){
			$a = "Song".$rm->indexOfSong($song);
			$b = $song->getName();
			$c =": ";
			$a =$a.$c;
			$a = $a.$b;
			$c ="";
		
			$mymusic.="<option>".$a."</option>";
		}
		return $mymusic;
		}
		
		

	}

	

	
	
	
	
	
	

?>
