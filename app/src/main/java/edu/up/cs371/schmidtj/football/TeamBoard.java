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
 */
public class TeamBoard extends ActionBarActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener{


    Intent intent;
    ImageView playerImage;

    public EditText playerGoals;
    public EditText playerAssists;
    public EditText playerName;

    public Button addAnotherPlayer;

    public Team theTeam;

    public Spinner playerSpinner;
    public Spinner imagePlayerSelector;

    public ArrayList<String> listImageSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_board);

        addAnotherPlayer= (Button) findViewById(R.id.createPlayer);
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

    public void return_back_click(View view)
    {
        intent.putExtra("returnATeam",theTeam);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onClick(View view) {

        if(view == addAnotherPlayer)
        {

            if(String.valueOf(playerGoals.getText()).isEmpty() || String.valueOf(playerAssists.getText()).isEmpty() || String.valueOf(playerName.getText()).isEmpty())
                return;

            if(theTeam.playerList.indexOf(String.valueOf(playerName.getText())) == -1 )
            {
                Player pTemp = new Player(String.valueOf(playerName.getText()), Integer.parseInt(String.valueOf(playerGoals.getText())), Integer.parseInt(String.valueOf(playerAssists.getText())));
                pTemp.setImageID( imagePlayerSelector.getSelectedItem().toString() );

                theTeam.addAPlayer(pTemp);

            }
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
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
