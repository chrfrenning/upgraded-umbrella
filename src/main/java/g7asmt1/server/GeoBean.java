package g7asmt1.server;
import com.opencsv.bean.CsvBindByName;

public class GeoBean {
    
    @CsvBindByName(column = "Geoname ID")
    private int geonameId;
    
    @CsvBindByName(column = "Name")
    private String name;
    
    @CsvBindByName(column = "Country Code")
    private String countryCode;
    
    @CsvBindByName(column = "Country name EN")
    private String countryNameEN;
    
    @CsvBindByName(column = "Population")
    private long population;
    
    @CsvBindByName(column = "Timezone")
    private String timezone;
    
    @CsvBindByName(column = "Coordinates")
    private String coordinates;

    // Getters and setters

    public int getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(int geonameId) {
        this.geonameId = geonameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryNameEN() {
        return countryNameEN;
    }

    public void setCountryNameEN(String countryNameEN) {
        this.countryNameEN = countryNameEN;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
