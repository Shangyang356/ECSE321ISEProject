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
		require_once(__DIR__.'\src\model\Genre.php');
        require_once(__DIR__.'\src\controller\HASController.php');
		
		session_start();

		?>


    <!-- Page Content -->
    <div class="container">

        <div class="row">
            <div class="col-lg-12 text-center" style="padding-bottom:40px">
                <h1>Home Audio System</h1>
            </div>
        </div>

        <div class="row">

            <div class="col-sm-4">
                <div class="panel panel-default">
                <div class="panel-body">
                <h4><strong>Add New Song</strong></h4>
                <!-- To process the form with php, the action should be a php file -->
                <form action="addSong.php" method="post">
                    <div class="form-group">
                        <label for="songName">Name:</label>
                        <input type="text" class="form-control" id="Songname" name="Songname">
                        <p>
	                    	<span class="error">
							<?php 
							if (isset($_SESSION['errorSongName']) && !empty($_SESSION['errorSongName'])){
							echo " * " . $_SESSION["errorSongName"];
							}
							?>
						</span>
						</p>
                        
                    </div>
                    <div class="form-group">
                        <label for="songDuration">Duration</label>
                        <input type="text" class="form-control" value = "HH:MM:SS" id="Songduration" name="Songduration">
                        <p>
                        	<span class="error">
							<?php 
							if (isset($_SESSION['errorSongduration']) && !empty($_SESSION['errorSongduration'])){
							echo " * " . $_SESSION["errorSongduration"];}
							?>
							</span>
						</p>
	
                    
                    <button type="submit" class="btn btn-default">Add Song</button>
                    </div>
                </form>
                <hr/>
                <h4><strong>Add New Album</strong></h4>
                <!-- To process the form with php, the action should be a php file -->
                <form action="addAlbum.php" method="post">
                    <div class="form-group">
                        <label for="albumName">Name:</label>
                        <input type="text" class="form-control" name = "AlbumName" id = "AlbumName" >
                        <p><span class="error">
							<?php 
							if (isset($_SESSION['errorAlbumname']) && !empty($_SESSION['errorAlbumname'])){
							echo " * " . $_SESSION["errorAlbumname"];
							}
							?>
						</span></p>
                    </div>
                    
                    <div class="form-group">
                        <label for="albumReleaseDate">ReleaseDate</label>
                        <input type="text" value = "YYYY-MM-DD" class="form-control" name = "AlbumDate" id = "AlbumDate">
                        <p>
                        	<span class="error">
							<?php 
							if (isset($_SESSION['errorAlbumreleasedate']) && !empty($_SESSION['errorAlbumreleasedate'])){
							echo " * " . $_SESSION["errorAlbumreleasedate"];}

							?>
							</span>
						</p>
						
                    </div>
                    <div class="form-group">
                        <label for="Genre">Genre</label>
                        <select name = "AlbumGenre" id = "AlbumGenre" >
                        	   <option value="1">Pop</option>
  							   <option value="2">EDM</option>
  							   <option value="3">Hip Hop</option>
                        
                        </select>
                        
                        <p>
                        	<span class="error">
							<?php 
							if (isset($_SESSION['errorAlbumgenre']) && !empty($_SESSION['errorAlbumgenre'])){
							echo " * " . $_SESSION["errorAlbumgenre"];}

							?>
							</span>
						</p>
						
                    </div>                    	

                    
                    <button type="submit" class="btn btn-default">Add Album</button>
      
                </form>
                
                <h4><strong>Add New Artist</strong></h4>
                <!-- To process the form with php, the action should be a php file -->
                <form action="addArtist.php" method="post">
                    <div class="form-group">
                        <label for="artistName">Name:</label>
                        <input type="text" class="form-control" id="artistName" name="artistName">
                        <p><span class="error">
							<?php 
							if (isset($_SESSION['errorArtistName']) && !empty($_SESSION['errorArtistName'])){
							echo " * " . $_SESSION["errorArtistName"];
							}
							?>
						</span></p>
                    </div>
                    
                    <button type="submit" class="btn btn-default">Add Artist</button>
                </form>
                
                <h4><strong>Add New Playlist</strong></h4>
                <!-- To process the form with php, the action should be a php file -->
                <form action="addplaylist.php" method="post">
                    <div class="form-group">
                        <label for="playlistName">Name:</label>
                        <input type="text" class="form-control" id="playlistName" name = "playlistName" >
                        <p><span class="error">
							<?php 
							if (isset($_SESSION["errorPlaylistName"]) && !empty($_SESSION["errorPlaylistName"])){
							echo " * " . $_SESSION["errorPlaylistName"];
							}
							?>
						</span></p>
                    </div>
                    
                    <button type="submit" class="btn btn-default">Add New Playlist</button>
                </form>                
                
                </div>
                </div>
            </div>

            <div class="col-sm-4">
                <div class = col>
                <div class="row-sm-w">
                	<h4><strong>Song Library</strong></h4>
                	<form action="deletesong.php" method="post">
						<select id="AddSongToLS" name ="AddSongToLS" style="width: 300px" size="20">
				    		<?php 
	                		$c = new HasController();
	                		echo $c->load_song();
	                		?>
                		</select>
                		<button type="submit" class="btn btn-default">delete</button>
                	</form>	
                </div>                
                </div>
                
                <div class = col>
                <div class="row-sm-w">
                	<h4><strong>Playlist Library</strong></h4>
                	<form action="addSongToListOfSong.php" method="post">
						<select id="Playlistlibrary" name ="Playlistlibrary" style="width: 300px" size="20">
			    		<?php 
                		$c = new HasController();
                		echo $c->load_playlist();
                		?>
                		</select>
						<div class="row">
							<div class = "col-sm-2">
								<select  id="AddSongToLS1" name ="AddSongToLS1" >
								    <?php 
					                $c = new HasController();
					                echo $c->load_Song();
					                
					                ?>
								</select>
							</div>	
						</div>                		
                		
                		<div class = "row">
                		<button type="submit" class="btn btn-default">add Song to Playlist</button>
                		</div>
                	</form>	
                </div>                
                </div>
                
            </div>

            <div class="col-sm-4">
            	 <div class = col>
                 <div class="row-sm-w">
               	 <h4><strong>Album Library</strong></h4>
                	<form action="addSongToListOfSong2.php" method="post">
						<select id="Albumlibrary" name ="Albumlibrary" style="width: 300px" size="20">
						<?php 
                			$c = new HasController();
                			echo $c->load_album();
                		?>
                		</select>
                		
 						<div class="row">
							<div class = "col-sm-2">
								<select  id="AddSongToLS2" name ="AddSongToLS2" >
								    <?php 
					                $c = new HasController();
					                echo $c->load_Song();
					                
					                ?>
								</select>
							</div>	
						</div>                  		
                		
                		<div class = "row">
                		<button type="submit" class="btn btn-default">Add Song To Album</button>
                		</div>
                		</form>	
                </div>                
                </div>
                
             	 <div class = col>
                 <div class="row-sm-w">
               	 <h4><strong>Artist Library</strong></h4>
                	<form action="addSongToListOfSong3.php" method="post">
						<select id="Artistlibrary" name ="Artistlibrary" style="width: 300px" size="20">
						<?php 
                			$c = new HasController();
                			echo $c->load_artist();
                		?>
                		</select>
                		
						<div class="row">
							<div class = "col-sm-2">
								<select  id="AddSongToLS3" name ="AddSongToLS3" >
								    <?php 
					                $c = new HasController();
					                echo $c->load_Song();
					                
					                ?>
								</select>
							</div>	
						</div>                   		
                		<div class = "row">
                		<button type="submit" class="btn btn-default">Add Song To Artist</button>
                		</div>
                		</form>	
                </div>                
                </div>               
                
                
            </div>

        </div>


        <div class="row">
            <!-- Add stuff similar to above, but with new fields -->
        </div>

    </div>
    
    
    <!-- /.container -->



    </body>
</html>