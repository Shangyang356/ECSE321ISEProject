<?php
/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.22.0.5146 modeling language!*/

class Genre
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static $Pop = 1;
  public static $EDM = 2;
  public static $Hiphop = 3;
  public static $Country = 4;
  public static $Rock = 5;
  public static $Indie = 6;
  public static $Rnb = 7;
  public static $Jazz = 8;
  public static $Soul = 9;
  public static $Classical = 10;
  public static $Kpop = 11;
  public static $Metal = 12;
  public static $Punk = 13;
  public static $Reggae = 14;
  public static $Blues = 15;
  public static $Funk = 16;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public function __construct()
  {}

  //------------------------
  // INTERFACE
  //------------------------

  public function equals($compareTo)
  {
    return $this == $compareTo;
  }

  public function delete()
  {}

}
?>