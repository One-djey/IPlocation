package COM;

public class IPinfo {
    private String statusCode;
    private String ipAddress;
    private String countryCode;
    private String countryName;
    private String regionName;
    private String cityName;
    private String zipCode;
    private String latitude;
    private String longitude;
    private String timeZone;

    public IPinfo(String statusCode, String ipAddress, String countryCode, String countryName, String regionName, String cityName, String zipCode, String latitude, String longitude, String timeZone) {
        this.statusCode = statusCode;
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.regionName = regionName;
        this.cityName = cityName;
        this.zipCode = zipCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeZone = timeZone;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String toString(){
        String str="";

        str+="{\n";
        str+="  statusCode:  '"+getStatusCode()+"'\n";
        str+="  ipAddress:   '"+getIpAddress()+"'\n";
        str+="  countryCode: '"+getCountryCode()+"'\n";
        str+="  countryName: '"+getCountryName()+"'\n";
        str+="  regionName:  '"+getRegionName()+"'\n";
        str+="  cityName:    '"+getCityName()+"'\n";
        str+="  zipCode:     '"+getZipCode()+"'\n";
        str+="  latitude:    '"+getLatitude()+"'\n";
        str+="  longitude:   '"+getLongitude()+"'\n";
        str+="  timeZone:    '"+getTimeZone()+"'\n";
        str+="}\n";

        return str;
    }
}
