package server;

import com.example.tyudy.ticket2rideclient.common.TTRGame;
import com.example.tyudy.ticket2rideclient.common.User;
import com.example.tyudy.ticket2rideclient.common.cities.City;
import com.example.tyudy.ticket2rideclient.common.cities.Path;

import java.util.ArrayList;

/**
 * Created by colefox on 4/1/17.
 */
public final class LongestRoadFinder
{
    public static User findLongestRoad(TTRGame game) {
        int longest = 0;
        User lUser = null;

        // for each user:
        // check for each claimed path to see if it is attached to only one other path (this is an endpoint)
        // for path in claimed paths
        // if either city is in original path
        // path is checked
        // update current length
        //    if current length > longest,
        //       update longest & lUser
        // check for that new path

        for (User u : game.getUsers()) {
            for (Path p : u.getClaimedPaths()) {
                if (isEndpoint (p, u.getClaimedPaths())) {
                    int userLongest = followPath(p, u.getClaimedPaths(), 0);
                    if (userLongest > longest) {
                        longest = userLongest;
                        lUser = u;
                    }
                    if (userLongest > u.getLongest()) {
                        u.setLongest(userLongest);
                    }
                }
            }
        }
        return lUser;
    }

    public static Boolean isEndpoint (Path path, ArrayList<Path> claimedPaths) {
        Boolean srcConnects = false;
        Boolean destConnects = false;
        City src = path.getCities().get(0);
        City dest = path.getCities().get(1);

        for (Path p : claimedPaths) {
            if (p.containsCity(src) && p.containsCity(dest)) {
                continue;
            }
            else if (p.containsCity(src)){
                srcConnects = true;
            } else if (p.containsCity(dest)) {
                destConnects = true;
            }
        }
        return !(srcConnects == destConnects);
    }

    public static int followPath(Path path, ArrayList<Path> claimedPaths, int curLength) {
        ArrayList<Path> newPaths = new ArrayList<>(claimedPaths);
        newPaths.remove(path);
        curLength += path.getDistance();
        int longest = curLength;
        for (Path p : newPaths) {
            if (p.containsCity(path.getCities().get(0)) ||
                    p.containsCity(path.getCities().get(1))) {
                int newLength = followPath(p, newPaths, curLength);
                if (newLength > longest) {
                    longest = newLength;
                }
            }
        }
        return longest;
    }
}
