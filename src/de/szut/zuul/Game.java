package de.szut.zuul;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.  Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * <p>
 * To play this game, create an instance of this class and call the "play"
 * method.
 * <p>
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game.  It also evaluates and
 * executes the commands that the parser returns.
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        this.player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room marketsquare, sorcererChamber, basement, templePyramid, tavern, sacrificialSite, hut, jungle, secretPassage, cave, beach;

        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");
        sorcererChamber = new Room("in the sorcerer chamber");
        basement = new Room("in the dark basement");

        // initialise room exits
        marketsquare.setExit("west", sacrificialSite);
        marketsquare.setExit("north", tavern);
        marketsquare.setExit("east", templePyramid);
        templePyramid.setExit("west", marketsquare);
        templePyramid.setExit("north", hut);
        templePyramid.setExit("up", sorcererChamber);
        templePyramid.setExit("down", basement);
        tavern.setExit("south", marketsquare);
        tavern.setExit("east", hut);
        sacrificialSite.setExit("east", marketsquare);
        sacrificialSite.setExit("down", cave);
        hut.setExit("west", tavern);
        hut.setExit("south", templePyramid);
        hut.setExit("east", jungle);
        jungle.setExit("west", hut);
        secretPassage.setExit("west", cave);
        secretPassage.setExit("east", basement);
        cave.setExit("up", sacrificialSite);
        cave.setExit("east", secretPassage);
        cave.setExit("south", beach);
        beach.setExit("north", cave);
        sorcererChamber.setExit("down", templePyramid);
        basement.setExit("up", templePyramid);
        sorcererChamber.setExit("window", marketsquare);

        Item bow = new Item("bow", "bow from wood", 0.5);
        Item treasure = new Item("treasure", "treasure chest with coins", 7.5);
        Item arrows = new Item("arrows", "a quiver with various arrows", 1.0);
        Item herb = new Item("herb", "healing herb", 0.5);
        Item cacao = new Item("cacao", "a tiny cacao tree", 5.0);
        Item knife = new Item("knife", "a big sharp knife", 1);
        Item spear = new Item("spear", "a spear with accompanying slingshot", 5.0);
        Item food = new Item("food", "a plate of hearty meat and maize porridge", 0.5);
        Item headdress = new Item("headdress", "a very pretty headdress", 1.0);

        marketsquare.putItem(bow);
        cave.putItem(treasure);
        sorcererChamber.putItem(arrows);
        jungle.putItem(herb);
        jungle.putItem(cacao);
        sacrificialSite.putItem(knife);
        hut.putItem(spear);
        tavern.putItem(food);
        cave.putItem(headdress);
        this.player.goTo(marketsquare);
    }

    /**
     * Main play routine.  Loops until end of play.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomInformation();
    }

    /**
     * Given a command, process (that is: execute) the command.
     *
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("take")) {
            takeItem(command);
        } else if (commandWord.equals("drop")) {
            dropItem(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("through the jungle. At once there is a glade. On it there a buildings...");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   " + parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = this.player.getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            this.player.goTo(nextRoom);
            printRoomInformation();
        }
    }

    private void printRoomInformation() {
        Player p = this.player;
        Room r = p.getCurrentRoom();
        System.out.println(r.getLongDescription());
    }

    private void look() {
        System.out.println(this.player.getCurrentRoom().getLongDescription());
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     *
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true;  // signal that we want to quit
        }
    }

    private void takeItem(Command command) {
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.player.getCurrentRoom().removeItem(itemName);
            if (item == null) {
                System.out.println("Item doesnt exist");
            } else {
                if (!this.player.takeItem(item)) {
                    this.player.getCurrentRoom().putItem(item);
                    System.out.println("Item too heavy");
                } else {
                    System.out.println(this.player.showStatus());
                    this.printRoomInformation();
                }

            }
        }
    }

    private void dropItem(Command command) {
        if (command.hasSecondWord()) {
            String itemName = command.getSecondWord();
            Item item = this.player.dropItem(itemName);
            if (item == null) {
                System.out.println("Item doesnt exist");
            } else {
                this.player.getCurrentRoom().putItem(item);
                System.out.println(this.player.showStatus());
                this.printRoomInformation();
            }
        }
    }
}

