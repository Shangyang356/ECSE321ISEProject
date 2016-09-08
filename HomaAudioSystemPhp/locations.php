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
		<!-- Page Content -->
<div class="container">

	<div class="row">
		<div class="col-sm-12 text-center">
			<h1>Group15 Home Audio System</h1><br/><br/>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4">
			<div class="col">
				<div class="row-sm-4">
                	<div class="panel panel-default">
                	<div class="panel-body">
               	 	<h4><strong>Add New Location</strong></h4>
                	<!-- To process the form with php, the action should be a php file -->
                	<form action="addlocation.php" method="post">
                    	<div class="form-group">
                        	<label for="LocationName">Name:</label>
                        	<input type="text" class="form-control" id="Location_name" name="Location_name">
                    	</div>
                    	<p><span class="error">
						<?php 
						if (isset($_SESSION['errorLocationName']) && !empty($_SESSION['errorLocationName'])){
						echo " * " . $_SESSION["errorLocationName"];
						}
						?>
						</span></p>
                    	<button type="submit" class="btn btn-default">Add location</button>
                	</form>
                	</div>
                	</div>
            	</div>
            	
            	<div class="row-sm-4">
                	<div class="panel panel-default">
                	<div class="panel-body">
               	 	<h4><strong>Add New GroupLocation</strong></h4>
                	<!-- To process the form with php, the action should be a php file -->
                	<form action="addgrouplocation.php" method="post">
                    	<div class="form-group">
                        	<label for="GrouplocationName">Name:</label>
                        	<input type="text" class="form-control" id="Grouplocation_name" name="Grouplocation_name">
                    	</div>
                    	<p><span class="error">
						<?php 
						if (isset($_SESSION['errorGroupLocationName']) && !empty($_SESSION['errorGroupLocationName'])){
						echo " * " . $_SESSION["errorGroupLocationName"];
						}
						?>
						</span></p>
                    	<button type="submit" class="btn btn-default">Add Grouplocation</button>
                	</form>
                	</div>
                	</div>
            	</div>
            	</div>
            
            </div>
            
            <div class="col-sm-4">
			   <div class = col>
				<div class="row-sm-4">
				<h4><strong>Location Library</strong></h4>
				<form action="deletelocation.php" method="post">
					<select id="Locationlibrary" name ="Locationlibrary" style="width: 300px" size="8">
				    <?php 
	                $c = new HasController();
	                echo $c->load_locations();
	                
	                ?>
					</select>
					
				<button type="submit" class="btn btn-default">delete</button>
				</form>	
				</div>
				<div class="row-sm-4">
				<h4><strong>Grouplocation Library</strong></h4>
				<form action="addtogrouplocation.php" method="post">
					<select  id="Grouplocationlibrary" name ="Grouplocationlibrary" style="width: 300px" size="8">
				    <?php 
	                $c = new HasController();
	                echo $c->load_grouplocation();
	                
	                ?>
					</select>
				<div class="row">
					<div class = "col-sm-2">
						<select  id="AddLocToG" name ="AddLocToG" >
						    <?php 
			                $c = new HasController();
			                echo $c->load_locations();
			                
			                ?>
						</select>
					</div>	
				</div>
				<div class = "row">
					<div class="col-sm-2">
					<button type="submit"  class="btn btn-default" id="addloctogroup" name="addloctogroup" >Add to GroupLocation</button>
					</div>
				</div>
				</form>					
				</div>	            	
			</div>
		</div>
		
		
		
		</div>

	</div>
</div>