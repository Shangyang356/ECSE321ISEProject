<?php
class PersistenceHAS{
	
	private $filename;
	
	function __construct($filename = 'dta.txt') {
		$this->filename = $filename;
	}
	
	function loadDataFromStore() {
		if (file_exists($this->filename)) {
			$str = file_get_contents($this->filename);
			$manager = unserialize($str);
		} else {
			$manager = Manager::getInstance();
		}
		return $manager;
	}
	
	function writeDataToStore($manager) {
	
		$str = serialize($manager);
		file_put_contents($this->filename, $str);
	}
	
}
?>