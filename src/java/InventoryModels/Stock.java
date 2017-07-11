package InventoryModels;

import java.util.Date;

/**
 *
 * @author Barry Gray
 */
public interface Stock {

    boolean setstockID(int id);

    int getstockID();

    boolean setDescription(String desc);

    String getDescription();

    boolean setQuantity(double quantity);

    double getQuantity();

    boolean setLowThreshold(double thresh);

    double getLowThreshold();

    boolean setExpirryDate(Date date);

    Date getExpiraryDate();

}
