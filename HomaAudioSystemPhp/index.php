<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Group15HomeAudioSystem</title>
		<style>
			.error {color: #FF0000;}
		</style>
	</head>
	<body>
		<?php 
		include 'header.php';
		require_once(__DIR__.'\src\model\Album.php');
		require_once(__DIR__.'\src\model\Artist.php');
		require_once(__DIR__.'\src\model\Genre.php');
		require_once(__DIR__.'\src\model\Manager.php');
		require_once(__DIR__.'\src\model\GroupLocation.php');
		require_once(__DIR__.'\src\model\ListOfSong.php');
		require_once(__DIR__.'\src\model\Location.php');
		require_once(__DIR__.'\src\model\Playlist.php');
		require_once(__DIR__.'\src\model\Song.php');
        require_once(__DIR__.'\src\controller\HASController.php');
		
		session_start();


		?>	
	<div class="row">
		<div class="col-sm-12 text-center">
			<h1>Group15 Home Audio System</h1><br/><br/>
		</div>
	</div>

<div class="container">
	<div class="col">
		<div class="row-sm-12">
			<div class = "row">
				<div class="col-sm-6">
				<div clas = "col">
					<div class="row-sm-3">
						<h4><strong>Music Library</strong></h4>
						<select id="musiclibrary" name ="musiclibrary" style="width: 260px" size="6">
						    <?php 
			                $c1 = new HasController();
			                echo $c1->load_music();
			                
			                ?>
						</select>
					</div>	
					<form action="playmusic.php" method="post">
					<div class="row-sm-3">	
					<h4><strong>GroupLocation Library</strong></h4>
					
						<select  id="locationlibrary" name ="locationlibrary" style="width: 260px" size="6">
						    <?php 
			                $c2 = new HasController();
			                echo $c2->load_grouplocationmusic();
			                
			                ?>
						</select>
					</div>
					<div class="row-sm-2">	
					<label for="MusicIndex">Music(format:artist0)</label>		
			        <input type="text" class="form-control" id="musicindex" name="musicindex" style="width: 126px">
		        	</div>		
					<div class="row-sm-2">
						<button type="submit" class="btn btn-default ">Playmusic</button>
					</div>
		        	</form>
				</div>
				</div>
				<div class="col-sm-6">
				<form action="setvolume.php" method="post">
				<div class = "col">
					<div class="row-sm-3">
						<h4><strong>Location Library</strong></h4>
						<select id="locationlibrary" name ="locationlibrary" style="width: 260px" size="6">
						    <?php 
			                $c1 = new HasController();
			                echo $c1->load_locations();
			                
			                ?>
						</select>
					</div>
					<div class="row-sm-3">
					<div class = "row">
					<div class = "col-sm-3">		
			        <input type="range"  min ="0" max ="100" step="1" onchange="showValue(this.value)" name ="volumesetter" id="volumesetter" style="width: 100px">
		        	</div>
		        	<div class="col-sm-3">
						<button type="submit" class="btn btn-default ">SetVolume</button>
					</div>
					</div>
					</div>
				</div>
		        </form>
				</div>	
			</div>
		</div>
	</div>
</div>
									
									
				            	
		