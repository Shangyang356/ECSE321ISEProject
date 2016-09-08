<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class Song
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Song Attributes
  private $name;
  private $duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct($aName, $aDuration)
  {
    $this->name = $aName;
    $this->duration = $aDuration;
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

  public function setDuration($aDuration)
  {
    $wasSet = false;
    $this->duration = $aDuration;
    $wasSet = true;
    return $wasSet;
  }

  public function getName()
  {	
    return $this->name;
  }

  public function getFormat()
  {
  	$a = $this->name;
  	$b = " Name: ";
  	$c = $b.$a;
  	$a = $this->duration;
  	$b = " Duration: ";
  	$d = $b.$a;
  	$c = $c.$d;
  	return $c;
  }
  
  public function getDuration()
  {
    return $this->duration;
  }

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>