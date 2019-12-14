package railroad.support;
import java.util.List;

public class Config{
    private List<RawStation> stations;
    private List<RawTrain> trains;

    public void showStations(){
        for ( RawStation x:
                stations) {
            System.out.println(x.id);
            System.out.println(x.name);
            System.out.println(x.type);
        }
    }

    public void showTrains(){
        for ( RawTrain x : trains) {
            System.out.println(x.id);
            System.out.println(x.name);
            System.out.println(x.type);
            for (RawTrain.RawCarriage y: x.carriages) {
                System.out.println(y.amountOfContent);
                System.out.println(y.type);
            }
        }
    }

    public List<RawTrain> getRawTrains() {
        return trains;
    }

    public void setRawTrains(List<RawTrain> rawTrains) {
        this.trains = rawTrains;
    }


    public void setTrains(List<RawTrain> trains) {
        this.trains = trains;
    }

    public List<RawStation> getRawStations() {
        return stations;
    }

    public void setRawStations(List<RawStation> stations) {
        this.stations = stations;
    }

    public List<RawStation> getStations() {
        return stations;
    }

    public void setStations(List<RawStation> stations) {
        this.stations = stations;
    }

    public List<RawTrain> getTrains() {
        return trains;
    }


    public static class RawStation{
        private Integer id;
        private String name;
        private String type;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class RawTrain {
        private Integer id;
        private String name;
        private String type;
        private Integer power;

        private List<RawCarriage> carriages;
        private List<Integer> route;

        public Integer getPower() {
            return power;
        }

        public void setPower(Integer power) {
            this.power = power;
        }

        public static class RawCarriage{
            Integer amountOfContent;
            String type;

            public Integer getAmountOfContent() {
                return amountOfContent;
            }

            public void setAmountOfContent(Integer amountOfContent) {
                this.amountOfContent = amountOfContent;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<RawCarriage> getCarriages() {
            return carriages;
        }

        public void setCarriages(List<RawCarriage> carriages) {
            this.carriages = carriages;
        }

        public List<Integer> getRoute() {
            return route;
        }

        public void setRoute(List<Integer> route) {
            this.route = route;
        }
    }

}