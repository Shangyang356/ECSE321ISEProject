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

class PersistenceEventRegistrationTest extends PHPUnit_Framework_TestCase
{
	protected $pm;

	protected  function setUp()
	{
		$this->c = new HasController();
	    $this->pm = new PersistenceHAS();
        $this->ma = $this->pm->loadDataFromStore();

	}

	protected function  tearDown()
	{
		$this->ma->delete();
		$this->pm->writeDataToStore($this->ma);
		
	}

	public function testPersistence()
	{
		//1.Create test data
		$ma= Manager::getInstance();
		$album = new Album("too sexy",Genre::$Jazz,"2016-12-21");
		$ma->addAlbum($album);

		// 2. Write all of the data
		$this->pm->writeDataToStore($ma);

		//3.Clear the data from memory
		$ma->delete();

		//4. Load it back in
		$ma=$this->pm->loadDataFromStore();

		//5. Check that we got it back
		$this->assertEquals(1, count($ma->getAlbums()));
		$albumName= $ma->getAlbum_index(0)->getName();
		$this->assertEquals("too sexy", $albumName);
		$releaseData = $ma->getAlbum_index(0)->getReleaseDate();
		$this->assertEquals("2016-12-21", $releaseData);
	}


}