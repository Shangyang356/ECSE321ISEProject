package michael.user.homeaudiosystemandroid;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse321.homeAudioSystem.model.Album;
import ca.mcgill.ecse321.homeAudioSystem.model.Artist;
import ca.mcgill.ecse321.homeAudioSystem.model.GroupLocation;
import ca.mcgill.ecse321.homeAudioSystem.model.ListOfSong;
import ca.mcgill.ecse321.homeAudioSystem.model.Location;
import ca.mcgill.ecse321.homeAudioSystem.model.Manager;
import ca.mcgill.ecse321.homeAudioSystem.model.Playlist;
import ca.mcgill.ecse321.homeAudioSystem.model.Song;
import ca.mcgill.ecse321.homeAudioSystem.persistence.PersistenceXstream;

//TODO: - able to add song to playlist, , add a song to location, add a playlist of currently playing songs
public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemSelectedListener, SeekBar.OnSeekBarChangeListener{
    //variables

    List<String> genre;
    List<GroupLocation> playingNowList = new ArrayList<GroupLocation>() ;
    ListOfSong songsToAddFromPlaylist;


    boolean hasLocations = false;
    Button btnDateAlbum;
    int year_x,month_x,day_x;
    static final int DIALOG_ID = 0;
    Spinner spinnerAlbumGenre ;
    String selectedGenre;

    String selectedAlbum;
    String selectedSong;
    String selectedArtist;
    String selectedPlaylist;
    String selectedLocation;
    String selectedGroupLocation;


    Album deleteAlbum;
    Song deleteSong;
    Playlist deletePlaylist;
    Artist deleteArtist;
    Location deleteLocationObject;
    GroupLocation deleteGroupLocationObject;
    GroupLocation storingGroup;
    GroupLocation storingGroupForSong;

    Artist storingArtist;
    Song storingSong;
    Playlist storingPlaylist;
    Song selectedSongToPlay;
    Album albumToPlay;
    Playlist playlistToPlay;

    Album storingAlbum;
    Location locationToGroup;
    GroupLocation groupToAddLocation;


    Button addAlbum ;
    Button addSong;
    Button addArtist;
    Button addPlaylist;
    Button addLocation;
    Button addGroupLocation;
    Button addLocationToGroupButton;
    Button addSongToPlaylist;
    Button addArtistToAlbum;

    Button deleteAlbumButton;
    Button deleteSongButton;
    Button deleteArtistButton;
    Button deletePlaylistButton;
    Button deleteLocation;
    Button deleteGroupLocation;

    Button updateLocationInfo;
    Button playSong;
    Button playAlbum;
    Button playPlaylist;




    Spinner locationSpinner ;
    Spinner locationGroupSpinner;
    Spinner playingNowSpinner;

    int Volume = 0;
    SeekBar sb;

    CheckBox mute;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshData();

        //setting up the seekbar and mute
        sb= (SeekBar) findViewById(R.id.violumeBar);
        sb.setMax(100);
        sb.setProgress(Volume);
        sb.setOnSeekBarChangeListener(this);
        mute =  (CheckBox) findViewById(R.id.Mute);


        //setting up the buttons
        addListenerOnButtons();


        //setting up initial dates to pick for album calendar
        final Calendar cal = Calendar.getInstance();
        year_x = cal.get(Calendar.YEAR);
        month_x = cal.get(Calendar.MONTH);
        day_x = cal.get(Calendar.DAY_OF_MONTH);


        showDialogOnButtonClick(); //to deal with the date pciker fro album

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //setting up the tab host
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        //setting it up the Home tab
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("home");
        tabSpec.setContent(R.id.tabLibrary);
        tabSpec.setIndicator("Home");
        tabHost.addTab(tabSpec);

        //setting it up the Music tab
        tabSpec = tabHost.newTabSpec("music");
        tabSpec.setContent(R.id.tabMusic);
        tabSpec.setIndicator("Music");
        tabHost.addTab(tabSpec);

        //setting it up the Music tab
        tabSpec = tabHost.newTabSpec("locations");
        tabSpec.setContent(R.id.tabLocations);
        tabSpec.setIndicator("Locations");
        tabHost.addTab(tabSpec);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    //dealing with the date picking for album button
    @Override
    protected Dialog onCreateDialog(int id){
        if(id ==DIALOG_ID){
            return new DatePickerDialog(this, dPickerListener, year_x,month_x,day_x);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener dPickerListener
            = new DatePickerDialog.OnDateSetListener(){
        @Override
        public  void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
         year_x =  year;
         month_x = monthOfYear + 1;
         day_x = dayOfMonth;
        // Toast.makeText(MainActivity.this, year_x+"/"+month_x+"/"+day_x, Toast.LENGTH_SHORT).show();
        }
    };

    public void showDialogOnButtonClick(){
        btnDateAlbum = (Button) findViewById(R.id.buttonPickDate);

        btnDateAlbum.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showDialog(DIALOG_ID);

                    }
                }
        );
    }

    // includes all of the spinners and list that'll have to be refreshed when there's a change

    private void refreshData(){
        Manager mr = Manager.getInstance();

        TextView textView = (TextView) findViewById(R.id.EditTextAddSong);
        textView.setText("");

        //spinner for currently playing songs

        playingNowSpinner = (Spinner) findViewById(R.id.playingNowSpinner);
        playingNowSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<GroupLocation> playingNowAdapter = new ArrayAdapter<GroupLocation>(this, android.R.layout.simple_spinner_item);
        playingNowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //adding a dummy String

        GroupLocation gl = new GroupLocation("List of Currently playing songs");
        playingNowAdapter.add(gl);

        for(int i =0; i< (playingNowList.size()); i++){

            playingNowAdapter.add(playingNowList.get(i));
            playingNowAdapter.notifyDataSetChanged();;
        }

        playingNowSpinner.setAdapter(playingNowAdapter);


        //spinner for Groups of locations
        locationGroupSpinner = (Spinner) findViewById(R.id.spinnerGroupLocation);
        locationGroupSpinner.setOnItemSelectedListener(this);
        List<GroupLocation> grouplocations = mr.getGroupLocations();
        ArrayAdapter<GroupLocation> groupLocationSpinnerAdapter = new ArrayAdapter<GroupLocation>(this, android.R.layout.simple_spinner_item);
        groupLocationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //adding a dummy group location to have the first slot print the following string
        String groupLocationName = "list of Group Locations:";
        GroupLocation grouplocation = new GroupLocation(groupLocationName);
        groupLocationSpinnerAdapter.add(grouplocation);

        for(int i =0; i< (grouplocations.size()); i++){

            groupLocationSpinnerAdapter.add(grouplocations.get(i));
            groupLocationSpinnerAdapter.notifyDataSetChanged();;
        }

        locationGroupSpinner.setAdapter(groupLocationSpinnerAdapter);


        //spinner for locations
        locationSpinner = (Spinner) findViewById(R.id.spinnerLocations);
        locationSpinner.setOnItemSelectedListener(this);
        List<Location> locations = mr.getLocations();
        ArrayAdapter<Location> locationSpinnerAdapter = new ArrayAdapter<Location>(this, android.R.layout.simple_spinner_item);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //adding a dummy location to have the first slot print the following string
       String locationName = "list of Locations:";
        Location location = new Location(locationName, 0);
        locationSpinnerAdapter.add(location);

        for(int i =0; i< (locations.size()); i++){

            locationSpinnerAdapter.add(locations.get(i));
            locationSpinnerAdapter.notifyDataSetChanged();;
        }

        locationSpinner.setAdapter(locationSpinnerAdapter);


        //spinner for albums

        Spinner spinner = (Spinner) findViewById(R.id.ListOfAlbums);
        spinner.setOnItemSelectedListener(this);
        List<Album> albums = mr.getAlbums();

        ArrayAdapter<Album> albumSpinner = new ArrayAdapter<Album>(this,android.R.layout.simple_spinner_item);
        albumSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dummy album
        String nameOfAlbum =  "List of Albums";
        String fullDate = "0000-00-00";
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = format1.parse(fullDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Album album = new Album(nameOfAlbum,  sqlDate);
            albumSpinner.add(album);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(int i =0; i<albums.size(); i++){

            albumSpinner.add((Album)albums.get(i));
            albumSpinner.notifyDataSetChanged();;
        }

        spinner.setAdapter(albumSpinner);

        //Spinner for the list of songs

        Spinner spinner2 = (Spinner) findViewById(R.id.ListOfSongs);
        spinner2.setOnItemSelectedListener(this);
        List<Song> songs = mr.getSongs();

        ArrayAdapter<Song> songSpinner = new ArrayAdapter<Song>(this,android.R.layout.simple_spinner_item);
        songSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dummy song
        String time = "00-00-00";
        SimpleDateFormat format2 = new SimpleDateFormat("HH-mm-ss");
        java.util.Date utilTime = null;
        try {
            utilTime = format2.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Time sqlTime = new Time(utilTime.getTime());
        Song dumSong = new Song("List of songs", sqlTime);
        songSpinner.add(dumSong);

        for(int i =0; i<songs.size(); i++){

            songSpinner.add(songs.get(i));
            songSpinner.notifyDataSetChanged();;
        }

        spinner2.setAdapter(songSpinner);

        //Spinner for list of Artists

        Spinner spinner3 = (Spinner) findViewById(R.id.ListOfArtists);
        spinner3.setOnItemSelectedListener(this);
        List<Artist> artists = mr.getArtists();


        ArrayAdapter<Artist> artistSpinner = new ArrayAdapter<Artist>(this,android.R.layout.simple_spinner_item);
        songSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dummy artist
        Artist artist = new Artist("List of Artists");
        artistSpinner.add(artist);

        for(int i =0; i<artists.size(); i++){

            artistSpinner.add(artists.get(i));
            artistSpinner.notifyDataSetChanged();;
        }

        spinner3.setAdapter(artistSpinner);

        //Spinner for list of Playlists

        Spinner spinner4 = (Spinner) findViewById(R.id.ListOfPlaylists);
        spinner4.setOnItemSelectedListener(this);
        List<Playlist> playlists = mr.getPlaylists();

        ArrayAdapter<Playlist> playlistSpinner = new ArrayAdapter<Playlist>(this,android.R.layout.simple_spinner_item);
        songSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dummy playlist
        Playlist playlist = new Playlist("List of Playlists");
        playlistSpinner.add(playlist);

        for(int i =0; i<playlists.size(); i++){

            playlistSpinner.add(playlists.get(i));
            playlistSpinner.notifyDataSetChanged();;
        }

        spinner4.setAdapter(playlistSpinner);



        // the spinner of the Genre of the ablbum

        spinnerAlbumGenre = (Spinner)findViewById(R.id.AlbumGenreSpinner);
        spinnerAlbumGenre.setOnItemSelectedListener(this);

        genre = new ArrayList<String>();
        genre.add("Pop");
        genre.add("Hiphop");
        genre.add("Country");
        genre.add("Rock");
        genre.add("Indie");
        genre.add("RnB");
        genre.add("Jazz");
        genre.add("Soul");
        genre.add("Classical");
        genre.add("Kpop");
        genre.add("Metal");
        genre.add("Punk");
        genre.add("Reggae");
        genre.add("Blues");
        genre.add("Funk");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAlbumGenre.setAdapter(dataAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //deals with all the buttons

    public void addListenerOnButtons(){

        addSong =(Button) findViewById(R.id.ButtonAddSong);
        addArtist =(Button) findViewById(R.id.ButtonAddArtist);
        addAlbum =(Button) findViewById(R.id.ButtonAddAlbum);
        addPlaylist =(Button) findViewById(R.id.ButtonAddPlaylist);
        addLocation = (Button) findViewById(R.id.addLocationButton);
        addGroupLocation = (Button) findViewById(R.id.addGroupButton);
        updateLocationInfo = (Button) findViewById(R.id.updateLocationButton);
        addLocationToGroupButton = (Button) findViewById(R.id.locationToGroup);
        addSongToPlaylist = (Button) findViewById(R.id.songToPlaylistButton);
        addArtistToAlbum = (Button) findViewById(R.id.artistToAlbumButton);
        playSong = (Button) findViewById(R.id.playSongButton);
        playPlaylist = (Button) findViewById(R.id.playPlaylistButton);

        playPlaylist.setOnClickListener(new View.OnClickListener() {
            Manager mr = Manager.getInstance();
            @Override
            public void onClick(View v) {

                List <Playlist> playlists = mr.getPlaylists();
                for(int i = 0; i<playlists.size();i++ ){
                    if(playlists.get(i).getName().equals(selectedPlaylist)) {

                        playlistToPlay = playlists.get(i);

                        if(playlistToPlay.getSongs().size() >0) {
                            List<Song> songsToConvert = playlistToPlay.getSongs();
                            songsToAddFromPlaylist = convertListToListOfSongs(songsToConvert);

                        }


                    }
                }

                List<GroupLocation> groupLocations = mr.getGroupLocations();
                for (int i = 0; i < groupLocations.size(); i++) {
                    if (groupLocations.get(i).getName().equals(selectedGroupLocation)) {
                        storingGroup = groupLocations.get(i);
                        storingGroup.addListOfSong(songsToAddFromPlaylist);



                    }
                }
                if(songsToAddFromPlaylist.getSongs().size()>0) {
                    Toast.makeText(MainActivity.this, "" + songsToAddFromPlaylist.toString(), Toast.LENGTH_LONG).show();

                    playingNowList.add(storingGroup);
                }

                refreshData();




            }
        });




        playSong.setOnClickListener(new View.OnClickListener() {

            Manager mr = Manager.getInstance();

            @Override
            public void onClick(View v) {

                List<Song> songs = mr.getSongs();
                for (int i = 0; i < songs.size(); i++) {
                    if (songs.get(i).getName().equals(selectedSong)) {
                        selectedSongToPlay = songs.get(i);


                    }

                }

                List<GroupLocation> groupLocations = mr.getGroupLocations();
                for (int i = 0; i < groupLocations.size(); i++) {
                    if (groupLocations.get(i).getName().equals(selectedGroupLocation)) {
                        storingGroupForSong = groupLocations.get(i);
                        storingGroupForSong.addSong(selectedSongToPlay);


                    }
                }
                Toast.makeText(MainActivity.this, "" + selectedSong.toString(), Toast.LENGTH_LONG).show();
                playingNowList.add(storingGroupForSong);

                refreshData();
            }
        });


        addSongToPlaylist.setOnClickListener(new View.OnClickListener() {
            Manager mr = Manager.getInstance();
            @Override
            public void onClick(View v) {
                List<Song> songList = mr.getSongs();
                for(int i = 0; i<songList.size(); i++){
                    if(songList.get(i).getName().equals(selectedSong)){
                        storingSong= songList.get(i);


                    }

                }

                List<Playlist> paylistList = mr.getPlaylists();
                for(int i = 0; i<paylistList.size(); i++){
                    if(paylistList.get(i).getName().equals(selectedPlaylist)){
                        storingPlaylist = paylistList.get(i);


                    }

                }

                mr.removePlaylist(storingPlaylist);
                storingPlaylist.addSong(storingSong);

                mr.addPlaylist(storingPlaylist);



            }
        });


        addArtistToAlbum.setOnClickListener(new View.OnClickListener() {

            Manager mr = Manager.getInstance();

            @Override
            public void onClick(View v) {

                //adding artist to album

                List<Artist> artistList = mr.getArtists();
                for(int i = 0; i<artistList.size(); i++){
                    if(artistList.get(i).getName().equals(selectedArtist)){
                        storingArtist = artistList.get(i);
                    }

                }

                List<Album> albumList = mr.getAlbums();
                for(int i = 0; i<albumList.size(); i++){
                    if(albumList.get(i).getName().equals(selectedAlbum)){
                        storingAlbum = albumList.get(i);
                    }

                }

                if(!(storingArtist.equals("List of Artists"))&& !(storingArtist.equals("List of Albums"))) {
                    mr.removeAlbum(storingAlbum);
                    storingAlbum.addArtist(storingArtist);
                    mr.addAlbum(storingAlbum);


                }


            }
        });

        addLocationToGroupButton.setOnClickListener(new View.OnClickListener() {
            Manager mr = Manager.getInstance();
            @Override
            public void onClick(View v) {


                List <GroupLocation> groups = mr.getGroupLocations();
                for(int i = 0; i<groups.size();i++ ){
                    if(groups.get(i).getName().equals(selectedGroupLocation)) {
                        groupToAddLocation = groups.get(i);


                    }
                }

                List<Location> locations = mr.getLocations();
                for(int i=0; i<locations.size();i++){
                    if(locations.get(i).getName().equals(selectedLocation)){
                        locationToGroup = locations.get(i);


                    }
                }
                    //does not add the location if the selected items are the dummmy variables
                if(!(groupToAddLocation.getName().equals("list of Group Locations:")) &&  !(locationToGroup.getName().equals("list of Locations:"))) {
                    mr.removeGroupLocation(groupToAddLocation);
                    groupToAddLocation.addLocation(locationToGroup);
                    mr.addGroupLocation(groupToAddLocation);
                    PersistenceXstream.saveToXMLwithXStream(mr);

                    String locationsToString = groupToAddLocation.getLocations().toString();
                    Toast.makeText(MainActivity.this, groupToAddLocation.getName() + " has the follwing locations: " + locationsToString, Toast.LENGTH_LONG).show();
                }
            }
        });


        addGroupLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroupLocation( v);
                refreshData();
            }
        });


        updateLocationInfo.setOnClickListener(new View.OnClickListener() {
            Manager mr = Manager.getInstance();

            @Override
            public void onClick(View v) {
               int  volumeUpdate = Volume;
                if(mute.isChecked()){
                    volumeUpdate = 0;
                }

                List<Location> locations = mr.getLocations();
                for(int i=0; i<locations.size();i++){
                    if(locations.get(i).getName().equals(selectedLocation)){
                        if(volumeUpdate == 0){
                            locations.get(i).setIsMuted(true);
                        }
                        locations.get(i).setVolume(volumeUpdate);
                        Toast.makeText(MainActivity.this, selectedLocation + " has an updated volume set to: "+ volumeUpdate, Toast.LENGTH_LONG).show();
                    }
                }
                PersistenceXstream.saveToXMLwithXStream(mr);

            }
        });



        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLocation(v,Volume);

                refreshData();
            }
        });



        addSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSong(v);


                refreshData();
            }
        });

        addArtist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddArtist(v);


                refreshData();
            }
        });

        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddAlbum(v);

                refreshData();

            }
        });

        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPlaylist(v);

                refreshData();

            }
        });



    }

    //deals with all of the spinners
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Manager mr = Manager.getInstance();

        deleteAlbumButton = (Button)findViewById(R.id.deleteAlbumButton);
        deleteSongButton = (Button) findViewById(R.id.deleteSongButton);
        deleteArtistButton = (Button) findViewById(R.id.deleteArtistButton);
        deletePlaylistButton = (Button) findViewById(R.id.deletePlaylistButton);
        deleteLocation = (Button) findViewById(R.id.deleteLocationButton);
        deleteGroupLocation = (Button) findViewById(R.id.deleteGroupButton);

        switch (parent.getId()) {

            case R.id.AlbumGenreSpinner:
            // On selecting a spinner item
            selectedGenre =  parent.getItemAtPosition(position).toString();



                break;

            case R.id.ListOfAlbums:

                selectedAlbum =   parent.getItemAtPosition(position).toString();

                List <Album> albums = mr.getAlbums();
                for(int i = 0; i<albums.size();i++ ){
                    if(albums.get(i).getName().equals(selectedAlbum)) {

                        storingAlbum = albums.get(i);
                        String artists = storingAlbum.getArtists().toString();
                        if(storingAlbum.getArtists().size()>0) {
                            Toast.makeText(MainActivity.this, "Name: " + storingAlbum.getName() +  " Artists: " + artists
                                    + " Date: " + storingAlbum.getReleaseDate(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, " Name: " + storingAlbum.getName() +  " Date: " + storingAlbum.getReleaseDate(), Toast.LENGTH_LONG).show();
                        }
                    }
                }
                deleteAlbumButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteAlbum(selectedAlbum);
                        refreshData();

                    }
                });

                break;


            case R.id.ListOfSongs:

                selectedSong =  parent.getItemAtPosition(position).toString();

                deleteSongButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteSong(selectedSong);
                        refreshData();
                    }
                });
                break;




            case R.id.ListOfArtists:
               selectedArtist =  parent.getItemAtPosition(position).toString();

                deleteArtistButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteArtist(selectedArtist);
                        refreshData();
                    }
                });
                break;

            case R.id.ListOfPlaylists:
                selectedPlaylist = parent.getItemAtPosition(position).toString();

                List <Playlist> playlists = mr.getPlaylists();
                for(int i = 0; i<playlists.size();i++ ){
                    if(playlists.get(i).getName().equals(selectedPlaylist)) {

                        storingPlaylist = playlists.get(i);
                        String songs = storingPlaylist.getSongs().toString();
                        if(storingPlaylist.getSongs().size()>0) {
                            Toast.makeText(MainActivity.this, selectedPlaylist + " has the following songs: " + songs , Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, " Name: " + storingPlaylist.getName() , Toast.LENGTH_LONG).show();
                        }
                    }
                }

                deletePlaylistButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deletePlaylist(selectedPlaylist);
                        refreshData();
                    }
                });

                break;

            case R.id.spinnerLocations:

                selectedLocation = parent.getItemAtPosition(position).toString();

                //printing the volume of the chosen location
                int volumeLevel = 0;
                List<Location> locations = mr.getLocations();
                for(int i=0; i<locations.size();i++){
                    if(locations.get(i).getName().equals(selectedLocation)){
                        volumeLevel = locations.get(i).getVolume();
                        Toast.makeText(MainActivity.this, selectedLocation+ " Has a Volume level of: "+ volumeLevel ,Toast.LENGTH_LONG).show();


                    }
                }



                deleteLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!(selectedLocation.equals("list of Locations:"))) {

                            deleteLocation(selectedLocation);
                            refreshData();
                        }

                    }
                });

                break;

            case R.id.spinnerGroupLocation:

                selectedGroupLocation = parent.getItemAtPosition(position).toString();

                //printing the locations of the selected group

                List <GroupLocation> groupLocationList = mr.getGroupLocations();
                for(int i = 0; i<groupLocationList.size();i++ ){
                    if(groupLocationList.get(i).getName().equals(selectedGroupLocation) && (groupLocationList.get(i).getLocations().size()>0)) {

                        String locationsToString = groupLocationList.get(i).getLocations().toString();
                        Toast.makeText(MainActivity.this, selectedGroupLocation + " has the follwing locations: " + locationsToString, Toast.LENGTH_LONG).show();

                    }
                }


                deleteGroupLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!(selectedGroupLocation.equals("list of Group Locations:"))){

                            deleteGroupLocation(selectedGroupLocation);
                            refreshData();
                        }
                    }
                });


        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //controller methods start here

    public void AddSong(View v) {

        TextView tvSong = (TextView) findViewById(R.id.EditTextAddSong);
        TextView tvduration = (TextView) findViewById(R.id.EditTextSongDuration);
        Manager mr = Manager.getInstance();

        //dealing with the time conversion into it's  Model Song's Time format
        String time = tvduration.getText().toString();
        String hour = "" + time.charAt(0) +time.charAt(1);
        String minutes = "" + time.charAt(3) + time.charAt(4);
        String seconds = "" + time.charAt(6) + time.charAt(7);

         String fullTime = hour + "-" + minutes + "-" + seconds;
        SimpleDateFormat format = new SimpleDateFormat("HH-mm-ss");
        try {
            java.util.Date utilTime = format.parse(fullTime);
            java.sql.Time sqlTime = new Time(utilTime.getTime());
            Song song = new Song(tvSong.getText().toString(), sqlTime);
          //  Toast.makeText(MainActivity.this, "" + sqlTime , Toast.LENGTH_LONG).show(); used to check if the conversion is correct
            mr.addSong(song);
            PersistenceXstream.saveToXMLwithXStream(mr);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        }

    public void DeleteSong(){


    }




    public void AddArtist(View v){

        TextView tvArtist = (TextView) findViewById(R.id.EditTextAddArtist);
        Manager mr  =  Manager.getInstance();
        Artist artist = new Artist(tvArtist.getText().toString());

        mr.addArtist(artist);
        PersistenceXstream.saveToXMLwithXStream(mr);

    }


    public void AddAlbum(View v){
        Manager mr = Manager.getInstance();
        TextView tvAlbum = (TextView) findViewById(R.id.EditTextAddAlbum);
        //setting the genre


        String nameOfAlbum =  tvAlbum.getText().toString();
        String fullDate = year_x + "-" + month_x + "-" + day_x;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date date = format.parse(fullDate);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());

            Album album = new Album(nameOfAlbum,  sqlDate);
            //adding genre
           // album.setGenre();
           // Album.Genre genre = (Album.Genre) Album.Genre.valueOf(selectedAlbum);

       //     album.setGenre(genre);
            mr.addAlbum(album);
            PersistenceXstream.saveToXMLwithXStream(mr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void AddPlaylist(View v){
        Manager mr = Manager.getInstance();
        TextView tv = (TextView) findViewById(R.id.EditTextAddPlaylist);

        mr.addPlaylist(new Playlist(tv.getText().toString()));
        PersistenceXstream.saveToXMLwithXStream(mr);


    }

    public void AddLocation(View v , int volume){
        Manager mr = Manager.getInstance();
        TextView tvLocation = (TextView) findViewById(R.id.addLocationEditText);

        Location location = new Location(tvLocation.getText().toString(), volume);

        mr.addLocation(location);
        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public void AddGroupLocation(View v){
        Manager mr = Manager.getInstance();
        TextView tvGroupLocation = (TextView) findViewById(R.id.addGroupLocationEditText);

        GroupLocation gl = new GroupLocation(tvGroupLocation.getText().toString());
        mr.addGroupLocation(gl);
        PersistenceXstream.saveToXMLwithXStream(mr);
    }




    public void deleteAlbum(String selectedAlbumToDelete){

        Manager mr = Manager.getInstance();
        List<Album> albumsForDeletion = mr.getAlbums();
        for(int i = 0; i<albumsForDeletion.size(); i++){
            if(albumsForDeletion.get(i).getName().equals(selectedAlbumToDelete)){
                deleteAlbum = albumsForDeletion.get(i);
                mr.removeAlbum(deleteAlbum);

            }

        }

        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public void deleteSong(String selectedSongToDelete){

        Manager mr = Manager.getInstance();
        List<Song> songsForDeletion = mr.getSongs();
        for(int i = 0; i<songsForDeletion.size(); i++){
            if(songsForDeletion.get(i).getName().equals(selectedSongToDelete)){
                deleteSong = songsForDeletion.get(i);
                mr.removeSong(deleteSong);

            }

        }

        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public void deleteArtist(String selectedArtistToDelete){

        Manager mr = Manager.getInstance();
        List<Artist> artistsForDeletion = mr.getArtists();
        for(int i = 0; i<artistsForDeletion.size(); i++){
            if(artistsForDeletion.get(i).getName().equals(selectedArtistToDelete)){
                deleteArtist = artistsForDeletion.get(i);
                mr.removeArtist(deleteArtist);

            }

        }

        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public void deletePlaylist(String selectedPlaylistToDelete){

        Manager mr = Manager.getInstance();
        List<Playlist> playlistsForDeletion = mr.getPlaylists();
        for(int i = 0; i<playlistsForDeletion.size(); i++){
            if(playlistsForDeletion.get(i).getName().equals(selectedPlaylistToDelete)){
                deletePlaylist = playlistsForDeletion.get(i);
                mr.removePlaylist(deletePlaylist);

            }

        }

        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public void deleteLocation(String selectedPlaylistToDelete){
        Manager mr = Manager.getInstance();
        List<Location> locationForDeletion = mr.getLocations();
        for(int i=0; i<locationForDeletion.size();i++){
            if(locationForDeletion.get(i).getName().equals(selectedPlaylistToDelete)){
                deleteLocationObject = locationForDeletion.get(i);
                mr.removeLocation(deleteLocationObject);

            }
        }
        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    public  void deleteGroupLocation(String selectedGroupLocation){
        Manager mr = Manager.getInstance().getInstance();
        List <GroupLocation> groupLocationForDeletion = mr.getGroupLocations();
        for(int i = 0; i<groupLocationForDeletion.size();i++ ){
            if(groupLocationForDeletion.get(i).getName().equals(selectedGroupLocation)) {
                deleteGroupLocationObject = groupLocationForDeletion.get(i);
                mr.removeGroupLocation(deleteGroupLocationObject);

            }
        }
        PersistenceXstream.saveToXMLwithXStream(mr);
    }

    //end of controller

    //volume bar controller
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    Volume = progress;
       // Toast.makeText(MainActivity.this, "Volume set to: "+ Volume,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public Playlist convertListToListOfSongs(List<Song> songList){

        Playlist pl = new Playlist("temp");


       for(int i = 0; i< songList.size(); i++){
           pl.addSong(songList.get(i));
       }

        return  pl;
    }








}
