<?php

require_once(__DIR__.'\src\controller\InputValidator.php');
require_once(__DIR__.'\src\controller\HASController.php');
require_once(__DIR__.'\src\Model\Album.php');
require_once(__DIR__.'\src\Model\Manager.php');
require_once(__DIR__.'\src\Model\Playlist.php');
require_once(__DIR__.'\src\Model\Location.php');
require_once(__DIR__.'\src\Model\Song.php');
require_once(__DIR__.'\src\Model\Artist.php');
require_once(__DIR__.'\src\Model\ListOfSong.php');
require_once(__DIR__.'\src\Model\GroupLocation.php');
require_once(__DIR__.'\src\Persistence\PersistenceHAS.php');

session_start();
	$pm = new PersistenceHAS();	
	$rm = $pm ->loadDataFromStore();
	$c = new HasController();
	
	$locationIndex = NULL;
	if(isset($_POST["Locationlibrary"])){
		$selectedOption = $_POST["Locationlibrary"];
		$locationIndex = substr($selectedOption,0,1);
		$location = $rm->getLocation_index($locationIndex);
		
		$c->removeLocation($location);
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/locations.php" /> </head>
</html>
