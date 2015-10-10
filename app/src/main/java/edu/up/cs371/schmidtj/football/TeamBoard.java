package edu.up.cs371.schmidtj.football;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Class: TeamBoard
 *
 * Purpose: Displays a team and gives options to add a player to the team
 */
public class TeamBoard extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    //Variable definitions for the TeamBoard

    Intent intent;  //This intent is used to pass information back to the MainActivity
    ImageView playerImage; //This ImageView is used to display the image of a player from the team that is passed into this activity

    public EditText playerGoals; //Allows the user to create a player, and customize the goals the player has scored
    public EditText playerAssists;//Allows the user to create a player, and customize the assists the player has earned
    public EditText playerName;//Allows the user to create a player, and customize the name of the player

    public Button addAnotherPlayer; //Declare a button to allow the user to add another player

    public Team theTeam; //This variable is used to store the team that is passed in from the MainActivity

    public Spinner playerSpinner; //Used to store the players of the team passed in and allow the user to select from that list.
    public Spinner imagePlayerSelector; //Stores the images that are possible for players, and lets the user select from them

    public ArrayList<String> listImageSelector; //Stores the names of the pictures that are possible to select from

    @Override
    /**
     * Method: onCreate
     *
     * Purpose: Method is called on the creation of this activity, and initializes all of the variables to their respective values
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_board);

        addAnotherPlayer = (Button) findViewById(R.id.createPlayer);
        addAnotherPlayer.setOnClickListener(this);

        playerGoals= (EditText) findViewById(R.id.playerGoals);
        playerAssists= (EditText) findViewById(R.id.playerAssists);
        playerName= (EditText) findViewById(R.id.playerName);

        intent = getIntent();

        playerImage = (ImageView) findViewById( R.id.playerImage );

        theTeam = (Team)  intent.getSerializableExtra("aTeam");

        playerGoals.setText( theTeam.getPlayer("TheBigFish").getPlayersFullName() );
        playerAssists.setText( String.valueOf(theTeam.getPlayer("TheBigFish").getGoals()) );
        playerName.setText( String.valueOf(theTeam.getPlayer("TheBigFish").getAssistsStat()) );

        int id = getResources().getIdentifier(this.getPackageName() + ":drawable/" + theTeam.getPlayer("TheBigFish").getImageID(), null, null);
        playerImage.setImageResource(id);

        listImageSelector = new ArrayList<String>();
        listImageSelector.add("orange_butterfly");
        listImageSelector.add("pink_butterfly");
        listImageSelector.add("green_cran");
        listImageSelector.add("blue_dragons");
        listImageSelector.add("green_dragons");
        listImageSelector.add("red_dragons");
        listImageSelector.add("orange_fish");
        listImageSelector.add("blue_pegasus");

        playerSpinner = (Spinner) findViewById(R.id.playerSpinner);
        ArrayAdapter<String> playerSpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, theTeam.playerList);
        playerSpinner.setAdapter(playerSpinnerAdapter);

        imagePlayerSelector = (Spinner) findViewById(R.id.imagePlayerSelector);
        ArrayAdapter<String> imagePlayerSelectorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listImageSelector);
        imagePlayerSelector.setAdapter(imagePlayerSelectorAdapter);

        playerSpinner.setOnItemSelectedListener(this);
        imagePlayerSelector.setOnItemSelectedListener(this);
    }

    /**
     * Method: return_back_click
     *
     * Purpose: return back to the main Activity, and pass the team that was being manipulated back to main
     * @param view The button that was clicked
     */
    public void return_back_click(View view)
    {
        intent.putExtra("returnATeam",theTeam);
        setResult(1, intent);
        finish();
    }

    @Override
    /**
     * Method: onClick
     *
     * Purpose: Handle touch events for the TeamBoard activity
     *
     */
    public void onClick(View view) {
        //if the user wants to add another player then perform the following code
        if(view == addAnotherPlayer)
        {
            //Check to make sure none of the desired fields for setting the player data are empty
            if(String.valueOf(playerGoals.getText()).isEmpty() || String.valueOf(playerAssists.getText()).isEmpty() || String.valueOf(playerName.getText()).isEmpty())
                return;
            //If the player is not in the list of current players, create a new player object and add it to the list
            if(theTeam.playerList.indexOf(String.valueOf(playerName.getText())) == -1 )
            {
                Player pTemp = new Player(String.valueOf(playerName.getText()), Integer.parseInt(String.valueOf(playerGoals.getText())), Integer.parseInt(String.valueOf(playerAssists.getText())));
                pTemp.setImageID( imagePlayerSelector.getSelectedItem().toString() );

                theTeam.addAPlayer(pTemp);

            }
            //otherwise overwrite the existing players stats
            else
            {
                theTeam.getPlayer(String.valueOf(playerName.getText())).setGoals( Integer.parseInt(String.valueOf(playerGoals.getText())) );
                theTeam.getPlayer(String.valueOf(playerName.getText())).setAssists(Integer.parseInt(String.valueOf(playerAssists.getText())) );
                theTeam.getPlayer(String.valueOf(playerName.getText())).setImageID( imagePlayerSelector.getSelectedItem().toString() );
            }

            playerSpinner.setSelection( theTeam.playerList.indexOf(String.valueOf(playerName.getText())) );

        }

    }

    @Override
    /**
     *
     * Method: onItemSelected
     *
     * Purpose: When the user makes a selection from the either spinner, the method will display the stats of the player or the image of
     *          the player depending on which spinner was chosen from
     * Parameters
     * adapterView:	The AdapterView where the selection happened
     * view:	        The view within the AdapterView that was clicked
     * i:	        The position of the view in the adapter
     * l:	        The row id of the item that is selected
     */
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //If the adapterView is equal to the playerSpinner, then display the stats of the player
        if(adapterView == playerSpinner)
        {
            playerGoals.setText(String.valueOf(theTeam.getPlayer(playerSpinner.getSelectedItem().toString()).getGoals()));
            playerName.setText(playerSpinner.getSelectedItem().toString());
            playerAssists.setText(String.valueOf(theTeam.getPlayer(playerSpinner.getSelectedItem().toString()).getAssistsStat()));

            int index = listImageSelector.indexOf(theTeam.getPlayer(playerSpinner.getSelectedItem().toString()).getImageID());
            imagePlayerSelector.setSelection( index );

            int id = getResources().getIdentifier(this.getPackageName() + ":drawable/" +  theTeam.getPlayer(playerSpinner.getSelectedItem().toString()).getImageID(), null, null);
            playerImage.setImageResource(id);
        }
        //if the adapterView is equal to the imagePlayerSelector then display the corresponding image of the player
        if(adapterView == imagePlayerSelector)
        {
            int id = getResources().getIdentifier(this.getPackageName() + ":drawable/" +  imagePlayerSelector.getSelectedItem().toString(), null, null);
            playerImage.setImageResource(id);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
