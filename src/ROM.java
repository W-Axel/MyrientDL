public class ROM {
    String name;
    Region region;
    Version version;
    long size;

    public ROM(String fileName, String fileSize) {

        String[] sizeAndOrder = fileSize.split(" ");

        long multiplier = switch (sizeAndOrder[1]) {
            case "KiB" -> 1024;
            case "MiB" -> 1048576;
            case "GiB" -> 1073741824;
            default -> 1;
        };

        this.size = (long) (Double.parseDouble(sizeAndOrder[0]) * multiplier);
    }
}
