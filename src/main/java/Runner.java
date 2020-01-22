import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.*;

public class Runner {
    public static void main(String[] args) throws IOException {
        RailRoadModel railRoadModel = new RailRoadModel();
        railRoadModel.start(args.toString());
    }
}

