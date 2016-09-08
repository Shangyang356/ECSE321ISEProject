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
$pm = new PersistenceHAS();
$rm = $pm->loadDataFromStore();
session_start();
$pm = new PersistenceHAS();
$rm = $pm->loadDataFromStore();
$c = new HasController();
$_SESSION["errorAlbumgenre"] = ""; $_SESSION["errorAlbumname"] = "";$_SESSION["errorAlbumreleasedate"] = "";
$_SESSION["errorArtistName"] = "";
$_SESSION["errorPlaylistName"] = "";
$_SESSION["errorSongName"] = ""; $_SESSION["errorSongduration"] = "";

$musicindex = NULL;
$type =NULL;
$index = NULL;
$grouplocationindex = NULL;
if (isset($_POST['musicindex'])) {
	$musicindex = $_POST['musicindex'];
}
if (isset($_POST['locationlibrary'])) {
	$grouplocationindex = $_POST['locationlibrary'];
}
$grouplocationindex = substr($grouplocationindex,0,1);

if (substr($musicindex, 0, 2) == "ar") {
	$index = substr($musicindex, 6, 1);
	$artist = $rm->getArtist_index($index);
	$c->assignListOfSongToGroup($grouplocationindex, $artist);
}
if (substr($musicindex, 0, 2) == "So") {
	$index = substr($musicindex, 4, 1);
	$song = $rm->getSong_index($index);
	$c->assignSongToGroup($grouplocationindex, $song);
}
if (substr($musicindex, 0, 2) == "Pl") {
	$index = substr($musicindex, 8, 1);
	$playlist = $rm->getPlaylist_index($index);
	$c->assignListOfSongToGroup($grouplocationindex, $playlist);
}
if (substr($musicindex, 0, 2) == "Al") {
	$index = substr($musicindex,5, 1);
	$album = $rm->getAlbum_index($index);
	$c->assignListOfSongToGroup($grouplocationindex, $album);
}
	




?>
<!DOCTYPE html>
<html>
       <head>
<meta http-equiv="refresh" content="0; url=/HomeAudioSystemPhp/" /> </head>
</html>