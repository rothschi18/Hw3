package edu.up.cs371.schmidtj.football;

import java.io.Serializable;

/**
 * Created by schmidtj on 9/23/2015.
 */

/**
 * Class: Player
 *
 * Purpose: Player class holds data for an individual player.
 *
 */
public class Player implements Serializable{
    //Variable definitions to hold data for a Player
    private String playersFullName;
    private int Goals;
    private int Assists;
    private String imageID;

    /**
     * Constructor: Player
     * @param name The name passed into the constructor
     * @param GoalsStat the number of goals passed in
     * @param AssistsStat the number of assists passed in
     */
    protected Player(String name, int GoalsStat, int AssistsStat)
    {
        this.playersFullName=name;
        this.Goals=GoalsStat;
        this.Assists=AssistsStat;

        this.setImageID("green_cran");
    }

    /**
     * Method: setImageID
     *
     * @param imageName the name of the Image passed in
     *
     * @return int --> shows the caller of the method whether it was successful in setting the name
     *
     * Purpose: Set the imageID of a Player object to the value passed in
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

    /**
     * Method: getImageID
     *
     * Purpose: return the imageID string from the respective Player object that this method was called on
     *
     * @return String --> the string from the Player that is being accessed
     */
    public String getImageID()
    {
        return this.imageID;
    }

    /**
     * Method: setGoals
     *
     * Purpose: add the goals passed in to the Players goals
     *
     * @param GoalsStat The number of goals passed in
     *
     * @return int --> lets the caller of the method know whether it was successful in setting the number of goals
     */
    public int setGoals(int GoalsStat)
    {
        try
        {
            this.Goals+=GoalsStat;
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Method never used
    public int setGoals(String GoalsStat)
    {
        try
        {
            this.Goals+=Integer.parseInt( GoalsStat );
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Method to return the number of goals the Player has
    public int getGoals()
    {
        return this.Goals;
    }

    /**
     * Method: setAssists
     *
     * Purpose: adds the number of assists passed in to the players current assists
     * @param AssistsStat Number of assists passed in
     *
     * @return int --> lets the caller know whether the method was successful
     *
     */
    public int setAssists(int AssistsStat)
    {
        try
        {
            this.Assists+=AssistsStat;
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Method never used
    public int setAssists(String AssistsStat)
    {
        try
        {
            this.Assists+=Integer.parseInt( AssistsStat );
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Return the nubmer of assists of a Player
    public int getAssistsStat()
    {
        return this.Assists;
    }
    //Method never used
    public int setPlayersFullName(String Name)
    {
        try
        {
            this.playersFullName=Name;
            return 1;
        }
        finally
        {
            return 0;
        }
    }
    //Returns the players full name
    public String getPlayersFullName()
    {
        return this.playersFullName;
    }

}
