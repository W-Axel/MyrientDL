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
        var sources = Jsoup.connect(ROOT).get().select("td.link > a[href]");

        List<String> sourcesTitles = sources.stream()
                .skip(1)
                .map(element -> element.getElementsByAttribute("title").getFirst().attr("title"))
                .toList();
        List<String> sourcesLinks = sources.stream()
                .skip(1)
                .map(element -> element.getElementsByAttribute("href").getFirst().attr("href"))
                .toList();

        var selection = JOptionPane.showOptionDialog(null, "Pick ur source", "Source", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                sourcesTitles.toArray(), sourcesTitles.getFirst());

        sources = Jsoup.connect(ROOT + sourcesLinks.get(selection)).get().select("td.link > a[href]");

        sourcesTitles = sources.stream()
                .skip(1)
                .map(element -> element.getElementsByAttribute("title").getFirst().attr("title"))
                .toList();
        sourcesLinks = sources.stream()
                .skip(1)
                .map(element -> element.getElementsByAttribute("href").getFirst().attr("href"))
                .toList();

        JList<String> list = new JList<>(sourcesTitles.toArray(new String[0]));
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setPreferredSize(new java.awt.Dimension(600, 400));

        selection = JOptionPane.showOptionDialog(null, scrollPane, "Pick ur system", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, null, null);

        if (selection == JOptionPane.OK_OPTION) {
            String selectedSource = list.getSelectedValue();
            System.out.println("Selected source: " + selectedSource);
        }
        return null;
    }

    private static Elements findDLURLs(String baseURL) throws IOException {
        Set<String> URLs = new HashSet<>();

        Document doc = Jsoup.connect(baseURL).get();

        String title = doc.title();

        return doc.getElementsByAttributeValueContaining("href", ".zip");
    }

}