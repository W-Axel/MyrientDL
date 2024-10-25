import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static final String ROOT = "https://myrient.erista.me/files/";

    public static void main(String[] args) throws IOException {
        /*String url = "https://myrient.erista.me/files/No-Intro/Nintendo%20-%20Game%20Boy/";
        String[] nameSegments = url.split("/");
        String dirName = nameSegments[nameSegments.length - 1]
                .replaceAll("%[0-9]+", "_");
        var elements = findDLURLs(url);

        Region strRegion = Region.values()[JOptionPane.showOptionDialog(null, "Pick ur region", "Region", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Region.values(), Region.Any)];
        Region strRegiona = Region.values()[JOptionPane.showOptionDialog(null, "Pick ur region", "Region", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, Region.values(), Region.Any)];*/
        getSystem();
    }

    private static Document getSystem() throws IOException {
        var url = ROOT;
        var sources = Jsoup.connect(url).get().select("td.link > a[href]");
        Map<String, String> titles = sources.stream()
                .skip(1)
                .collect(Collectors.toMap(element -> element.getElementsByAttribute("title").getFirst().attr("title"),
                                          element -> element.getElementsByAttribute("href").getFirst().attr("href")));
        JList<String> list = new JList<>(titles.keySet().stream().sorted().toList().toArray(new String[0]));
        String selectedSource = requestSelection(list, "Pick ur source");
        url += titles.get(selectedSource);


        sources = Jsoup.connect(url).get().select("td.link > a[href]");
        titles = sources.stream()
                .skip(1)
                .collect(Collectors.toMap(element -> element.getElementsByAttribute("title").getFirst().attr("title"),
                                          element -> element.getElementsByAttribute("href").getFirst().attr("href")));
        list = new JList<>(titles.keySet().stream().sorted().toList().toArray(new String[0]));
        selectedSource = requestSelection(list, "Pick ur system");
        url += titles.get(selectedSource);


        sources = Jsoup.connect(url).get().select("td.link > a[href]");


        return null;
    }

    private static String requestSelection(JList<String> list, String message) {
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));

        JOptionPane.showOptionDialog(null, scrollPane, message, JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);
        //var selection = list.getSelectedIndex();

        return list.getSelectedValue();
    }

    private static Elements findDLURLs(String baseURL) throws IOException {
        Set<String> URLs = new HashSet<>();

        Document doc = Jsoup.connect(baseURL).get();

        String title = doc.title();

        return doc.getElementsByAttributeValueContaining("href", ".zip");
    }

}