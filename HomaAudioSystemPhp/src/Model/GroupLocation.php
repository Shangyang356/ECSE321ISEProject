<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class GroupLocation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GroupLocation Attributes
  private $name;

  //GroupLocation Associations
  private $listOfSongs;
  private $locations;
  private $songs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName)
  {
    $this->name = $aName;
    $this->listOfSongs = array();
    $this->locations = array();
    $this->songs = array();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public function setName($aName)
  {
    $wasSet = false;
    $this->name = $aName;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {
    return $this->name;
  }

  public function getListOfSong_index($index)
  {
    $aListOfSong = $this->listOfSongs[$index];
    return $aListOfSong;
  }

  public function getListOfSongs()
  {
    $newListOfSongs = $this->listOfSongs;
    return $newListOfSongs;
  }

  public function numberOfListOfSongs()
  {
    $number = count($this->listOfSongs);
    return $number;
  }

  public function hasListOfSongs()
  {
    $has = $this->numberOfListOfSongs() > 0;
    return $has;
  }

  public function indexOfListOfSong($aListOfSong)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->listOfSongs as $listOfSong)
    {
      if ($listOfSong->equals($aListOfSong))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getLocation_index($index)
  {
    $aLocation = $this->locations[$index];
    return $aLocation;
  }

  public function getLocations()
  {
    $newLocations = $this->locations;
    return $newLocations;
  }

  public function numberOfLocations()
  {
    $number = count($this->locations);
    return $number;
  }

  public function hasLocations()
  {
    $has = $this->numberOfLocations() > 0;
    return $has;
  }

  public function indexOfLocation($aLocation)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->locations as $location)
    {
      if ($location->equals($aLocation))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public function getSong_index($index)
  {
    $aSong = $this->songs[$index];
    return $aSong;
  }

  public function getSongs()
  {
    $newSongs = $this->songs;
    return $newSongs;
  }

  public function numberOfSongs()
  {
    $number = count($this->songs);
    return $number;
  }

  public function hasSongs()
  {
    $has = $this->numberOfSongs() > 0;
    return $has;
  }

  public function indexOfSong($aSong)
  {
    $wasFound = false;
    $index = 0;
    foreach($this->songs as $song)
    {
      if ($song->equals($aSong))
      {
        $wasFound = true;
        break;
      }
      $index += 1;
    }
    $index = $wasFound ? $index : -1;
    return $index;
  }

  public static function minimumNumberOfListOfSongs()
  {
    return 0;
  }

  public function addListOfSong($aListOfSong)
  {
    $wasAdded = false;
    if ($this->indexOfListOfSong($aListOfSong) !== -1) { return false; }
    $this->listOfSongs[] = $aListOfSong;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeListOfSong($aListOfSong)
  {
    $wasRemoved = false;
    if ($this->indexOfListOfSong($aListOfSong) != -1)
    {
      unset($this->listOfSongs[$this->indexOfListOfSong($aListOfSong)]);
      $this->listOfSongs = array_values($this->listOfSongs);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addListOfSongAt($aListOfSong, $index)
  {  
    $wasAdded = false;
    if($this->addListOfSong($aListOfSong))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfListOfSongs()) { $index = $this->numberOfListOfSongs() - 1; }
      array_splice($this->listOfSongs, $this->indexOfListOfSong($aListOfSong), 1);
      array_splice($this->listOfSongs, $index, 0, array($aListOfSong));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveListOfSongAt($aListOfSong, $index)
  {
    $wasAdded = false;
    if($this->indexOfListOfSong($aListOfSong) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfListOfSongs()) { $index = $this->numberOfListOfSongs() - 1; }
      array_splice($this->listOfSongs, $this->indexOfListOfSong($aListOfSong), 1);
      array_splice($this->listOfSongs, $index, 0, array($aListOfSong));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addListOfSongAt($aListOfSong, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfLocations()
  {
    return 0;
  }

  public function addLocation($aLocation)
  {
    $wasAdded = false;
    if ($this->indexOfLocation($aLocation) !== -1) { return false; }
    $this->locations[] = $aLocation;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeLocation($aLocation)
  {
    $wasRemoved = false;
    if ($this->indexOfLocation($aLocation) != -1)
    {
      unset($this->locations[$this->indexOfLocation($aLocation)]);
      $this->locations = array_values($this->locations);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addLocationAt($aLocation, $index)
  {  
    $wasAdded = false;
    if($this->addLocation($aLocation))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfLocations()) { $index = $this->numberOfLocations() - 1; }
      array_splice($this->locations, $this->indexOfLocation($aLocation), 1);
      array_splice($this->locations, $index, 0, array($aLocation));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveLocationAt($aLocation, $index)
  {
    $wasAdded = false;
    if($this->indexOfLocation($aLocation) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfLocations()) { $index = $this->numberOfLocations() - 1; }
      array_splice($this->locations, $this->indexOfLocation($aLocation), 1);
      array_splice($this->locations, $index, 0, array($aLocation));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addLocationAt($aLocation, $index);
    }
    return $wasAdded;
  }

  public static function minimumNumberOfSongs()
  {
    return 0;
  }

  public function addSong($aSong)
  {
    $wasAdded = false;
    if ($this->indexOfSong($aSong) !== -1) { return false; }
    $this->songs[] = $aSong;
    $wasAdded = true;
    return $wasAdded;
  }

  public function removeSong($aSong)
  {
    $wasRemoved = false;
    if ($this->indexOfSong($aSong) != -1)
    {
      unset($this->songs[$this->indexOfSong($aSong)]);
      $this->songs = array_values($this->songs);
      $wasRemoved = true;
    }
    return $wasRemoved;
  }

  public function addSongAt($aSong, $index)
  {  
    $wasAdded = false;
    if($this->addSong($aSong))
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfSongs()) { $index = $this->numberOfSongs() - 1; }
      array_splice($this->songs, $this->indexOfSong($aSong), 1);
      array_splice($this->songs, $index, 0, array($aSong));
      $wasAdded = true;
    }
    return $wasAdded;
  }

  public function addOrMoveSongAt($aSong, $index)
  {
    $wasAdded = false;
    if($this->indexOfSong($aSong) !== -1)
    {
      if($index < 0 ) { $index = 0; }
      if($index > $this->numberOfSongs()) { $index = $this->numberOfSongs() - 1; }
      array_splice($this->songs, $this->indexOfSong($aSong), 1);
      array_splice($this->songs, $index, 0, array($aSong));
      $wasAdded = true;
    } 
    else 
    {
      $wasAdded = $this->addSongAt($aSong, $index);
    }
    return $wasAdded;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {
    $this->listOfSongs = array();
    $this->locations = array();
    $this->songs = array();
  }

}
?>