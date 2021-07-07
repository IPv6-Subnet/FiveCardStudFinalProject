package edu.wvup.pmetzger1;// import com.doYouKnowDeWay;
/**
 *
 */

public class IllegalBetException extends Exception
{
    //
    private String reason;


    /**
     *
     */
    public IllegalBetException(String reason)
    {
        this.reason = reason;
    }

    /**
     *
     */
    public IllegalBetException()
    {
        reason = "An illegal bet was made!";
    }

    /*
     * Returns reason for failure
     */
    public String toString()
    {
        return "Reason for failure: " + reason + " was found.";
    }
}
