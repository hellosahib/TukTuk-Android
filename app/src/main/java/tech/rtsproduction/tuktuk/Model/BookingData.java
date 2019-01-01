package tech.rtsproduction.tuktuk.Model;

public class BookingData {

    private String mPickupLocation, mDropOfLocation;
    private String mBookingTime;
    private String mBookingDate;

    public BookingData(String mPickupLocation, String mDropOfLocation, String mBookingTime, String mBookingDate) {
        this.mPickupLocation = mPickupLocation;
        this.mDropOfLocation = mDropOfLocation;
        this.mBookingTime = mBookingTime;
        this.mBookingDate = mBookingDate;
    }

    public String getPickupLocation() {
        return mPickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.mPickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return mDropOfLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.mDropOfLocation = dropoffLocation;
    }

    public String getBookingTime() {
        return mBookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.mBookingTime = bookingTime;
    }

    public String getmBookingDate() {
        return mBookingDate;
    }

    public void setmBookingDate(String mBookingDate) {
        this.mBookingDate = mBookingDate;
    }
}
