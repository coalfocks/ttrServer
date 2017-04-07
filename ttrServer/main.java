import com.example.tyudy.ticket2rideclient.common.ColorENUM;
import com.example.tyudy.ticket2rideclient.common.DataTransferObject;
import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.cities.City;
import com.example.tyudy.ticket2rideclient.common.cities.Path;
import com.example.tyudy.ticket2rideclient.common.commands.SendChatCommand;
import server.*;
import server.Database.Database;

/**
 * Created by colefox on 2/9/17.
 */
public class
main
{
    /* add username to User data field,
       only list games not in progress,
       start game when users >= 5,
       can't join if game in progress
       method to get gameID (maybe)*/

    public static void main(String[] args)
    {
        ServerCommunicator server = new ServerCommunicator();
        server.run();

//        City Atlanta = new City("Atlanta", .707f, .477f);
//        City Boston = new City("Boston", .8941f, .1722f);
//        City Calgary = new City("Calgary", .2314f, .0260f);
//        City Charleston = new City("Charleston", .7838f, .4861f);
//        City Chicago = new City("Chicago", .6402f, .2787f);
//        City Dallas = new City("Dallas", .4589f, .5306f);
//        City Denver = new City("Denver", .3204f, .338f);
//        City Duluth = new City("Duluth", .5084f, .1796f);
//        City El_Paso = new City("El Paso", .3108f, .5416f);
//        City Helena = new City("Helena", .2917f, .1398f);
//        City Houston = new City("Houston", .4814f, .5898f);
//        City Kansas_City = new City("Kansas City", .4760f, .3476f);
//        City Las_Vegas = new City("Las Vegas", .1616f, .3935f);
//        City Little_Rock = new City("Little Rock", .5619f, .4666f);
//        City Los_Angeles = new City("Los Angeles", .0822f, .4269f);
//        City Miami = new City("Miami", .802f, .669f);
//        City Montreal = new City("Montreal", .8412f, .1065f);
//        City Nashville = new City("Nashville", .6644f, .4194f);
//        City New_Orleans = new City("New Orleans", .5743f, .5731f);
//        City New_York = new City("New York", .8648f, .2435f);
//        City Oklahoma_City = new City("Oklahoma City", .4561f, .4398f);
//        City Omaha = new City("Omaha", .4856f, .2744f);
//
//        City Phoenix = new City("Phoenix", .1859f, .4755f);
//        City Pittsburgh = new City("Pittsburgh", .7712f, .2809f);
//        City Portland = new City("Portland", .0512f, .1113f);
//        City Raleigh = new City("Raleigh", .7588f, .4162f);
//        City St_Louis = new City("St Louis", .5774f, .3633f);
//        City Salt_Lake = new City("Salt Lake", .2101f, .3142f);
//        City Santa_Fe = new City("Santa Fe", .2963f, .4180f);
//        City Sault_St_Marie = new City("Sault St Marie", .6810f, .1214f);
//        City Seattle = new City("Seattle", .0822f, .0622f);
//        City Toronto = new City("Toronto", .7278f, .1724f);
//        City Vancouver = new City("Vancouver", .1358f, .0204f);
//        City Washington_DC = new City("Washington DC", .8168f, .3235f);
//        City Winnipeg = new City("Winnipeg", .4349f, .0399f);
//        City San_Francisco = new City("San Francisco", .0355f, .2917f);
//
//
//        //Create all of the Paths NOTE: Their name is based on the two connected cities alphabetical order-------------------------------
//
//        Path Calgary_to_Vancouver = new Path(ColorENUM.COLORLESS, 3, Calgary, Vancouver, "Calgary_to_Vancouver");
//        Path Calgary_to_Winnipeg = new Path(ColorENUM.WHITE, 6, Calgary, Winnipeg, "Calgary_to_Winnipeg");
//        Path Calgary_to_Helena = new Path(ColorENUM.COLORLESS, 4, Calgary, Helena, "Calgary_to_Helena");
//        //Path Calgary_to_Seattle = new Path(Color.COLORLESS, 4, Calgary, Seattle);     NOT IMPLEMENTED IN OUR MAP
//        Path Dallas_to_ElPaso = new Path(ColorENUM.RED, 4, Dallas, El_Paso, "Dallas_to_ElPaso");
//        Path Dallas_to_OklahomaCity = new Path(ColorENUM.COLORLESS, 2, Dallas, Oklahoma_City, "Dallas_to_OklahomaCity");
//        Path Dallas_to_Houston = new Path(ColorENUM.COLORLESS, 1, Dallas, Houston, "Dallas_to_Houston");
//        Path Denver_to_Phoenix = new Path(ColorENUM.WHITE, 5, Denver, Phoenix, "Denver_to_Phoenix");
//        Path Denver_to_SaltLake = new Path(ColorENUM.YELLOW, 3, Denver, Salt_Lake, "Denver_to_SaltLake");
//        Path Denver_to_OklahomaCity = new Path(ColorENUM.RED, 4, Denver, Oklahoma_City, "Denver_to_OklahomaCity");
//        Path Denver_to_KansasCity = new Path(ColorENUM.BLACK, 4, Denver, Kansas_City, "Denver_to_KansasCity");
//        Path Denver_to_SantaFe = new Path(ColorENUM.COLORLESS, 2, Denver, Santa_Fe, "Denver_to_SantaFe");
//        Path Denver_to_Omaha = new Path(ColorENUM.PURPLE, 4, Denver, Omaha, "Denver_to_Omaha");
//        Path Denver_to_Helena = new Path(ColorENUM.GREEN, 4, Denver, Helena, "Denver_to_Helena");
//        Path Duluth_to_Helena = new Path(ColorENUM.ORANGE, 6, Duluth, Helena, "Duluth_to_Helena");
//        Path Duluth_to_Omaha = new Path(ColorENUM.COLORLESS, 2, Duluth, Omaha, "Duluth_to_Omaha");
//        Path ElPaso_Houston = new Path(ColorENUM.GREEN, 6, El_Paso, Houston, "ElPaso_Houston");
//        Path ElPaso_to_OklahomaCity = new Path(ColorENUM.YELLOW, 5, El_Paso, Oklahoma_City, "ElPaso_to_OklahomaCity");
//        Path ElPaso_to_SantaFe = new Path(ColorENUM.COLORLESS, 2, El_Paso, Santa_Fe, "ElPaso_to_SantaFe");
//        Path ElPaso_to_LosAngeles = new Path(ColorENUM.BLACK, 6, El_Paso, Los_Angeles, "ElPaso_to_LosAngeles");
//        Path ElPaso_to_Phoenix = new Path(ColorENUM.COLORLESS, 3, El_Paso, Phoenix, "ElPaso_to_Phoenix");
//        Path Helena_to_Seattle = new Path(ColorENUM.YELLOW, 6, Seattle, Helena, "Helena_to_Seattle");
//        Path Helena_to_Winnipeg = new Path(ColorENUM.BLACK, 4, Helena, Winnipeg, "Helena_to_Winnipeg");
//        Path Helena_to_SaltLake = new Path(ColorENUM.PURPLE, 3, Helena, Salt_Lake, "Helena_to_SaltLake");
//        Path Helena_to_Omaha = new Path(ColorENUM.RED, 5, Helena, Omaha, "Helena_to_Omaha");
//        Path KansasCity_to_Omaha = new Path(ColorENUM.COLORLESS, 1, Kansas_City, Omaha, "KansasCity_to_Omaha");
//        Path KansasCity_to_OklahomaCity = new Path(ColorENUM.COLORLESS, 2, Kansas_City, Oklahoma_City, "KansasCity_to_OklahomaCity");
//        Path LasVegas_to_LosAngeles = new Path(ColorENUM.COLORLESS, 2, Las_Vegas, Los_Angeles, "LasVegas_to_LosAngeles");
//        Path LasVegas_to_SaltLake = new Path(ColorENUM.ORANGE, 3, Las_Vegas, Salt_Lake, "LasVegas_to_SaltLake");
//        Path LosAngeles_to_SanFrancisco = new Path(ColorENUM.YELLOW, 3, Los_Angeles, San_Francisco, "LosAngeles_to_SanFrancisco");
//        Path LosAngeles_to_Phoenix = new Path(ColorENUM.COLORLESS, 3, Los_Angeles, Las_Vegas, "LosAngeles_to_Phoenix");
//        Path OklahomaCity_to_SantaFe = new Path(ColorENUM.BLUE, 3, Oklahoma_City, Santa_Fe, "OklahomaCity_to_SantaFe");
//        Path Phoenix_to_SantaFe = new Path(ColorENUM.COLORLESS, 3, Phoenix, Santa_Fe, "Phoenix_to_SantaFe");
//        Path Portland_to_Seattle = new Path(ColorENUM.COLORLESS, 1, Portland, Seattle, "Portland_to_Seattle");
//        Path Portland_to_SanFrancisco = new Path(ColorENUM.GREEN, 5, Portland, San_Francisco, "Portland_to_SanFrancisco");
//        Path Portland_to_SaltLake = new Path(ColorENUM.BLUE, 6, Portland, Salt_Lake, "Portland_to_SaltLake");
//        //Path SaltLake_to_SanFrancisco = new Path(Color.)      NOT IMPLEMENTED IN OUR MAP
//        Path Seattle_to_Vancouver = new Path(ColorENUM.COLORLESS, 1, Seattle, Vancouver, "Seattle_to_Vancouver");
//
//
//
//        Path Atlanta_to_Raleigh = new Path(ColorENUM.COLORLESS, 2, Atlanta, Raleigh, "Atlanta_to_Raleigh");
//        Path Atlanta_to_Charleston = new Path(ColorENUM.COLORLESS, 2, Atlanta, Charleston, "Atlanta_to_Charleston");
//        Path Atlanta_to_Nashville = new Path(ColorENUM.COLORLESS, 1, Atlanta, Nashville, "Atlanta_to_Nashville");
//        Path Atlanta_to_New_Orleans = new Path(ColorENUM.ORANGE, 4,   Atlanta,New_Orleans, "Atlanta_to_New_Orleans");
//        Path Atlanta_to_Miami = new Path(ColorENUM.BLUE, 5,   Atlanta, Miami, "Atlanta_to_Miami");
//        Path Boston_to_Montreal = new Path(ColorENUM.COLORLESS, 2, Boston, Montreal, "Boston_to_Montreal");
//        Path Boston_to_New_York = new Path(ColorENUM.RED, 2, Boston,  New_York, "Boston_to_New_York");
//        Path Charleston_to_Raleigh = new Path(ColorENUM.COLORLESS, 2, Charleston, Raleigh, "Charleston_to_Raleigh");
//        Path Charleston_to_Miami = new Path(ColorENUM.PURPLE, 4, Charleston, Miami, "Charleston_to_Miami");
//        Path Chicago_to_Toronto = new Path(ColorENUM.WHITE, 4, Chicago, Toronto, "Chicago_to_Toronto");
//        Path Chicago_to_Duluth = new Path(ColorENUM.RED, 3, Chicago, Duluth, "Chicago_to_Duluth");
//        Path Chicago_to_Omaha = new Path(ColorENUM.BLUE, 4, Chicago, Omaha, "Chicago_to_Omaha");
//        Path Chicago_to_St_Louis = new Path(ColorENUM.GREEN, 2, Chicago, St_Louis, "Chicago_to_St_Louis");
//        Path Chicago_to_Pittsburgh = new Path(ColorENUM.BLACK, 3, Chicago, Pittsburgh, "Chicago_to_Pittsburgh");
//        Path Dallas_to_Little_Rock = new Path(ColorENUM.COLORLESS, 2,  Dallas, Little_Rock, "Dallas_to_Little_Rock");
//        Path Duluth_to_Winnipeg = new Path(ColorENUM.BLACK,4, Duluth, Winnipeg, "Duluth_to_Winnipeg");
//        Path Duluth_to_Sault_St_Marie = new Path(ColorENUM.COLORLESS,3, Duluth, Sault_St_Marie, "Duluth_to_Sault_St_Marie");
//        Path Duluth_to_Toronto = new Path(ColorENUM.PURPLE, 6, Duluth, Toronto, "Duluth_to_Toronto");
//        Path Houston_to_New_Orleans = new Path(ColorENUM.COLORLESS, 2,   Houston, New_Orleans, "Houston_to_New_Orleans");
//        Path Kansas_City_to_St_Louis = new Path(ColorENUM.PURPLE, 2,  Kansas_City, St_Louis, "Kansas_City_to_St_Louis");
//        Path Little_Rock_to_Nashville = new Path(ColorENUM.WHITE, 3,  Little_Rock, Nashville, "Little_Rock_to_Nashville");
//        Path Little_Rock_to_New_Orleans = new Path(ColorENUM.GREEN, 3,  Little_Rock, New_Orleans, "Little_Rock_to_New_Orleans");
//        Path Little_Rock_to_St_Louis = new Path(ColorENUM.COLORLESS, 2,  Little_Rock, St_Louis, "Little_Rock_to_St_Louis");
//        Path Little_Rock_to_Oklahoma_City = new Path(ColorENUM.COLORLESS, 2,  Little_Rock, Oklahoma_City, "Little_Rock_to_Oklahoma_City");
//        Path Montreal_to_Sault_St_Marie = new Path(ColorENUM.BLACK, 5, Montreal, Sault_St_Marie, "Montreal_to_Sault_St_Marie");
//        Path Montreal_to_New_York = new Path(ColorENUM.BLUE, 3, Montreal, New_York, "Montreal_to_New_York");
//        Path Montreal_to_Toronto = new Path(ColorENUM.COLORLESS, 3, Montreal, Toronto, "Montreal_to_Toronto");
//        Path Nashville_to_Raleigh = new Path(ColorENUM.BLACK, 3,  Nashville, Raleigh, "Nashville_to_Raleigh");
//        Path Nashville_to_St_Louis = new Path(ColorENUM.COLORLESS, 2,  Nashville, St_Louis, "Nashville_to_St_Louis");
//        Path New_Orleans_to_Miami = new Path(ColorENUM.RED, 6,   New_Orleans, Miami, "New_Orleans_to_Miami");
//        Path New_York_to_Washington_DC = new Path(ColorENUM.ORANGE, 2, New_York, Washington_DC, "New_York_to_Washington_DC");
//        Path New_York_to_Pittsburgh = new Path(ColorENUM.GREEN, 2, New_York, Pittsburgh, "New_York_to_Pittsburgh");
//        Path Pittsburgh_to_Toronto = new Path(ColorENUM.COLORLESS, 2, Pittsburgh,Toronto, "Pittsburgh_to_Toronto");
//        Path Pittsburgh_to_St_Louis = new Path(ColorENUM.GREEN, 5, Pittsburgh,St_Louis, "Pittsburgh_to_St_Louis");
//        Path Pittsburgh_to_Nashville = new Path(ColorENUM.YELLOW, 4, Pittsburgh,Nashville, "Pittsburgh_to_Nashville");
//        Path Pittsburgh_to_Raleigh = new Path(ColorENUM.COLORLESS, 2, Pittsburgh,Raleigh, "Pittsburgh_to_Raleigh");
//        Path Raleigh_to_Washington = new Path(ColorENUM.COLORLESS, 2, Raleigh,Washington_DC, "Raleigh_to_Washington");
//        Path Sault_St_Marie_to_Winnipeg = new Path(ColorENUM.COLORLESS, 6, Sault_St_Marie , Winnipeg, "Sault_St_Marie_to_Winnipeg");
//        Path Toronto_to_Sault_St_Marie = new Path(ColorENUM.COLORLESS, 2, Toronto,Sault_St_Marie, "Toronto_to_Sault_St_Marie");
//
//
//        User amy = new User();
//        amy.setUsername("amy");
//        amy.setPlayerID(0);
//        amy.claimPath(Duluth_to_Helena);
//        amy.claimPath(Helena_to_SaltLake);
//        amy.claimPath(LasVegas_to_SaltLake);
//        //distance 12
//        amy.claimPath(Raleigh_to_Washington);
//        amy.claimPath(New_York_to_Washington_DC);
//        amy.claimPath(New_York_to_Pittsburgh);
//        amy.claimPath(Pittsburgh_to_Toronto);
//        amy.claimPath(Montreal_to_Toronto);
//        amy.claimPath(Montreal_to_Sault_St_Marie);
//        //distance 15?
//
//        User bob = new User();
//        bob.setUsername("bob");
//        bob.setPlayerID(1);
//        bob.claimPath(Pittsburgh_to_Nashville);
//        bob.claimPath(Nashville_to_St_Louis);
//        bob.claimPath(Pittsburgh_to_St_Louis);
//        bob.claimPath(Pittsburgh_to_Raleigh);
//        //distance 13
//
//        User cole = new User();
//        cole.setUsername("cole");
//        cole.setPlayerID(100);
//        cole.claimPath(Boston_to_Montreal);
//        cole.claimPath(Sault_St_Marie_to_Winnipeg);
//        cole.claimPath(Phoenix_to_SantaFe);
//
//
//        TTRGame game = new TTRGame();
//        game.addPlayer(amy);
//        game.addPlayer(bob);
//
//        User longest = LongestRoadFinder.findLongestRoad(game);
//        longest.addPoints(10);



        //server.stop();
    }
}
