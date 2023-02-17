package thomas.inventoryappc482;
/** --------------------------------------------------------------------------------------------------------------*/

/** In-house class is an extension of the part class */
public class InHouse extends Part{
    /** When the In-house radio button is selected by user 'machineID'
     * will be added to the part constructor */
    private int machineID;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @param machineID Sets 'machineID' */
    public void setMachineID(int machineID){
        this.machineID = machineID;
    }
    /** @return machineID */
    public int getMachineID(){
        return machineID;
    }
}
