package edu.up.cs371.schmidtj.football;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

/*
* Class: MainActivity
*
* Purpose: Starting activity for Fantasy Football Leaugue. Class implements basic features such as viewing team data, has options for
*          adding a team, and clearing team stats
*
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    /*
    * Variables declared to be used by MainActivity
     */
    public Button clearStat; //clearStat is a button that when pressed will clear all the stats of the team
    public Button addAnotherTeam; //addAnotherTeam is a button that will give the user the option of adding a team
    public ImageButton clickToTeamRoster; //Button that Switches activities to view a Team

    public EditText teamWins; //User can input the number of wins for a team
    public EditText teamLoses; //User can input the number of loses for a team
    public EditText teamName; //User can input the name of a team

    public ArrayList<String> listOfTeams; //declare an ArrayList of teams
    public ArrayList<String> listImageSelector;

    public Spinner teamSpinner; //stores the list of teams
    public Spinner imageTeamSelector;

    public HashMap<String,Team> Teams; //declare a hashmap of <key, Value> where key is a string that will reference to a certain team

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        * Following lines of code are used to initialize the variables that were declared.
        * 1. Find all the buttons by their id in the MainActivity
        * 2. Set all of the buttons to respond to OnClick events
        * 3. Set the EditText fields to the corresponding EditText views in MainActivity
         */
        clearStat= (Button) findViewById(R.id.clearStat);
        addAnotherTeam= (Button) findViewById(R.id.createTeam);
        clickToTeamRoster= (ImageButton) findViewById(R.id.to_new_activity);

        clearStat.setOnClickListener(this);
        addAnotherTeam.setOnClickListener(this);
        clickToTeamRoster.setOnClickListener(this);

        teamWins= (EditText) findViewById(R.id.teamWins);
        teamLoses= (EditText) findViewById(R.id.teamLoses);
        teamName= (EditText) findViewById(R.id.teamName);


        /*
        * Create two default teams, initialize the Teams HashMap, and add the teams to it.
         */
        Team defualtTeam1 = new Team("Dragons",0,0);
        defualtTeam1.setImageID("blue_dragons");

        Team defualtTeam2 = new Team("Butterfly",0,0);
        defualtTeam2.setImageID("orange_butterfly");

        Teams=new HashMap<String,Team>();

        Teams.put("Dragons",defualtTeam1);
        Teams.put("Butterfly",defualtTeam2);


        /*
        * Add the default teams to the arrayList
        *
        * Add all of the image resource names to the list of images
         */
        listOfTeams = new ArrayList<String>();
        listOfTeams.add("Dragons");
        listOfTeams.add("Butterfly");

        listImageSelector = new ArrayList<String>();
        listImageSelector.add("orange_butterfly");
        listImageSelector.add("pink_butterfly");
        listImageSelector.add("green_cran");
        listImageSelector.add("blue_dragons");
        listImageSelector.add("green_dragons");
        listImageSelector.add("red_dragons");
        listImageSelector.add("orange_fish");
        listImageSelector.add("blue_pegasus");


        /*
        * Initialize the Spinners
         */
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);
        ArrayAdapter<String> teamSpinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listOfTeams);
        teamSpinner.setAdapter(teamSpinnerAdapter);

        imageTeamSelector = (Spinner) findViewById(R.id.imageTeamSelector);
        ArrayAdapter<String> imageTeamSelectorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listImageSelector);
        imageTeamSelector.setAdapter(imageTeamSelectorAdapter);

        teamSpinner.setOnItemSelectedListener(this);
        imageTeamSelector.setOnItemSelectedListener(this);

        int index = listImageSelector.indexOf(Teams.get("Dragons").getImageID());
        imageTeamSelector.setSelection( index );
    }




    @Override
    /*
    * Method: onClick
    *
    * Purpose: Handles touch events for the MainActivity
    *
    * Return Type: None
    *
    * Paramater(s): view is the view that was clicked.
     */
    public void onClick(View view) {
        //Clear all the stats of the team
        if(view == clearStat)
        {
            teamWins.setText("");
            teamLoses.setText("");
            teamName.setText("");
            teamSpinner.setSelection(0);
        }
        //Start a new TeamBoard activity and send the Team data that was selected from the spinner
        if(view == clickToTeamRoster)
        {
            //Create a new intent with the TeamBoard class
            Intent intent = new Intent(this, TeamBoard.class);
            //Send the data of the team that was clicked
            intent.putExtra("aTeam", Teams.get(teamSpinner.getSelectedItem().toString()));

            //Start the activity, with the code 100 being the return code
            startActivityForResult(intent,100);

        }

        // add a team with the values in the edit text fields
        if(view == addAnotherTeam)
        {
            //If any of the values are empty, return, If program tried to create team with null value it would complain
            if(String.valueOf(teamWins.getText()).isEmpty() || String.valueOf(teamLoses.getText()).isEmpty() || String.valueOf(teamName.getText()).isEmpty())
                return;
            //If the team is not in the list, create a new team Object and add it to the list
            if( listOfTeams.indexOf( String.valueOf(teamName.getText()) ) == -1 )
            {
                Team pTemp = new Team(String.valueOf(teamName.getText()), Integer.parseInt(String.valueOf(teamWins.getText())), Integer.parseInt(String.valueOf(teamLoses.getText())));
                pTemp.setImageID( imageTeamSelector.getSelectedItem().toString() );

                Teams.put(String.valueOf(teamName.getText()), pTemp);

                listOfTeams.add(String.valueOf(teamName.getText()));
            }
            //Else, edit the fields of the existing team
            else
            {
                Teams.get(String.valueOf(teamName.getText())).setLoses(String.valueOf(teamLoses.getText()));
                Teams.get(String.valueOf(teamName.getText())).setWins(String.valueOf(teamWins.getText()));
                Teams.get(String.valueOf(teamName.getText())).setImageID(imageTeamSelector.getSelectedItem().toString());
            }
            //sets the object to the position indicated
            teamSpinner.setSelection( listOfTeams.indexOf( String.valueOf(teamName.getText()) ));

        }

    }

    /*
    * Method: onActivityResult
    *
    * Return type: void
    *
    * Parameters: requestCode, resultCode, and data
    *
    * Purpose: Get the team back from the TeamBoard activity, and update the list of Teams
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100)
        {
            if (resultCode == 1)
            {
                Team tTemp = (Team)  data.getSerializableExtra("returnATeam");
                Teams.put( tTemp.getTeamFullName().toString(), tTemp);
            }
        }
    }

    @Override
    /*
    * Method: onItemSelected
    *
    * Return Type: void
    *
    * Parameters
    * adapterView:	The AdapterView where the selection happened
    * view:	        The view within the AdapterView that was clicked
    * i:	        The position of the view in the adapter
    * l:	        The row id of the item that is selected
    *
    * Purpose: Upon selection of an item from the spinner, the method will display the textViews of the main activity
    *          to all of the values of the object clicked
     */
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //check if the selection was made from the teamSpinner, if it was, set all of the text fields to their respective values
        //from the object that was selected
        if(adapterView == teamSpinner)
        {
            teamLoses.setText(String.valueOf(Teams.get(teamSpinner.getSelectedItem().toString()).getLoses()));
            teamName.setText(teamSpinner.getSelectedItem().toString());
            teamWins.setText(String.valueOf(Teams.get(teamSpinner.getSelectedItem().toString()).getWin()));

            int index = listImageSelector.indexOf(Teams.get(teamSpinner.getSelectedItem().toString()).getImageID());
            imageTeamSelector.setSelection( index );
        }
        //if the imageTeamSelector was pressed, create an id for the teams image and set the imagebutton to that image
        if(adapterView == imageTeamSelector)
        {
            int id = getResources().getIdentifier(this.getPackageName() + ":drawable/" + imageTeamSelector.getSelectedItem().toString(), null, null);
            clickToTeamRoster.setImageResource(id);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {


    }
}
