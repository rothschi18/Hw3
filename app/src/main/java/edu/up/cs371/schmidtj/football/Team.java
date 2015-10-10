package edu.up.cs371.schmidtj.football;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by schmidtj on 9/23/2015.
 */

/**
 * Class: Team
 *
 * Purpose: Holds the data for a team.
 */
public class Team implements Serializable{
    //Variables to hold data of a team
    private String teamsName;
    private int gamesWins;
    private int gamesPlayed;
    private int gamesLost;
    private String imageID;
    public ArrayList<String> playerList;
    private HashMap<String,Player> teamRoster;

    /**
     * Constructor: Team
     *
     * Purpose: Construct a Team object, and add a teamManager player
     *
     * @param name Name of Team passed in
     * @param games_Wins Number of games won
     * @param games_Lost Number of games lost
     */
    protected Team(String name, int games_Wins, int games_Lost)
    {
        this.teamsName=name;
        //negative games won makes no sense, default to 0
        if(games_Wins < 0)
            games_Wins = 0;

        this.gamesWins=games_Wins;
        //negative games lost makes no sense, default to 0
        if(games_Lost < 0)
            games_Lost = 0;
        this.gamesLost=games_Lost;

        this.gamesPlayed = this.gamesLost+this.gamesWins;

        this.setImageID("green_cran");

        playerList = new ArrayList<String>();
        teamRoster = new HashMap<String,Player>();

        Player teamManager = new Player("TheBigFish",0,0);
        teamManager.setImageID("orange_fish");

        this.addAPlayer(teamManager);
    }

    /**
     * Method: addAPlayer
     *
     * Purpose: add a player to the teamRoster list
     * @param instPlayer
     * @return success value
     */
    public int addAPlayer(Player instPlayer)
    {
        try
        {
            teamRoster.put(instPlayer.getPlayersFullName(), instPlayer);
            if( playerList.indexOf(instPlayer.getPlayersFullName()) == -1)
                playerList.add(instPlayer.getPlayersFullName());
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //return the player with indicated name
    public Player getPlayer(String playerName)
    {
        return teamRoster.get(playerName);
    }

    /**
     * Method: setImageID
     *
     * Purpose: set the imageId of the team to the imageName passed in
     * @param imageName name of the image that the user wants to set the team to
     * @return int --> success value
     */
    public int setImageID(String imageName)
    {
        try
        {
            this.imageID= imageName;
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //get the image id string of the teams image
    public String getImageID()
    {
        return this.imageID;
    }
    //Method never used
    public void updateWin()
    {
        this.gamesWins++;
        this.updatePlayed();
    }
    //Return the number of wins for the team
    public int getWin()
    {
        return this.gamesWins;
    }
    //Adds one to the number of games played
    private void updatePlayed()
    {
        this.gamesPlayed++;
    }
    //Method never used
    public int setWins(int games_Wins)
    {
        try
        {
            this.gamesWins=games_Wins;
            return 1;
        }
        finally
        {
            return 0;
        }
    }

    /**
     * Method: setWins
     *
     * Purpose: sets the wins of the team
     * @param games_Wins
     * @return int --> success value
     */
    public int setWins(String games_Wins)
    {
        try
        {
            this.gamesWins=Integer.parseInt(games_Wins);
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Method never used
    public int getPlayed()
    {
        return this.gamesPlayed;
    }
    //Method never used
    public void updateLoses()
    {
        this.gamesLost++;
        this.updatePlayed();
    }
    //returns the games lost
    public int getLoses()
    {
        return this.gamesLost;
    }

    //Method never used
    public int setLoses(int games_lost)
    {
        try
        {
            this.gamesLost=games_lost;
            return 1;
        }
        finally
        {
            return 0;
        }
    }

    /**
     * Method setLoses
     *
     * Purpose: sets the loses of the team
     * @param games_lost Number of losses passed in
     * @return int --> success value
     */
    public int setLoses(String games_lost)
    {
        try
        {
            this.gamesLost=Integer.parseInt(games_lost);
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Method never used
    public int setTeamFullName(String Name)
    {
        try
        {
            this.teamsName=Name;
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //return the name of the team
    public String getTeamFullName()
    {
        return this.teamsName;
    }
    //Method never used
    public int updatePlayers(String playerName, int Assists, int Goals)
    {
        if( teamRoster.get(playerName) == null)
            return 0;

        teamRoster.get(playerName).setAssists(Assists);
        teamRoster.get(playerName).setAssists(Goals);

        return 1;
    }

}
