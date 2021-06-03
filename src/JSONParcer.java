import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Mokhovikov Anatoly R3137
 * main class of the program
 */
public class JSONParcer {

    public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();
        String h = "IN";
        JSONArray arr = (JSONArray) parser.parse(new FileReader(System.getenv(h)));
        Date date = new Date();
        ArrayList<MusicBand> list = new ArrayList<>();

        for (Object o : arr) {
            JSONObject obj = (JSONObject) o;

            Long id = (Long) obj.get("id");
            String name = (String) obj.get("name");
            JSONObject obj_coord = (JSONObject) obj.get("coordinates");
            Integer x_int = ((Long)obj_coord.get("x")).intValue();;
            Double y_doub = (Double) obj_coord.get("y");
            Coordinates coordinates = new Coordinates(x_int, y_doub);
            String creationDate_str = (String) obj.get("creationDate");
            DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            java.util.Date creationDate = format.parse(creationDate_str);


            long numberOfParticipants = (long) obj.get("numberOfParticipants");
            long singlesCount = (long) obj.get("singlesCount");
            Integer albumsCount = ((Long)obj.get("albumsCount")).intValue();
            String genre = (String) obj.get("genre");
            JSONObject obj_stud = (JSONObject) obj.get("studio");
            String name_str = (String) obj_stud.get("name");
            String address_str = (String) obj_stud.get("address");
            Studio studio = new Studio(name_str, address_str);

            MusicBand musBand = new MusicBand(id, name, coordinates, creationDate, numberOfParticipants, singlesCount, albumsCount, genre, studio);
            list.add(musBand);

        }

