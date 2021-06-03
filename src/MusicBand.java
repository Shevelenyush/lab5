import java.util.Date;
import java.util.Random;

/**
 * The class where's described the info about elements of MusicBand
 */
public class MusicBand {
    private Long id;
    private String name;
    private Coordinates coordinates;
    private java.util.Date creationDate;
    private long numberOfParticipants;
    private long singlesCount;
    private Integer albumsCount;
    private MusicGenre genre;
    private Studio studio;

    /**
     * Constructor of the Music Band
     * @param id of the band
     * @param name of the Band
     * @param coordinates where are they
     * @param creationDate when the element of the list was created
     * @param numberOfParticipants how much of them is there
     * @param singlesCount how much songs do the band have
     * @param albumsCount how much albums
     * @param genre what is the genre
     * @param studio name and address of the studio
     */
    public MusicBand(Long id, String name, Coordinates coordinates, java.util.Date creationDate, long numberOfParticipants, long singlesCount, Integer albumsCount, String genre, Studio studio) {
        this.id = id;
        if (id == null)
        {
            Long i = new Random().nextLong();
            if(i < 0) i = i*(-1);
            this.id = i;

        }
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = new Date();
        this.numberOfParticipants = numberOfParticipants;
        this.singlesCount = singlesCount;
        this.albumsCount = albumsCount;

        switch (genre) {
            case ("PROGRESSIVE_ROCK"):
                this.genre = MusicGenre.PROGRESSIVE_ROCK;
                break;
            case ("PSYCHEDELIC_ROCK"):
                this.genre = MusicGenre.PSYCHEDELIC_ROCK;
                break;
            case ("POST_ROCK"):
                this.genre = MusicGenre.POST_ROCK;
                break;
            case ("POST_PUNK"):
                this.genre = MusicGenre.POST_PUNK;
                break;
            case ("BRIT_POP"):
                this.genre = MusicGenre.BRIT_POP;
                break;
            default:
                System.out.println("Wrong format. Try again: aoaoao");

        }

        this.studio = studio;

    }

    /**
     * @return the band information
     */
    public String showBandInfo() {
        return "id: " + id + " name: " + name + " coordinates: " + coordinates.getX() +
                " " + coordinates.getY() + " creationDate: " + creationDate +
                " numberOfParticipants: " + numberOfParticipants + " singlesCount: " + singlesCount +
                " albumsCount: " + albumsCount + " genre: " + genre + " studio: " + studio.getName() + " " + studio.getAddress();
    }

    /**
     * @return the id of the band
     */
    public long getId() {
        return id;
    }
    /**
     * sets the random id of the band
     */
    public void setId(Long id) {
        this.id = id;
        if (id == null) {
            Long i = new Random().nextLong();
            if(i < 0) i = i*(-1);
            this.id = i;
        }
    }

    /**
     * @return the genre of the band
     */
    public String getGenre () {
        switch(genre) {
            case BRIT_POP:
                return "BRIT_POP";
            case POST_PUNK:
                return "POST_PUNK";
            case POST_ROCK:
                return "POST_ROCK";
            case PROGRESSIVE_ROCK:
                return "PROGRESSIVE_ROCK";
            case PSYCHEDELIC_ROCK:
                return "PSYCHEDELIC_ROCK";
            default:
                return null;
        }
    }

    public static boolean checkGenre(String g) {
        switch(g) {
            case "BRIT_POP":
                return true;
            case "POST_PUNK":
                return true;
            case "POST_ROCK":
                return true;
            case "PROGRESSIVE_ROCK":
                return true;
            case "PSYCHEDELIC_ROCK":
                return true;
            default:
                return false;
        }
    }



    /**
     * @return the studio of the band
     */
    public Studio getStudio () {
        return studio;
    }

    /**
     * @return the name of the band
     */
    public String getName() {
        return name;
    }

    /**
     * @return the coordinates of the band
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }
    /**
     * @return the creation date of the element of the list
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @return the album count of the band
     */
    public Integer getAlbumsCount() {
        return albumsCount;
    }

    /**
     * @return the number of the participants of the band
     */
    public long getNumberOfParticipants() {
        return numberOfParticipants;
    }

    /**
     * @return the singles count of the band
     */
    public long getSinglesCount() {
        return singlesCount;
    }
}

/**
 * This class describes the elements of the Studio of the Music Band
 */
class Studio {
    private String name; //Поле может быть null
    private String address; //Поле не может быть null

    /**
     * The constructor of the Studio class
     * @param name
     * @param address
     */
    public Studio (String name, String address) {
        this.name = name;
        this.address = address;
    }

    /**
     * @return the address of the studio
     */
    public String getAddress() {
        return address;
    }

    /**
     * @return the name of the studio
     */
    public String getName() {
        return name;
    }
}

/**
 * The class of the coordinates o the band
 */
class Coordinates {
    private Integer x;
    private Double y;

    /**
     * The constructor of the class
     * @param x
     * @param y
     */
    public Coordinates (Integer x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return y coordinate
     */
    public Double getY() {
        return y;
    }

    /**
     * @return x coordinate
     */
    public Integer getX() {
        return x;
    }
}

/**
 * The enum of the music genres
 */
enum MusicGenre {
    PROGRESSIVE_ROCK,
    PSYCHEDELIC_ROCK,
    POST_ROCK,
    POST_PUNK,
    BRIT_POP;

}