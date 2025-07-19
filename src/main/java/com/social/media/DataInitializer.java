package com.social.media;

import com.social.media.models.Post;
import com.social.media.models.SocialGroup;
import com.social.media.models.SocialProfile;
import com.social.media.models.SocialUser;
import com.social.media.repositories.PostRepository;
import com.social.media.repositories.SocialGroupRepository;
import com.social.media.repositories.SocialProfileRepository;
import com.social.media.repositories.SocialUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private final SocialUserRepository userRepository;
    private final SocialGroupRepository groupRepository;
    private final SocialProfileRepository socialProfileRepository;
    private final PostRepository postRepository;

    public DataInitializer(SocialUserRepository userRepository, SocialGroupRepository groupRepository, SocialProfileRepository socialProfileRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.socialProfileRepository = socialProfileRepository;
        this.postRepository = postRepository;
    }

    @Bean
    public CommandLineRunner initializeData(){
        return args -> {
            // Create some users
            SocialUser user1 = new SocialUser();
            SocialUser user2 = new SocialUser();
            SocialUser user3 = new SocialUser();

            //save user to the database
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            // create some groups
            SocialGroup group1 = new SocialGroup();
            SocialGroup group2 = new SocialGroup();

            // add users to groups
            group1.getSocialUsers().add(user1);
            group1.getSocialUsers().add(user2);

            group2.getSocialUsers().add(user2);
            group2.getSocialUsers().add(user3);

            // save groups to database
            groupRepository.save(group1);
            groupRepository.save(group2);

            // Associate users with groups
            user1.getGroups().add(group1);
            user2.getGroups().add(group1);
            user2.getGroups().add(group2);
            user3.getGroups().add(group2);

            //save users back to database to update associations
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);


            // create some posts
            Post post1 = new Post();
            Post post2 = new Post();
            Post post3 = new Post();

            //associate posts with users;
            post1.setSocialUser(user1);
            post2.setSocialUser(user2);
            post3.setSocialUser(user3);

            //save posts to the database (assuming you have post repository)
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);

            //create some social profiles
            SocialProfile profile1 = new SocialProfile();
            SocialProfile profile2 = new SocialProfile();
            SocialProfile profile3 = new SocialProfile();

            // Associate profiles with users
            profile1.setUser(user1);
            profile2.setUser(user2);
            profile3.setUser(user3);

            //save profile to the database (assuming you have SocialProfileRepository)
            socialProfileRepository.save(profile1);
            socialProfileRepository.save(profile2);
            socialProfileRepository.save(profile3);
        };

    }
}