        newId(list);
        Scanner c = new Scanner(System.in);
        consoleProcess(c, list, date);
    }

    public static void consoleProcess(Scanner sc, ArrayList<MusicBand> list, Date cr_date) throws IOException {
        boolean isExit = false;
        ArrayDeque<String> history = new ArrayDeque<>();

        while (!isExit) {
            String[] data = null;
            if(sc.hasNext()){
                data = sc.nextLine().replaceAll("^\\s+", "").split(" ", 2);
            }
            else {
                System.exit(0);
            }

            String cmd = data[0];
            String arg;

            try{
                arg = data[1];
            } catch (ArrayIndexOutOfBoundsException e) {
                arg = null;
            }

            switch (cmd) {
                case ("help"):
                    System.out.println("help : show the information about the commands\n" +
                            "info : show the collection information(type, date of initialization, number of elements etc.)\n)" +
                            "show : show all the elements of collection\n" +
                            "add {element} : add the new element to collection\n" +
                            "update_id {element} : update the id of the collection element by its id\n" +
                            "remove_by_id id : delete the element by its id\n" +
                            "clear : clear the collection\n" +
                            "save : save the collection to file\n" +
                            "execute_script file_name : read and execute script from th file.\n" +
                            "exit : exit the program without saving\n" +
                            "remove_last : delete the last element of the collection\n" +
                            "remove_greater {element} : delete all the element higher than given one\n" +
                            "history : show the last 11 commands\n" +
                            "remove_any_by_genre genre : delete 1 collection element which genre field is equal to given from you\n" +
                            "count_by_studio {Studio name} : show the number of th collection elements, which studio field is equal to given from you\n" +
                            "print_field_descending_studio : show the values of the studio field of the all elements in descending order ");
                    updateHistory(history, cmd);
                    break;
                case ("info"):
                    System.out.println("Collection type: ArrayList\n" +
                            "Date of initialization: " + cr_date +
                            "\nNumber of the collection elements: " + list.size());
                    updateHistory(history, cmd);
                    break;
                case ("show"):
                    if(list.isEmpty()) {
                        System.out.println("The list of the bands is empty");
                        updateHistory(history, cmd);
                        break;
                    }
                    for(MusicBand musicBand : list) {
                        System.out.println(musicBand.showBandInfo());
                    }
                    updateHistory(history, cmd);
                    break;
                case ("add"):
                    MusicBand band = addBand();
                    list.add(band);
                    System.out.println("The new band was added");
                    updateHistory(history, cmd);
                    break;
                case ("update_id"):
                    try {
                        Long id = Long.parseLong(arg);
                        for (MusicBand bnd : list) {
                            if (bnd.getId() == id) {
                                bnd.setId(null);
                                System.out.println("Band ID was updated");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format. Try again. ");
                    }
                    updateHistory(history, cmd);
                    break;
                case ("remove_by_id"):
                    try{
                        Long id = Long.parseLong(arg);
                        for(Iterator<MusicBand> it = list.iterator(); it.hasNext(); ) {
                            MusicBand musicBand = it.next();
                            if (musicBand.getId() == id) {
                                it.remove();
                                System.out.println("The band was deleted");
                            }
                        }
                    } catch(NumberFormatException e) {
                        System.out.println("Wrong format. Try again. ");
                    }
                    updateHistory(history, cmd);
                    break;
                case ("clear"):
                    list.clear();
                    System.out.println("Everything was deleted. ");
                    updateHistory(history, cmd);
                    break;
                case ("save"):
                    String output_name = "data.json";
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(output_name))){
                        writer.write("[ ");
                        for(MusicBand musicBand : list) {
                            String id = String.valueOf(musicBand.getId());
                            String name = musicBand.getName();
                            String coordinate_x = String.valueOf(musicBand.getCoordinates().getX());
                            String coordinate_y = String.valueOf(musicBand.getCoordinates().getY());
                            String date = String.valueOf(musicBand.getCreationDate());
                            String particNumber = String.valueOf(musicBand.getNumberOfParticipants());
                            String singlesCount = String.valueOf(musicBand.getSinglesCount());
                            String albumsCount = String.valueOf(musicBand.getAlbumsCount());
                            String genre = String.valueOf(musicBand.getGenre());
                            String address = musicBand.getStudio().getAddress();
                            String st_name = musicBand.getStudio().getName();

                            writer.write("{\n" +
                                    "    \"id\": "+ id +" ,\n" +
                                    "    \"name\": \""+ name +"\",\n" +
                                    "    \"coordinates\": {\n" +
                                    "      \"x\": "+ coordinate_x +",\n" +
                                    "      \"y\": "+ coordinate_y +"\n" +
                                    "    },\n" +
                                    "    \"creationDate\": \"" + date +"\",\n" +
                                    "    \"numberOfParticipants\": " + particNumber +",\n" +
                                    "    \"singlesCount\": "+ singlesCount +",\n" +
                                    "    \"albumsCount\": "+ albumsCount +",\n" +
                                    "    \"genre\": \""+ genre +"\",\n" +
                                    "    \"studio\": {\n" +
                                    "      \"name\": \""+ st_name +"\",\n" +
                                    "      \"address\": \""+ address +"\"\n" +
                                    "    }\n" +
                                    "  }");
                            if(musicBand != list.get(list.size() - 1)) {
                                writer.write(",");
                            }
                        }
                        writer.write("]");
                    } catch (IOException e) {
                        System.out.println("Output file error");
                    }
                    System.out.println("The list was saved to file");
                    break;
                case ("execute_script"):
                    try {
                        File script = new File(arg);
                        consoleProcess(new Scanner(script), list, cr_date);
                        Scanner c = new Scanner(System.in);
                        consoleProcess(c, list, cr_date);
                    } catch (FileNotFoundException e) {
                        System.out.println("No way to file");
                    } catch (NullPointerException e) {
                        System.out.println("Enter the way to file: ");
                    }
                    break;
                case ("exit"):
                    isExit = true;
                    break;
                case ("remove_last"):
                    list.remove(list.size() - 1);
                    System.out.println("The last band in list was deleted. ");
                    updateHistory(history, cmd);
                    break;
                case ("remove_greater"):
                    try {
                        Long id = new Long(arg);
                        boolean isId = false;
                        for(Iterator<MusicBand> it = list.iterator(); it.hasNext(); ) {
                            MusicBand musicBand = it.next();
                            if(isId) {
                                it.remove();
                            }
                            if (musicBand.getId() == id) {
                                isId = true;
                            }
                        }
                        System.out.println("All the bands higher than given ID were deleted ");
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format. Try again. ");
                    }
                    updateHistory(history, cmd);
                    break;
                case ("history"):
                    System.out.println("Here's the last" + history.size() + " commands.");
                    for(String s : history)
                    {
                        System.out.println("- " + s);
                    }
                    updateHistory(history, cmd);
                    break;
                case ("remove_any_by_genre"):
                    try{
                        for(Iterator<MusicBand> it = list.iterator(); it.hasNext(); ) {
                            MusicBand musicBand = it.next();
                            if(musicBand.getGenre().equals(arg)) {
                                it.remove();
                                System.out.println("The band was deleted by " + arg + " genre");
                                break;
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format. Try again.");
                    }
                    updateHistory(history, cmd);
                    break;
                case ("count_by_studio"):
                    int counter = 0;
                    try{
                        for (MusicBand musicBand : list) {
                            if (musicBand.getStudio().getName().equals(arg)) {
                                counter++;
                            }
                        }
                        System.out.println("Number of values is " + counter);
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format. Try again.");
                    }
                    updateHistory(history, cmd);
                    break;
                case ("print_field_descending_studio"):
                    HashMap<String, String> st = new HashMap<>();
                    try{
                        for (MusicBand musicBand : list) {
                            st.put(musicBand.getStudio().getName(),musicBand.getStudio().getAddress());
                        }
                        System.out.println("Output field values by th descending order: ");
                        st.entrySet().stream().sorted(Map.Entry.<String, String>comparingByKey().reversed()).forEach(System.out::println);
                    } catch (NumberFormatException e) {
                        System.out.println("Wrong format. Try again.");
                    }
                    updateHistory(history, cmd);
                    break;
                default:
                    System.out.println("There's no commands like this. Try \"help\" to see the list of the commands. ");
                    updateHistory(history, cmd);
            }
        }
    }

    /**
     *@return new music band
     */
    public static MusicBand addBand() {
        MusicBand mb = null;
        try {
            String name = "";
            Integer x = null;
            Double y = null;
            long numberOfParticipants = 0;
            long singlesCount = 0;
            Integer albumsCount = null;
            String genre = null;
            String st_name = null;
            String address = null;

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the band name: ");
            if(scanner.hasNext()) {
                name = scanner.nextLine();
            }
            System.out.print("Enter the x coordinate as Integer number: ");
            while (x==null)
            {
                if(scanner.hasNext()) {
                    try {
                        x = Integer.parseInt(scanner.nextLine());
                        if(x <= -994) {
                            x=null;
                            System.out.print("The number mustn't be lower than -994. Try again: ");
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the y coordinate as Real number: ");
            while (y == null)
            {
                if(scanner.hasNext()) {
                    try {
                        y = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the number of the participants: ");
            while(numberOfParticipants == 0) {
                if(scanner.hasNext()) {
                    try {
                        numberOfParticipants = Long.parseLong(scanner.nextLine());
                        if(numberOfParticipants < 0) {
                            System.out.print("The number mustn't be lower than 0. Try again: ");
                            numberOfParticipants = 0;
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the number of the band songs: ");
            while (singlesCount == 0) {
                if(scanner.hasNext()) {
                    try {
                        singlesCount = Long.parseLong(scanner.nextLine());
                        if(singlesCount < 0) {
                            System.out.print("The number mustn't be lower than 0. Try again: ");
                            singlesCount = 0;
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the number of the albums: ");
            while (true) {
                if(scanner.hasNext()) {
                    try {
                        albumsCount = Integer.parseInt(scanner.nextLine());
                        if (albumsCount < 0) {
                            System.out.print("The number mustn't be lower than 0. Try again: ");
                        }
                        else break;
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the genre(PROGRESSIVE_ROCK, PSYCHEDELIC_ROCK, POST_ROCK, POST_PUNK, BRIT_POP): ");
            while (genre==null) {
                if(scanner.hasNext()) {
                    try {
                        genre = scanner.nextLine();
                        if(!MusicBand.checkGenre(genre)) {
                            System.out.print("Wrong format. Try again: ");
                            genre = null;
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("Wrong format. Try again. ");
                    }
                }
            }
            System.out.print("Enter the studio name: ");
            if(scanner.hasNext()) {
                try {
                    st_name = scanner.nextLine();

                } catch (NumberFormatException e) {
                    System.out.print("Wrong format. Try again. ");
                }
            }
            System.out.print("Enter the studio address: ");
            if(scanner.hasNext()) {
                try {
                    address = scanner.nextLine();

                } catch (NumberFormatException e) {
                    System.out.print("Wrong format. Try again. ");
                }
            }

            Coordinates coordinates = new Coordinates(x, y);
            Studio studio = new Studio(st_name, address);
            java.util.Date date = null;
            Long id = null;
            MusicBand m = new MusicBand(id, name, coordinates, date, numberOfParticipants, singlesCount, albumsCount, genre, studio);
            mb = m;
        } catch (NoSuchElementException e) {
            System.exit(0);
        }
        return mb;
    }

    /**
     * This method creates the new random ID for the music band
     * @param list of music bands
     */
    public static void newId(ArrayList<MusicBand> list) {
        LinkedHashSet<Long> newId = new LinkedHashSet<>();
        for(MusicBand band : list) {
            if(!newId.add(band.getId())) {
                Long nextId = new Random().nextLong();
                band.setId(nextId);
            }
        }
    }

    /**
     * This method updates the list of commands in History
     * @param hist the history array
     * @param cmd the name of the command
     */
    public static void updateHistory(ArrayDeque<String> hist, String cmd) {
        hist.addFirst(cmd);
        if(hist.size() > 11) {
            hist.removeLast();
        }
    }

}

