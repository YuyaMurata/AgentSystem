package rda;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.impl.HPAEntity;
import com.ibm.agent.exa.TxID;

/**
 * Generated code for profile.
 *
 * <p>entity type="profile tablename="profile <br>
 * attribute name="UserID" type="STRING" primarykey="true" relationto="UserID" <br>
 * attribute name="Name" type="STRING" <br>
 * attribute name="Sex" type="STRING" <br>
 * attribute name="Age" type="STRING" <br>
 * attribute name="Address" type="STRING" <br> 
 * attribute name="LastAccessTime" type="TIMESTAMP" <br>
**/
public class Profile extends HPAEntity {
    /**
    * Primary key size
    **/
    public static final int PKINDEXSIZE = 1;

    /**
    * Primary key index of UserID
    **/
    public static final int PKINDEX_USERID = 0;

    /**
    * Column index of UserID
    **/
    public static final int USERID = 0;

    /**
    * Column index of Name
    **/
    public static final int NAME = 1;

    /**
    * Column index of Sex
    **/
    public static final int SEX = 2;

    /**
    * Column index of Age
    **/
    public static final int AGE = 3;

    /**
    * Column index of Address
    **/
    public static final int ADDRESS = 4;

    /**
    * Column index of LastAccessTime
    **/
    public static final int LASTACCESSTIME = 5;

    /**
     * This constructor is used by the runtime.
     * An application should not create an instance with this constructor
    **/
    public Profile() {
        super();
    }

    /**
     * Get the version string
     * @return 
    **/
    @Override
    public String getVersion() {
        return "rda1.0";
    }

    /**
     * Get a value of UserID. 
     * The setter method of UserID is not generated because this attribute is a primarykey. 
     * @param tx
     * @return UserID
     **/
    public final String getUserID(TxID tx) {
        // generated code
        return getString(tx,0);
    }

    /**
     * @param tx
     * @return Name
     **/
    public final String getName(TxID tx) {
        // generated code
        return getString(tx,1);
    }

    /**
     * Set a value to Name. 
     * @param tx a transaction context
     * @param value a value to be set to Name
     * @throws com.ibm.agent.exa.AgentException
     **/
    public final void  setName(TxID tx, String value) throws AgentException {
        // generated code
        if (value != null && value.length() > 32) {
            throw new AgentException("Name > maxlength(32)");
        }
        setString(tx,1,value);
    }

    /**
     * @param tx
     * @return Sex
     **/
    public final String getSex(TxID tx) {
        // generated code
        return getString(tx,2);
    }

    /**
     * Set a value to Sex. 
     * @param tx a transaction context
     * @param value a value to be set to Sex
     * @throws com.ibm.agent.exa.AgentException
     **/
    public final void  setSex(TxID tx, String value) throws AgentException {
        // generated code
        if (value != null && value.length() > 2) {
            throw new AgentException("Sex > maxlength(2)");
        }
        setString(tx,2,value);
    }

    /**
     * @param tx
     * @return Age
     **/
    public final String getAge(TxID tx) {
        // generated code
        return getString(tx,3);
    }

    /**
     * Set a value to Age. 
     * @param tx a transaction context
     * @param value a value to be set to Age
     * @throws com.ibm.agent.exa.AgentException
     **/
    public final void  setAge(TxID tx, String value) throws AgentException {
        // generated code
        if (value != null && value.length() > 4) {
            throw new AgentException("Age > maxlength(4)");
        }
        setString(tx,3,value);
    }

    /**
     * @param tx
     * @return Address
     **/
    public final String getAddress(TxID tx) {
        // generated code
        return getString(tx,4);
    }

    /**
     * Set a value to Address. 
     * @param tx a transaction context
     * @param value a value to be set to Address
     * @throws com.ibm.agent.exa.AgentException
     **/
    public final void  setAddress(TxID tx, String value) throws AgentException {
        // generated code
        if (value != null && value.length() > 64) {
            throw new AgentException("Address > maxlength(64)");
        }
        setString(tx,4,value);
    }

    /**
     * @param tx
     * @return LastAccessTime
     **/
    public final java.sql.Timestamp getLastAccessTime(TxID tx) {
        // generated code
        return getTimestamp(tx,5);
    }

    /**
     * Set a value to LastAccessTime. 
     * @param tx a transaction context
     * @param value a value to be set to LastAccessTime
     * @throws com.ibm.agent.exa.AgentException
     **/
    public final void  setLastAccessTime(TxID tx, java.sql.Timestamp value) throws AgentException {
        // generated code
        setTimestamp(tx,5,value);
    }

}
