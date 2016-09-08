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
$rm = $pm->loadDataFromStore();
$c = new HasController();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";

if (isset($_POST['volumesetter'])) {
	$volume = $_POST['volumesetter'];
}
if (isset($_POST['locationlibrary'])) {
	$locationindex = $_POST['locationlibrary'];
}
$locationindex = substr($locationindex, 0, 1);
$location = $rm->getLocation_index($locationindex);
$c->setVolumeByLoc($location, $volume);
$pm->writeDataToStore($rm);


?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/" /> </head>
</html>

