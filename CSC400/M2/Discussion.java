package CSC400.M2;

import java.util.*;

public class Discussion {
    public static void main(String[] args) {
        String[] topFiveMovies = {"Dune: Part Two", "Kung Fu Panda 4", "Godzilla x Kong: The New Empire", "Ghostbusters: Frozen Empire", "Bob Marley: One Love"};
        System.out.println("Array contents: " + Arrays.toString(topFiveMovies));

        ArrayList<String> nowPlayingMovies = new ArrayList<>();
        for (String movie : topFiveMovies) {
            nowPlayingMovies.add(movie);
        }
        System.out.println("ArrayList size before removal: " + nowPlayingMovies.size());
        nowPlayingMovies.remove(0);
        System.out.println("ArrayList size after removal: " + nowPlayingMovies.size());
    }
}
 