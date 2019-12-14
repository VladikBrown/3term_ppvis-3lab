import railroad.support.Config;
import railroad.support.ConfigReader;

import java.io.*;

public class Runner {
    public static void main(String[] args) throws IOException {
        DispatcherCenter dispatcherCenter = DispatcherCenter.getRailRoad("rt");
        Config config = ConfigReader.read(DispatcherCenter.CONFIG_PATH);
        dispatcherCenter.applyConfigurationOfStations(config);

    }
}

/*
1) Добавить поля power и operationPeriod в config.yaml и Config.java +
2) Написать конструктор для поезда + пишеться
3) Продумать и написать систему времени перемещения поездов в зависимости от : +
  1. Мощности Локомотива
  2. Веса состава
  3. Длинны дороги
4) Написать метод "Считывания матрицы в RailRoad.java" +
5) МЯСО пишеться
 */