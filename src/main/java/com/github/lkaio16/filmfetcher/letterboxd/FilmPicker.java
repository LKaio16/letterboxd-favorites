package com.github.lkaio16.filmfetcher.letterboxd;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilmPicker {

    /**
     * Retorna o HTML da página de perfil de um usuário no Letterboxd.
     * @param username O nome de usuário no Letterboxd.
     * @return O HTML da página de perfil.
     * @throws IOException Se ocorrer um erro na conexão.
     */
    public static String getProfileHtml(String username) throws IOException {
        String url = "https://letterboxd.com/" + username + "/";
        Document doc = Jsoup.connect(url).get();
        return doc.outerHtml();
    }

    /**
     * Extrai os filmes favoritos de um usuário no Letterboxd a partir do HTML do perfil.
     * @param html O HTML da página de perfil do usuário.
     * @return Lista de strings contendo os nomes dos filmes favoritos.
     */
    public static ArrayList<String> extractFavoriteMovies(String html) {
        Document doc = Jsoup.parse(html);
        Element metaDescription = doc.selectFirst("meta[name=description]");
        if (metaDescription != null) {
            String content = metaDescription.attr("content");
            String[] parts = content.split("Favorites: ");
            if (parts.length > 1) {
                String favoritesPart = parts[1].split("\\.")[0]; // Assume que termina com um ponto.
                Pattern p = Pattern.compile("(.+? \\(\\d{4}\\))(, |$)");
                Matcher m = p.matcher(favoritesPart);
                ArrayList<String> movies = new ArrayList<>();
                while (m.find()) {
                    movies.add(m.group(1));
                }
                return movies;
            }
        }
        return new ArrayList<>(); // Retorna uma lista vazia se não encontrar filmes.
    }

    public void getFavorites(String user) {
        try {
            String html = getProfileHtml(user);
            ArrayList<String> favoriteMovies = extractFavoriteMovies(html);
            System.out.println("Favorite Movies of " + user + ":");
            for (String movie : favoriteMovies) {
                System.out.println("- " + movie);
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
