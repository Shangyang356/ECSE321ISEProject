<?php
require_once(__DIR__.'\..\src\controller\HASController.php');
require_once(__DIR__.'\..\src\controller\InputValidator.php');
require_once(__DIR__.'\..\src\Model\Album.php');
require_once(__DIR__.'\..\src\Model\Manager.php');
require_once(__DIR__.'\..\src\Model\Playlist.php');
require_once(__DIR__.'\..\src\Model\Location.php');
require_once(__DIR__.'\..\src\Model\Song.php');
require_once(__DIR__.'\..\src\Model\Genre.php');
require_once(__DIR__.'\..\src\Model\Artist.php');
require_once(__DIR__.'\..\src\Model\ListOfSong.php');
require_once(__DIR__.'\..\src\persistence\PersistenceHAS.php');


class HAScontrollerTest extends PHPUnit_Framework_TestCase{

protected $c; 
protected $pm;
protected $ma;

protected function setUp()
{		
		$this->c = new HasController();
		
	    $this->pm = new PersistenceHAS();
        $this->ma = $this->pm->loadDataFromStore();

        $this->ma->delete();
        $this->pm->writeDataToStore($this->ma);
        
}

protected function tearDown()
{
	$singleton = Manager::getInstance(); // no idea what's inside
	$reflection = new ReflectionClass($singleton);
	$instance = $reflection->getProperty('theInstance');
	$instance->setAccessible(true); // now we can modify that :)
	$instance->setValue(null, null); // instance is gone
	$instance->setAccessible(false); // clean up
	
	$singleton = Manager::getInstance();
		
}

public function testCreateAlbum(){
	
	$this->assertEquals(0,count($this->ma->getAlbums()));
	
	$name = "albumA";
	$genre = 4;
	
	$releaseDate= "2016-10-16";

	try {
		$this->c->addAlbum($name, $genre, $releaseDate);
	} catch (Exception $e) {
		// check that no error occurred
		$this->fail();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getAlbums()));
	$this->assertEquals($name, $this->ma->getAlbum_index(0)->getName());
	$this->assertEquals(Genre::$Country, $this->ma->getAlbum_index(0)->getGenre());
	$this->assertEquals($releaseDate, $this->ma->getAlbum_index(0)->getReleaseDate());
	
}

public function testCreateEmptyALbum(){
	
	$this->assertEquals(0,count($this->ma->getAlbums()));
	
	$name = "";
	$genre = "";
	$releaseDate = "";
	
	$error = "";
	try {
		$this->c->addAlbum($name, $genre, $releaseDate);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}
	
	$this->assertEquals("@1 name cannot be empty! @2 genre cannot be empty! @3 releaseDate cannot be empty @4 releaseDate must be in YYYY-MM-DD format",$error);
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getAlbums()));
	
	
}

public function testCreateNullAlbum(){
	$this->assertEquals(0,count($this->ma->getAlbums()));
	
	$name = null;
	$genre = null;
	$releaseDate = null;
	
	$error = "";
	try {
		$this->c->addAlbum($name, $genre, $releaseDate);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}
	
	//check error
	$this->assertEquals("@1 name cannot be empty! @2 genre cannot be empty! @3 releaseDate cannot be empty @4 releaseDate must be in YYYY-MM-DD format",$error);
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getAlbums()));

}

public function testIncorrectAlbumDate()
{
	$this->assertEquals(0,count($this->ma->getAlbums()));
	
	$name = "albumA";
	$genre = Genre::$Country;
	
	$releaseDate= "206-103-6";
	$error = "";
	
	try {
		$this->c->addAlbum($name, $genre, $releaseDate);
	} catch (Exception $e) {
		// check that no error occurred
		$error = $e->getMessage();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	//check error
	$this->assertEquals("@4 releaseDate must be in YYYY-MM-DD format",$error);
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getAlbums()));	
	
	
}


public function testAddPlaylist()
{
	$this->ma = $this->pm->loadDataFromStore();
	
	$this->assertEquals(0,count($this->ma->getPlaylists()));
	
	$name = "bouyah";
	
	try {
		$this->c->addPlaylist($name);
	} catch (Exception $e) {
		// check that no error occurred
		$this->fail();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getPlaylists()));
	$this->assertEquals($name, $this->ma->getPlaylist_index(0)->getName());
	
}

public function testAddNullPlaylist(){

	$this->assertEquals(0,count($this->ma->getPlaylists()));

	$name = NULL;
	$error = "";
	try {
		$this->c->addPlaylist($name);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}

	$this->assertEquals("@1 name cannot be empty!",$error);

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getPlaylists()));

}

