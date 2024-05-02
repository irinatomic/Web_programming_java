package org.example.repositories;

import org.example.entities.Comment;
import org.example.entities.Post;

import javax.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Singleton
public class InMemoryPostRepository implements PostRepository {

    private static List<Post> posts = new CopyOnWriteArrayList<>();
    private static Long nextPostId = 0L;
    private static Long nextCommentId = 0L;

    public InMemoryPostRepository() {
        initialisePosts();
        System.out.println("InMemoryPostRepository: " + this);
    }

    @Override
    public Post addPost(Post post) {
        int listIndex = Math.toIntExact(nextPostId++);
        post.setId(listIndex);
        post.setDate(LocalDate.now().toString());
        post.setComments(new ArrayList<>());
        posts.add(listIndex, post);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }

    @Override
    public Post getPostById(Integer id) {
        return posts.get(id);
    }

    @Override
    public Comment addComment(Integer postId, Comment comment) {
        int listIndex = Math.toIntExact(nextCommentId++);
        comment.setId(listIndex);
        posts.get(postId).addComment(comment);
        return comment;
    }

    private void initialisePosts() {
        Post post1 = new Post("John Doe", "Excited about My First Adventure", "Today marks the beginning of my journey into the world of adventure! I've always dreamed of exploring distant lands, meeting new people, and experiencing different cultures. With my backpack ready and my heart full of excitement, I'm stepping into the unknown. Follow along as I share my adventures, mishaps, and unforgettable moments with you all!");
        Post post2 = new Post("Jane Doe", "Reflecting on a Serene Morning", "As the sun rises and paints the sky with hues of orange and pink, I find myself immersed in the tranquility of dawn. The world awakens with a gentle whisper, and I take a moment to appreciate the beauty that surrounds me. In these quiet moments, I find solace and inspiration. Let's embrace the serenity of mornings and cherish the simple joys life has to offer.");

        String author3 = "Djordje Cvarkov";
        String title3 = "Pustite korisnike, pretnja pocinje u lancu isporuke softvera";
        String content3 = "lako se svaka velika i mala korporativna mreza trudi da primeni bezbednosne politike kako bi osigurala da korisnici mogu da rade samo sa legitimnim aplikacijama" +
                "i uslugama podataka - kada je ugrozen sam lanac isporuke osnovnog softvera, tada i same pretje dobijaju novi nivo legitimiteta. Korisnici su cesto problematini, ali pretje mogu da ponu u samom kanalu. " +
                "Korisnici su nepromislieni, to zna svaki softverski inzenjer. Mozda korisnici nigde nisu tako nepromisjeni kao u delovima " +
                "korporativne mree gde provale bezbednosti mogu da se otvore kontaktom sa zlonamernim softverom, ili kada se nehotice izloze sistemske ranjivosti. " +
                "Kada koriste sopstveni uredaj (BYOD - Bring Your Own Device), pa cak i kada koriste odobrene i naizgled zakljucane korporativne uredaje, cini se da korisnici imaju bogomdanu sposobnost\n" +
                "otvaranja nepotrebnih makronaredbi u dokumentima, kliknu na zlonamerne linkove i zaobilaze " +
                "smernica kompanije instaliranjem „IT-a u senci\" ili da zaista odu u grad i ponu da prikljucuju spoline USB diskove u svaki dostupan ovor koji mogu da pronadu.";
        Post post3 = new Post(author3, title3, content3);

        post1.setId(0);
        post2.setId(1);
        post3.setId(2);

        posts.add(0, post1);
        posts.add(1, post2);
        posts.add(2, post3);
        nextPostId = 3L;
    }
}
