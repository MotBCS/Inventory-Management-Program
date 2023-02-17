package thomas.inventoryappc482;
/** --------------------------------------------------------------------------------------------------------------*/

/** Outsource class is an extension of the part class */
public class OutSource extends Part {

    /** When the Out-source radio button is selected by user 'Company'
     * will be added to the part constructor */
    private String company;
    public OutSource(int id, String name, double price, int stock, int min, int max, String company){
        super(id, name, price, stock, min, max);
        this.company = company;
    }
    /** --------------------------------------------------------------------------------------------------------------*/

    /** @param company Sets 'company' */
    public void setCompany(String company){
        this.company = company;
    }

    /** @return company (Company's Name)*/
    public String getCompany(){
        return company;
    }
}