public function testAddEmptyPlaylist(){

	$this->assertEquals(0,count($this->ma->getPlaylists()));

	$name = "";
	$error = "";
	try {
		$this->c->addPlaylist($name);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}

	$this->assertEquals("@1 name cannot be empty!",$error);

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getPlaylists()));

}


public function testAddArtist()
{
	
	$this->assertEquals(0,count($this->ma->getArtists()));
	
	$name = "Taylor Swift";
	
	try {
		$this->c->addArtist($name);
	} catch (Exception $e) {
		// check that no error occurred
		$this->fail();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getArtists()));
	$this->assertEquals($name, $this->ma->getArtist_index(0)->getName());
	
}






public function testCreateEmptyArtist(){

	$this->assertEquals(0,count($this->ma->getArtists()));

	$name = "";
	$error = "";
	try {
		$this->c->addArtist($name);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}

	$this->assertEquals("@1 name cannot be empty!",$error);

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getArtists()));


}

public function testCreateNullArtist(){

	$this->assertEquals(0,count($this->ma->getArtists()));

	$name = NULL;
	$error = "";
	try {
		$this->c->addArtist($name);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}

	$this->assertEquals("@1 name cannot be empty!",$error);

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getArtists()));

}

public function testAddSong()
{
	
	$this->assertEquals(0,count($this->ma->getSongs()));
	
	$name = "Yellow Submarine";
	$duration = "00:07:50";
	$error = "";
	
	try
	{
		$this->c->addSong($name,$duration);
	}catch(Exception $e)
	{
		$error= $e->getMessage();
	}

	$this->assertEquals("", $error);
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getSongs()));
	$this->assertEquals($name, $this->ma->getSong_index(0)->getName());
	$this->assertEquals($duration, $this->ma->getSong_index(0)->getDuration());
	
}

public function testAddSongEmpty()
{

	$this->assertEquals(0,count($this->ma->getSongs()));

	$name = "";
	$duration = "";
	$error = "";

	try
	{
		$this->c->addSong($name,$duration);
	}catch(Exception $e)
	{
		$error= $e->getMessage();
	}

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getSongs()));
	$this->assertEquals("@1 name cannot be empty! @2 duration cannot be empty! @ duration must be specified correctly (HH:MM:SS)!", $error);

}

public function testAddSongIncorrectDuration()
{

	$this->assertEquals(0,count($this->ma->getSongs()));

	$name = "Yellow Submarine";
	$duration = "54:70:100";
	$error = "";

	try
	{
		$this->c->addSong($name,$duration);
	}catch(Exception $e)
	{
		$error= $e->getMessage();
	}

	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getSongs()));
	$this->assertEquals("@ duration must be specified correctly (HH:MM:SS)!", $error);
}



public function testAddSongtoList()
{
	$name = "jack";
	$duration = "00:07:50";
	$song = new Song($name, $duration);
	
	$error="";
	$this->ma = $this->pm->loadDataFromStore();
	
	$m = $this->ma->getInstance();
	
	$this->assertEquals(0,count($this->ma->getAlbums()));
	
	try
	{

		$this->c->addAlbum("Album",Genre::$Country , "2016-10-16");
		$this->c->addArtist("Taylor Swift");
		$this->c->addPlaylist("playlist");
		
		$album = $m->getAlbum_index(0);
		$artist = $m->getArtist_index(0);
		$playlist = $m->getPlaylist_index(0);
		
		$this->c->addSongToListOfSong($album, $song);
		$this->c->addSongToListOfSong($artist, $song);
		$this->c->addSongToListOfSong($playlist, $song);
		
	}catch(Exception $e)
	{
		$this->fail();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	
	$this->assertEquals(1,count($this->ma->getAlbum_index(0)->getSongs()));
	$this->assertEquals(1,count($this->ma->getArtist_index(0)->getSongs()));
	$this->assertEquals(1,count($this->ma->getPlaylist_index(0)->getSongs()));
	
}


public function testSetLocation()
{
	$this->assertEquals(0,count($this->ma->getLocations()));
	
	$name = "living Room";
	
	try {
		$this->c->setLocation($name);
	} catch (Exception $e) {
		// check that no error occurred
		$this->fail();
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getLocations()));
	$this->assertEquals($name, $this->ma->getLocation_index(0)->getName());
}


public function testSetLocationEmpty()
{
	
	$this->assertEquals(0,count($this->ma->getLocations()));
	
	$name = "";
	$error = "";
	try {
		$this->c->setLocation($name);
	} catch (Exception $e) {
		$error = $e->getMessage();
	}
	
	$this->assertEquals("@1 name cannot be empty!",$error);
	
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(0, count($this->ma->getLocations()));
	
	
}

