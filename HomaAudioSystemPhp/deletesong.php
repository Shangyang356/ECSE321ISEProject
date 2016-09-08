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
	
	$songindex = NULL;
	if(isset($_POST["AddSongToLS"])){
		$selectedOption = $_POST["AddSongToLS"];
		$songindex = substr($selectedOption,0,1);
		$song = $rm->getSong_index($songindex);
		
		$c->removeSong($song);
	}
?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/music.php" /> </head>
</html>