package utils;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Random;

public class Tools {

    private static final StandardAnalyzer analyzer = new StandardAnalyzer();
    private static FSDirectory index = null;

    public static void indexCsv() {
        try {
            index = FSDirectory.open(Paths.get(Constant.repo + "/dico_index"));
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter w = new IndexWriter(index, config);

            for (String letter : Constant.alphabet) {
                indexLetter(letter, w);
            }

            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openIndex() {
        try {
            index = FSDirectory.open(Paths.get(Constant.repo + "/dico_index"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void indexLetter(String letter, IndexWriter w) throws IOException {
        FileReader fr = new FileReader("resources/dictionaries/" + letter + ".csv");
        BufferedReader br = new BufferedReader(fr);

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            String[] tokens = line.split(" ", 3);

            if (tokens.length > 2) {
                String name = tokens[0].replaceFirst("\"", "");
                if (name.charAt(0) == '"') {
                    System.out.println(name);
                }
                String type = tokens[1];
                String desc = tokens[2];
                if (type.charAt(type.length() - 1) != ')') {
                    String[] tokensBis = desc.split(" ", 2);
                    type = type + " " + tokensBis[0];
                    desc = tokensBis[1];
                }
                desc = desc.replace("\"", "");
                if (type.equals("(n.)") || type.equals("(a.)") || type.equals("(v.)") || type.equals("(v. t.)") || type.equals("(v. i.)") || type.equals("(prep.)") || type.equals("(adv.)")) {
                    if (!desc.split(" ")[0].equals("See")) {
                        Document doc = new Document();
                        doc.add(new TextField("Word", name, Field.Store.YES));
                        doc.add(new TextField("Definition", desc, Field.Store.YES));
                        w.addDocument(doc);
                    }
                }
            }
        }
        br.close();
    }

    public static Document random() throws IOException {
        IndexReader r = DirectoryReader.open(index);
        int randInt = new Random().nextInt(r.maxDoc());
        String word = r.document(randInt).get("Word");
        while (Constant.record.contains(word)) {
            randInt = new Random().nextInt(r.maxDoc());
            word = r.document(randInt).get("Word");
        }
        return r.document(randInt);
    }

    public static HashSet<Document> getDocuments(String word) {
        HashSet<Document> docs = new HashSet<>();
        try {
            Query q = new QueryParser("Word", analyzer).parse(word);
            IndexReader r = DirectoryReader.open(index);
            IndexSearcher s = new IndexSearcher(r);
            TopDocs tops = s.search(q,20);
            for (ScoreDoc hit : tops.scoreDocs) {
                docs.add(s.doc(hit.doc));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return docs;
    }

    public static void save() {
        File file = new File(Constant.repo + "/save.sav");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            for (String word : Constant.record.getRecord()) {
                fw.write(word + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fileFav = new File(Constant.repo + "/favs.sav");
        if (fileFav.exists()) {
            fileFav.delete();
        }
        try {
            fileFav.createNewFile();
            FileWriter fw = new FileWriter(fileFav);
            for (String word : Constant.record.getFav()) {
                fw.write(word + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() { 
        File file = new File(Constant.repo + "/save.sav");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            HashSet<String> record = Constant.record.getRecord();
            String str = br.readLine();
            while (str != null) {
                record.add(str);
                str = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        File fileFav = new File(Constant.repo + "/favs.sav");
        try {
            FileReader fr = new FileReader(fileFav);
            BufferedReader br = new BufferedReader(fr);
            HashSet<String> fav = Constant.record.getFav();
            String str = br.readLine();
            while (str != null) {
                fav.add(str);
                str = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