public function testCreateGroupLocation()
{
	$this->assertEquals(0,count($this->ma->getGroupLocations()));
	
	$name = "living Room";
	$error = "";
	
	try {
		$this->c->createGroupLocation($name);
	} catch (Exception $e) {
		// check that no error occurred
		$error= $e->getMessage();
	}
	$this->assertEquals("", $error);
	$this->ma = $this->pm->loadDataFromStore();
	$this->assertEquals(1, count($this->ma->getGroupLocations()));
	$this->assertEquals($name, $this->ma->getGroupLocation_index(0)->getName());
	
}

public function testAssignLocationToGroup()
{
	
	$groupname = "lala";
	
	$locname = "livingroom";
	
	$error ="";
	
	$m = $this->ma->getInstance();
	try {
		$this->c->createGroupLocation($groupname);
		$this->c->setLocation($locname);
		
		
		$this->c->assignLocationToGroup(0, $m->getLocation_index(0));
		
	}catch (Exception $e)
	{
		$error = $e->getMessage();
		
	}
	
	$this->ma = $this->pm->loadDataFromStore();
	
	$this->assertEquals(1,count($this->ma->getGroupLocation_index(0)->getLocations()));
	
	
}

public function testassignMusicToGroupLoc()
{
	$m = $this->ma->getInstance();
	$error = "";
	
	$song = new Song("Testo", "00:05:30");
	
	Try{
		$this->c->createGroupLocation("Test");
		$this->c->assignSongToGroup(0, $song);
		
	}catch (Exception $e)
	{
		$error = $e->getMessage();
	}
	
	$m = $this->pm->loadDataFromStore();
	$this->assertEquals(1,count($m->getGroupLocation_index(0)->getListOfSongs()));
	$this->assertEquals(1,count($m->getGroupLocation_index(0)->getListOfSong_index(0)->getSongs()));
	
}

public function testassignListOfSongToGrouloc()
{
	$m = $this->ma->getInstance();
	$error = "";
	
	$album = new Album("lala", Genre::$Hiphop, "2016-10-16");
	
	try{
		$this->c->createGroupLocation("lolo");
		$this->c->assignListOfSongToGroup(0, $album);
	}catch(Exception $e)
	{
		$error= $e->getMessage();
	}
	
	$this->assertEquals("", $error);
	$m = $this->pm->loadDataFromStore();
	$this->assertEquals(1,count($m->getGroupLocation_index(0)->getListOfSongs()));
	
}


public function testDeleteSong()
{
	
	$m = $this->ma->getInstance();
	$error = "";
	
	try{
		$this->c->addAlbum("testAlbum", Genre::$EDM, "2016-09-01");
		$this->c->addPlaylist("testPlaylist");
		$this->c->addArtist("testArtist");
		$this->c->createGroupLocation("testGroup");
		
		$this->c->addSong("TestSong", "00:05:10");
		
		$this->c->assignSongToGroup(0, $m->getSong_index(0));
		$this->c->addSongToListOfSong($m->getAlbum_index(0), $m->getSong_index(0));
		$this->c->addSongToListOfSong($m->getPlaylist_index(0), $m->getSong_index(0));
		
		$this->c->removeSong($m->getSong_index(0));
	}catch(Exception $e)
	{
		$error = $e->getMessage();
	}
	
	$m = $this->pm->loadDataFromStore();
	$this->assertEquals("", $error);
	
	$this->assertEquals(0,count($m->getAlbum_index(0)->getSongs()));
	$this->assertEquals(0,count($m->getArtist_index(0)->getSongs()));
	$this->assertEquals(0,count($m->getPlaylist_index(0)->getSongs()));
	$this->assertEquals(0, count($m->getGroupLocation_index(0)->getListOfSongs()));
}

public function testSort()
{
	$m = $this->ma->getInstance();
	$error = "";
	
	try {
		$m->addSong(new Song("bdc","00:05:10"));
		$m->addSong(new Song("aaa","00:05:10"));
		$m->addSong(new Song("cpo","00:05:10"));
		$m->addSong(new Song("bbb","00:05:10"));
		
		$this->c->SortSong($m);
		
	}catch(Exception $e)
	{
		$error = $e->getMessage();
	}
	$m = $this->pm->loadDataFromStore();
	
	$this->assertEquals("aaa", $m->getSong_index(0)->getName());
	$this->assertEquals("bbb", $m->getSong_index(1)->getName());
	$this->assertEquals("bdc", $m->getSong_index(2)->getName());
	$this->assertEquals("cpo", $m->getSong_index(3)->getName());
	
}



}